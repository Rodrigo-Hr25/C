import java.util.Scanner;
import java.util.Stack;

public class Ex3 {

    public static void main(String[] args) {

        Stack<Double> pilha = new Stack<>();
        Scanner sc = new Scanner(System.in);

        while(sc.hasNextLine()) {
            String token = sc.next();

            try{
                double number = Double.parseDouble(token);
                pilha.push(number);
                System.out.println("Stack: " + pilha);
            } catch(NumberFormatException e) {
                if (pilha.size() < 2){
                    System.err.println("Operação inválida: " + token);
                    System.exit(1);
                }

                double b = pilha.pop();
                double a = pilha.pop();
                double result;

                switch(token){
                    case "+":
                        result = a + b;
                        break;
                    case "-":
                        result = a - b;
                        break;
                    case "*":
                        result = a * b;
                        break;
                    case "/":
                        if(b == 0){
                            System.err.println("Divisão por zero");
                            System.exit(1);
                        }
                        result = a / b;
                        break;
                    default:
                        System.err.println("Operação inválida: " + token);
                        System.exit(1);
                    return;
                }

                pilha.push(result);
                System.out.println("Stack: " + pilha);

            }
        }
        sc.close();
    }

}
