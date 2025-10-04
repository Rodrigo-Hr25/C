import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("CheckReturnValue")
public class Execute extends CalculatorBaseVisitor<Integer> {

   public static Map<String, Integer> variables = new HashMap<String, Integer>();

   @Override public Integer visitProgram(CalculatorParser.ProgramContext ctx) {
      for (CalculatorParser.StatContext stat : ctx.stat()) {
         Integer res = visit(stat);
         if (res != null) {
             System.out.println(res);
         }
     }
     return null;
   }

   @Override public Integer visitStat(CalculatorParser.StatContext ctx) {
      if (ctx.expr() != null)
            return visit(ctx.expr());
        if (ctx.assign() != null)
            return visit(ctx.assign());
        return null;
   }

   @Override public Integer visitAssign(CalculatorParser.AssignContext ctx) {
      Integer expr = visit(ctx.expr());
      variables.put(ctx.ID().getText(), expr);
      return expr;
   }

   @Override public Integer visitExprAddSub(CalculatorParser.ExprAddSubContext ctx) {
      Integer res = null;
      Integer e1 = visit(ctx.expr(0));
      Integer e2 = visit(ctx.expr(1));

      String op = ctx.op.getText();

      switch (op) {
         case "+":
            res = e1 + e2;
            break;
      
         case "-":
            res = e1 - e2;
            break;   

         default:
            break;
      }
      return res;
   }

   @Override public Integer visitExprParent(CalculatorParser.ExprParentContext ctx) {
      Integer res = visit(ctx.expr());
      return res;
   }

   @Override public Integer visitExprUnary(CalculatorParser.ExprUnaryContext ctx) {
      Integer value = visit(ctx.expr());
      switch (ctx.op.getText()){
         case "+":
            return value;  
         case "-":
            return 0 - value;
      }
      return value;
   }

   @Override public Integer visitExprInteger(CalculatorParser.ExprIntegerContext ctx) {
      Integer res = Integer.parseInt(ctx.Integer().getText());
      return res;
   }

   @Override public Integer visitExprID(CalculatorParser.ExprIDContext ctx) {
      String id = ctx.ID().getText();
        if (variables.containsKey(id))
            return variables.get(id);
        throw new RuntimeException("Unknown Variable: " + id);
      //return res;
   }

   @Override public Integer visitExprMultDivMod(CalculatorParser.ExprMultDivModContext ctx) {
      Integer res = null;
      Integer e1 = visit(ctx.expr(0));
      Integer e2 = visit(ctx.expr(1));

      String op = ctx.op.getText();

      switch (op) {
         case "*":
            res = e1 * e2;
            break;
      
         case "/":
            res = e1 / e2;
            break;   

         case "%":
            res = e1 % e2;
            break;   

         default:
            break;
      }

      return res;
   }
}