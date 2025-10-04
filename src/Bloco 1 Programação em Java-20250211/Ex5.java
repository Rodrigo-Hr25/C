import java.util.*;
import java.io.*;

public class Ex5 {
    
    private static final Map<String, Integer> numeros = new HashMap<>();

    private static void CarregarFicheiro(String ficheiro) {
        try (BufferedReader br = new BufferedReader(new FileReader(ficheiro))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] parts = linha.split(" - ");
                if (parts.length == 2) {
                    int valor = Integer.parseInt(parts[0].trim());
                    String palavra = parts[1].trim().toLowerCase();
                    numeros.put(palavra, valor);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o ficheiro: " + e.getMessage());
            System.exit(1);
        }
    }

    public static int converter(String texto) {
        String[] palavras = texto.split("\\s+");
        int resultado = 0;
        int acumulador = 0;

        for (String palavra : palavras) {
            if (numeros.containsKey(palavra)) {
                int valor = numeros.get(palavra);

                if (valor == 100) {
                    acumulador *= 100;
                } else if (valor >= 1000) {
                    resultado += acumulador * valor;
                    acumulador = 0;
                } else {
                    acumulador += valor;
                }
            } else if (palavra.equals("and")) {
                continue;
            } else {
                System.err.println("Palavra inválida: " + palavra);
                return -1;
            }
        }

        return resultado + acumulador;
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Uso: java Ex5 <ficheiro>");
            System.exit(1);
        }

        String ficheiro = "bloco1/numbers.txt";
        CarregarFicheiro(ficheiro);

        Scanner sc = new Scanner(System.in);
        System.out.println("Digite um número por extenso:");
        String entrada = sc.nextLine();
        sc.close();

        int numero = converter(entrada);
        System.out.println(entrada + " -> " + numero);
    }
}
