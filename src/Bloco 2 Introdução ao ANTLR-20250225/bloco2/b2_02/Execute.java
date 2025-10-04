import java.util.List;
import org.antlr.v4.runtime.tree.TerminalNode;

@SuppressWarnings("CheckReturnValue")
public class Execute extends SuffixCalculatorBaseVisitor<Double> {

   @Override public Double visitProgram(SuffixCalculatorParser.ProgramContext ctx) {
      Double result = null;
      for(SuffixCalculatorParser.StatContext statCtx : ctx.stat()) {
         Double res = visit(statCtx);
         if(res != null){
            System.out.println(res);   // Print do resultado de cada linha
         }
      }
      return result;
   }

   @Override public Double visitStat(SuffixCalculatorParser.StatContext ctx) {
      if(ctx.expr() != null){
         return visit(ctx.expr()); // Converte para numero
      }
      return null;
   }

   @Override public Double visitExpr(SuffixCalculatorParser.ExprContext ctx) {
      if(ctx.Number() != null){
         return Double.parseDouble(ctx.Number().getText()); // Converte para numero
      }

      // Caso seja operação binária (notação pós-fixa)
      List<SuffixCalculatorParser.ExprContext> exprs = ctx.expr();
      Double left = visit(exprs.get(0));
      Double right = visit(exprs.get(1));
      String op = ctx.op.getText(); // obtem o operador

      switch(op){
         case "+": return left + right;
         case "-": return left - right;
         case "*": return left * right;
         case "/": return right != 0 ? left / right : Double.POSITIVE_INFINITY;
         default: return null;
      }
   }
}