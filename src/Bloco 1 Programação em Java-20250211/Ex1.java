import java.util.Scanner;

public class Ex1 {
    public static void main(String[] args) throws Exception{

        System.out.println("Digite a conta que deseja fazer: ");

        Scanner sc = new Scanner(System.in);

        String op = sc.nextLine();
        String[] parts = op.split(" ");
        String num1 = parts[0];
        String num2 = parts[2];
        String operador = parts[1];

        double n1 = Double.parseDouble(num1);
        double n2 = Double.parseDouble(num2);

        if(operador.equals("+")){
            System.out.println(n1 + n2);
        }else if(operador.equals("-")){
            System.out.println(n1 - n2);
        }else if(operador.equals("*")){
            System.out.println(n1 * n2);
        }else if(operador.equals("/")){
            System.out.println(n1 / n2);
        }

        sc.close();

    }
    
}
