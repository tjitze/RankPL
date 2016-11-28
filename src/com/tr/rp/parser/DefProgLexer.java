// Generated from /Users/tjitze/Documents/workspace/RankPL/src/com/tr/defaultprogramming/parser/DefProg.g by ANTLR 4.5

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
		null, "';'", "'RANKED'", "'('", "','", "')'", "'NONDET'", "':='", "'IF'", 
		"'THEN'", "'ELSE'", "'WHILE'", "'DO'", "'OBSERVE'", "'SKIP'", "'<'", "'<='", 
		"'>'", "'>='", "'=='", "'!='", "'&'", "'|'", "'!'", "'true'", "'false'", 
		"'+'", "'-'", "'/'", "'*'"
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2$\u00c2\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\5\3\5"+
		"\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\t\3\t\3\t\3\n\3\n\3"+
		"\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3"+
		"\r\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3"+
		"\20\3\20\3\21\3\21\3\21\3\22\3\22\3\23\3\23\3\23\3\24\3\24\3\24\3\25\3"+
		"\25\3\25\3\26\3\26\3\27\3\27\3\30\3\30\3\31\3\31\3\31\3\31\3\31\3\32\3"+
		"\32\3\32\3\32\3\32\3\32\3\33\3\33\3\34\3\34\3\35\3\35\3\36\3\36\3\37\3"+
		"\37\3 \6 \u00b0\n \r \16 \u00b1\3!\3!\7!\u00b6\n!\f!\16!\u00b9\13!\3!"+
		"\3!\3\"\3\"\3\"\3\"\3#\3#\2\2$\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13"+
		"\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61"+
		"\32\63\33\65\34\67\359\36;\37= ?!A\"C#E$\3\2\6\3\2c|\3\2\62;\4\2\f\f\17"+
		"\17\5\2\13\f\17\17\"\"\u00c3\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3"+
		"\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2"+
		"\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37"+
		"\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3"+
		"\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2"+
		"\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C"+
		"\3\2\2\2\2E\3\2\2\2\3G\3\2\2\2\5I\3\2\2\2\7P\3\2\2\2\tR\3\2\2\2\13T\3"+
		"\2\2\2\rV\3\2\2\2\17]\3\2\2\2\21`\3\2\2\2\23c\3\2\2\2\25h\3\2\2\2\27m"+
		"\3\2\2\2\31s\3\2\2\2\33v\3\2\2\2\35~\3\2\2\2\37\u0083\3\2\2\2!\u0085\3"+
		"\2\2\2#\u0088\3\2\2\2%\u008a\3\2\2\2\'\u008d\3\2\2\2)\u0090\3\2\2\2+\u0093"+
		"\3\2\2\2-\u0095\3\2\2\2/\u0097\3\2\2\2\61\u0099\3\2\2\2\63\u009e\3\2\2"+
		"\2\65\u00a4\3\2\2\2\67\u00a6\3\2\2\29\u00a8\3\2\2\2;\u00aa\3\2\2\2=\u00ac"+
		"\3\2\2\2?\u00af\3\2\2\2A\u00b3\3\2\2\2C\u00bc\3\2\2\2E\u00c0\3\2\2\2G"+
		"H\7=\2\2H\4\3\2\2\2IJ\7T\2\2JK\7C\2\2KL\7P\2\2LM\7M\2\2MN\7G\2\2NO\7F"+
		"\2\2O\6\3\2\2\2PQ\7*\2\2Q\b\3\2\2\2RS\7.\2\2S\n\3\2\2\2TU\7+\2\2U\f\3"+
		"\2\2\2VW\7P\2\2WX\7Q\2\2XY\7P\2\2YZ\7F\2\2Z[\7G\2\2[\\\7V\2\2\\\16\3\2"+
		"\2\2]^\7<\2\2^_\7?\2\2_\20\3\2\2\2`a\7K\2\2ab\7H\2\2b\22\3\2\2\2cd\7V"+
		"\2\2de\7J\2\2ef\7G\2\2fg\7P\2\2g\24\3\2\2\2hi\7G\2\2ij\7N\2\2jk\7U\2\2"+
		"kl\7G\2\2l\26\3\2\2\2mn\7Y\2\2no\7J\2\2op\7K\2\2pq\7N\2\2qr\7G\2\2r\30"+
		"\3\2\2\2st\7F\2\2tu\7Q\2\2u\32\3\2\2\2vw\7Q\2\2wx\7D\2\2xy\7U\2\2yz\7"+
		"G\2\2z{\7T\2\2{|\7X\2\2|}\7G\2\2}\34\3\2\2\2~\177\7U\2\2\177\u0080\7M"+
		"\2\2\u0080\u0081\7K\2\2\u0081\u0082\7R\2\2\u0082\36\3\2\2\2\u0083\u0084"+
		"\7>\2\2\u0084 \3\2\2\2\u0085\u0086\7>\2\2\u0086\u0087\7?\2\2\u0087\"\3"+
		"\2\2\2\u0088\u0089\7@\2\2\u0089$\3\2\2\2\u008a\u008b\7@\2\2\u008b\u008c"+
		"\7?\2\2\u008c&\3\2\2\2\u008d\u008e\7?\2\2\u008e\u008f\7?\2\2\u008f(\3"+
		"\2\2\2\u0090\u0091\7#\2\2\u0091\u0092\7?\2\2\u0092*\3\2\2\2\u0093\u0094"+
		"\7(\2\2\u0094,\3\2\2\2\u0095\u0096\7~\2\2\u0096.\3\2\2\2\u0097\u0098\7"+
		"#\2\2\u0098\60\3\2\2\2\u0099\u009a\7v\2\2\u009a\u009b\7t\2\2\u009b\u009c"+
		"\7w\2\2\u009c\u009d\7g\2\2\u009d\62\3\2\2\2\u009e\u009f\7h\2\2\u009f\u00a0"+
		"\7c\2\2\u00a0\u00a1\7n\2\2\u00a1\u00a2\7u\2\2\u00a2\u00a3\7g\2\2\u00a3"+
		"\64\3\2\2\2\u00a4\u00a5\7-\2\2\u00a5\66\3\2\2\2\u00a6\u00a7\7/\2\2\u00a7"+
		"8\3\2\2\2\u00a8\u00a9\7\61\2\2\u00a9:\3\2\2\2\u00aa\u00ab\7,\2\2\u00ab"+
		"<\3\2\2\2\u00ac\u00ad\t\2\2\2\u00ad>\3\2\2\2\u00ae\u00b0\t\3\2\2\u00af"+
		"\u00ae\3\2\2\2\u00b0\u00b1\3\2\2\2\u00b1\u00af\3\2\2\2\u00b1\u00b2\3\2"+
		"\2\2\u00b2@\3\2\2\2\u00b3\u00b7\7%\2\2\u00b4\u00b6\n\4\2\2\u00b5\u00b4"+
		"\3\2\2\2\u00b6\u00b9\3\2\2\2\u00b7\u00b5\3\2\2\2\u00b7\u00b8\3\2\2\2\u00b8"+
		"\u00ba\3\2\2\2\u00b9\u00b7\3\2\2\2\u00ba\u00bb\b!\2\2\u00bbB\3\2\2\2\u00bc"+
		"\u00bd\t\5\2\2\u00bd\u00be\3\2\2\2\u00be\u00bf\b\"\2\2\u00bfD\3\2\2\2"+
		"\u00c0\u00c1\13\2\2\2\u00c1F\3\2\2\2\5\2\u00b1\u00b7\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}