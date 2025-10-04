// Generated from Vector.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class VectorLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		VECTOR=10, NUMBER=11, ID=12, WS=13, COMMENT=14, ERROR=15;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
			"VECTOR", "NUMBER", "ID", "WS", "COMMENT", "ERROR"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "';'", "'show'", "'->'", "'+'", "'-'", "'*'", "'div'", "'('", "')'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, "VECTOR", 
			"NUMBER", "ID", "WS", "COMMENT", "ERROR"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public VectorLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Vector.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\u0004\u0000\u000fm\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002\u0001"+
		"\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004"+
		"\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007"+
		"\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b"+
		"\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0001"+
		"\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001"+
		"\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001\t\u0001"+
		"\t\u0001\t\u0001\t\u0005\t<\b\t\n\t\f\t?\t\t\u0003\tA\b\t\u0001\t\u0001"+
		"\t\u0001\n\u0004\nF\b\n\u000b\n\f\nG\u0001\n\u0001\n\u0004\nL\b\n\u000b"+
		"\n\f\nM\u0003\nP\b\n\u0001\u000b\u0004\u000bS\b\u000b\u000b\u000b\f\u000b"+
		"T\u0001\u000b\u0005\u000bX\b\u000b\n\u000b\f\u000b[\t\u000b\u0001\f\u0001"+
		"\f\u0001\f\u0001\f\u0001\r\u0001\r\u0005\rc\b\r\n\r\f\rf\t\r\u0001\r\u0001"+
		"\r\u0001\r\u0001\r\u0001\u000e\u0001\u000e\u0002=d\u0000\u000f\u0001\u0001"+
		"\u0003\u0002\u0005\u0003\u0007\u0004\t\u0005\u000b\u0006\r\u0007\u000f"+
		"\b\u0011\t\u0013\n\u0015\u000b\u0017\f\u0019\r\u001b\u000e\u001d\u000f"+
		"\u0001\u0000\u0003\u0001\u000009\u0001\u0000az\u0003\u0000\t\n\r\r  t"+
		"\u0000\u0001\u0001\u0000\u0000\u0000\u0000\u0003\u0001\u0000\u0000\u0000"+
		"\u0000\u0005\u0001\u0000\u0000\u0000\u0000\u0007\u0001\u0000\u0000\u0000"+
		"\u0000\t\u0001\u0000\u0000\u0000\u0000\u000b\u0001\u0000\u0000\u0000\u0000"+
		"\r\u0001\u0000\u0000\u0000\u0000\u000f\u0001\u0000\u0000\u0000\u0000\u0011"+
		"\u0001\u0000\u0000\u0000\u0000\u0013\u0001\u0000\u0000\u0000\u0000\u0015"+
		"\u0001\u0000\u0000\u0000\u0000\u0017\u0001\u0000\u0000\u0000\u0000\u0019"+
		"\u0001\u0000\u0000\u0000\u0000\u001b\u0001\u0000\u0000\u0000\u0000\u001d"+
		"\u0001\u0000\u0000\u0000\u0001\u001f\u0001\u0000\u0000\u0000\u0003!\u0001"+
		"\u0000\u0000\u0000\u0005&\u0001\u0000\u0000\u0000\u0007)\u0001\u0000\u0000"+
		"\u0000\t+\u0001\u0000\u0000\u0000\u000b-\u0001\u0000\u0000\u0000\r/\u0001"+
		"\u0000\u0000\u0000\u000f3\u0001\u0000\u0000\u0000\u00115\u0001\u0000\u0000"+
		"\u0000\u00137\u0001\u0000\u0000\u0000\u0015E\u0001\u0000\u0000\u0000\u0017"+
		"R\u0001\u0000\u0000\u0000\u0019\\\u0001\u0000\u0000\u0000\u001b`\u0001"+
		"\u0000\u0000\u0000\u001dk\u0001\u0000\u0000\u0000\u001f \u0005;\u0000"+
		"\u0000 \u0002\u0001\u0000\u0000\u0000!\"\u0005s\u0000\u0000\"#\u0005h"+
		"\u0000\u0000#$\u0005o\u0000\u0000$%\u0005w\u0000\u0000%\u0004\u0001\u0000"+
		"\u0000\u0000&\'\u0005-\u0000\u0000\'(\u0005>\u0000\u0000(\u0006\u0001"+
		"\u0000\u0000\u0000)*\u0005+\u0000\u0000*\b\u0001\u0000\u0000\u0000+,\u0005"+
		"-\u0000\u0000,\n\u0001\u0000\u0000\u0000-.\u0005*\u0000\u0000.\f\u0001"+
		"\u0000\u0000\u0000/0\u0005d\u0000\u000001\u0005i\u0000\u000012\u0005v"+
		"\u0000\u00002\u000e\u0001\u0000\u0000\u000034\u0005(\u0000\u00004\u0010"+
		"\u0001\u0000\u0000\u000056\u0005)\u0000\u00006\u0012\u0001\u0000\u0000"+
		"\u00007@\u0005[\u0000\u00008=\u0003\u0015\n\u00009:\u0005,\u0000\u0000"+
		":<\u0003\u0015\n\u0000;9\u0001\u0000\u0000\u0000<?\u0001\u0000\u0000\u0000"+
		"=>\u0001\u0000\u0000\u0000=;\u0001\u0000\u0000\u0000>A\u0001\u0000\u0000"+
		"\u0000?=\u0001\u0000\u0000\u0000@8\u0001\u0000\u0000\u0000@A\u0001\u0000"+
		"\u0000\u0000AB\u0001\u0000\u0000\u0000BC\u0005]\u0000\u0000C\u0014\u0001"+
		"\u0000\u0000\u0000DF\u0007\u0000\u0000\u0000ED\u0001\u0000\u0000\u0000"+
		"FG\u0001\u0000\u0000\u0000GE\u0001\u0000\u0000\u0000GH\u0001\u0000\u0000"+
		"\u0000HO\u0001\u0000\u0000\u0000IK\u0005.\u0000\u0000JL\u0007\u0000\u0000"+
		"\u0000KJ\u0001\u0000\u0000\u0000LM\u0001\u0000\u0000\u0000MK\u0001\u0000"+
		"\u0000\u0000MN\u0001\u0000\u0000\u0000NP\u0001\u0000\u0000\u0000OI\u0001"+
		"\u0000\u0000\u0000OP\u0001\u0000\u0000\u0000P\u0016\u0001\u0000\u0000"+
		"\u0000QS\u0007\u0001\u0000\u0000RQ\u0001\u0000\u0000\u0000ST\u0001\u0000"+
		"\u0000\u0000TR\u0001\u0000\u0000\u0000TU\u0001\u0000\u0000\u0000UY\u0001"+
		"\u0000\u0000\u0000VX\u0007\u0000\u0000\u0000WV\u0001\u0000\u0000\u0000"+
		"X[\u0001\u0000\u0000\u0000YW\u0001\u0000\u0000\u0000YZ\u0001\u0000\u0000"+
		"\u0000Z\u0018\u0001\u0000\u0000\u0000[Y\u0001\u0000\u0000\u0000\\]\u0007"+
		"\u0002\u0000\u0000]^\u0001\u0000\u0000\u0000^_\u0006\f\u0000\u0000_\u001a"+
		"\u0001\u0000\u0000\u0000`d\u0005#\u0000\u0000ac\t\u0000\u0000\u0000ba"+
		"\u0001\u0000\u0000\u0000cf\u0001\u0000\u0000\u0000de\u0001\u0000\u0000"+
		"\u0000db\u0001\u0000\u0000\u0000eg\u0001\u0000\u0000\u0000fd\u0001\u0000"+
		"\u0000\u0000gh\u0005\n\u0000\u0000hi\u0001\u0000\u0000\u0000ij\u0006\r"+
		"\u0000\u0000j\u001c\u0001\u0000\u0000\u0000kl\t\u0000\u0000\u0000l\u001e"+
		"\u0001\u0000\u0000\u0000\t\u0000=@GMOTYd\u0001\u0006\u0000\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}