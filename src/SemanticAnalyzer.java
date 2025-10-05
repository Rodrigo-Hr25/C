import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import error.ErrorHandling;

@SuppressWarnings("CheckReturnValue")
public class SemanticAnalyzer extends IMLBaseVisitor<VariableTypes> {
   private final HashMap<String, VariableTypes> variablesMap;
   private final Set<String> reservedWords;

   public SemanticAnalyzer() {
      super();
      this.variablesMap = new HashMap<>();
      this.reservedWords = new HashSet<>(Arrays.asList(
         "until", "while", "for", "do", "done",
         "if", "else", "then",
         "not", "and", "or", "any", "all",
         "list", "of", "is", "in", "within", "by",
         "append", "insert", "remove", "replace",
         "store", "into", "load", "read", "run",
         "output", "draw", "count", "from",
         "columns", "rows", "pixel",
         "string", "number", "percentage", "image",
         "erode", "dilate", "open", "close", "tophat", "blackhat"
      ));
   }

   public HashMap<String, VariableTypes> getVariablesMap() {
      return variablesMap;
   }

   private VariableTypes checkVariableExists(org.antlr.v4.runtime.ParserRuleContext ctx, String varName) {
      if (!variablesMap.containsKey(varName)) {
         ErrorHandling.printError(ctx, "Variable not declared: " + varName);
         return VariableTypes.ERROR;
      }
      return variablesMap.get(varName);
   }

   private VariableTypes validateMorphOperation(org.antlr.v4.runtime.ParserRuleContext ctx, 
                                                VariableTypes leftType, VariableTypes rightType, 
                                                String operation) {
      if (leftType == VariableTypes.ERROR || rightType == VariableTypes.ERROR) {
         return VariableTypes.ERROR;
      }

      // For pixel-wise operations (.*, .+, .-), allow IMAGE with numeric types or vice versa
      boolean leftIsImage = (leftType == VariableTypes.IMAGE || leftType == VariableTypes.PIXEL);
      boolean rightIsImage = (rightType == VariableTypes.IMAGE || rightType == VariableTypes.PIXEL);
      boolean leftIsNumeric = leftType.isNumeric();
      boolean rightIsNumeric = rightType.isNumeric();
      
      // For morphological operations, also allow matrices (list of list of number) as kernels
      String rightTypeStr = rightType.toString();
      boolean rightIsMatrix = (rightType == VariableTypes.LIST_NUMBER || 
                              rightTypeStr.contains("LIST") && rightTypeStr.contains("NUMBER"));

      if ((leftIsImage && rightIsNumeric) || (leftIsNumeric && rightIsImage) || 
          (leftIsImage && rightIsImage) || (leftIsImage && rightIsMatrix)) {
         return VariableTypes.IMAGE; // Result is always an image
      }

      ErrorHandling.printError(ctx, operation + " operation requires an IMAGE and numeric/matrix type, but found: " + leftType + " and " + rightType);
      return VariableTypes.ERROR;
   }

   private VariableTypes validatePixelComparison(org.antlr.v4.runtime.ParserRuleContext ctx, 
                                                  String varName, String op, VariableTypes exprType) {
      VariableTypes varType = checkVariableExists(ctx, varName);
      if (varType == VariableTypes.ERROR) {
         return VariableTypes.ERROR;
      }

      if (varType != VariableTypes.IMAGE) {
         ErrorHandling.printError(ctx, "Variable is not an image: " + varName);
         return VariableTypes.ERROR;
      }

      if (op == null) {
         ErrorHandling.printError(ctx, "Comparison operation requires a valid operator, but found: " + op);
         return VariableTypes.ERROR;
      }

      if (!exprType.isNumeric()) {
         ErrorHandling.printError(ctx, "Comparison operation requires a numeric type, but found: " + exprType);
         return VariableTypes.ERROR;
      }

      return VariableTypes.BOOLEAN;
   }

   @Override public VariableTypes visitDeclStmt(IMLParser.DeclStmtContext ctx) {
      String baseType = ctx.type().getText();
      int listDepth = 0;
      
      // Count list nesting depth
      for (int i = 0; i < ctx.getChildCount(); i++) {
         if (ctx.getChild(i).getText().equals("list")) {
            listDepth++;
         }
      }
      
      // Construct type string based on nesting
      StringBuilder typeBuilder = new StringBuilder();
      for (int i = 0; i < listDepth; i++) {
         typeBuilder.append("list_");
      }
      typeBuilder.append(baseType);
      
      String typeStr = typeBuilder.toString();
      VariableTypes type = VariableTypes.fromString(typeStr);
      if (type == VariableTypes.ERROR) {
         ErrorHandling.printError(ctx, "Invalid type: " + typeStr);
      }

      String varName = ctx.ID().getText();
      if (variablesMap.containsKey(varName)) {
         ErrorHandling.printError(ctx, "Variable already declared: " + varName);
      }

      if (ctx.expr() != null) {
         VariableTypes exprType = visit(ctx.expr());
         // Allow LIST_EMPTY to be assigned to any list type
         if (exprType != type && !(exprType == VariableTypes.LIST_EMPTY && type.toString().startsWith("LIST_"))) {
            ErrorHandling.printError(ctx, "Type mismatch: expected " + type + ", but found " + exprType);
         }
      }

      if (reservedWords.contains(varName)) {
         ErrorHandling.printError(ctx, "Variable name cannot be a reserved word: " + varName);
      }

      variablesMap.put(varName, type);

      return VariableTypes.VOID;
   }

   @Override public VariableTypes visitAssignStmt(IMLParser.AssignStmtContext ctx) {
      if (!variablesMap.containsKey(ctx.ID().getText())) {
         ErrorHandling.printError(ctx, "Variable not declared: " + ctx.ID().getText());
      }
      return VariableTypes.VOID;
   }

   @Override public VariableTypes visitListEditStmt(IMLParser.ListEditStmtContext ctx) {
      String listName = ctx.ID().getText();

      if (!variablesMap.containsKey(listName)) {
         ErrorHandling.printError(ctx, "Variable not declared: " + listName);
      }

      VariableTypes listType = variablesMap.get(listName);
      String listTypeName = listType.toString();
      if (!listTypeName.startsWith("LIST_")) {
         ErrorHandling.printError(ctx, "Variable is not a list: " + listName);
      }

      return VariableTypes.VOID;
   }

   @Override public VariableTypes visitStoreStmt(IMLParser.StoreStmtContext ctx) {
      String id = ctx.ID().getText();

      if (!variablesMap.containsKey(id)) {
         ErrorHandling.printError(ctx, "Variable not declared: " + id);
      }

      VariableTypes idType = variablesMap.get(id);
      if (idType != VariableTypes.IMAGE && idType != VariableTypes.LIST_IMAGE) {
         ErrorHandling.printError(ctx, "Variable is not an image or list of images: " + id);
      }

      return VariableTypes.VOID;
   }

   @Override public VariableTypes visitOutputStmt(IMLParser.OutputStmtContext ctx) {
      return VariableTypes.VOID;
   }

   @Override public VariableTypes visitDrawStmt(IMLParser.DrawStmtContext ctx) {
      String id = ctx.ID().getText();

      if (!variablesMap.containsKey(id)) {
         ErrorHandling.printError(ctx, "Variable not declared: " + id);
      }

      VariableTypes idType = variablesMap.get(id);
      if (idType != VariableTypes.IMAGE) {
         ErrorHandling.printError(ctx, "Variable is not an image: " + id);
      }

      return VariableTypes.VOID;
   }

   @Override public VariableTypes visitIfStmt(IMLParser.IfStmtContext ctx) {
      for (IMLParser.CondExprContext condExpr : ctx.condExpr()) {
         VariableTypes condType = visit(condExpr);
         if (condType != VariableTypes.BOOLEAN) {
            ErrorHandling.printError(ctx, "Condition must be of type BOOLEAN, but found: " + condType);
         }
      }

      return VariableTypes.VOID;
   }

   @Override public VariableTypes visitForLoop(IMLParser.ForLoopContext ctx) {
      String iteratedId = ctx.ID(0).getText();
      String listId = ctx.ID(1).getText();

      if (variablesMap.containsKey(iteratedId)) {
         ErrorHandling.printError(ctx, "Variable already declared: " + iteratedId);
      }

      if (!variablesMap.containsKey(listId)) {
         ErrorHandling.printError(ctx, "Variable not declared: " + listId);
      }

      VariableTypes listType = variablesMap.get(listId);
      String listTypeName = listType.toString();
      if (!listTypeName.startsWith("LIST_")) {
         ErrorHandling.printError(ctx, "Variable is not a list: " + listId);
      }

      String listTypeString = listType.toString().toLowerCase();
      VariableTypes elementType = VariableTypes.getElementType(listTypeString);

      VariableTypes expectedType = VariableTypes.fromString(ctx.type().getText());
      if (elementType != expectedType) {
         ErrorHandling.printError(ctx, "Type mismatch: expected " + expectedType + ", but found " + elementType);
      }

      return VariableTypes.VOID;
   }

   @Override public VariableTypes visitUntilLoop(IMLParser.UntilLoopContext ctx) {
      VariableTypes condType = visit(ctx.condExpr());
      if (condType != VariableTypes.BOOLEAN) {
         ErrorHandling.printError(ctx, "Condition must be of type BOOLEAN, but found: " + condType);
      }

      return VariableTypes.VOID;
   }

   @Override public VariableTypes visitArithExpr(IMLParser.ArithExprContext ctx) {
      VariableTypes leftType = visit(ctx.expr(0));
      VariableTypes rightType = visit(ctx.expr(1));
      String op = ctx.op.getText();

      if (leftType == VariableTypes.ERROR || rightType == VariableTypes.ERROR) {
         return VariableTypes.ERROR;
      }

      if (leftType != rightType) {
         ErrorHandling.printError(ctx, "Arithmetic operation requires same types, but found: " + leftType + " and " + rightType);
         return VariableTypes.ERROR;
      }

      if (!leftType.isNumeric()) {
         ErrorHandling.printError(ctx, "Arithmetic operation requires a numeric type, but found: " + leftType);
         return VariableTypes.ERROR;
      }
      return leftType;
   }

   @Override public VariableTypes visitValueExpr(IMLParser.ValueExprContext ctx) {
      IMLParser.ValueContext valueCtx = ctx.value();

      if (valueCtx.ID() != null) {
         String varName = valueCtx.ID().getText();
         if (!variablesMap.containsKey(varName)) {
            ErrorHandling.printError(ctx, "Undeclared variable: " + varName);
            return VariableTypes.ERROR;
         }
         return variablesMap.get(varName);
      }

      if (valueCtx.NUM() != null) return VariableTypes.NUMBER;
      if (valueCtx.PERCENT() != null) return VariableTypes.PERCENTAGE;
      if (valueCtx.STRING() != null) return VariableTypes.STRING;

      ErrorHandling.printError(ctx, "Unrecognized value expression.");
      return VariableTypes.ERROR;
   }

   @Override public VariableTypes visitCastExpr(IMLParser.CastExprContext ctx) {
      VariableTypes exprType = visit(ctx.expr());
      String targetType = ctx.type().getText();
      
      switch (targetType) {
         case "string":
            return VariableTypes.STRING;
         case "number":
            return VariableTypes.NUMBER;
         case "percentage":
            return VariableTypes.PERCENTAGE;
         case "image":
            return VariableTypes.IMAGE;
         default:
            ErrorHandling.printError(ctx, "Unknown target type for cast: " + targetType);
            return VariableTypes.ERROR;
      }
   }

   @Override public VariableTypes visitCountPixelExpr(IMLParser.CountPixelExprContext ctx) {
      VariableTypes exprType = visit(ctx.expr());
      if (exprType != VariableTypes.NUMBER) {
         ErrorHandling.printError(ctx, "Count operation requires a number, but found: " + exprType);
         return VariableTypes.ERROR;
      }

      String id = ctx.ID().getText();
      if (!variablesMap.containsKey(id)) {
         ErrorHandling.printError(ctx, "Variable not declared: " + id);
         return VariableTypes.ERROR;
      }

      if (variablesMap.get(id) != VariableTypes.IMAGE) {
         ErrorHandling.printError(ctx, "Variable is not a image: " + id);
         return VariableTypes.ERROR;
      }

      return VariableTypes.NUMBER;
   }

   @Override public VariableTypes visitScaleExpr(IMLParser.ScaleExprContext ctx) {
      VariableTypes leftType = visit(ctx.expr(0));
      VariableTypes rightType = visit(ctx.expr(1));

      String op = ctx.op.getText();

      if (leftType == VariableTypes.ERROR || rightType == VariableTypes.ERROR) {
         return VariableTypes.ERROR;
      }

      if (leftType != VariableTypes.IMAGE) {
         ErrorHandling.printError(ctx, "Scale operation requires an IMAGE type, but found: " + leftType);
         return VariableTypes.ERROR;
      }

      if (!rightType.isNumeric()) {
         ErrorHandling.printError(ctx, "Scale operation requires a numeric type, but found: " + rightType);
         return VariableTypes.ERROR;
      }

      return leftType;
   }

   @Override public VariableTypes visitMorphAddExpr(IMLParser.MorphAddExprContext ctx) {
      VariableTypes leftType = visit(ctx.expr(0));
      VariableTypes rightType = visit(ctx.expr(1));
      String op = ctx.op.getText();

      return validateMorphOperation(ctx, leftType, rightType, "MorphAdd");
   }

   @Override public VariableTypes visitMorphMulExpr(IMLParser.MorphMulExprContext ctx) {
      VariableTypes leftType = visit(ctx.expr(0));
      VariableTypes rightType = visit(ctx.expr(1));
      String op = ctx.op.getText();

      return validateMorphOperation(ctx, leftType, rightType, "MorphMul");
   }

   @Override public VariableTypes visitMorphFuncExpr(IMLParser.MorphFuncExprContext ctx) {
      VariableTypes leftType = visit(ctx.expr(0));
      VariableTypes rightType = visit(ctx.expr(1));
      String func = ctx.morphFunc().getText();

      return validateMorphOperation(ctx, leftType, rightType, "MorphFunc");
   }

   @Override public VariableTypes visitUnaryExpr(IMLParser.UnaryExprContext ctx) {
      VariableTypes expr = visit(ctx.expr());
      if (expr.isNumeric()) {
         return expr;
      } else {
         ErrorHandling.printError(ctx, "Invalid unary operation");
         return VariableTypes.ERROR;
      }
   }

   @Override public VariableTypes visitLoadExpr(IMLParser.LoadExprContext ctx) {
      VariableTypes exprType = visit(ctx.expr());
      if (exprType != VariableTypes.STRING) {
         ErrorHandling.printError(ctx, "Load operation requires a STRING type, but found: " + exprType);
         return VariableTypes.ERROR;
      }
      return VariableTypes.IMAGE;
   }

   @Override public VariableTypes visitReadExpr(IMLParser.ReadExprContext ctx) {
      return VariableTypes.STRING;
   }

   @Override public VariableTypes visitRunExpr(IMLParser.RunExprContext ctx) {
      // 'run from read' executes the secondary IIML language and returns an image
      if (ctx.STRING() != null) {
         return VariableTypes.IMAGE;
      }

      if (ctx.ID() != null) {
         String varName = ctx.ID().getText();
         if (!variablesMap.containsKey(varName)) {
            ErrorHandling.printError(ctx, "Undeclared variable used in 'run from read': " + varName);
            return VariableTypes.ERROR;
         }

         VariableTypes type = variablesMap.get(varName);
         if (type != VariableTypes.STRING) {
            ErrorHandling.printError(ctx, "Expected STRING in 'run from read', but got: " + type);
            return VariableTypes.ERROR;
         }

         return VariableTypes.IMAGE;
      }

      ErrorHandling.printError(ctx, "Expected STRING or ID in 'run from read'");
      return VariableTypes.ERROR;
   }

   @Override public VariableTypes visitListExpr(IMLParser.ListExprContext ctx) {
      VariableTypes listType = null;

      for (IMLParser.ValueContext expr : ctx.value()) {
         VariableTypes exprType = visit(expr);
         if (exprType == VariableTypes.ERROR) {
            return VariableTypes.ERROR;
         }

         if (listType == null) {
            listType = exprType;
         } else if (listType != exprType) {
            ErrorHandling.printError(ctx, "List elements must be of the same type, but found: " + listType + " and " + exprType);
            return VariableTypes.ERROR;
         }
      }

      if (listType == null) {
         return VariableTypes.LIST_EMPTY;
      }

      if (listType == VariableTypes.STRING) {
         return VariableTypes.LIST_STRING;
      } else if (listType == VariableTypes.NUMBER) {
         return VariableTypes.LIST_NUMBER;
      } else if (listType == VariableTypes.PERCENTAGE) {
         return VariableTypes.LIST_PERCENTAGE;
      } else if (listType == VariableTypes.IMAGE) {
         return VariableTypes.LIST_IMAGE;
      } else if (listType == VariableTypes.BOOLEAN) {
         return VariableTypes.LIST_BOOLEAN;
      } else if (listType == VariableTypes.PIXEL) {
         return VariableTypes.LIST_PIXEL;
      } else {
         ErrorHandling.printError(ctx, "Invalid list type: " + listType);
         return VariableTypes.ERROR;
      }
   }

   @Override public VariableTypes visitIndexExpr(IMLParser.IndexExprContext ctx) {
      VariableTypes listType = visit(ctx.expr(0));
      VariableTypes indexType = visit(ctx.expr(1));

      if (!indexType.isNumeric()) {
         ErrorHandling.printError(ctx, "Index must be numeric, but found: " + indexType);
         return VariableTypes.ERROR;
      }

      String listTypeName = listType.toString();
      if (listTypeName.startsWith("LIST_")) {
         String listTypeString = listType.toString().toLowerCase();
         return VariableTypes.getElementType(listTypeString);
      } else {
         ErrorHandling.printError(ctx, "Index operation requires a list type, but found: " + listType);
         return VariableTypes.ERROR;
      }
   }

   @Override public VariableTypes visitParenExpr(IMLParser.ParenExprContext ctx) {
      return visit(ctx.expr());
   }

   @Override public VariableTypes visitSizeOfExpr(IMLParser.SizeOfExprContext ctx) {
      VariableTypes exprType = visit(ctx.expr());
      if (exprType != VariableTypes.IMAGE) {
         ErrorHandling.printError(ctx, "SizeOf operation requires an IMAGE type, but found: " + exprType);
         return VariableTypes.ERROR;
      }

      return VariableTypes.NUMBER;
   }

   @Override public VariableTypes visitType(IMLParser.TypeContext ctx) {
      return VariableTypes.fromString(ctx.getText());
   }

   @Override public VariableTypes visitOrCond(IMLParser.OrCondContext ctx) {
      VariableTypes leftType = visit(ctx.condExpr(0));
      VariableTypes rightType = visit(ctx.condExpr(1));

      if (leftType != VariableTypes.BOOLEAN || rightType != VariableTypes.BOOLEAN) {
         ErrorHandling.printError(ctx, "OR operation requires a BOOLEAN type, but found: " + leftType);
         return VariableTypes.ERROR;
      }

      return VariableTypes.BOOLEAN;
   }

   @Override public VariableTypes visitAndCond(IMLParser.AndCondContext ctx) {
      VariableTypes leftType = visit(ctx.condExpr(0));
      VariableTypes rightType = visit(ctx.condExpr(1));

      if (leftType != VariableTypes.BOOLEAN || rightType != VariableTypes.BOOLEAN) {
         ErrorHandling.printError(ctx, "AND operation requires a BOOLEAN type, but found: " + leftType);
         return VariableTypes.ERROR;
      }

      return VariableTypes.BOOLEAN;
   }

   @Override public VariableTypes visitComparisonCond(IMLParser.ComparisonCondContext ctx) {
      VariableTypes leftType = visit(ctx.expr(0));
      VariableTypes rightType = visit(ctx.expr(1));
      String op = ctx.op.getText();

      if (leftType == VariableTypes.ERROR || rightType == VariableTypes.ERROR) {
         return VariableTypes.ERROR;
      }

      if (leftType != rightType) {
         ErrorHandling.printError(ctx, "Comparison operation requires same types, but found: " + leftType + " and " + rightType);
         return VariableTypes.ERROR;
      }

      if (op == null) {
         ErrorHandling.printError(ctx, "Comparison operation requires a valid operator, but found: " + op);
         return VariableTypes.ERROR;
      }

      if (op.equals("==") || op.equals("!=")) {
         if (leftType != VariableTypes.STRING && leftType != VariableTypes.NUMBER && leftType != VariableTypes.PERCENTAGE && leftType != VariableTypes.BOOLEAN) {
            ErrorHandling.printError(ctx, "Comparison operation requires a STRING, NUMBER, PERCENTAGE or BOOLEAN type, but found: " + leftType);
            return VariableTypes.ERROR;
         }
      } else if (op.equals("<") || op.equals("<=") || op.equals(">") || op.equals(">=")) {
         if (leftType != VariableTypes.NUMBER && leftType != VariableTypes.PERCENTAGE) {
            ErrorHandling.printError(ctx, "Comparison operation requires a NUMBER or PERCENTAGE type, but found: " + leftType);
            return VariableTypes.ERROR;
         }
      } else {
         ErrorHandling.printError(ctx, "Invalid comparison operator: " + op);
         return VariableTypes.ERROR;
      }

      return VariableTypes.BOOLEAN;
   }

   @Override public VariableTypes visitNotCond(IMLParser.NotCondContext ctx) {
      VariableTypes exprType = visit(ctx.condExpr());

      if (exprType != VariableTypes.BOOLEAN) {
         ErrorHandling.printError(ctx, "NOT operation requires a BOOLEAN type, but found: " + exprType);
         return VariableTypes.ERROR;
      }

      return VariableTypes.BOOLEAN;
   }

   @Override public VariableTypes visitAnyPixelCond(IMLParser.AnyPixelCondContext ctx) {
      String varName = ctx.ID().getText();
      String op = ctx.op.getText();
      VariableTypes exprType = visit(ctx.expr());

      return validatePixelComparison(ctx, varName, op, exprType);
   }

   @Override public VariableTypes visitAllPixelCond(IMLParser.AllPixelCondContext ctx) {
      String varName = ctx.ID().getText();
      String op = ctx.op.getText();
      VariableTypes exprType = visit(ctx.expr());

      return validatePixelComparison(ctx, varName, op, exprType);
   }
}
