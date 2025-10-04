import java.util.List;

// @SuppressWarnings("CheckReturnValue")
// public class Execute extends HelloBaseVisitor<String> {

//    // @Override public String visitR(HelloParser.RContext ctx) {
//    //    String res = null;
//    //    return visitChildren(ctx);
//    //    //return res;
//    // }

//    // @Override public String visitGreetings(HelloParser.GreetingsContext ctx) {
//    //    String id = ctx.ID().getText(); 
//    //      String result = "Olá " + id;
//    //      System.out.println(result);
//    //      return result;
//    // }

//    // @Override public String visitFarewell (HelloParser.FarewellContext ctx) {
//    //    String id = ctx.ID().getText(); 
//    //      String result = "Adeus " + id;
//    //      System.out.println(result);
//    //      return result;
//    // }

//    @Override public String visitGreetings(HelloParser.GreetingsContext ctx) {
//       StringBuilder res = new StringBuilder("Hello!");
//       for (TerminalNode id : ctx.ID()) {
//          res.append(" ").append(id.getText());
//       }
//       System.out.println(res.toString());
//       return visitChildren(ctx);
//    }

//    @Override public String visitBye(HelloParser.ByeContext ctx) {
//       StringBuilder res = new StringBuilder("Bye!");
//       for (TerminalNode id : ctx.ID()) {
//          res.append(" ").append(id.getText());
//       }
//       System.out.println(res.toString());
//       return visitChildren(ctx);
//    }
// }


@SuppressWarnings("CheckReturnValue")
public class Execute extends HelloBaseVisitor<String> {

   @Override
   public String visitStart(HelloParser.StartContext ctx) {
      return visitChildren(ctx);  // Visita todas as regras repetidas
   }

   @Override 
   public String visitGreetings(HelloParser.GreetingsContext ctx) {
      String id = ctx.ID().getText(); 
      String result = "Olá " + id;
      System.out.println(result);
      return result;
   }

   @Override 
   public String visitFarewell(HelloParser.FarewellContext ctx) {
      String id = ctx.ID().getText(); 
      String result = "Adeus " + id;
      System.out.println(result);
      return result;
   }
}
