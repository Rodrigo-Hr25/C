import java.util.*;
import java.util.Scanner;

@SuppressWarnings("CheckReturnValue")
public class Execute extends CalculatorBaseVisitor<Double> {

    private final Map<String, Double> memory = new HashMap<>();
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public Double visitProgram(CalculatorParser.ProgramContext ctx) {
        for (var stat : ctx.stat()) {
            visit(stat);
        }
        return null;
    }

    @Override
    public Double visitPrint(CalculatorParser.PrintContext ctx) {
        Double value = visit(ctx.expr());
        System.out.println(value);
        return null;
    }

    @Override
    public Double visitPrintReduce(CalculatorParser.PrintReduceContext ctx) {
        Double value = visit(ctx.expr());
        System.out.println(value);  // sem redução, apenas imprime o Double
        return null;
    }

    @Override
    public Double visitAssign(CalculatorParser.AssignContext ctx) {
        String id = ctx.ID().getText();
        Double value = visit(ctx.expr());
        memory.put(id, value);
        return null;
    }

    @Override
    public Double visitRead(CalculatorParser.ReadContext ctx) {
        String id = ctx.ID().getText();
        System.out.print("Introduza uma fracção (formato a/b): ");
        String input = scanner.nextLine();
        String[] parts = input.trim().split("/");
        int num = Integer.parseInt(parts[0]);
        int den = Integer.parseInt(parts[1]);
        Double frac = num / (double) den;
        memory.put(id, frac);
        return null;
    }

    @Override
    public Double visitExprRead(CalculatorParser.ExprReadContext ctx) {
        System.out.print(ctx.STRING().getText().replaceAll("\"", "") + ": ");
        String input = scanner.nextLine();
        String[] parts = input.trim().split("/");
        int num = Integer.parseInt(parts[0]);
        int den = Integer.parseInt(parts[1]);
        return num / (double) den;
    }

    @Override
    public Double visitExprReduce(CalculatorParser.ExprReduceContext ctx) {
        return visit(ctx.expr());  // sem simplificação
    }

    @Override
    public Double visitExprAddSub(CalculatorParser.ExprAddSubContext ctx) {
        Double left = visit(ctx.expr(0));
        Double right = visit(ctx.expr(1));
        return ctx.op.getText().equals("+") ? left + right : left - right;
    }

    @Override
    public Double visitExprMulDiv(CalculatorParser.ExprMulDivContext ctx) {
        Double left = visit(ctx.expr(0));
        Double right = visit(ctx.expr(1));
        return ctx.op.getText().equals("*") ? left * right : left / right;
    }

    @Override
    public Double visitExprPow(CalculatorParser.ExprPowContext ctx) {
        Double base = visit(ctx.expr(0));
        Double exponent = visit(ctx.expr(1));
        return Math.pow(base, exponent);
    }

    @Override
    public Double visitExprNeg(CalculatorParser.ExprNegContext ctx) {
        return -visit(ctx.expr());
    }

    @Override
    public Double visitExprParen(CalculatorParser.ExprParenContext ctx) {
        return visit(ctx.expr());
    }

    @Override
    public Double visitExprFrac(CalculatorParser.ExprFracContext ctx) {
        int num = Integer.parseInt(ctx.fraction().Integer(0).getText());
        int den = Integer.parseInt(ctx.fraction().Integer(1).getText());
        return num / (double) den;
    }

    @Override
    public Double visitExprInt(CalculatorParser.ExprIntContext ctx) {
        return Double.parseDouble(ctx.Integer().getText());
    }

    @Override
    public Double visitExprID(CalculatorParser.ExprIDContext ctx) {
        String id = ctx.ID().getText();
        if (!memory.containsKey(id)) {
            System.err.println("Erro: variável '" + id + "' não definida.");
            return 0.0;
        }
        return memory.get(id);
    }
}