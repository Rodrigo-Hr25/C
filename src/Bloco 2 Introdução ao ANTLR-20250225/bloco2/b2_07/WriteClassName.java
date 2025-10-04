@SuppressWarnings("CheckReturnValue")

public class WriteClassName extends Java8ParserBaseListener {

   @Override public void exitNormalClassDeclaration(Java8Parser.NormalClassDeclarationContext ctx) {
      System.out.println("Class Name: " + ctx.Identifier().getText());
   }

}
