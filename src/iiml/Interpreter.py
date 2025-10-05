import numpy as np
from antlr4 import *
from IIMLParser import IIMLParser
from IIMLVisitor import IIMLVisitor

class Interpreter(IIMLVisitor):
    def __init__(self):
        super().__init__()
        self.variables = {}
        self.current_image = None

    def visitProgram(self, ctx:IIMLParser.ProgramContext):
        for statement in ctx.statement():
            self.visit(statement)
        # Return as float32 in [0,1] range to match main IML image format
        return self.current_image

    def visitInstantiate(self, ctx:IIMLParser.InstantiateContext):
        return self.visit(ctx.instantiateStmt())

    def visitPlace(self, ctx:IIMLParser.PlaceContext):
        return self.visit(ctx.placeStmt())

    def visitDecl(self, ctx:IIMLParser.DeclContext):
        return self.visit(ctx.declStmt())

    def visitAssign(self, ctx:IIMLParser.AssignContext):
        return self.visit(ctx.assignStmt())

    def visitFor(self, ctx:IIMLParser.ForContext):
        return self.visit(ctx.forStmt())

    def visitInstantiateStmt(self, ctx:IIMLParser.InstantiateStmtContext):
        type_name = ctx.type_().getText()
        size_info = self.visit(ctx.size())
        background = float(ctx.NUM().getText())

        if type_name == "image":
            if isinstance(size_info, tuple):
                width, height = size_info
                self.current_image = np.full((int(height), int(width)), background, dtype=np.float32)
            else:
                size = int(size_info * 2)
                self.current_image = np.full((size, size), background, dtype=np.float32)

        return self.current_image

    def visitPlaceStmt(self, ctx:IIMLParser.PlaceStmtContext):
        shape_name = ctx.shape().getText()
        size_info = self.visit(ctx.sizeOrDimensions())
        x = float(self.visit(ctx.expr(0)))
        y = float(self.visit(ctx.expr(1)))
        intensity = self.visit(ctx.expr(2))

        if self.current_image is None:
            raise Exception("No image instantiated")

        height, width = self.current_image.shape

        if shape_name == "circle":
            radius = float(size_info)
            self._draw_circle(x, y, radius, intensity)
        elif shape_name == "rect":
            w, h = size_info if isinstance(size_info, tuple) else (size_info, size_info)
            self._draw_rectangle(x, y, float(w), float(h), intensity)
        elif shape_name == "cross":
            size = float(size_info) if not isinstance(size_info, tuple) else float(max(size_info))
            self._draw_cross(x, y, size, intensity)
        elif shape_name == "plus":
            size = float(size_info) if not isinstance(size_info, tuple) else float(max(size_info))
            self._draw_plus(x, y, size, intensity)

    def visitForStmt(self, ctx:IIMLParser.ForStmtContext):
        var_name = ctx.ID().getText()
        iterable = self.visit(ctx.expr())
        
        if not isinstance(iterable, list):
            raise Exception(f"Cannot iterate over non-list: {type(iterable)}")
        
        # Store original variable value if it exists
        original_value = self.variables.get(var_name)
        
        try:
            for item in iterable:
                self.variables[var_name] = item
                # Execute all statements in the loop body
                if ctx.statement():
                    for statement in ctx.statement():
                        self.visit(statement)
        finally:
            # Restore original variable value
            if original_value is not None:
                self.variables[var_name] = original_value
            elif var_name in self.variables:
                del self.variables[var_name]

    def visitDeclStmt(self, ctx:IIMLParser.DeclStmtContext):
        var_name = ctx.ID().getText()
        value = self.visit(ctx.expr())
        self.variables[var_name] = value
        return value

    def visitAssignStmt(self, ctx:IIMLParser.AssignStmtContext):
        var_name = ctx.ID().getText()
        value = self.visit(ctx.expr())
        self.variables[var_name] = value
        return value

    def visitType(self, ctx:IIMLParser.TypeContext):
        return ctx.getText()

    def visitShape(self, ctx:IIMLParser.ShapeContext):
        return ctx.getText()

    def visitSizeBy(self, ctx:IIMLParser.SizeByContext):
        width = self.visit(ctx.expr(0))
        height = self.visit(ctx.expr(1))
        return (width, height)

    def visitRadius(self, ctx:IIMLParser.RadiusContext):
        return self.visit(ctx.expr())

    def visitSizeSpec(self, ctx:IIMLParser.SizeSpecContext):
        return self.visit(ctx.size())

    def visitDimensions(self, ctx:IIMLParser.DimensionsContext):
        width = self.visit(ctx.expr(0))
        height = self.visit(ctx.expr(1))
        return (width, height)

    def visitRadiusSpec(self, ctx:IIMLParser.RadiusSpecContext):
        return self.visit(ctx.expr())

    def visitMulExpr(self, ctx:IIMLParser.MulExprContext):
        left = self.visit(ctx.expr(0))
        right = self.visit(ctx.expr(1))
        return left * right

    def visitValueExpr(self, ctx:IIMLParser.ValueExprContext):
        return self.visit(ctx.value())

    def visitDivExpr(self, ctx:IIMLParser.DivExprContext):
        left = self.visit(ctx.expr(0))
        right = self.visit(ctx.expr(1))
        return left / right

    def visitReadExpr(self, ctx:IIMLParser.ReadExprContext):
        prompt = ctx.STRING().getText().strip('"')
        value = input(prompt)
        return value

    def visitSubExpr(self, ctx:IIMLParser.SubExprContext):
        left = self.visit(ctx.expr(0))
        right = self.visit(ctx.expr(1))
        return left - right

    def visitPowExpr(self, ctx:IIMLParser.PowExprContext):
        left = self.visit(ctx.expr(0))
        right = self.visit(ctx.expr(1))
        return left ** right

    def visitAddExpr(self, ctx:IIMLParser.AddExprContext):
        left = self.visit(ctx.expr(0))
        right = self.visit(ctx.expr(1))
        return left + right

    def visitModExpr(self, ctx:IIMLParser.ModExprContext):
        left = self.visit(ctx.expr(0))
        right = self.visit(ctx.expr(1))
        return left % right

    def visitFuncCallExpr(self, ctx:IIMLParser.FuncCallExprContext):
        func_name = ctx.getChild(0).getText()

        if func_name == "number":
            if ctx.expr():
                arg = self.visit(ctx.expr(0))
                if isinstance(arg, str):
                    try:
                        return float(arg) if '.' in arg else int(arg)
                    except ValueError:
                        return 0
                return float(arg)
            return 0

        raise Exception(f"Unknown function: {func_name}")

    def visitListLiteral(self, ctx:IIMLParser.ListLiteralContext):
        """Visit list literal: [1, 2, 3] or [[1,2], [3,4]]"""
        if ctx.expr():
            return [self.visit(expr) for expr in ctx.expr()]
        return []

    def visitIndexExpr(self, ctx:IIMLParser.IndexExprContext):
        """Visit list indexing: list[index]"""
        list_expr = self.visit(ctx.expr(0))
        index_expr = self.visit(ctx.expr(1))
        
        if not isinstance(list_expr, list):
            raise Exception(f"Cannot index non-list type: {type(list_expr)}")
        
        index = int(index_expr)
        if index < 0 or index >= len(list_expr):
            raise Exception(f"List index {index} out of bounds for list of length {len(list_expr)}")
        
        return list_expr[index]

    def visitValue(self, ctx:IIMLParser.ValueContext):
        if ctx.ID():
            var_name = ctx.ID().getText()
            if var_name in self.variables:
                return self.variables[var_name]
            else:
                raise Exception(f"Variable {var_name} not declared")
        elif ctx.NUM():
            num_text = ctx.NUM().getText()
            result = float(num_text)
            return result
        elif ctx.STRING():
            return ctx.STRING().getText().strip('"')

        return None

    def _draw_circle(self, cx, cy, radius, intensity):
        """Draw circle with optimized bounding box calculation."""
        if self.current_image is None:
            raise ValueError("No image initialized")
            
        height, width = self.current_image.shape
        
        # Calculate bounding box to limit computation area
        x_min = max(0, int(cx - radius - 1))
        x_max = min(width, int(cx + radius + 2))
        y_min = max(0, int(cy - radius - 1))
        y_max = min(height, int(cy + radius + 2))
        
        # Only process pixels within bounding box
        if x_min < x_max and y_min < y_max:
            y_slice, x_slice = np.ogrid[y_min:y_max, x_min:x_max]
            mask = (x_slice - cx) ** 2 + (y_slice - cy) ** 2 <= radius ** 2
            self.current_image[y_min:y_max, x_min:x_max][mask] = intensity

    def _draw_rectangle(self, cx, cy, w, h, intensity):
        height, width = self.current_image.shape
        x1, y1 = max(0, int(cx - w/2)), max(0, int(cy - h/2))
        x2, y2 = min(width, int(cx + w/2) + 1), min(height, int(cy + h/2) + 1)
        self.current_image[y1:y2, x1:x2] = intensity

    def _draw_cross(self, cx, cy, size, intensity):
        height, width = self.current_image.shape
        x = int(cx)
        y1, y2 = max(0, int(cy - size/2)), min(height, int(cy + size/2) + 1)
        if 0 <= x < width:
            self.current_image[y1:y2, x] = intensity
        y = int(cy)
        x1, x2 = max(0, int(cx - size/2)), min(width, int(cx + size/2) + 1)
        if 0 <= y < height:
            self.current_image[y, x1:x2] = intensity

    def _draw_plus(self, cx, cy, size, intensity):
        """Draw diagonal plus pattern with vectorized operations."""
        if self.current_image is None:
            raise ValueError("No image initialized")
            
        height, width = self.current_image.shape
        half_size = size / 2
        
        # Create coordinate arrays for diagonal lines
        offsets = np.arange(-half_size, half_size + 1)
        
        # Main diagonal coordinates
        x1_coords = (cx + offsets).astype(int)
        y1_coords = (cy + offsets).astype(int)
        
        # Anti-diagonal coordinates  
        x2_coords = (cx - offsets).astype(int)
        y2_coords = (cy + offsets).astype(int)
        
        # Filter valid coordinates and set pixels
        valid1 = (x1_coords >= 0) & (x1_coords < width) & (y1_coords >= 0) & (y1_coords < height)
        valid2 = (x2_coords >= 0) & (x2_coords < width) & (y2_coords >= 0) & (y2_coords < height)
        
        self.current_image[y1_coords[valid1], x1_coords[valid1]] = intensity
        self.current_image[y2_coords[valid2], x2_coords[valid2]] = intensity
