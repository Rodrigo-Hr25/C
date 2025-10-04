import java.util.*;

@SuppressWarnings("CheckReturnValue")
public class Execute extends VectorBaseVisitor<String> {

   HashMap<String,Vector> vars = new HashMap<>();

   @Override public String visitShow(VectorParser.ShowContext ctx) {
      Vector res = visit(ctx.expression());
      if(res != null){
         System.out.print(res);
      }
      return res;
   }

   @Override public String visitAssignment(VectorParser.AssignmentContext ctx) {
      Vector res = visit(ctx.expression());
      Strign id = ctx.ID().getText();
      if(res != null && id != null){
         vars.put(id, res);
      }

      return res;
   }

   @Override public String visitMultDivExpr(VectorParser.MultDivExprContext ctx) {
      Vector res = null;
      Vector v1 = visit(ctx.expression(0));
      Vector v2 = visit(ctx.expression(1));

      if (v1 != null && v2 != null) {
         switch(ctx.op.getText()) {
            case "*":
               if (!v1.scalar() && !v2.scalar()) {
                  System.err.println("ERRO: Não existe multiplicação entre dois vetores!");
                  return null;
               } 
               res = v1.multplication(v2);
               break;
            case ".":
               if (v1.scalar() || v2.scalar()) {
                  System.err.println("ERRO: Não existe produto interno sem ser entre vetores!");
                  return null;
               }
               if (v1.dimension() != v2.dimension()) {
                  System.err.println("ERRO: Não existe produto interno entre vetores de dimensões diferentes!");
                  return null;
               }
               res = v1.multplication(v2);
               break;
         }
      }

      return res;
   }
   }

   @Override public String visitNumberExpr(VectorParser.NumberExprContext ctx) {
      ArrayList<Double> value = new ArrayList<>();
      double number = Double.parseDouble(ctx.NUMBER().getText());
      value.add(number);
      Vector res = new Vector(value, true);
      if(res.error()){
         System.err.println("Error: Vetor inválido");
      }
      return res;
   }

   @Override public String visitParentExpr(VectorParser.ParentExprContext ctx) {
      return visit(ctx.expression());
   }

   @Override public String visitVectorExpr(VectorParser.VectorExprContext ctx) {
      String vector = ctx.VECTOR().getText();
      Vector res = new Vector();
      if(res.error()){
         System.out.println("Error: Vetor inválido");
         return null;
      }
      return res;
   }

   @Override public String visitUnaryExpr(VectorParser.UnaryExprContext ctx) {
      Vector res = null;
      Vector vector = visit(ctx.expression());
      String op = ctx.op.getText();
      if(vector != null && op != null){
         if(op.equals('-')){
            res = vector.symmetric();
         }
         else{
            res = vector;
         }
      }
      return res;
   }

   @Override public String visitAddSubExpr(VectorParser.AddSubExprContext ctx) {
      Vector res = null;
      Vector n1 = visit(ctx.expression(0));
      Vector n2 = visit(ctx.expression(1));

      if(n1 != null && n2 != null){
         if(n1.scalar() != n2.scalar()){
            System.err.println("Error: Não é possível adicionar/subtrair escalares com vetores");
            return null;
         }
         if(n1.dimension() != n2.dimension()){
            System.out.println("Error: Não é possível adicionar/subtrair elementos com dimensões diferentes");
            return null;
         }

         switch (ctx.op.getText()) {
            case '+':
               res = n1.add(n2);
               break;
         
            case '-':
               res = n1.subtract(n2);
               break;
         }
      }
      return res;
   }

   @Override public String visitIdExpr(VectorParser.IdExprContext ctx) {
      Vector res = null;
      String id = ctx.ID().getText();
      if(!vars.containsKey(id)){
         System.out.println("Error: Variável não definida");
      }
      else {
         res = vars.get(id);
      }
      return res;
   }
}
