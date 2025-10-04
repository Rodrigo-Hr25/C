import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;

public class Ex4 {

    public static void main(String[] args) {

        Map<String, String> dictionary = new HashMap<>();

        Scanner sc = new Scanner(System.in);

        try (Scanner fileScanner = new Scanner(new File("bloco1/numbers.txt"))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }

                String[] parts = line.split("\\s*-\\s*");
                if (parts.length == 2) {
                    String number = parts[0].trim();
                    String word = parts[1].trim().toLowerCase();
                    dictionary.put(word, number);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Ficheiro bloco1/numbers.txt não encontrado.");
            System.exit(1);
        }

        while (sc.hasNextLine()) {
            String inputLine = sc.nextLine();
            String[] words = inputLine.split("\\s+");

            StringBuilder outputLine = new StringBuilder();

            for (String token : words) {
                String tokenLower = token.toLowerCase();

                if (dictionary.containsKey(tokenLower)) {
                    outputLine.append(dictionary.get(tokenLower)).append(" ");
                } else {
                    outputLine.append(token).append(" ");
                }
            }
            System.out.println(outputLine.toString().trim());
        }
        sc.close();
    }
}
