import java.util.*;

@SuppressWarnings("CheckReturnValue")
public class Execute extends TupleNumBaseVisitor<String> {

    private java.util.Map<String, java.util.ArrayList<Double>> variables = new java.util.HashMap<>();

    // @Override
    // public String visitProgram(TupleNumParser.ProgramContext ctx) {
    //     StringBuilder result = new StringBuilder();
    //     for (var stat : ctx.stat()) {
    //         result.append(visit(stat)).append("\n");
    //     }
    //     return result.toString().trim();
    // }

    // @Override
    // public String visitStat(TupleNumParser.StatContext ctx) {
    //     return visit(ctx.getChild(0));
    // }

    @Override
    public String visitPrint(TupleNumParser.PrintContext ctx) {
        String value = visit(ctx.expression());
        if (value != null && !value.isEmpty()) {
            System.out.println(value);
        }
        return value != null ? value : "";
    }

    @Override
    public String visitAssignment(TupleNumParser.AssignmentContext ctx) {
        String id = ctx.ID().getText();
        String value = visit(ctx.expression());
        if (value.startsWith("[")) {
            java.util.ArrayList<Double> numbers = parseTuple(value);
            variables.put(id, numbers);
        }
        return "";
    }

    @Override
    public String visitAverageExpr(TupleNumParser.AverageExprContext ctx) {
        String tupleStr = visit(ctx.expression());
        java.util.ArrayList<Double> numbers = parseTuple(tupleStr);
        if (numbers.isEmpty()) return "0.0";
        double sum = numbers.stream().mapToDouble(Double::doubleValue).sum();
        return String.format("%.1f", sum / numbers.size());
    }

    @Override
    public String visitIdExpr(TupleNumParser.IdExprContext ctx) {
        String id = ctx.ID().getText();
        java.util.ArrayList<Double> numbers = variables.get(id);
        return numbers != null ? numbers.toString() : "[]";
    }

    @Override
    public String visitHeadExpr(TupleNumParser.HeadExprContext ctx) {
        String tupleStr = visit(ctx.expression());
        java.util.ArrayList<Double> numbers = parseTuple(tupleStr);
        return numbers.isEmpty() ? "[]" : String.format("%.1f", numbers.get(0));
    }

    @Override
    public String visitTailExpr(TupleNumParser.TailExprContext ctx) {
        String tupleStr = visit(ctx.expression());
        java.util.ArrayList<Double> numbers = parseTuple(tupleStr);
        if (numbers.size() <= 1) return "[]";
        return numbers.subList(1, numbers.size()).toString();
    }

    @Override
    public String visitAddExpr(TupleNumParser.AddExprContext ctx) {
        String left = visit(ctx.expression(0));
        String right = visit(ctx.expression(1));
        java.util.ArrayList<Double> leftNumbers = parseTuple(left);
        java.util.ArrayList<Double> rightNumbers = parseTuple(right);

        java.util.ArrayList<Double> result = new java.util.ArrayList<>(leftNumbers);
        result.addAll(rightNumbers);
        return result.toString();
    }

    @Override
    public String visitSumExpr(TupleNumParser.SumExprContext ctx) {
        String tupleStr = visit(ctx.expression());
        java.util.ArrayList<Double> numbers = parseTuple(tupleStr);
        return String.format("%.1f", numbers.stream().mapToDouble(Double::doubleValue).sum());
    }

    @Override
    public String visitTupleExpr(TupleNumParser.TupleExprContext ctx) {
        return visit(ctx.tuple());
    }

    @Override
    public String visitTuple(TupleNumParser.TupleContext ctx) {
        java.util.ArrayList<String> numbers = new java.util.ArrayList<>();
        for (var num : ctx.NUMBER()) {
            numbers.add(num.getText());
        }
        return "[" + String.join(", ", numbers) + "]";
    }

    private java.util.ArrayList<Double> parseTuple(String tupleStr) {
        java.util.ArrayList<Double> result = new java.util.ArrayList<>();
        if (tupleStr == null || !tupleStr.startsWith("[") || !tupleStr.endsWith("]")) {
            return result;
        }
        tupleStr = tupleStr.substring(1, tupleStr.length() - 1);
        if (tupleStr.isEmpty()) return result;
        for (String num : tupleStr.split(", ")) {
            result.add(Double.parseDouble(num));
        }
        return result;
    }
}