// Generated from Calculator.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link CalculatorParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface CalculatorVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link CalculatorParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(CalculatorParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Print}
	 * labeled alternative in {@link CalculatorParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrint(CalculatorParser.PrintContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PrintReduce}
	 * labeled alternative in {@link CalculatorParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrintReduce(CalculatorParser.PrintReduceContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Read}
	 * labeled alternative in {@link CalculatorParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRead(CalculatorParser.ReadContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Assign}
	 * labeled alternative in {@link CalculatorParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssign(CalculatorParser.AssignContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExprFrac}
	 * labeled alternative in {@link CalculatorParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprFrac(CalculatorParser.ExprFracContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExprAddSub}
	 * labeled alternative in {@link CalculatorParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprAddSub(CalculatorParser.ExprAddSubContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExprPow}
	 * labeled alternative in {@link CalculatorParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprPow(CalculatorParser.ExprPowContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExprRead}
	 * labeled alternative in {@link CalculatorParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprRead(CalculatorParser.ExprReadContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExprParen}
	 * labeled alternative in {@link CalculatorParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprParen(CalculatorParser.ExprParenContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExprMulDiv}
	 * labeled alternative in {@link CalculatorParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprMulDiv(CalculatorParser.ExprMulDivContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExprInt}
	 * labeled alternative in {@link CalculatorParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprInt(CalculatorParser.ExprIntContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExprNeg}
	 * labeled alternative in {@link CalculatorParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprNeg(CalculatorParser.ExprNegContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExprReduce}
	 * labeled alternative in {@link CalculatorParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprReduce(CalculatorParser.ExprReduceContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExprID}
	 * labeled alternative in {@link CalculatorParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprID(CalculatorParser.ExprIDContext ctx);
	/**
	 * Visit a parse tree produced by {@link CalculatorParser#fraction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFraction(CalculatorParser.FractionContext ctx);
}