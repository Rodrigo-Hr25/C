import java.util.*;

@SuppressWarnings("CheckReturnValue")
public class Execute extends CalculatorBaseVisitor<String> {

   @Override
   public String visitExprAddSub(CalculatorParser.ExprAddSubContext ctx) {
      String left = visit(ctx.expr(0));
      String right = visit(ctx.expr(1));
      return left + " " + right + " " + ctx.op.getText();
   }

   @Override
   public String visitExprMultDivMod(CalculatorParser.ExprMultDivModContext ctx) {
      String left = visit(ctx.expr(0));
      String right = visit(ctx.expr(1));
      return left + " " + right + " " + ctx.op.getText();
   }

   @Override
   public String visitExprParent(CalculatorParser.ExprParentContext ctx) {
      return visit(ctx.expr());
   }

   @Override
   public String visitExprInteger(CalculatorParser.ExprIntegerContext ctx) {
      return ctx.Integer().getText();
   }

   @Override
   public String visitExprID(CalculatorParser.ExprIDContext ctx) {
      return ctx.ID().getText();
   }

   @Override
   public String visitExprUnary(CalculatorParser.ExprUnaryContext ctx) {
      String inner = visit(ctx.expr());
      String op = ctx.op.getText().equals("+") ? "!+" : "!-";
      return inner + " " + op;
   }

   @Override
   public String visitStat(CalculatorParser.StatContext ctx) {
      if (ctx.expr() != null) {
         String result = visit(ctx.expr());
         System.out.println("Expressão sufixa: " + result);
         return result;
      } else if (ctx.assign() != null) {
         String id = ctx.assign().ID().getText();
         String expr = visit(ctx.assign().expr());
         String assignment = id + " = " + expr;
         System.out.println("Atribuição convertida: " + assignment);
         return assignment;
      }
      return "";
   }

   @Override
   public String visitProgram(CalculatorParser.ProgramContext ctx) {
      StringBuilder result = new StringBuilder();
      for (var stat : ctx.stat()) {
         String converted = visit(stat);
         if (!converted.isBlank()) {
            result.append(converted).append("\n");
         }
      }
      System.out.print(result);
      return result.toString();
   }
}