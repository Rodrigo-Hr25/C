import org.stringtemplate.v4.*;

import java.util.*;

@SuppressWarnings("CheckReturnValue")
public class CodeGenerator extends IMLBaseVisitor<StringTemplateWithType> {
   private HashMap<String, VariableTypes> variablesType;

   private List<String> pythonKeywords = Arrays.asList(
      "False", "None", "True", "and", "as", "assert", "async", "await",
      "break", "class", "continue", "def", "del", "elif", "else", "except",
      "finally", "for", "from", "global", "if", "import", "in", "is",
      "lambda", "nonlocal", "not", "or", "pass", "raise", "return",
      "try", "while", "with", "yield"
   );

   private final STGroup stg = new STGroupFile("Template.stg");

   public CodeGenerator(HashMap<String, VariableTypes> variablesType) {
      super();
      this.variablesType = variablesType;
   }

   private String sanitizePythonIdentifier(String varName) {
      return pythonKeywords.contains(varName) ? varName + "_" : varName;
   }

   private StringTemplateWithType createMorphOperation(StringTemplateWithType left, StringTemplateWithType right,
                                                       String operator, String operation) {
      String leftRendered = left.getTemplate().render();
      String rightRendered = right.getTemplate().render();

      if (operator.equals(".+") || operator.equals(".-") || operator.equals(".*")) {
         String cleanOperator = operator.substring(1);
         ST morphTemplate = stg.getInstanceOf("morphOperation");
         morphTemplate.add("left", leftRendered);
         morphTemplate.add("operator", cleanOperator);
         morphTemplate.add("right", rightRendered);
         return new StringTemplateWithType(morphTemplate, left.getType());
      } else {
         // For non-morphological operations, use element template
         ST element = stg.getInstanceOf("element");
         element.add("elem", leftRendered + " " + operator + " " + rightRendered);
         return new StringTemplateWithType(element, left.getType());
      }
   }

   private StringTemplateWithType createPixelComparison(String varName, String operator,
                                                         StringTemplateWithType expr, String function) {
      String sanitizedVar = sanitizePythonIdentifier(varName);
      String exprRendered = expr.getTemplate().render();

      ST pixelTemplate = stg.getInstanceOf("pixelComparison");
      pixelTemplate.add("image", sanitizedVar);
      pixelTemplate.add("operator", operator);
      pixelTemplate.add("value", exprRendered);
      pixelTemplate.add("function", function);

      return new StringTemplateWithType(pixelTemplate, VariableTypes.BOOLEAN);
   }

   @Override public StringTemplateWithType visitProgram(IMLParser.ProgramContext ctx) {
      ST main = stg.getInstanceOf("main");
      ST statements = stg.getInstanceOf("statements");

      for (int i = 0; i < ctx.statementWithSeparator().size(); i++) {
         ST statement = visit(ctx.statementWithSeparator(i)).getTemplate();
         if (statement != null) {
            statements.add("statement", statement.render());
         }
      }

      main.add("statements", statements.render());
      return new StringTemplateWithType(main, VariableTypes.ERROR);
   }

   @Override public StringTemplateWithType visitStatementWithSeparator(IMLParser.StatementWithSeparatorContext ctx) {
      ST res = visit(ctx.statement()).getTemplate();
      return new StringTemplateWithType(res, VariableTypes.ERROR);
   }

   @Override public StringTemplateWithType visitDeclStmt(IMLParser.DeclStmtContext ctx) {
      String varName = sanitizePythonIdentifier(ctx.ID().getText());

      if (ctx.expr() != null) {
         // Declaration with initialization
         StringTemplateWithType exprResult = visit(ctx.expr());
         ST assignment = stg.getInstanceOf("assignment");
         assignment.add("var", varName);
         assignment.add("value", exprResult.getTemplate().render());
         return new StringTemplateWithType(assignment, exprResult.getType());
      } else {
         // Declaration without initialization
         ST assignment = stg.getInstanceOf("assignment");
         assignment.add("var", varName);
         assignment.add("value", "None");
         return new StringTemplateWithType(assignment, VariableTypes.fromString(ctx.type().getText()));
      }
   }

   @Override public StringTemplateWithType visitAssignStmt(IMLParser.AssignStmtContext ctx) {
      String varName = sanitizePythonIdentifier(ctx.ID().getText());

      StringTemplateWithType exprResult = visit(ctx.expr());
      ST assignment = stg.getInstanceOf("assignment");
      assignment.add("var", varName);
      assignment.add("value", exprResult.getTemplate().render());
      return new StringTemplateWithType(assignment, exprResult.getType());
   }

   @Override public StringTemplateWithType visitListEditStmt(IMLParser.ListEditStmtContext ctx) {
      String varName = sanitizePythonIdentifier(ctx.ID().getText());

      StringTemplateWithType exprResult = visit(ctx.expr());
      String operation = ctx.getChild(1).getText();

      ST statement = stg.getInstanceOf("statement");
      String pythonOp = switch (operation) {
          case "append" -> varName + ".append(" + exprResult.getTemplate().render() + ")";
          case "remove" -> varName + ".remove(" + exprResult.getTemplate().render() + ")";
          case "insert" -> varName + ".insert(0, " + exprResult.getTemplate().render() + ")";
          case "replace" -> varName + " = [" + exprResult.getTemplate().render() + "]";
          default -> varName + ".append(" + exprResult.getTemplate().render() + ")";
      };

      statement.add("code", pythonOp);
      return new StringTemplateWithType(statement, VariableTypes.VOID);
   }

   @Override public StringTemplateWithType visitStoreStmt(IMLParser.StoreStmtContext ctx) {
      String varName = sanitizePythonIdentifier(ctx.ID().getText());

      String filename = ctx.STRING().getText();
      filename = filename.substring(1, filename.length() - 1);

      VariableTypes varType = variablesType.get(ctx.ID().getText());
      ST statement = stg.getInstanceOf("statement");

      if (varType == VariableTypes.LIST_IMAGE || filename.endsWith(".gif")) {
         statement.add("code", "save_gif(" + varName + ", '" + filename + "')");
      } else {
         statement.add("code", "cv2.imwrite('" + filename + "', " + varName + " * 255)");
      }
      return new StringTemplateWithType(statement, VariableTypes.VOID);
   }

   @Override public StringTemplateWithType visitOutputStmt(IMLParser.OutputStmtContext ctx) {
      StringTemplateWithType exprResult = visit(ctx.expr());
      ST output = stg.getInstanceOf("output");
      output.add("value", exprResult.getTemplate().render());
      return new StringTemplateWithType(output, VariableTypes.VOID);
   }

   @Override public StringTemplateWithType visitDrawStmt(IMLParser.DrawStmtContext ctx) {
      String varName = sanitizePythonIdentifier(ctx.ID().getText());

      ST statement = stg.getInstanceOf("statement");
      statement.add("code", "cv2.imshow('Image', " + varName + "); cv2.waitKey(0); cv2.destroyAllWindows()");
      return new StringTemplateWithType(statement, VariableTypes.VOID);
   }

   @Override public StringTemplateWithType visitIfStmt(IMLParser.IfStmtContext ctx) {
      int numConditions = ctx.condExpr().size();
      boolean hasElse = ctx.getText().matches(".*else\\s+(?!if).*");

      if (numConditions == 1 && !hasElse) {
         // if-only case
         ST res = stg.getInstanceOf("ifOnly");
         res.add("condition", visit(ctx.condExpr(0)).getTemplate().render());

         for (int i = 0; i < ctx.statement().size(); i++) {
            ST stmt = visit(ctx.statement(i)).getTemplate();
            if (stmt != null) {
               res.add("statement", stmt.render());
            }
         }

         return new StringTemplateWithType(res, VariableTypes.VOID);

      } else if (numConditions == 1 && hasElse) {
         // if-else case
         ST res = stg.getInstanceOf("ifElse");
         res.add("condition", visit(ctx.condExpr(0)).getTemplate().render());

         int totalStatements = ctx.statement().size();
         int ifStmtCount = totalStatements / 2;

         // Add if statements
         for (int i = 0; i < ifStmtCount; i++) {
            ST stmt = visit(ctx.statement(i)).getTemplate();
            if (stmt != null) {
               res.add("ifStatement", stmt.render());
            }
         }

         // Add else statements
         for (int i = ifStmtCount; i < totalStatements; i++) {
            ST stmt = visit(ctx.statement(i)).getTemplate();
            if (stmt != null) {
               res.add("elseStatement", stmt.render());
            }
         }

         return new StringTemplateWithType(res, VariableTypes.VOID);

      } else {
         // else-if case
         ST res = stg.getInstanceOf("ifElseIf");
         res.add("condition", visit(ctx.condExpr(0)).getTemplate().render());

         int totalStatements = ctx.statement().size();
         int numBlocks = hasElse ? numConditions + 1 : numConditions;
         int statementsPerBlock = totalStatements / numBlocks;

         // Add if statements
         for (int i = 0; i < statementsPerBlock; i++) {
            ST stmt = visit(ctx.statement(i)).getTemplate();
            if (stmt != null) {
               res.add("ifStatement", stmt.render());
            }
         }

         // Add else-if conditions and statements
         for (int condIndex = 1; condIndex < numConditions; condIndex++) {
            res.add("elseIfConditions", visit(ctx.condExpr(condIndex)).getTemplate().render());

            ST elseIfStmts = stg.getInstanceOf("statements");
            int startIdx = condIndex * statementsPerBlock;
            int endIdx = startIdx + statementsPerBlock;

            for (int i = startIdx; i < endIdx; i++) {
               ST stmt = visit(ctx.statement(i)).getTemplate();
               if (stmt != null) {
                  elseIfStmts.add("statement", stmt.render());
               }
            }
            res.add("elseIfStatements", elseIfStmts.render());
         }

         // Add final else statements if present
         if (hasElse) {
            int elseStartIdx = numConditions * statementsPerBlock;
            for (int i = elseStartIdx; i < totalStatements; i++) {
               ST stmt = visit(ctx.statement(i)).getTemplate();
               if (stmt != null) {
                  res.add("elseStatement", stmt.render());
               }
            }
         }

         return new StringTemplateWithType(res, VariableTypes.VOID);
      }
   }

   @Override public StringTemplateWithType visitForLoop(IMLParser.ForLoopContext ctx) {
      String iterVar = sanitizePythonIdentifier(ctx.ID(0).getText());
      String listVar = sanitizePythonIdentifier(ctx.ID(1).getText());

      ST statements = stg.getInstanceOf("statements");
      for (int i = 0; i < ctx.statement().size(); i++) {
         ST stmt = visit(ctx.statement(i)).getTemplate();
         if (stmt != null) {
            statements.add("statement", stmt.render());
         }
      }

      ST forLoop = stg.getInstanceOf("forLoopList");
      forLoop.add("value", iterVar);
      forLoop.add("list", listVar);
      forLoop.add("body", statements.render());

      return new StringTemplateWithType(forLoop, VariableTypes.VOID);
   }

   @Override public StringTemplateWithType visitUntilLoop(IMLParser.UntilLoopContext ctx) {
      StringTemplateWithType condition = visit(ctx.condExpr());

      ST statements = stg.getInstanceOf("statements");
      for (int i = 0; i < ctx.statement().size(); i++) {
         ST stmt = visit(ctx.statement(i)).getTemplate();
         if (stmt != null) {
            statements.add("statement", stmt.render());
         }
      }

      ST until = stg.getInstanceOf("until");
      until.add("cond", "(" + condition.getTemplate().render() + ")");
      until.add("statement", statements.render());

      return new StringTemplateWithType(until, VariableTypes.VOID);
   }

   @Override public StringTemplateWithType visitArithExpr(IMLParser.ArithExprContext ctx) {
      StringTemplateWithType left = visit(ctx.expr(0));
      StringTemplateWithType right = visit(ctx.expr(1));
      String op = ctx.op.getText();

      ST binaryOp = stg.getInstanceOf("binaryOperation");
      binaryOp.add("e1", left.getTemplate().render());
      binaryOp.add("e2", op);
      binaryOp.add("e3", right.getTemplate().render());

      return new StringTemplateWithType(binaryOp, left.getType());
   }

   @Override public StringTemplateWithType visitValueExpr(IMLParser.ValueExprContext ctx) {
      return visit(ctx.value());
   }

   @Override public StringTemplateWithType visitCastExpr(IMLParser.CastExprContext ctx) {
      StringTemplateWithType expr = visit(ctx.expr());
      String targetType = ctx.type().getText();

      ST element = stg.getInstanceOf("element");
      String castOp;

      switch (targetType) {
         case "string":
            castOp = "str(" + expr.getTemplate().render() + ")";
            break;
         case "number":
            castOp = "float(" + expr.getTemplate().render() + ")";
            break;
         case "percentage":
            castOp = "float(" + expr.getTemplate().render() + ") / 100.0";
            break;
         case "image":
            castOp = expr.getTemplate().render();
            break;
         default:
            castOp = expr.getTemplate().render();
      }

      element.add("elem", castOp);

      VariableTypes resultType;
      switch (targetType) {
         case "string":
            resultType = VariableTypes.STRING;
            break;
         case "number":
            resultType = VariableTypes.NUMBER;
            break;
         case "percentage":
            resultType = VariableTypes.PERCENTAGE;
            break;
         case "image":
            resultType = VariableTypes.IMAGE;
            break;
         default:
            resultType = expr.getType();
      }

      return new StringTemplateWithType(element, resultType);
   }

   @Override public StringTemplateWithType visitCountPixelExpr(IMLParser.CountPixelExprContext ctx) {
      StringTemplateWithType exprResult = visit(ctx.expr());
      String varName = sanitizePythonIdentifier(ctx.ID().getText());

      ST element = stg.getInstanceOf("element");
      element.add("elem", "np.count_nonzero(" + varName + " == " + exprResult.getTemplate().render() + ")");
      return new StringTemplateWithType(element, VariableTypes.NUMBER);
   }

   @Override public StringTemplateWithType visitScaleExpr(IMLParser.ScaleExprContext ctx) {
      StringTemplateWithType left = visit(ctx.expr(0));
      StringTemplateWithType right = visit(ctx.expr(1));
      String op = ctx.op.getText();

      String leftRendered = left.getTemplate().render();
      String rightRendered = right.getTemplate().render();

      ST element = stg.getInstanceOf("element");
      String scaleOp;
      switch (op) {
         case "-*": // horizontal scale
            scaleOp = "cv2.resize(" + leftRendered + ", (int(" + leftRendered + ".shape[1] * " + rightRendered + "), " + leftRendered + ".shape[0]))";
            break;
         case "|*": // vertical scale
            scaleOp = "cv2.resize(" + leftRendered + ", (" + leftRendered + ".shape[1], int(" + leftRendered + ".shape[0] * " + rightRendered + ")))";
            break;
         case "+*": // both axes scale
            scaleOp = "cv2.resize(" + leftRendered + ", (int(" + leftRendered + ".shape[1] * " + rightRendered + "), int(" + leftRendered + ".shape[0] * " + rightRendered + ")))";
            break;
         default:
            scaleOp = leftRendered + " * " + rightRendered;
      }

      element.add("elem", scaleOp);
      return new StringTemplateWithType(element, left.getType());
   }

   @Override public StringTemplateWithType visitMorphAddExpr(IMLParser.MorphAddExprContext ctx) {
      StringTemplateWithType left = visit(ctx.expr(0));
      StringTemplateWithType right = visit(ctx.expr(1));
      String op = ctx.op.getText();

      return createMorphOperation(left, right, op, "MorphAdd");
   }

   @Override public StringTemplateWithType visitMorphMulExpr(IMLParser.MorphMulExprContext ctx) {
      StringTemplateWithType left = visit(ctx.expr(0));
      StringTemplateWithType right = visit(ctx.expr(1));
      String op = ctx.op.getText();

      return createMorphOperation(left, right, op, "MorphMul");
   }

   @Override public StringTemplateWithType visitMorphFuncExpr(IMLParser.MorphFuncExprContext ctx) {
      StringTemplateWithType left = visit(ctx.expr(0));
      StringTemplateWithType right = visit(ctx.expr(1));
      String func = ctx.morphFunc().getText();

      ST element = stg.getInstanceOf("element");
      String morphOp;
      // Convert kernel to proper numpy array format
      String kernelExpr = "(np.array(" + right.getTemplate().render() + ") * 255).astype(np.uint8)";

      switch (func) {
         case "erode":
            morphOp = "cv2.erode(" + left.getTemplate().render() + ", " + kernelExpr + ")";
            break;
         case "dilate":
            morphOp = "cv2.dilate(" + left.getTemplate().render() + ", " + kernelExpr + ")";
            break;
         case "open":
            morphOp = "cv2.morphologyEx(" + left.getTemplate().render() + ", cv2.MORPH_OPEN, " + kernelExpr + ")";
            break;
         case "close":
            morphOp = "cv2.morphologyEx(" + left.getTemplate().render() + ", cv2.MORPH_CLOSE, " + kernelExpr + ")";
            break;
         case "tophat":
            morphOp = "cv2.morphologyEx(" + left.getTemplate().render() + ", cv2.MORPH_TOPHAT, " + kernelExpr + ")";
            break;
         case "blackhat":
            morphOp = "cv2.morphologyEx(" + left.getTemplate().render() + ", cv2.MORPH_BLACKHAT, " + kernelExpr + ")";
            break;
         default:
            morphOp = func + "(" + left.getTemplate().render() + ", " + right.getTemplate().render() + ")";
      }

      element.add("elem", morphOp);
      return new StringTemplateWithType(element, left.getType());
   }

   @Override public StringTemplateWithType visitUnaryExpr(IMLParser.UnaryExprContext ctx) {
      StringTemplateWithType expr = visit(ctx.expr());
      String op = ctx.op.getText();

      ST unaryOp = stg.getInstanceOf("unaryOperation");
      String pythonOp;
      switch (op) {
         case "-":
            pythonOp = "np.flipud(" + expr.getTemplate().render() + ")"; // vertical flip
            break;
         case "|":
            pythonOp = "np.fliplr(" + expr.getTemplate().render() + ")"; // horizontal flip
            break;
         case "+":
            pythonOp = "np.flipud(np.fliplr(" + expr.getTemplate().render() + "))"; // both axes flip
            break;
         case ".-":
            pythonOp = "1 - " + expr.getTemplate().render(); // image inversion
            break;
         default:
            pythonOp = op + expr.getTemplate().render();
      }

      unaryOp.add("op", "");
      unaryOp.add("e1", pythonOp);
      return new StringTemplateWithType(unaryOp, expr.getType());
   }

   @Override public StringTemplateWithType visitLoadExpr(IMLParser.LoadExprContext ctx) {
      String filename = visit(ctx.expr()).getTemplate().render();
      ST loadImage = stg.getInstanceOf("loadImage");
      loadImage.add("filename", filename);
      return new StringTemplateWithType(loadImage, VariableTypes.IMAGE);
   }

   @Override public StringTemplateWithType visitReadExpr(IMLParser.ReadExprContext ctx) {
      String prompt = ctx.STRING().getText();
      ST read = stg.getInstanceOf("read");
      read.add("prompt", prompt);
      return new StringTemplateWithType(read, VariableTypes.STRING);
   }

   @Override public StringTemplateWithType visitRunExpr(IMLParser.RunExprContext ctx) {
      String filenameExpr;
      if (ctx.STRING() != null) {
         String prompt = ctx.STRING().getText();
         filenameExpr = "input(" + prompt + ")";
      } else {
         String varName = sanitizePythonIdentifier(ctx.ID().getText());
         filenameExpr = varName;
      }

      ST runIIML = stg.getInstanceOf("runIIML");
      runIIML.add("filename", filenameExpr);
      return new StringTemplateWithType(runIIML, VariableTypes.IMAGE);
   }

   @Override public StringTemplateWithType visitListExpr(IMLParser.ListExprContext ctx) {
      ST element = stg.getInstanceOf("element");
      StringBuilder listElements = new StringBuilder("[");

      VariableTypes listType = VariableTypes.LIST_EMPTY;

      if (ctx.value() != null && !ctx.value().isEmpty()) {
         StringTemplateWithType firstElement = visit(ctx.value(0));
         VariableTypes elementType = firstElement.getType();

         // Determine list type based on first element
         if (elementType.isList()) {
            // If first element is a list, this creates a nested list
            String nestedTypeString = "list_" + elementType.toString().toLowerCase();
            listType = VariableTypes.fromString(nestedTypeString);
         } else {
            // Convert base type to list type
            String listTypeString = "list_" + elementType.toString().toLowerCase();
            listType = VariableTypes.fromString(listTypeString);
         }

         // Build the list elements
         for (int i = 0; i < ctx.value().size(); i++) {
            StringTemplateWithType valueResult = visit(ctx.value(i));
            listElements.append(valueResult.getTemplate().render());
            if (i < ctx.value().size() - 1) {
               listElements.append(", ");
            }
         }
      }
      listElements.append("]");

      element.add("elem", listElements.toString());
      return new StringTemplateWithType(element, listType);
   }

   @Override public StringTemplateWithType visitIndexExpr(IMLParser.IndexExprContext ctx) {
      StringTemplateWithType listExpr = visit(ctx.expr(0));
      StringTemplateWithType indexExpr = visit(ctx.expr(1));

      ST element = stg.getInstanceOf("element");
      element.add("elem", listExpr.getTemplate().render() + "[int(" + indexExpr.getTemplate().render() + ")]");

      VariableTypes listType = listExpr.getType();
      String listTypeName = listType.toString();
      VariableTypes elementType = VariableTypes.ERROR;

      if (listTypeName.startsWith("LIST_")) {
         String listTypeString = listType.toString().toLowerCase();
         elementType = VariableTypes.getElementType(listTypeString);
      }

      return new StringTemplateWithType(element, elementType);
   }

   @Override public StringTemplateWithType visitParenExpr(IMLParser.ParenExprContext ctx) {
      return visit(ctx.expr());
   }

   @Override public StringTemplateWithType visitSizeOfExpr(IMLParser.SizeOfExprContext ctx) {
      StringTemplateWithType expr = visit(ctx.expr());
      String dim = ctx.dim.getText();

      ST element = stg.getInstanceOf("element");
      String sizeOp;
      if (dim.equals("rows")) {
         sizeOp = expr.getTemplate().render() + ".shape[0]";
      } else { // columns
         sizeOp = expr.getTemplate().render() + ".shape[1]";
      }

      element.add("elem", sizeOp);
      return new StringTemplateWithType(element, VariableTypes.NUMBER);
   }

   @Override public StringTemplateWithType visitType(IMLParser.TypeContext ctx) {
      VariableTypes type = VariableTypes.fromString(ctx.getText());
      return new StringTemplateWithType(null, type);
   }

   @Override public StringTemplateWithType visitValue(IMLParser.ValueContext ctx) {
      ST element = stg.getInstanceOf("element");

      if (ctx.ID() != null) {
         String varName = sanitizePythonIdentifier(ctx.ID().getText());
         element.add("elem", varName);
         VariableTypes type = variablesType.get(ctx.ID().getText());
         return new StringTemplateWithType(element, type != null ? type : VariableTypes.ERROR);
      } else if (ctx.NUM() != null) {
         element.add("elem", ctx.NUM().getText());
         return new StringTemplateWithType(element, VariableTypes.NUMBER);
      } else if (ctx.PERCENT() != null) {
         String percentText = ctx.PERCENT().getText();
         // Convert percentage to decimal (remove % and divide by 100)
         String numPart = percentText.substring(0, percentText.length() - 1);
         element.add("elem", Double.parseDouble(numPart) / 100.0);
         return new StringTemplateWithType(element, VariableTypes.PERCENTAGE);
      } else if (ctx.STRING() != null) {
         element.add("elem", ctx.STRING().getText());
         return new StringTemplateWithType(element, VariableTypes.STRING);
      }

      return new StringTemplateWithType(element, VariableTypes.ERROR);
   }

   @Override public StringTemplateWithType visitMorphFunc(IMLParser.MorphFuncContext ctx) {
      ST element = stg.getInstanceOf("element");
      element.add("elem", ctx.getText());
      return new StringTemplateWithType(element, VariableTypes.STRING);
   }

   @Override public StringTemplateWithType visitOrCond(IMLParser.OrCondContext ctx) {
      StringTemplateWithType left = visit(ctx.condExpr(0));
      StringTemplateWithType right = visit(ctx.condExpr(1));

      ST binaryOp = stg.getInstanceOf("binaryOperation");
      binaryOp.add("e1", left.getTemplate().render());
      binaryOp.add("e2", "or");
      binaryOp.add("e3", right.getTemplate().render());

      return new StringTemplateWithType(binaryOp, VariableTypes.BOOLEAN);
   }

   @Override public StringTemplateWithType visitComparisonCond(IMLParser.ComparisonCondContext ctx) {
      StringTemplateWithType left = visit(ctx.expr(0));
      StringTemplateWithType right = visit(ctx.expr(1));
      String op = ctx.op.getText();

      ST binaryOp = stg.getInstanceOf("binaryOperation");
      binaryOp.add("e1", left.getTemplate().render());
      binaryOp.add("e2", op);
      binaryOp.add("e3", right.getTemplate().render());

      return new StringTemplateWithType(binaryOp, VariableTypes.BOOLEAN);
   }

   @Override public StringTemplateWithType visitAndCond(IMLParser.AndCondContext ctx) {
      StringTemplateWithType left = visit(ctx.condExpr(0));
      StringTemplateWithType right = visit(ctx.condExpr(1));

      ST binaryOp = stg.getInstanceOf("binaryOperation");
      binaryOp.add("e1", left.getTemplate().render());
      binaryOp.add("e2", "and");
      binaryOp.add("e3", right.getTemplate().render());

      return new StringTemplateWithType(binaryOp, VariableTypes.BOOLEAN);
   }

   @Override public StringTemplateWithType visitNotCond(IMLParser.NotCondContext ctx) {
      StringTemplateWithType expr = visit(ctx.condExpr());

      ST unaryOp = stg.getInstanceOf("unaryOperation");
      unaryOp.add("op", "not");
      unaryOp.add("e1", expr.getTemplate().render());

      return new StringTemplateWithType(unaryOp, VariableTypes.BOOLEAN);
   }

   @Override public StringTemplateWithType visitAnyPixelCond(IMLParser.AnyPixelCondContext ctx) {
      String varName = ctx.ID().getText();
      StringTemplateWithType expr = visit(ctx.expr());
      String op = ctx.op.getText();

      String standardOp = op.substring(1); // Remove the '.' prefix
      return createPixelComparison(varName, standardOp, expr, "np.any");
   }

   @Override public StringTemplateWithType visitAllPixelCond(IMLParser.AllPixelCondContext ctx) {
      String varName = ctx.ID().getText();
      StringTemplateWithType expr = visit(ctx.expr());
      String op = ctx.op.getText();

      String standardOp = op.substring(1); // Remove the '.' prefix
      return createPixelComparison(varName, standardOp, expr, "np.all");
   }
}
