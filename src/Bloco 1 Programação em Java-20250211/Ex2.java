import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;

public class Ex2 {

    private static Map<String, Double> variaveis = new HashMap<>();

    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);

        while (sc.hasNextLine()) {
            String linha = sc.nextLine().trim();
            if (linha.isEmpty()) {
                continue;
            }

            if (linha.contains("=")) {
                atribuirValor(linha);
            } else {
                processarExpressao(linha);
            }
        }

        sc.close();
    }

    public static void atribuirValor(String linha) {
        String[] parts = linha.split("=", 2);
        if (parts.length < 2) {
            System.err.println("Atribuição inválida: " + linha);
            return;
        }

        String var = parts[0].trim();        // ex.: "n"
        String expressao = parts[1].trim();  // ex.: "n + 1"

        double valorCalculado = avaliarExpressao(expressao);
        variaveis.put(var, valorCalculado);

        System.out.println(var + " = " + valorCalculado);
    }

    public static void processarExpressao(String linha) {
        double valorCalculado = avaliarExpressao(linha);
        System.out.println(valorCalculado);
    }

    public static double avaliarExpressao(String expressao) {
        String[] tokens = expressao.split("\\s+");
        if (tokens.length == 0) {
            return 0;
        }

        double resultado = obterValor(tokens[0]);

        int i = 1;
        while (i < tokens.length - 1) {
            String operador = tokens[i];
            double proximoValor = obterValor(tokens[i + 1]);

            switch (operador) {
                case "+":
                    resultado = resultado + proximoValor;
                    break;
                case "-":
                    resultado = resultado - proximoValor;
                    break;
                case "*":
                    resultado = resultado * proximoValor;
                    break;
                case "/":
                    resultado = resultado / proximoValor;
                    break;
                default:
                    System.err.println("Operador inválido: " + operador);
                    break;
            }
            i += 2;
        }

        return resultado;
    }

    private static double obterValor(String token) {
        try {
            return Double.parseDouble(token);
        } catch (NumberFormatException e) {
            if (variaveis.containsKey(token)) {
                return variaveis.get(token);
            } else {
                System.err.println("Variável '" + token + "' não está definida. Usando 0 como valor.");
                return 0;
            }
        }
    }
}
