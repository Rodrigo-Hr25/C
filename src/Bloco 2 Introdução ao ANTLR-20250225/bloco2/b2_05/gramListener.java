// Generated from gram.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link gramParser}.
 */
public interface gramListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link gramParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(gramParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link gramParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(gramParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link gramParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterStat(gramParser.StatContext ctx);
	/**
	 * Exit a parse tree produced by {@link gramParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitStat(gramParser.StatContext ctx);
}