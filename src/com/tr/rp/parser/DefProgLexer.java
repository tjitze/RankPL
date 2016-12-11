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
		T__24=25, T__25=26, T__26=27, T__27=28, VAR=29, INT=30, COMMENT=31, SPACE=32, 
		OTHER=33;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
		"T__9", "T__10", "T__11", "T__12", "T__13", "T__14", "T__15", "T__16", 
		"T__17", "T__18", "T__19", "T__20", "T__21", "T__22", "T__23", "T__24", 
		"T__25", "T__26", "T__27", "VAR", "INT", "COMMENT", "SPACE", "OTHER"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "';'", "'RANK'", "'DEF'", "'ELSE'", "':='", "'IF'", "'THEN'", "'WHILE'", 
		"'DO'", "'OBSERVE'", "'SKIP'", "'('", "'<'", "'<='", "'>'", "'>='", "'=='", 
		"'!='", "')'", "'&'", "'|'", "'!'", "'true'", "'false'", "'+'", "'-'", 
		"'/'", "'*'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, "VAR", "INT", "COMMENT", "SPACE", "OTHER"
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2#\u00b9\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3"+
		"\5\3\5\3\6\3\6\3\6\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t"+
		"\3\t\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3"+
		"\f\3\f\3\r\3\r\3\16\3\16\3\17\3\17\3\17\3\20\3\20\3\21\3\21\3\21\3\22"+
		"\3\22\3\22\3\23\3\23\3\23\3\24\3\24\3\25\3\25\3\26\3\26\3\27\3\27\3\30"+
		"\3\30\3\30\3\30\3\30\3\31\3\31\3\31\3\31\3\31\3\31\3\32\3\32\3\33\3\33"+
		"\3\34\3\34\3\35\3\35\3\36\3\36\3\37\6\37\u00a7\n\37\r\37\16\37\u00a8\3"+
		" \3 \7 \u00ad\n \f \16 \u00b0\13 \3 \3 \3!\3!\3!\3!\3\"\3\"\2\2#\3\3\5"+
		"\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21"+
		"!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\34\67\359\36;\37= ?!"+
		"A\"C#\3\2\6\3\2c|\3\2\62;\4\2\f\f\17\17\5\2\13\f\17\17\"\"\u00ba\2\3\3"+
		"\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2"+
		"\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3"+
		"\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2"+
		"%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61"+
		"\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2"+
		"\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\3E\3\2\2\2\5G\3\2\2\2\7L"+
		"\3\2\2\2\tP\3\2\2\2\13U\3\2\2\2\rX\3\2\2\2\17[\3\2\2\2\21`\3\2\2\2\23"+
		"f\3\2\2\2\25i\3\2\2\2\27q\3\2\2\2\31v\3\2\2\2\33x\3\2\2\2\35z\3\2\2\2"+
		"\37}\3\2\2\2!\177\3\2\2\2#\u0082\3\2\2\2%\u0085\3\2\2\2\'\u0088\3\2\2"+
		"\2)\u008a\3\2\2\2+\u008c\3\2\2\2-\u008e\3\2\2\2/\u0090\3\2\2\2\61\u0095"+
		"\3\2\2\2\63\u009b\3\2\2\2\65\u009d\3\2\2\2\67\u009f\3\2\2\29\u00a1\3\2"+
		"\2\2;\u00a3\3\2\2\2=\u00a6\3\2\2\2?\u00aa\3\2\2\2A\u00b3\3\2\2\2C\u00b7"+
		"\3\2\2\2EF\7=\2\2F\4\3\2\2\2GH\7T\2\2HI\7C\2\2IJ\7P\2\2JK\7M\2\2K\6\3"+
		"\2\2\2LM\7F\2\2MN\7G\2\2NO\7H\2\2O\b\3\2\2\2PQ\7G\2\2QR\7N\2\2RS\7U\2"+
		"\2ST\7G\2\2T\n\3\2\2\2UV\7<\2\2VW\7?\2\2W\f\3\2\2\2XY\7K\2\2YZ\7H\2\2"+
		"Z\16\3\2\2\2[\\\7V\2\2\\]\7J\2\2]^\7G\2\2^_\7P\2\2_\20\3\2\2\2`a\7Y\2"+
		"\2ab\7J\2\2bc\7K\2\2cd\7N\2\2de\7G\2\2e\22\3\2\2\2fg\7F\2\2gh\7Q\2\2h"+
		"\24\3\2\2\2ij\7Q\2\2jk\7D\2\2kl\7U\2\2lm\7G\2\2mn\7T\2\2no\7X\2\2op\7"+
		"G\2\2p\26\3\2\2\2qr\7U\2\2rs\7M\2\2st\7K\2\2tu\7R\2\2u\30\3\2\2\2vw\7"+
		"*\2\2w\32\3\2\2\2xy\7>\2\2y\34\3\2\2\2z{\7>\2\2{|\7?\2\2|\36\3\2\2\2}"+
		"~\7@\2\2~ \3\2\2\2\177\u0080\7@\2\2\u0080\u0081\7?\2\2\u0081\"\3\2\2\2"+
		"\u0082\u0083\7?\2\2\u0083\u0084\7?\2\2\u0084$\3\2\2\2\u0085\u0086\7#\2"+
		"\2\u0086\u0087\7?\2\2\u0087&\3\2\2\2\u0088\u0089\7+\2\2\u0089(\3\2\2\2"+
		"\u008a\u008b\7(\2\2\u008b*\3\2\2\2\u008c\u008d\7~\2\2\u008d,\3\2\2\2\u008e"+
		"\u008f\7#\2\2\u008f.\3\2\2\2\u0090\u0091\7v\2\2\u0091\u0092\7t\2\2\u0092"+
		"\u0093\7w\2\2\u0093\u0094\7g\2\2\u0094\60\3\2\2\2\u0095\u0096\7h\2\2\u0096"+
		"\u0097\7c\2\2\u0097\u0098\7n\2\2\u0098\u0099\7u\2\2\u0099\u009a\7g\2\2"+
		"\u009a\62\3\2\2\2\u009b\u009c\7-\2\2\u009c\64\3\2\2\2\u009d\u009e\7/\2"+
		"\2\u009e\66\3\2\2\2\u009f\u00a0\7\61\2\2\u00a08\3\2\2\2\u00a1\u00a2\7"+
		",\2\2\u00a2:\3\2\2\2\u00a3\u00a4\t\2\2\2\u00a4<\3\2\2\2\u00a5\u00a7\t"+
		"\3\2\2\u00a6\u00a5\3\2\2\2\u00a7\u00a8\3\2\2\2\u00a8\u00a6\3\2\2\2\u00a8"+
		"\u00a9\3\2\2\2\u00a9>\3\2\2\2\u00aa\u00ae\7%\2\2\u00ab\u00ad\n\4\2\2\u00ac"+
		"\u00ab\3\2\2\2\u00ad\u00b0\3\2\2\2\u00ae\u00ac\3\2\2\2\u00ae\u00af\3\2"+
		"\2\2\u00af\u00b1\3\2\2\2\u00b0\u00ae\3\2\2\2\u00b1\u00b2\b \2\2\u00b2"+
		"@\3\2\2\2\u00b3\u00b4\t\5\2\2\u00b4\u00b5\3\2\2\2\u00b5\u00b6\b!\2\2\u00b6"+
		"B\3\2\2\2\u00b7\u00b8\13\2\2\2\u00b8D\3\2\2\2\5\2\u00a8\u00ae\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}