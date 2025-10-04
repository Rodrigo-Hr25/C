// Generated from TupleNum.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link TupleNumParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface TupleNumVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link TupleNumParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(TupleNumParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link TupleNumParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStat(TupleNumParser.StatContext ctx);
	/**
	 * Visit a parse tree produced by {@link TupleNumParser#print}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrint(TupleNumParser.PrintContext ctx);
	/**
	 * Visit a parse tree produced by {@link TupleNumParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment(TupleNumParser.AssignmentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AverageExpr}
	 * labeled alternative in {@link TupleNumParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAverageExpr(TupleNumParser.AverageExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IdExpr}
	 * labeled alternative in {@link TupleNumParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdExpr(TupleNumParser.IdExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code HeadExpr}
	 * labeled alternative in {@link TupleNumParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHeadExpr(TupleNumParser.HeadExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TailExpr}
	 * labeled alternative in {@link TupleNumParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTailExpr(TupleNumParser.TailExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AddExpr}
	 * labeled alternative in {@link TupleNumParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddExpr(TupleNumParser.AddExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SumExpr}
	 * labeled alternative in {@link TupleNumParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSumExpr(TupleNumParser.SumExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TupleExpr}
	 * labeled alternative in {@link TupleNumParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTupleExpr(TupleNumParser.TupleExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link TupleNumParser#tuple}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTuple(TupleNumParser.TupleContext ctx);
}