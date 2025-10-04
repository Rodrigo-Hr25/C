// Generated from gram.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link gramParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface gramVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link gramParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(gramParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link gramParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStat(gramParser.StatContext ctx);
}