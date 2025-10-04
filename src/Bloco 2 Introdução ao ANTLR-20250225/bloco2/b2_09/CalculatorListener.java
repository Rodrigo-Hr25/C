// Generated from Calculator.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link CalculatorParser}.
 */
public interface CalculatorListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link CalculatorParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(CalculatorParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link CalculatorParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(CalculatorParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Print}
	 * labeled alternative in {@link CalculatorParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterPrint(CalculatorParser.PrintContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Print}
	 * labeled alternative in {@link CalculatorParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitPrint(CalculatorParser.PrintContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PrintReduce}
	 * labeled alternative in {@link CalculatorParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterPrintReduce(CalculatorParser.PrintReduceContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PrintReduce}
	 * labeled alternative in {@link CalculatorParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitPrintReduce(CalculatorParser.PrintReduceContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Read}
	 * labeled alternative in {@link CalculatorParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterRead(CalculatorParser.ReadContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Read}
	 * labeled alternative in {@link CalculatorParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitRead(CalculatorParser.ReadContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Assign}
	 * labeled alternative in {@link CalculatorParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterAssign(CalculatorParser.AssignContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Assign}
	 * labeled alternative in {@link CalculatorParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitAssign(CalculatorParser.AssignContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExprFrac}
	 * labeled alternative in {@link CalculatorParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExprFrac(CalculatorParser.ExprFracContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExprFrac}
	 * labeled alternative in {@link CalculatorParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExprFrac(CalculatorParser.ExprFracContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExprAddSub}
	 * labeled alternative in {@link CalculatorParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExprAddSub(CalculatorParser.ExprAddSubContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExprAddSub}
	 * labeled alternative in {@link CalculatorParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExprAddSub(CalculatorParser.ExprAddSubContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExprPow}
	 * labeled alternative in {@link CalculatorParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExprPow(CalculatorParser.ExprPowContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExprPow}
	 * labeled alternative in {@link CalculatorParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExprPow(CalculatorParser.ExprPowContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExprRead}
	 * labeled alternative in {@link CalculatorParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExprRead(CalculatorParser.ExprReadContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExprRead}
	 * labeled alternative in {@link CalculatorParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExprRead(CalculatorParser.ExprReadContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExprParen}
	 * labeled alternative in {@link CalculatorParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExprParen(CalculatorParser.ExprParenContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExprParen}
	 * labeled alternative in {@link CalculatorParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExprParen(CalculatorParser.ExprParenContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExprMulDiv}
	 * labeled alternative in {@link CalculatorParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExprMulDiv(CalculatorParser.ExprMulDivContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExprMulDiv}
	 * labeled alternative in {@link CalculatorParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExprMulDiv(CalculatorParser.ExprMulDivContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExprInt}
	 * labeled alternative in {@link CalculatorParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExprInt(CalculatorParser.ExprIntContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExprInt}
	 * labeled alternative in {@link CalculatorParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExprInt(CalculatorParser.ExprIntContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExprNeg}
	 * labeled alternative in {@link CalculatorParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExprNeg(CalculatorParser.ExprNegContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExprNeg}
	 * labeled alternative in {@link CalculatorParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExprNeg(CalculatorParser.ExprNegContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExprReduce}
	 * labeled alternative in {@link CalculatorParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExprReduce(CalculatorParser.ExprReduceContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExprReduce}
	 * labeled alternative in {@link CalculatorParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExprReduce(CalculatorParser.ExprReduceContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExprID}
	 * labeled alternative in {@link CalculatorParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExprID(CalculatorParser.ExprIDContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExprID}
	 * labeled alternative in {@link CalculatorParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExprID(CalculatorParser.ExprIDContext ctx);
	/**
	 * Enter a parse tree produced by {@link CalculatorParser#fraction}.
	 * @param ctx the parse tree
	 */
	void enterFraction(CalculatorParser.FractionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CalculatorParser#fraction}.
	 * @param ctx the parse tree
	 */
	void exitFraction(CalculatorParser.FractionContext ctx);
}