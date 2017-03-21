// Generated from /Users/tjitze/Desktop/workspace/RankPL/src/com/tr/rp/parser/DefProg.g by ANTLR 4.5

package com.tr.rp.parser;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class DefProgLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.5", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, VAR=31, INT=32, 
		COMMENT=33, SPACE=34, OTHER=35;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
		"T__9", "T__10", "T__11", "T__12", "T__13", "T__14", "T__15", "T__16", 
		"T__17", "T__18", "T__19", "T__20", "T__21", "T__22", "T__23", "T__24", 
		"T__25", "T__26", "T__27", "T__28", "T__29", "VAR", "INT", "COMMENT", 
		"SPACE", "OTHER"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "';'", "'{'", "'}'", "'<<'", "'>>'", "':='", "'if'", "'then'", "'else'", 
		"'while'", "'do'", "'observe'", "'skip'", "'('", "'<'", "'<='", "'>'", 
		"'>='", "'=='", "'!='", "')'", "'&'", "'|'", "'!'", "'true'", "'false'", 
		"'+'", "'-'", "'/'", "'*'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, "VAR", "INT", "COMMENT", "SPACE", 
		"OTHER"
	};
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


	public DefProgLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "DefProg.g"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2%\u00be\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\5\3\6\3\6\3"+
		"\6\3\7\3\7\3\7\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\13"+
		"\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r"+
		"\3\16\3\16\3\16\3\16\3\16\3\17\3\17\3\20\3\20\3\21\3\21\3\21\3\22\3\22"+
		"\3\23\3\23\3\23\3\24\3\24\3\24\3\25\3\25\3\25\3\26\3\26\3\27\3\27\3\30"+
		"\3\30\3\31\3\31\3\32\3\32\3\32\3\32\3\32\3\33\3\33\3\33\3\33\3\33\3\33"+
		"\3\34\3\34\3\35\3\35\3\36\3\36\3\37\3\37\3 \3 \3!\6!\u00ac\n!\r!\16!\u00ad"+
		"\3\"\3\"\7\"\u00b2\n\"\f\"\16\"\u00b5\13\"\3\"\3\"\3#\3#\3#\3#\3$\3$\2"+
		"\2%\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35"+
		"\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\34\67\359\36"+
		";\37= ?!A\"C#E$G%\3\2\6\3\2c|\3\2\62;\4\2\f\f\17\17\5\2\13\f\17\17\"\""+
		"\u00bf\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2"+
		"\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3"+
		"\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2"+
		"\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2"+
		"/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2"+
		"\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2"+
		"G\3\2\2\2\3I\3\2\2\2\5K\3\2\2\2\7M\3\2\2\2\tO\3\2\2\2\13R\3\2\2\2\rU\3"+
		"\2\2\2\17X\3\2\2\2\21[\3\2\2\2\23`\3\2\2\2\25e\3\2\2\2\27k\3\2\2\2\31"+
		"n\3\2\2\2\33v\3\2\2\2\35{\3\2\2\2\37}\3\2\2\2!\177\3\2\2\2#\u0082\3\2"+
		"\2\2%\u0084\3\2\2\2\'\u0087\3\2\2\2)\u008a\3\2\2\2+\u008d\3\2\2\2-\u008f"+
		"\3\2\2\2/\u0091\3\2\2\2\61\u0093\3\2\2\2\63\u0095\3\2\2\2\65\u009a\3\2"+
		"\2\2\67\u00a0\3\2\2\29\u00a2\3\2\2\2;\u00a4\3\2\2\2=\u00a6\3\2\2\2?\u00a8"+
		"\3\2\2\2A\u00ab\3\2\2\2C\u00af\3\2\2\2E\u00b8\3\2\2\2G\u00bc\3\2\2\2I"+
		"J\7=\2\2J\4\3\2\2\2KL\7}\2\2L\6\3\2\2\2MN\7\177\2\2N\b\3\2\2\2OP\7>\2"+
		"\2PQ\7>\2\2Q\n\3\2\2\2RS\7@\2\2ST\7@\2\2T\f\3\2\2\2UV\7<\2\2VW\7?\2\2"+
		"W\16\3\2\2\2XY\7k\2\2YZ\7h\2\2Z\20\3\2\2\2[\\\7v\2\2\\]\7j\2\2]^\7g\2"+
		"\2^_\7p\2\2_\22\3\2\2\2`a\7g\2\2ab\7n\2\2bc\7u\2\2cd\7g\2\2d\24\3\2\2"+
		"\2ef\7y\2\2fg\7j\2\2gh\7k\2\2hi\7n\2\2ij\7g\2\2j\26\3\2\2\2kl\7f\2\2l"+
		"m\7q\2\2m\30\3\2\2\2no\7q\2\2op\7d\2\2pq\7u\2\2qr\7g\2\2rs\7t\2\2st\7"+
		"x\2\2tu\7g\2\2u\32\3\2\2\2vw\7u\2\2wx\7m\2\2xy\7k\2\2yz\7r\2\2z\34\3\2"+
		"\2\2{|\7*\2\2|\36\3\2\2\2}~\7>\2\2~ \3\2\2\2\177\u0080\7>\2\2\u0080\u0081"+
		"\7?\2\2\u0081\"\3\2\2\2\u0082\u0083\7@\2\2\u0083$\3\2\2\2\u0084\u0085"+
		"\7@\2\2\u0085\u0086\7?\2\2\u0086&\3\2\2\2\u0087\u0088\7?\2\2\u0088\u0089"+
		"\7?\2\2\u0089(\3\2\2\2\u008a\u008b\7#\2\2\u008b\u008c\7?\2\2\u008c*\3"+
		"\2\2\2\u008d\u008e\7+\2\2\u008e,\3\2\2\2\u008f\u0090\7(\2\2\u0090.\3\2"+
		"\2\2\u0091\u0092\7~\2\2\u0092\60\3\2\2\2\u0093\u0094\7#\2\2\u0094\62\3"+
		"\2\2\2\u0095\u0096\7v\2\2\u0096\u0097\7t\2\2\u0097\u0098\7w\2\2\u0098"+
		"\u0099\7g\2\2\u0099\64\3\2\2\2\u009a\u009b\7h\2\2\u009b\u009c\7c\2\2\u009c"+
		"\u009d\7n\2\2\u009d\u009e\7u\2\2\u009e\u009f\7g\2\2\u009f\66\3\2\2\2\u00a0"+
		"\u00a1\7-\2\2\u00a18\3\2\2\2\u00a2\u00a3\7/\2\2\u00a3:\3\2\2\2\u00a4\u00a5"+
		"\7\61\2\2\u00a5<\3\2\2\2\u00a6\u00a7\7,\2\2\u00a7>\3\2\2\2\u00a8\u00a9"+
		"\t\2\2\2\u00a9@\3\2\2\2\u00aa\u00ac\t\3\2\2\u00ab\u00aa\3\2\2\2\u00ac"+
		"\u00ad\3\2\2\2\u00ad\u00ab\3\2\2\2\u00ad\u00ae\3\2\2\2\u00aeB\3\2\2\2"+
		"\u00af\u00b3\7%\2\2\u00b0\u00b2\n\4\2\2\u00b1\u00b0\3\2\2\2\u00b2\u00b5"+
		"\3\2\2\2\u00b3\u00b1\3\2\2\2\u00b3\u00b4\3\2\2\2\u00b4\u00b6\3\2\2\2\u00b5"+
		"\u00b3\3\2\2\2\u00b6\u00b7\b\"\2\2\u00b7D\3\2\2\2\u00b8\u00b9\t\5\2\2"+
		"\u00b9\u00ba\3\2\2\2\u00ba\u00bb\b#\2\2\u00bbF\3\2\2\2\u00bc\u00bd\13"+
		"\2\2\2\u00bdH\3\2\2\2\5\2\u00ad\u00b3\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}