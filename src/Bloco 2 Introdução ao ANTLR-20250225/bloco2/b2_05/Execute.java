import java.util.*;

@SuppressWarnings("CheckReturnValue")
public class Execute extends gramBaseVisitor<Void> {

    // Tabela para armazenar os números e suas representações por extenso
    private final Map<Integer, String> numberWords = new HashMap<>();

    @Override
    public Void visitProgram(gramParser.ProgramContext ctx) {
        for (var statCtx : ctx.stat()) {
            visit(statCtx);  // Processa cada instrução
        }

        // Mostrar o resultado final
        System.out.println("Números mapeados:");
        for (var entry : numberWords.entrySet()) {
            System.out.println(entry.getKey() + " → " + entry.getValue());
        }

        return null;
    }

    @Override
   public Void visitStat(gramParser.StatContext ctx) {
      if (ctx.getChildCount() == 3 && ctx.getChild(1).getText().equals("-")) {
         int number = Integer.parseInt(ctx.getChild(0).getText());
         String word = ctx.getChild(2).getText();

         numberWords.put(number, word);  // Armazena no array associativo
      } else {
         System.err.println("Erro na linha: " + ctx.getText());
      }
      return null;
   }

}