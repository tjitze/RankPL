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
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, VAR=30, INT=31, COMMENT=32, 
		SPACE=33, OTHER=34;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
		"T__9", "T__10", "T__11", "T__12", "T__13", "T__14", "T__15", "T__16", 
		"T__17", "T__18", "T__19", "T__20", "T__21", "T__22", "T__23", "T__24", 
		"T__25", "T__26", "T__27", "T__28", "VAR", "INT", "COMMENT", "SPACE", 
		"OTHER"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "';'", "'CHOOSE'", "'NORMALLY'", "'EXCEPTIONALLY'", "':='", "'IF'", 
		"'THEN'", "'ELSE'", "'WHILE'", "'DO'", "'OBSERVE'", "'SKIP'", "'('", "'<'", 
		"'<='", "'>'", "'>='", "'=='", "'!='", "')'", "'&'", "'|'", "'!'", "'true'", 
		"'false'", "'+'", "'-'", "'/'", "'*'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, "VAR", "INT", "COMMENT", "SPACE", 
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2$\u00d0\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4"+
		"\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3"+
		"\5\3\5\3\6\3\6\3\6\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t"+
		"\3\n\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3"+
		"\f\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\17\3\17\3\20\3\20\3\20\3\21\3\21\3"+
		"\22\3\22\3\22\3\23\3\23\3\23\3\24\3\24\3\24\3\25\3\25\3\26\3\26\3\27\3"+
		"\27\3\30\3\30\3\31\3\31\3\31\3\31\3\31\3\32\3\32\3\32\3\32\3\32\3\32\3"+
		"\33\3\33\3\34\3\34\3\35\3\35\3\36\3\36\3\37\3\37\3 \6 \u00be\n \r \16"+
		" \u00bf\3!\3!\7!\u00c4\n!\f!\16!\u00c7\13!\3!\3!\3\"\3\"\3\"\3\"\3#\3"+
		"#\2\2$\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17"+
		"\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\34\67\35"+
		"9\36;\37= ?!A\"C#E$\3\2\6\3\2c|\3\2\62;\4\2\f\f\17\17\5\2\13\f\17\17\""+
		"\"\u00d1\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2"+
		"\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27"+
		"\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2"+
		"\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2"+
		"\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2"+
		"\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2"+
		"\3G\3\2\2\2\5I\3\2\2\2\7P\3\2\2\2\tY\3\2\2\2\13g\3\2\2\2\rj\3\2\2\2\17"+
		"m\3\2\2\2\21r\3\2\2\2\23w\3\2\2\2\25}\3\2\2\2\27\u0080\3\2\2\2\31\u0088"+
		"\3\2\2\2\33\u008d\3\2\2\2\35\u008f\3\2\2\2\37\u0091\3\2\2\2!\u0094\3\2"+
		"\2\2#\u0096\3\2\2\2%\u0099\3\2\2\2\'\u009c\3\2\2\2)\u009f\3\2\2\2+\u00a1"+
		"\3\2\2\2-\u00a3\3\2\2\2/\u00a5\3\2\2\2\61\u00a7\3\2\2\2\63\u00ac\3\2\2"+
		"\2\65\u00b2\3\2\2\2\67\u00b4\3\2\2\29\u00b6\3\2\2\2;\u00b8\3\2\2\2=\u00ba"+
		"\3\2\2\2?\u00bd\3\2\2\2A\u00c1\3\2\2\2C\u00ca\3\2\2\2E\u00ce\3\2\2\2G"+
		"H\7=\2\2H\4\3\2\2\2IJ\7E\2\2JK\7J\2\2KL\7Q\2\2LM\7Q\2\2MN\7U\2\2NO\7G"+
		"\2\2O\6\3\2\2\2PQ\7P\2\2QR\7Q\2\2RS\7T\2\2ST\7O\2\2TU\7C\2\2UV\7N\2\2"+
		"VW\7N\2\2WX\7[\2\2X\b\3\2\2\2YZ\7G\2\2Z[\7Z\2\2[\\\7E\2\2\\]\7G\2\2]^"+
		"\7R\2\2^_\7V\2\2_`\7K\2\2`a\7Q\2\2ab\7P\2\2bc\7C\2\2cd\7N\2\2de\7N\2\2"+
		"ef\7[\2\2f\n\3\2\2\2gh\7<\2\2hi\7?\2\2i\f\3\2\2\2jk\7K\2\2kl\7H\2\2l\16"+
		"\3\2\2\2mn\7V\2\2no\7J\2\2op\7G\2\2pq\7P\2\2q\20\3\2\2\2rs\7G\2\2st\7"+
		"N\2\2tu\7U\2\2uv\7G\2\2v\22\3\2\2\2wx\7Y\2\2xy\7J\2\2yz\7K\2\2z{\7N\2"+
		"\2{|\7G\2\2|\24\3\2\2\2}~\7F\2\2~\177\7Q\2\2\177\26\3\2\2\2\u0080\u0081"+
		"\7Q\2\2\u0081\u0082\7D\2\2\u0082\u0083\7U\2\2\u0083\u0084\7G\2\2\u0084"+
		"\u0085\7T\2\2\u0085\u0086\7X\2\2\u0086\u0087\7G\2\2\u0087\30\3\2\2\2\u0088"+
		"\u0089\7U\2\2\u0089\u008a\7M\2\2\u008a\u008b\7K\2\2\u008b\u008c\7R\2\2"+
		"\u008c\32\3\2\2\2\u008d\u008e\7*\2\2\u008e\34\3\2\2\2\u008f\u0090\7>\2"+
		"\2\u0090\36\3\2\2\2\u0091\u0092\7>\2\2\u0092\u0093\7?\2\2\u0093 \3\2\2"+
		"\2\u0094\u0095\7@\2\2\u0095\"\3\2\2\2\u0096\u0097\7@\2\2\u0097\u0098\7"+
		"?\2\2\u0098$\3\2\2\2\u0099\u009a\7?\2\2\u009a\u009b\7?\2\2\u009b&\3\2"+
		"\2\2\u009c\u009d\7#\2\2\u009d\u009e\7?\2\2\u009e(\3\2\2\2\u009f\u00a0"+
		"\7+\2\2\u00a0*\3\2\2\2\u00a1\u00a2\7(\2\2\u00a2,\3\2\2\2\u00a3\u00a4\7"+
		"~\2\2\u00a4.\3\2\2\2\u00a5\u00a6\7#\2\2\u00a6\60\3\2\2\2\u00a7\u00a8\7"+
		"v\2\2\u00a8\u00a9\7t\2\2\u00a9\u00aa\7w\2\2\u00aa\u00ab\7g\2\2\u00ab\62"+
		"\3\2\2\2\u00ac\u00ad\7h\2\2\u00ad\u00ae\7c\2\2\u00ae\u00af\7n\2\2\u00af"+
		"\u00b0\7u\2\2\u00b0\u00b1\7g\2\2\u00b1\64\3\2\2\2\u00b2\u00b3\7-\2\2\u00b3"+
		"\66\3\2\2\2\u00b4\u00b5\7/\2\2\u00b58\3\2\2\2\u00b6\u00b7\7\61\2\2\u00b7"+
		":\3\2\2\2\u00b8\u00b9\7,\2\2\u00b9<\3\2\2\2\u00ba\u00bb\t\2\2\2\u00bb"+
		">\3\2\2\2\u00bc\u00be\t\3\2\2\u00bd\u00bc\3\2\2\2\u00be\u00bf\3\2\2\2"+
		"\u00bf\u00bd\3\2\2\2\u00bf\u00c0\3\2\2\2\u00c0@\3\2\2\2\u00c1\u00c5\7"+
		"%\2\2\u00c2\u00c4\n\4\2\2\u00c3\u00c2\3\2\2\2\u00c4\u00c7\3\2\2\2\u00c5"+
		"\u00c3\3\2\2\2\u00c5\u00c6\3\2\2\2\u00c6\u00c8\3\2\2\2\u00c7\u00c5\3\2"+
		"\2\2\u00c8\u00c9\b!\2\2\u00c9B\3\2\2\2\u00ca\u00cb\t\5\2\2\u00cb\u00cc"+
		"\3\2\2\2\u00cc\u00cd\b\"\2\2\u00cdD\3\2\2\2\u00ce\u00cf\13\2\2\2\u00cf"+
		"F\3\2\2\2\5\2\u00bf\u00c5\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}