// Generated from TupleNum.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link TupleNumParser}.
 */
public interface TupleNumListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link TupleNumParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(TupleNumParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link TupleNumParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(TupleNumParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link TupleNumParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterStat(TupleNumParser.StatContext ctx);
	/**
	 * Exit a parse tree produced by {@link TupleNumParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitStat(TupleNumParser.StatContext ctx);
	/**
	 * Enter a parse tree produced by {@link TupleNumParser#print}.
	 * @param ctx the parse tree
	 */
	void enterPrint(TupleNumParser.PrintContext ctx);
	/**
	 * Exit a parse tree produced by {@link TupleNumParser#print}.
	 * @param ctx the parse tree
	 */
	void exitPrint(TupleNumParser.PrintContext ctx);
	/**
	 * Enter a parse tree produced by {@link TupleNumParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(TupleNumParser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link TupleNumParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(TupleNumParser.AssignmentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AverageExpr}
	 * labeled alternative in {@link TupleNumParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAverageExpr(TupleNumParser.AverageExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AverageExpr}
	 * labeled alternative in {@link TupleNumParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAverageExpr(TupleNumParser.AverageExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IdExpr}
	 * labeled alternative in {@link TupleNumParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterIdExpr(TupleNumParser.IdExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IdExpr}
	 * labeled alternative in {@link TupleNumParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitIdExpr(TupleNumParser.IdExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code HeadExpr}
	 * labeled alternative in {@link TupleNumParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterHeadExpr(TupleNumParser.HeadExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code HeadExpr}
	 * labeled alternative in {@link TupleNumParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitHeadExpr(TupleNumParser.HeadExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code TailExpr}
	 * labeled alternative in {@link TupleNumParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterTailExpr(TupleNumParser.TailExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code TailExpr}
	 * labeled alternative in {@link TupleNumParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitTailExpr(TupleNumParser.TailExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AddExpr}
	 * labeled alternative in {@link TupleNumParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAddExpr(TupleNumParser.AddExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AddExpr}
	 * labeled alternative in {@link TupleNumParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAddExpr(TupleNumParser.AddExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SumExpr}
	 * labeled alternative in {@link TupleNumParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterSumExpr(TupleNumParser.SumExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SumExpr}
	 * labeled alternative in {@link TupleNumParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitSumExpr(TupleNumParser.SumExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code TupleExpr}
	 * labeled alternative in {@link TupleNumParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterTupleExpr(TupleNumParser.TupleExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code TupleExpr}
	 * labeled alternative in {@link TupleNumParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitTupleExpr(TupleNumParser.TupleExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link TupleNumParser#tuple}.
	 * @param ctx the parse tree
	 */
	void enterTuple(TupleNumParser.TupleContext ctx);
	/**
	 * Exit a parse tree produced by {@link TupleNumParser#tuple}.
	 * @param ctx the parse tree
	 */
	void exitTuple(TupleNumParser.TupleContext ctx);
}