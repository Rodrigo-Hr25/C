@SuppressWarnings("CheckReturnValue")

public class WriteMethodsName extends Java8ParserBaseListener {

   @Override public void exitMethodDeclarator(Java8Parser.MethodDeclaratorContext ctx) {
      System.out.println("Method Name: " + ctx.Identifier().getText());
   }

}
