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
		T__31=32, T__32=33, T__33=34, T__34=35, T__35=36, T__36=37, T__37=38, 
		T__38=39, T__39=40, T__40=41, T__41=42, T__42=43, T__43=44, T__44=45, 
		VAR=46, INT=47, COMMENT=48, SPACE=49, OTHER=50;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
		"T__9", "T__10", "T__11", "T__12", "T__13", "T__14", "T__15", "T__16", 
		"T__17", "T__18", "T__19", "T__20", "T__21", "T__22", "T__23", "T__24", 
		"T__25", "T__26", "T__27", "T__28", "T__29", "T__30", "T__31", "T__32", 
		"T__33", "T__34", "T__35", "T__36", "T__37", "T__38", "T__39", "T__40", 
		"T__41", "T__42", "T__43", "T__44", "VAR", "INT", "COMMENT", "SPACE", 
		"OTHER"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "';'", "'{'", "'}'", "'<<'", "'>>'", "':='", "'IF'", "'if'", "'THEN'", 
		"'then'", "'ELSE'", "'else'", "'WHILE'", "'while'", "'DO'", "'do'", "'OBSERVE'", 
		"'observe'", "'SKIP'", "'skip'", "'('", "')'", "'!'", "'<'", "'<='", "'>'", 
		"'>='", "'=='", "'!='", "'&'", "'|'", "'^'", "'TRUE'", "'true'", "'FALSE'", 
		"'false'", "'*'", "'/'", "'%'", "'+'", "'-'", "'RANK'", "'rank'", "'['", 
		"']'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, "VAR", "INT", 
		"COMMENT", "SPACE", "OTHER"
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\64\u0121\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t"+
		" \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t"+
		"+\4,\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\3\2"+
		"\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\5\3\6\3\6\3\6\3\7\3\7\3\7\3\b\3\b\3\b\3"+
		"\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3"+
		"\f\3\f\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\16\3\17\3\17\3\17"+
		"\3\17\3\17\3\17\3\20\3\20\3\20\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22"+
		"\3\22\3\22\3\22\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\24\3\24\3\24"+
		"\3\24\3\24\3\25\3\25\3\25\3\25\3\25\3\26\3\26\3\27\3\27\3\30\3\30\3\31"+
		"\3\31\3\32\3\32\3\32\3\33\3\33\3\34\3\34\3\34\3\35\3\35\3\35\3\36\3\36"+
		"\3\36\3\37\3\37\3 \3 \3!\3!\3\"\3\"\3\"\3\"\3\"\3#\3#\3#\3#\3#\3$\3$\3"+
		"$\3$\3$\3$\3%\3%\3%\3%\3%\3%\3&\3&\3\'\3\'\3(\3(\3)\3)\3*\3*\3+\3+\3+"+
		"\3+\3+\3,\3,\3,\3,\3,\3-\3-\3.\3.\3/\3/\7/\u0109\n/\f/\16/\u010c\13/\3"+
		"\60\6\60\u010f\n\60\r\60\16\60\u0110\3\61\3\61\7\61\u0115\n\61\f\61\16"+
		"\61\u0118\13\61\3\61\3\61\3\62\3\62\3\62\3\62\3\63\3\63\2\2\64\3\3\5\4"+
		"\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22"+
		"#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\34\67\359\36;\37= ?!A\"C"+
		"#E$G%I&K\'M(O)Q*S+U,W-Y.[/]\60_\61a\62c\63e\64\3\2\7\3\2c|\4\2\62;c|\3"+
		"\2\62;\4\2\f\f\17\17\5\2\13\f\17\17\"\"\u0123\2\3\3\2\2\2\2\5\3\2\2\2"+
		"\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3"+
		"\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2"+
		"\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2"+
		"\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2"+
		"\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2"+
		"\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2"+
		"\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2Y"+
		"\3\2\2\2\2[\3\2\2\2\2]\3\2\2\2\2_\3\2\2\2\2a\3\2\2\2\2c\3\2\2\2\2e\3\2"+
		"\2\2\3g\3\2\2\2\5i\3\2\2\2\7k\3\2\2\2\tm\3\2\2\2\13p\3\2\2\2\rs\3\2\2"+
		"\2\17v\3\2\2\2\21y\3\2\2\2\23|\3\2\2\2\25\u0081\3\2\2\2\27\u0086\3\2\2"+
		"\2\31\u008b\3\2\2\2\33\u0090\3\2\2\2\35\u0096\3\2\2\2\37\u009c\3\2\2\2"+
		"!\u009f\3\2\2\2#\u00a2\3\2\2\2%\u00aa\3\2\2\2\'\u00b2\3\2\2\2)\u00b7\3"+
		"\2\2\2+\u00bc\3\2\2\2-\u00be\3\2\2\2/\u00c0\3\2\2\2\61\u00c2\3\2\2\2\63"+
		"\u00c4\3\2\2\2\65\u00c7\3\2\2\2\67\u00c9\3\2\2\29\u00cc\3\2\2\2;\u00cf"+
		"\3\2\2\2=\u00d2\3\2\2\2?\u00d4\3\2\2\2A\u00d6\3\2\2\2C\u00d8\3\2\2\2E"+
		"\u00dd\3\2\2\2G\u00e2\3\2\2\2I\u00e8\3\2\2\2K\u00ee\3\2\2\2M\u00f0\3\2"+
		"\2\2O\u00f2\3\2\2\2Q\u00f4\3\2\2\2S\u00f6\3\2\2\2U\u00f8\3\2\2\2W\u00fd"+
		"\3\2\2\2Y\u0102\3\2\2\2[\u0104\3\2\2\2]\u0106\3\2\2\2_\u010e\3\2\2\2a"+
		"\u0112\3\2\2\2c\u011b\3\2\2\2e\u011f\3\2\2\2gh\7=\2\2h\4\3\2\2\2ij\7}"+
		"\2\2j\6\3\2\2\2kl\7\177\2\2l\b\3\2\2\2mn\7>\2\2no\7>\2\2o\n\3\2\2\2pq"+
		"\7@\2\2qr\7@\2\2r\f\3\2\2\2st\7<\2\2tu\7?\2\2u\16\3\2\2\2vw\7K\2\2wx\7"+
		"H\2\2x\20\3\2\2\2yz\7k\2\2z{\7h\2\2{\22\3\2\2\2|}\7V\2\2}~\7J\2\2~\177"+
		"\7G\2\2\177\u0080\7P\2\2\u0080\24\3\2\2\2\u0081\u0082\7v\2\2\u0082\u0083"+
		"\7j\2\2\u0083\u0084\7g\2\2\u0084\u0085\7p\2\2\u0085\26\3\2\2\2\u0086\u0087"+
		"\7G\2\2\u0087\u0088\7N\2\2\u0088\u0089\7U\2\2\u0089\u008a\7G\2\2\u008a"+
		"\30\3\2\2\2\u008b\u008c\7g\2\2\u008c\u008d\7n\2\2\u008d\u008e\7u\2\2\u008e"+
		"\u008f\7g\2\2\u008f\32\3\2\2\2\u0090\u0091\7Y\2\2\u0091\u0092\7J\2\2\u0092"+
		"\u0093\7K\2\2\u0093\u0094\7N\2\2\u0094\u0095\7G\2\2\u0095\34\3\2\2\2\u0096"+
		"\u0097\7y\2\2\u0097\u0098\7j\2\2\u0098\u0099\7k\2\2\u0099\u009a\7n\2\2"+
		"\u009a\u009b\7g\2\2\u009b\36\3\2\2\2\u009c\u009d\7F\2\2\u009d\u009e\7"+
		"Q\2\2\u009e \3\2\2\2\u009f\u00a0\7f\2\2\u00a0\u00a1\7q\2\2\u00a1\"\3\2"+
		"\2\2\u00a2\u00a3\7Q\2\2\u00a3\u00a4\7D\2\2\u00a4\u00a5\7U\2\2\u00a5\u00a6"+
		"\7G\2\2\u00a6\u00a7\7T\2\2\u00a7\u00a8\7X\2\2\u00a8\u00a9\7G\2\2\u00a9"+
		"$\3\2\2\2\u00aa\u00ab\7q\2\2\u00ab\u00ac\7d\2\2\u00ac\u00ad\7u\2\2\u00ad"+
		"\u00ae\7g\2\2\u00ae\u00af\7t\2\2\u00af\u00b0\7x\2\2\u00b0\u00b1\7g\2\2"+
		"\u00b1&\3\2\2\2\u00b2\u00b3\7U\2\2\u00b3\u00b4\7M\2\2\u00b4\u00b5\7K\2"+
		"\2\u00b5\u00b6\7R\2\2\u00b6(\3\2\2\2\u00b7\u00b8\7u\2\2\u00b8\u00b9\7"+
		"m\2\2\u00b9\u00ba\7k\2\2\u00ba\u00bb\7r\2\2\u00bb*\3\2\2\2\u00bc\u00bd"+
		"\7*\2\2\u00bd,\3\2\2\2\u00be\u00bf\7+\2\2\u00bf.\3\2\2\2\u00c0\u00c1\7"+
		"#\2\2\u00c1\60\3\2\2\2\u00c2\u00c3\7>\2\2\u00c3\62\3\2\2\2\u00c4\u00c5"+
		"\7>\2\2\u00c5\u00c6\7?\2\2\u00c6\64\3\2\2\2\u00c7\u00c8\7@\2\2\u00c8\66"+
		"\3\2\2\2\u00c9\u00ca\7@\2\2\u00ca\u00cb\7?\2\2\u00cb8\3\2\2\2\u00cc\u00cd"+
		"\7?\2\2\u00cd\u00ce\7?\2\2\u00ce:\3\2\2\2\u00cf\u00d0\7#\2\2\u00d0\u00d1"+
		"\7?\2\2\u00d1<\3\2\2\2\u00d2\u00d3\7(\2\2\u00d3>\3\2\2\2\u00d4\u00d5\7"+
		"~\2\2\u00d5@\3\2\2\2\u00d6\u00d7\7`\2\2\u00d7B\3\2\2\2\u00d8\u00d9\7V"+
		"\2\2\u00d9\u00da\7T\2\2\u00da\u00db\7W\2\2\u00db\u00dc\7G\2\2\u00dcD\3"+
		"\2\2\2\u00dd\u00de\7v\2\2\u00de\u00df\7t\2\2\u00df\u00e0\7w\2\2\u00e0"+
		"\u00e1\7g\2\2\u00e1F\3\2\2\2\u00e2\u00e3\7H\2\2\u00e3\u00e4\7C\2\2\u00e4"+
		"\u00e5\7N\2\2\u00e5\u00e6\7U\2\2\u00e6\u00e7\7G\2\2\u00e7H\3\2\2\2\u00e8"+
		"\u00e9\7h\2\2\u00e9\u00ea\7c\2\2\u00ea\u00eb\7n\2\2\u00eb\u00ec\7u\2\2"+
		"\u00ec\u00ed\7g\2\2\u00edJ\3\2\2\2\u00ee\u00ef\7,\2\2\u00efL\3\2\2\2\u00f0"+
		"\u00f1\7\61\2\2\u00f1N\3\2\2\2\u00f2\u00f3\7\'\2\2\u00f3P\3\2\2\2\u00f4"+
		"\u00f5\7-\2\2\u00f5R\3\2\2\2\u00f6\u00f7\7/\2\2\u00f7T\3\2\2\2\u00f8\u00f9"+
		"\7T\2\2\u00f9\u00fa\7C\2\2\u00fa\u00fb\7P\2\2\u00fb\u00fc\7M\2\2\u00fc"+
		"V\3\2\2\2\u00fd\u00fe\7t\2\2\u00fe\u00ff\7c\2\2\u00ff\u0100\7p\2\2\u0100"+
		"\u0101\7m\2\2\u0101X\3\2\2\2\u0102\u0103\7]\2\2\u0103Z\3\2\2\2\u0104\u0105"+
		"\7_\2\2\u0105\\\3\2\2\2\u0106\u010a\t\2\2\2\u0107\u0109\t\3\2\2\u0108"+
		"\u0107\3\2\2\2\u0109\u010c\3\2\2\2\u010a\u0108\3\2\2\2\u010a\u010b\3\2"+
		"\2\2\u010b^\3\2\2\2\u010c\u010a\3\2\2\2\u010d\u010f\t\4\2\2\u010e\u010d"+
		"\3\2\2\2\u010f\u0110\3\2\2\2\u0110\u010e\3\2\2\2\u0110\u0111\3\2\2\2\u0111"+
		"`\3\2\2\2\u0112\u0116\7%\2\2\u0113\u0115\n\5\2\2\u0114\u0113\3\2\2\2\u0115"+
		"\u0118\3\2\2\2\u0116\u0114\3\2\2\2\u0116\u0117\3\2\2\2\u0117\u0119\3\2"+
		"\2\2\u0118\u0116\3\2\2\2\u0119\u011a\b\61\2\2\u011ab\3\2\2\2\u011b\u011c"+
		"\t\6\2\2\u011c\u011d\3\2\2\2\u011d\u011e\b\62\2\2\u011ed\3\2\2\2\u011f"+
		"\u0120\13\2\2\2\u0120f\3\2\2\2\7\2\u0108\u010a\u0110\u0116\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}