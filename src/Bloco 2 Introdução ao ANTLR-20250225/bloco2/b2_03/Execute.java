@SuppressWarnings("CheckReturnValue")
public class Execute extends CalculatorBaseVisitor<String> {

   @Override public String visitProgram(CalculatorParser.ProgramContext ctx) {
      String res = null;
      for(CalculatorParser.StatContext statCtx : ctx.stat()){
         String result = visit(statCtx);
         if(result != null){
            System.out.println(result);
         }
      }
      return res;
   }

   @Override public String visitStat(CalculatorParser.StatContext ctx) {
      if(ctx.expr()!= null){
         return visit(ctx.expr());
      }
      return null;
   }

   @Override public String visitExprAddSub(CalculatorParser.ExprAddSubContext ctx) {
      double left = Double.parseDouble(ctx.expr(0).getText());
      double right = Double.parseDouble(ctx.expr(1).getText());
      String op = ctx.op.getText();
      double result = 0;

      switch(op){
         case "+": result = left + right; break;
         case "-": result = left - right; break;
         default: return null;
      }
      return String.valueOf(result);
   }

   @Override public String visitExprParent(CalculatorParser.ExprParentContext ctx) {
      return visit(ctx.expr());
   }

   @Override public String visitExprInteger(CalculatorParser.ExprIntegerContext ctx) {
      return ctx.Integer().getText();
   }

   @Override public String visitExprMultDivMod(CalculatorParser.ExprMultDivModContext ctx) {
      double left = Double.parseDouble(ctx.expr(0).getText());
      double right = Double.parseDouble(ctx.expr(1).getText());
      String op = ctx.op.getText();
      double result = 0;

      switch(op){
         case "*": result = left * right; break;
         case "/": result = left / right; break;
         case "%": result = left % right; break;
         default: return null;
      }
      return String.valueOf(result);
   }
}
