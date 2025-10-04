import java.util.*;

@SuppressWarnings("CheckReturnValue")
public class Execute extends PrefixCalculatorBaseVisitor<Integer> {

   @Override
   public Integer visitProgram(PrefixCalculatorParser.ProgramContext ctx) {
      Integer result = null;
      for(PrefixCalculatorParser.StatContext statCtx : ctx.stat()) {
         Integer res = visit(statCtx);
         if(res != null){
            System.out.println(res);  
         }
      }
      return result;
   }

   @Override
   public Integer visitStat(PrefixCalculatorParser.StatContext ctx) {
      return visit(ctx.expr());
   }

   @Override
   public Integer visitExprPrefix(PrefixCalculatorParser.ExprPrefixContext ctx) {
      String operator = ctx.getChild(0).getText();
      int left = visit(ctx.expr(0)); 
      int right = visit(ctx.expr(1));

      switch (operator) {
         case "+": return left + right;
         case "-": return left - right;
         case "*": return left * right;
         case "/":
            if (right == 0) {
               System.err.println("Erro: Divisão por zero.");
               return 0;
            }
            return left / right;
         default:
            System.err.println("Erro: Operador desconhecido " + operator);
            return 0;
      }
   }

   @Override
   public Integer visitExprNumber(PrefixCalculatorParser.ExprNumberContext ctx) {
      return Integer.parseInt(ctx.getText());
   }
}