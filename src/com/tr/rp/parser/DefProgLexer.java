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
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, VAR=33, INT=34, COMMENT=35, SPACE=36, OTHER=37;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
		"T__9", "T__10", "T__11", "T__12", "T__13", "T__14", "T__15", "T__16", 
		"T__17", "T__18", "T__19", "T__20", "T__21", "T__22", "T__23", "T__24", 
		"T__25", "T__26", "T__27", "T__28", "T__29", "T__30", "T__31", "VAR", 
		"INT", "COMMENT", "SPACE", "OTHER"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "';'", "'{'", "'}'", "'<<'", "'>>'", "':='", "'IF'", "'THEN'", "'ELSE'", 
		"'WHILE'", "'DO'", "'OBSERVE'", "'SKIP'", "'('", "'<'", "'<='", "'>'", 
		"'>='", "'=='", "'!='", "')'", "'&'", "'|'", "'^'", "'!'", "'true'", "'false'", 
		"'+'", "'-'", "'/'", "'%'", "'*'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, "VAR", "INT", "COMMENT", 
		"SPACE", "OTHER"
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\'\u00cb\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3"+
		"\5\3\6\3\6\3\6\3\7\3\7\3\7\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n"+
		"\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r"+
		"\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\17\3\17\3\20\3\20\3\21\3\21\3"+
		"\21\3\22\3\22\3\23\3\23\3\23\3\24\3\24\3\24\3\25\3\25\3\25\3\26\3\26\3"+
		"\27\3\27\3\30\3\30\3\31\3\31\3\32\3\32\3\33\3\33\3\33\3\33\3\33\3\34\3"+
		"\34\3\34\3\34\3\34\3\34\3\35\3\35\3\36\3\36\3\37\3\37\3 \3 \3!\3!\3\""+
		"\3\"\7\"\u00b3\n\"\f\"\16\"\u00b6\13\"\3#\6#\u00b9\n#\r#\16#\u00ba\3$"+
		"\3$\7$\u00bf\n$\f$\16$\u00c2\13$\3$\3$\3%\3%\3%\3%\3&\3&\2\2\'\3\3\5\4"+
		"\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22"+
		"#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\34\67\359\36;\37= ?!A\"C"+
		"#E$G%I&K\'\3\2\7\3\2c|\4\2\62;c|\3\2\62;\4\2\f\f\17\17\5\2\13\f\17\17"+
		"\"\"\u00cd\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2"+
		"\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27"+
		"\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2"+
		"\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2"+
		"\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2"+
		"\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2"+
		"\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\3M\3\2\2\2\5O\3\2\2\2\7Q\3\2\2\2\tS"+
		"\3\2\2\2\13V\3\2\2\2\rY\3\2\2\2\17\\\3\2\2\2\21_\3\2\2\2\23d\3\2\2\2\25"+
		"i\3\2\2\2\27o\3\2\2\2\31r\3\2\2\2\33z\3\2\2\2\35\177\3\2\2\2\37\u0081"+
		"\3\2\2\2!\u0083\3\2\2\2#\u0086\3\2\2\2%\u0088\3\2\2\2\'\u008b\3\2\2\2"+
		")\u008e\3\2\2\2+\u0091\3\2\2\2-\u0093\3\2\2\2/\u0095\3\2\2\2\61\u0097"+
		"\3\2\2\2\63\u0099\3\2\2\2\65\u009b\3\2\2\2\67\u00a0\3\2\2\29\u00a6\3\2"+
		"\2\2;\u00a8\3\2\2\2=\u00aa\3\2\2\2?\u00ac\3\2\2\2A\u00ae\3\2\2\2C\u00b0"+
		"\3\2\2\2E\u00b8\3\2\2\2G\u00bc\3\2\2\2I\u00c5\3\2\2\2K\u00c9\3\2\2\2M"+
		"N\7=\2\2N\4\3\2\2\2OP\7}\2\2P\6\3\2\2\2QR\7\177\2\2R\b\3\2\2\2ST\7>\2"+
		"\2TU\7>\2\2U\n\3\2\2\2VW\7@\2\2WX\7@\2\2X\f\3\2\2\2YZ\7<\2\2Z[\7?\2\2"+
		"[\16\3\2\2\2\\]\7K\2\2]^\7H\2\2^\20\3\2\2\2_`\7V\2\2`a\7J\2\2ab\7G\2\2"+
		"bc\7P\2\2c\22\3\2\2\2de\7G\2\2ef\7N\2\2fg\7U\2\2gh\7G\2\2h\24\3\2\2\2"+
		"ij\7Y\2\2jk\7J\2\2kl\7K\2\2lm\7N\2\2mn\7G\2\2n\26\3\2\2\2op\7F\2\2pq\7"+
		"Q\2\2q\30\3\2\2\2rs\7Q\2\2st\7D\2\2tu\7U\2\2uv\7G\2\2vw\7T\2\2wx\7X\2"+
		"\2xy\7G\2\2y\32\3\2\2\2z{\7U\2\2{|\7M\2\2|}\7K\2\2}~\7R\2\2~\34\3\2\2"+
		"\2\177\u0080\7*\2\2\u0080\36\3\2\2\2\u0081\u0082\7>\2\2\u0082 \3\2\2\2"+
		"\u0083\u0084\7>\2\2\u0084\u0085\7?\2\2\u0085\"\3\2\2\2\u0086\u0087\7@"+
		"\2\2\u0087$\3\2\2\2\u0088\u0089\7@\2\2\u0089\u008a\7?\2\2\u008a&\3\2\2"+
		"\2\u008b\u008c\7?\2\2\u008c\u008d\7?\2\2\u008d(\3\2\2\2\u008e\u008f\7"+
		"#\2\2\u008f\u0090\7?\2\2\u0090*\3\2\2\2\u0091\u0092\7+\2\2\u0092,\3\2"+
		"\2\2\u0093\u0094\7(\2\2\u0094.\3\2\2\2\u0095\u0096\7~\2\2\u0096\60\3\2"+
		"\2\2\u0097\u0098\7`\2\2\u0098\62\3\2\2\2\u0099\u009a\7#\2\2\u009a\64\3"+
		"\2\2\2\u009b\u009c\7v\2\2\u009c\u009d\7t\2\2\u009d\u009e\7w\2\2\u009e"+
		"\u009f\7g\2\2\u009f\66\3\2\2\2\u00a0\u00a1\7h\2\2\u00a1\u00a2\7c\2\2\u00a2"+
		"\u00a3\7n\2\2\u00a3\u00a4\7u\2\2\u00a4\u00a5\7g\2\2\u00a58\3\2\2\2\u00a6"+
		"\u00a7\7-\2\2\u00a7:\3\2\2\2\u00a8\u00a9\7/\2\2\u00a9<\3\2\2\2\u00aa\u00ab"+
		"\7\61\2\2\u00ab>\3\2\2\2\u00ac\u00ad\7\'\2\2\u00ad@\3\2\2\2\u00ae\u00af"+
		"\7,\2\2\u00afB\3\2\2\2\u00b0\u00b4\t\2\2\2\u00b1\u00b3\t\3\2\2\u00b2\u00b1"+
		"\3\2\2\2\u00b3\u00b6\3\2\2\2\u00b4\u00b2\3\2\2\2\u00b4\u00b5\3\2\2\2\u00b5"+
		"D\3\2\2\2\u00b6\u00b4\3\2\2\2\u00b7\u00b9\t\4\2\2\u00b8\u00b7\3\2\2\2"+
		"\u00b9\u00ba\3\2\2\2\u00ba\u00b8\3\2\2\2\u00ba\u00bb\3\2\2\2\u00bbF\3"+
		"\2\2\2\u00bc\u00c0\7%\2\2\u00bd\u00bf\n\5\2\2\u00be\u00bd\3\2\2\2\u00bf"+
		"\u00c2\3\2\2\2\u00c0\u00be\3\2\2\2\u00c0\u00c1\3\2\2\2\u00c1\u00c3\3\2"+
		"\2\2\u00c2\u00c0\3\2\2\2\u00c3\u00c4\b$\2\2\u00c4H\3\2\2\2\u00c5\u00c6"+
		"\t\6\2\2\u00c6\u00c7\3\2\2\2\u00c7\u00c8\b%\2\2\u00c8J\3\2\2\2\u00c9\u00ca"+
		"\13\2\2\2\u00caL\3\2\2\2\7\2\u00b2\u00b4\u00ba\u00c0\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}