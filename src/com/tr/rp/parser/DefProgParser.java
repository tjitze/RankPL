// Generated from /Users/tjitze/Desktop/workspace/RankPL/src/com/tr/rp/parser/DefProg.g by ANTLR 4.5

package com.tr.rp.parser;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class DefProgParser extends Parser {
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
	public static final int
		RULE_parse = 0, RULE_program = 1, RULE_statement = 2, RULE_ranked_choice = 3, 
		RULE_choice_assignment_stat = 4, RULE_assignment_stat = 5, RULE_if_stat = 6, 
		RULE_while_stat = 7, RULE_observe_stat = 8, RULE_skip_stat = 9, RULE_boolexpr = 10, 
		RULE_compareexpr = 11, RULE_booleanexpr = 12, RULE_negateexpr = 13, RULE_litboolexpr = 14, 
		RULE_numexpr = 15, RULE_arithnumexpr = 16, RULE_litnumexpr = 17, RULE_varnumexpr = 18;
	public static final String[] ruleNames = {
		"parse", "program", "statement", "ranked_choice", "choice_assignment_stat", 
		"assignment_stat", "if_stat", "while_stat", "observe_stat", "skip_stat", 
		"boolexpr", "compareexpr", "booleanexpr", "negateexpr", "litboolexpr", 
		"numexpr", "arithnumexpr", "litnumexpr", "varnumexpr"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "';'", "'{'", "'}'", "'<<'", "'>>'", "':='", "'if'", "'then'", "'else'", 
		"'while'", "'do'", "'observe'", "'skip'", "'('", "'<'", "'<='", "'>'", 
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

	@Override
	public String getGrammarFileName() { return "DefProg.g"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public DefProgParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ParseContext extends ParserRuleContext {
		public ProgramContext program() {
			return getRuleContext(ProgramContext.class,0);
		}
		public ParseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parse; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).enterParse(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).exitParse(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DefProgVisitor ) return ((DefProgVisitor<? extends T>)visitor).visitParse(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParseContext parse() throws RecognitionException {
		ParseContext _localctx = new ParseContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_parse);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(38);
			program();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ProgramContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(DefProgParser.EOF, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).enterProgram(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).exitProgram(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DefProgVisitor ) return ((DefProgVisitor<? extends T>)visitor).visitProgram(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(43); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(40);
				statement();
				setState(41);
				match(T__0);
				}
				}
				setState(45); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << T__6) | (1L << T__9) | (1L << T__11) | (1L << T__12) | (1L << VAR))) != 0) );
			setState(47);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementContext extends ParserRuleContext {
		public Ranked_choiceContext ranked_choice() {
			return getRuleContext(Ranked_choiceContext.class,0);
		}
		public Assignment_statContext assignment_stat() {
			return getRuleContext(Assignment_statContext.class,0);
		}
		public Choice_assignment_statContext choice_assignment_stat() {
			return getRuleContext(Choice_assignment_statContext.class,0);
		}
		public If_statContext if_stat() {
			return getRuleContext(If_statContext.class,0);
		}
		public While_statContext while_stat() {
			return getRuleContext(While_statContext.class,0);
		}
		public Observe_statContext observe_stat() {
			return getRuleContext(Observe_statContext.class,0);
		}
		public Skip_statContext skip_stat() {
			return getRuleContext(Skip_statContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).exitStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DefProgVisitor ) return ((DefProgVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_statement);
		try {
			setState(56);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(49);
				ranked_choice();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(50);
				assignment_stat();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(51);
				choice_assignment_stat();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(52);
				if_stat();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(53);
				while_stat();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(54);
				observe_stat();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(55);
				skip_stat();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Ranked_choiceContext extends ParserRuleContext {
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public NumexprContext numexpr() {
			return getRuleContext(NumexprContext.class,0);
		}
		public Ranked_choiceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ranked_choice; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).enterRanked_choice(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).exitRanked_choice(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DefProgVisitor ) return ((DefProgVisitor<? extends T>)visitor).visitRanked_choice(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Ranked_choiceContext ranked_choice() throws RecognitionException {
		Ranked_choiceContext _localctx = new Ranked_choiceContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_ranked_choice);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(58);
			match(T__1);
			setState(59);
			statement();
			setState(60);
			match(T__2);
			setState(61);
			match(T__3);
			setState(62);
			numexpr();
			setState(63);
			match(T__4);
			setState(64);
			match(T__1);
			setState(65);
			statement();
			setState(66);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Choice_assignment_statContext extends ParserRuleContext {
		public TerminalNode VAR() { return getToken(DefProgParser.VAR, 0); }
		public List<NumexprContext> numexpr() {
			return getRuleContexts(NumexprContext.class);
		}
		public NumexprContext numexpr(int i) {
			return getRuleContext(NumexprContext.class,i);
		}
		public Choice_assignment_statContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_choice_assignment_stat; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).enterChoice_assignment_stat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).exitChoice_assignment_stat(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DefProgVisitor ) return ((DefProgVisitor<? extends T>)visitor).visitChoice_assignment_stat(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Choice_assignment_statContext choice_assignment_stat() throws RecognitionException {
		Choice_assignment_statContext _localctx = new Choice_assignment_statContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_choice_assignment_stat);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(68);
			match(VAR);
			setState(69);
			match(T__5);
			setState(70);
			numexpr();
			setState(71);
			match(T__3);
			setState(72);
			numexpr();
			setState(73);
			match(T__4);
			setState(74);
			numexpr();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Assignment_statContext extends ParserRuleContext {
		public TerminalNode VAR() { return getToken(DefProgParser.VAR, 0); }
		public NumexprContext numexpr() {
			return getRuleContext(NumexprContext.class,0);
		}
		public Assignment_statContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignment_stat; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).enterAssignment_stat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).exitAssignment_stat(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DefProgVisitor ) return ((DefProgVisitor<? extends T>)visitor).visitAssignment_stat(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Assignment_statContext assignment_stat() throws RecognitionException {
		Assignment_statContext _localctx = new Assignment_statContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_assignment_stat);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(76);
			match(VAR);
			setState(77);
			match(T__5);
			setState(78);
			numexpr();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class If_statContext extends ParserRuleContext {
		public BoolexprContext boolexpr() {
			return getRuleContext(BoolexprContext.class,0);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public If_statContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_if_stat; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).enterIf_stat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).exitIf_stat(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DefProgVisitor ) return ((DefProgVisitor<? extends T>)visitor).visitIf_stat(this);
			else return visitor.visitChildren(this);
		}
	}

	public final If_statContext if_stat() throws RecognitionException {
		If_statContext _localctx = new If_statContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_if_stat);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(80);
			match(T__6);
			setState(81);
			boolexpr();
			setState(82);
			match(T__7);
			setState(83);
			statement();
			setState(84);
			match(T__8);
			setState(85);
			statement();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class While_statContext extends ParserRuleContext {
		public BoolexprContext boolexpr() {
			return getRuleContext(BoolexprContext.class,0);
		}
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public While_statContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_while_stat; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).enterWhile_stat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).exitWhile_stat(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DefProgVisitor ) return ((DefProgVisitor<? extends T>)visitor).visitWhile_stat(this);
			else return visitor.visitChildren(this);
		}
	}

	public final While_statContext while_stat() throws RecognitionException {
		While_statContext _localctx = new While_statContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_while_stat);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(87);
			match(T__9);
			setState(88);
			boolexpr();
			setState(89);
			match(T__10);
			setState(90);
			statement();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Observe_statContext extends ParserRuleContext {
		public BoolexprContext boolexpr() {
			return getRuleContext(BoolexprContext.class,0);
		}
		public Observe_statContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_observe_stat; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).enterObserve_stat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).exitObserve_stat(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DefProgVisitor ) return ((DefProgVisitor<? extends T>)visitor).visitObserve_stat(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Observe_statContext observe_stat() throws RecognitionException {
		Observe_statContext _localctx = new Observe_statContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_observe_stat);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(92);
			match(T__11);
			setState(93);
			boolexpr();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Skip_statContext extends ParserRuleContext {
		public Skip_statContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_skip_stat; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).enterSkip_stat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).exitSkip_stat(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DefProgVisitor ) return ((DefProgVisitor<? extends T>)visitor).visitSkip_stat(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Skip_statContext skip_stat() throws RecognitionException {
		Skip_statContext _localctx = new Skip_statContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_skip_stat);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(95);
			match(T__12);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BoolexprContext extends ParserRuleContext {
		public CompareexprContext compareexpr() {
			return getRuleContext(CompareexprContext.class,0);
		}
		public BooleanexprContext booleanexpr() {
			return getRuleContext(BooleanexprContext.class,0);
		}
		public NegateexprContext negateexpr() {
			return getRuleContext(NegateexprContext.class,0);
		}
		public LitboolexprContext litboolexpr() {
			return getRuleContext(LitboolexprContext.class,0);
		}
		public BoolexprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_boolexpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).enterBoolexpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).exitBoolexpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DefProgVisitor ) return ((DefProgVisitor<? extends T>)visitor).visitBoolexpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BoolexprContext boolexpr() throws RecognitionException {
		BoolexprContext _localctx = new BoolexprContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_boolexpr);
		try {
			setState(101);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(97);
				compareexpr();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(98);
				booleanexpr();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(99);
				negateexpr();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(100);
				litboolexpr();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CompareexprContext extends ParserRuleContext {
		public Token cop;
		public List<NumexprContext> numexpr() {
			return getRuleContexts(NumexprContext.class);
		}
		public NumexprContext numexpr(int i) {
			return getRuleContext(NumexprContext.class,i);
		}
		public CompareexprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_compareexpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).enterCompareexpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).exitCompareexpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DefProgVisitor ) return ((DefProgVisitor<? extends T>)visitor).visitCompareexpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CompareexprContext compareexpr() throws RecognitionException {
		CompareexprContext _localctx = new CompareexprContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_compareexpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(103);
			match(T__13);
			setState(104);
			numexpr();
			setState(105);
			((CompareexprContext)_localctx).cop = _input.LT(1);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__14) | (1L << T__15) | (1L << T__16) | (1L << T__17) | (1L << T__18) | (1L << T__19))) != 0)) ) {
				((CompareexprContext)_localctx).cop = (Token)_errHandler.recoverInline(this);
			} else {
				consume();
			}
			setState(106);
			numexpr();
			setState(107);
			match(T__20);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BooleanexprContext extends ParserRuleContext {
		public Token bop;
		public List<BoolexprContext> boolexpr() {
			return getRuleContexts(BoolexprContext.class);
		}
		public BoolexprContext boolexpr(int i) {
			return getRuleContext(BoolexprContext.class,i);
		}
		public BooleanexprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_booleanexpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).enterBooleanexpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).exitBooleanexpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DefProgVisitor ) return ((DefProgVisitor<? extends T>)visitor).visitBooleanexpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BooleanexprContext booleanexpr() throws RecognitionException {
		BooleanexprContext _localctx = new BooleanexprContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_booleanexpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(109);
			match(T__13);
			setState(110);
			boolexpr();
			setState(111);
			((BooleanexprContext)_localctx).bop = _input.LT(1);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__21) | (1L << T__22) | (1L << T__23))) != 0)) ) {
				((BooleanexprContext)_localctx).bop = (Token)_errHandler.recoverInline(this);
			} else {
				consume();
			}
			setState(112);
			boolexpr();
			setState(113);
			match(T__20);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NegateexprContext extends ParserRuleContext {
		public BoolexprContext boolexpr() {
			return getRuleContext(BoolexprContext.class,0);
		}
		public NegateexprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_negateexpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).enterNegateexpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).exitNegateexpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DefProgVisitor ) return ((DefProgVisitor<? extends T>)visitor).visitNegateexpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NegateexprContext negateexpr() throws RecognitionException {
		NegateexprContext _localctx = new NegateexprContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_negateexpr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(115);
			match(T__24);
			setState(116);
			boolexpr();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LitboolexprContext extends ParserRuleContext {
		public LitboolexprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_litboolexpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).enterLitboolexpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).exitLitboolexpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DefProgVisitor ) return ((DefProgVisitor<? extends T>)visitor).visitLitboolexpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LitboolexprContext litboolexpr() throws RecognitionException {
		LitboolexprContext _localctx = new LitboolexprContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_litboolexpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(118);
			_la = _input.LA(1);
			if ( !(_la==T__25 || _la==T__26) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NumexprContext extends ParserRuleContext {
		public ArithnumexprContext arithnumexpr() {
			return getRuleContext(ArithnumexprContext.class,0);
		}
		public LitnumexprContext litnumexpr() {
			return getRuleContext(LitnumexprContext.class,0);
		}
		public VarnumexprContext varnumexpr() {
			return getRuleContext(VarnumexprContext.class,0);
		}
		public NumexprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_numexpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).enterNumexpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).exitNumexpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DefProgVisitor ) return ((DefProgVisitor<? extends T>)visitor).visitNumexpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NumexprContext numexpr() throws RecognitionException {
		NumexprContext _localctx = new NumexprContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_numexpr);
		try {
			setState(123);
			switch (_input.LA(1)) {
			case T__13:
				enterOuterAlt(_localctx, 1);
				{
				setState(120);
				arithnumexpr();
				}
				break;
			case INT:
				enterOuterAlt(_localctx, 2);
				{
				setState(121);
				litnumexpr();
				}
				break;
			case VAR:
				enterOuterAlt(_localctx, 3);
				{
				setState(122);
				varnumexpr();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArithnumexprContext extends ParserRuleContext {
		public Token aop;
		public List<NumexprContext> numexpr() {
			return getRuleContexts(NumexprContext.class);
		}
		public NumexprContext numexpr(int i) {
			return getRuleContext(NumexprContext.class,i);
		}
		public ArithnumexprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arithnumexpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).enterArithnumexpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).exitArithnumexpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DefProgVisitor ) return ((DefProgVisitor<? extends T>)visitor).visitArithnumexpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArithnumexprContext arithnumexpr() throws RecognitionException {
		ArithnumexprContext _localctx = new ArithnumexprContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_arithnumexpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(125);
			match(T__13);
			setState(126);
			numexpr();
			setState(127);
			((ArithnumexprContext)_localctx).aop = _input.LT(1);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__21) | (1L << T__22) | (1L << T__23) | (1L << T__27) | (1L << T__28) | (1L << T__29) | (1L << T__30) | (1L << T__31))) != 0)) ) {
				((ArithnumexprContext)_localctx).aop = (Token)_errHandler.recoverInline(this);
			} else {
				consume();
			}
			setState(128);
			numexpr();
			setState(129);
			match(T__20);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LitnumexprContext extends ParserRuleContext {
		public TerminalNode INT() { return getToken(DefProgParser.INT, 0); }
		public LitnumexprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_litnumexpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).enterLitnumexpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).exitLitnumexpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DefProgVisitor ) return ((DefProgVisitor<? extends T>)visitor).visitLitnumexpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LitnumexprContext litnumexpr() throws RecognitionException {
		LitnumexprContext _localctx = new LitnumexprContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_litnumexpr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(131);
			match(INT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VarnumexprContext extends ParserRuleContext {
		public TerminalNode VAR() { return getToken(DefProgParser.VAR, 0); }
		public VarnumexprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varnumexpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).enterVarnumexpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).exitVarnumexpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DefProgVisitor ) return ((DefProgVisitor<? extends T>)visitor).visitVarnumexpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarnumexprContext varnumexpr() throws RecognitionException {
		VarnumexprContext _localctx = new VarnumexprContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_varnumexpr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(133);
			match(VAR);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\'\u008a\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\3\2\3\2\3\3\3\3\3\3\6\3.\n\3\r\3\16\3/\3\3\3\3\3"+
		"\4\3\4\3\4\3\4\3\4\3\4\3\4\5\4;\n\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3"+
		"\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b"+
		"\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\13\3\13\3\f\3\f\3\f\3\f"+
		"\5\fh\n\f\3\r\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\16\3\17\3"+
		"\17\3\17\3\20\3\20\3\21\3\21\3\21\5\21~\n\21\3\22\3\22\3\22\3\22\3\22"+
		"\3\22\3\23\3\23\3\24\3\24\3\24\2\2\25\2\4\6\b\n\f\16\20\22\24\26\30\32"+
		"\34\36 \"$&\2\6\3\2\21\26\3\2\30\32\3\2\34\35\4\2\30\32\36\"\u0082\2("+
		"\3\2\2\2\4-\3\2\2\2\6:\3\2\2\2\b<\3\2\2\2\nF\3\2\2\2\fN\3\2\2\2\16R\3"+
		"\2\2\2\20Y\3\2\2\2\22^\3\2\2\2\24a\3\2\2\2\26g\3\2\2\2\30i\3\2\2\2\32"+
		"o\3\2\2\2\34u\3\2\2\2\36x\3\2\2\2 }\3\2\2\2\"\177\3\2\2\2$\u0085\3\2\2"+
		"\2&\u0087\3\2\2\2()\5\4\3\2)\3\3\2\2\2*+\5\6\4\2+,\7\3\2\2,.\3\2\2\2-"+
		"*\3\2\2\2./\3\2\2\2/-\3\2\2\2/\60\3\2\2\2\60\61\3\2\2\2\61\62\7\2\2\3"+
		"\62\5\3\2\2\2\63;\5\b\5\2\64;\5\f\7\2\65;\5\n\6\2\66;\5\16\b\2\67;\5\20"+
		"\t\28;\5\22\n\29;\5\24\13\2:\63\3\2\2\2:\64\3\2\2\2:\65\3\2\2\2:\66\3"+
		"\2\2\2:\67\3\2\2\2:8\3\2\2\2:9\3\2\2\2;\7\3\2\2\2<=\7\4\2\2=>\5\6\4\2"+
		">?\7\5\2\2?@\7\6\2\2@A\5 \21\2AB\7\7\2\2BC\7\4\2\2CD\5\6\4\2DE\7\5\2\2"+
		"E\t\3\2\2\2FG\7#\2\2GH\7\b\2\2HI\5 \21\2IJ\7\6\2\2JK\5 \21\2KL\7\7\2\2"+
		"LM\5 \21\2M\13\3\2\2\2NO\7#\2\2OP\7\b\2\2PQ\5 \21\2Q\r\3\2\2\2RS\7\t\2"+
		"\2ST\5\26\f\2TU\7\n\2\2UV\5\6\4\2VW\7\13\2\2WX\5\6\4\2X\17\3\2\2\2YZ\7"+
		"\f\2\2Z[\5\26\f\2[\\\7\r\2\2\\]\5\6\4\2]\21\3\2\2\2^_\7\16\2\2_`\5\26"+
		"\f\2`\23\3\2\2\2ab\7\17\2\2b\25\3\2\2\2ch\5\30\r\2dh\5\32\16\2eh\5\34"+
		"\17\2fh\5\36\20\2gc\3\2\2\2gd\3\2\2\2ge\3\2\2\2gf\3\2\2\2h\27\3\2\2\2"+
		"ij\7\20\2\2jk\5 \21\2kl\t\2\2\2lm\5 \21\2mn\7\27\2\2n\31\3\2\2\2op\7\20"+
		"\2\2pq\5\26\f\2qr\t\3\2\2rs\5\26\f\2st\7\27\2\2t\33\3\2\2\2uv\7\33\2\2"+
		"vw\5\26\f\2w\35\3\2\2\2xy\t\4\2\2y\37\3\2\2\2z~\5\"\22\2{~\5$\23\2|~\5"+
		"&\24\2}z\3\2\2\2}{\3\2\2\2}|\3\2\2\2~!\3\2\2\2\177\u0080\7\20\2\2\u0080"+
		"\u0081\5 \21\2\u0081\u0082\t\5\2\2\u0082\u0083\5 \21\2\u0083\u0084\7\27"+
		"\2\2\u0084#\3\2\2\2\u0085\u0086\7$\2\2\u0086%\3\2\2\2\u0087\u0088\7#\2"+
		"\2\u0088\'\3\2\2\2\6/:g}";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}