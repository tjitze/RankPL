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
		T__24=25, T__25=26, T__26=27, T__27=28, VAR=29, INT=30, COMMENT=31, SPACE=32, 
		OTHER=33;
	public static final int
		RULE_parse = 0, RULE_program = 1, RULE_statement = 2, RULE_rank_assign_stat = 3, 
		RULE_assignment_stat = 4, RULE_if_stat = 5, RULE_while_stat = 6, RULE_observe_stat = 7, 
		RULE_skip_stat = 8, RULE_boolexpr = 9, RULE_compareexpr = 10, RULE_booleanexpr = 11, 
		RULE_negateexpr = 12, RULE_litboolexpr = 13, RULE_numexpr = 14, RULE_arithnumexpr = 15, 
		RULE_litnumexpr = 16, RULE_varnumexpr = 17;
	public static final String[] ruleNames = {
		"parse", "program", "statement", "rank_assign_stat", "assignment_stat", 
		"if_stat", "while_stat", "observe_stat", "skip_stat", "boolexpr", "compareexpr", 
		"booleanexpr", "negateexpr", "litboolexpr", "numexpr", "arithnumexpr", 
		"litnumexpr", "varnumexpr"
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
			setState(36);
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
			setState(41); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(38);
				statement();
				setState(39);
				match(T__0);
				}
				}
				setState(43); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << T__5) | (1L << T__7) | (1L << T__9) | (1L << T__10) | (1L << VAR))) != 0) );
			setState(45);
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
		public Rank_assign_statContext rank_assign_stat() {
			return getRuleContext(Rank_assign_statContext.class,0);
		}
		public Assignment_statContext assignment_stat() {
			return getRuleContext(Assignment_statContext.class,0);
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
			setState(53);
			switch (_input.LA(1)) {
			case T__1:
				enterOuterAlt(_localctx, 1);
				{
				setState(47);
				rank_assign_stat();
				}
				break;
			case VAR:
				enterOuterAlt(_localctx, 2);
				{
				setState(48);
				assignment_stat();
				}
				break;
			case T__5:
				enterOuterAlt(_localctx, 3);
				{
				setState(49);
				if_stat();
				}
				break;
			case T__7:
				enterOuterAlt(_localctx, 4);
				{
				setState(50);
				while_stat();
				}
				break;
			case T__9:
				enterOuterAlt(_localctx, 5);
				{
				setState(51);
				observe_stat();
				}
				break;
			case T__10:
				enterOuterAlt(_localctx, 6);
				{
				setState(52);
				skip_stat();
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

	public static class Rank_assign_statContext extends ParserRuleContext {
		public NumexprContext numexpr() {
			return getRuleContext(NumexprContext.class,0);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public Rank_assign_statContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rank_assign_stat; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).enterRank_assign_stat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).exitRank_assign_stat(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DefProgVisitor ) return ((DefProgVisitor<? extends T>)visitor).visitRank_assign_stat(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Rank_assign_statContext rank_assign_stat() throws RecognitionException {
		Rank_assign_statContext _localctx = new Rank_assign_statContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_rank_assign_stat);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(55);
			match(T__1);
			setState(56);
			numexpr();
			setState(57);
			match(T__2);
			setState(58);
			statement();
			setState(59);
			match(T__3);
			setState(60);
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
		enterRule(_localctx, 8, RULE_assignment_stat);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(62);
			match(VAR);
			setState(63);
			match(T__4);
			setState(64);
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
		enterRule(_localctx, 10, RULE_if_stat);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(66);
			match(T__5);
			setState(67);
			boolexpr();
			setState(68);
			match(T__6);
			setState(69);
			statement();
			setState(70);
			match(T__3);
			setState(71);
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
		enterRule(_localctx, 12, RULE_while_stat);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(73);
			match(T__7);
			setState(74);
			boolexpr();
			setState(75);
			match(T__8);
			setState(76);
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
		enterRule(_localctx, 14, RULE_observe_stat);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(78);
			match(T__9);
			setState(79);
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
		enterRule(_localctx, 16, RULE_skip_stat);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(81);
			match(T__10);
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
		enterRule(_localctx, 18, RULE_boolexpr);
		try {
			setState(87);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(83);
				compareexpr();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(84);
				booleanexpr();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(85);
				negateexpr();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(86);
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
		enterRule(_localctx, 20, RULE_compareexpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(89);
			match(T__11);
			setState(90);
			numexpr();
			setState(91);
			((CompareexprContext)_localctx).cop = _input.LT(1);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__12) | (1L << T__13) | (1L << T__14) | (1L << T__15) | (1L << T__16) | (1L << T__17))) != 0)) ) {
				((CompareexprContext)_localctx).cop = (Token)_errHandler.recoverInline(this);
			} else {
				consume();
			}
			setState(92);
			numexpr();
			setState(93);
			match(T__18);
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
		enterRule(_localctx, 22, RULE_booleanexpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(95);
			match(T__11);
			setState(96);
			boolexpr();
			setState(97);
			((BooleanexprContext)_localctx).bop = _input.LT(1);
			_la = _input.LA(1);
			if ( !(_la==T__19 || _la==T__20) ) {
				((BooleanexprContext)_localctx).bop = (Token)_errHandler.recoverInline(this);
			} else {
				consume();
			}
			setState(98);
			boolexpr();
			setState(99);
			match(T__18);
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
		enterRule(_localctx, 24, RULE_negateexpr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(101);
			match(T__21);
			setState(102);
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
		enterRule(_localctx, 26, RULE_litboolexpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(104);
			_la = _input.LA(1);
			if ( !(_la==T__22 || _la==T__23) ) {
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
		enterRule(_localctx, 28, RULE_numexpr);
		try {
			setState(109);
			switch (_input.LA(1)) {
			case T__11:
				enterOuterAlt(_localctx, 1);
				{
				setState(106);
				arithnumexpr();
				}
				break;
			case INT:
				enterOuterAlt(_localctx, 2);
				{
				setState(107);
				litnumexpr();
				}
				break;
			case VAR:
				enterOuterAlt(_localctx, 3);
				{
				setState(108);
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
		enterRule(_localctx, 30, RULE_arithnumexpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(111);
			match(T__11);
			setState(112);
			numexpr();
			setState(113);
			((ArithnumexprContext)_localctx).aop = _input.LT(1);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__24) | (1L << T__25) | (1L << T__26) | (1L << T__27))) != 0)) ) {
				((ArithnumexprContext)_localctx).aop = (Token)_errHandler.recoverInline(this);
			} else {
				consume();
			}
			setState(114);
			numexpr();
			setState(115);
			match(T__18);
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
		enterRule(_localctx, 32, RULE_litnumexpr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(117);
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
		enterRule(_localctx, 34, RULE_varnumexpr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(119);
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3#|\4\2\t\2\4\3\t\3"+
		"\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t\13\4\f"+
		"\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22\4\23\t"+
		"\23\3\2\3\2\3\3\3\3\3\3\6\3,\n\3\r\3\16\3-\3\3\3\3\3\4\3\4\3\4\3\4\3\4"+
		"\3\4\5\48\n\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\7\3\7\3\7"+
		"\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\n\3\n\3\13\3\13\3\13"+
		"\3\13\5\13Z\n\13\3\f\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\16"+
		"\3\16\3\16\3\17\3\17\3\20\3\20\3\20\5\20p\n\20\3\21\3\21\3\21\3\21\3\21"+
		"\3\21\3\22\3\22\3\23\3\23\3\23\2\2\24\2\4\6\b\n\f\16\20\22\24\26\30\32"+
		"\34\36 \"$\2\6\3\2\17\24\3\2\26\27\3\2\31\32\3\2\33\36t\2&\3\2\2\2\4+"+
		"\3\2\2\2\6\67\3\2\2\2\b9\3\2\2\2\n@\3\2\2\2\fD\3\2\2\2\16K\3\2\2\2\20"+
		"P\3\2\2\2\22S\3\2\2\2\24Y\3\2\2\2\26[\3\2\2\2\30a\3\2\2\2\32g\3\2\2\2"+
		"\34j\3\2\2\2\36o\3\2\2\2 q\3\2\2\2\"w\3\2\2\2$y\3\2\2\2&\'\5\4\3\2\'\3"+
		"\3\2\2\2()\5\6\4\2)*\7\3\2\2*,\3\2\2\2+(\3\2\2\2,-\3\2\2\2-+\3\2\2\2-"+
		".\3\2\2\2./\3\2\2\2/\60\7\2\2\3\60\5\3\2\2\2\618\5\b\5\2\628\5\n\6\2\63"+
		"8\5\f\7\2\648\5\16\b\2\658\5\20\t\2\668\5\22\n\2\67\61\3\2\2\2\67\62\3"+
		"\2\2\2\67\63\3\2\2\2\67\64\3\2\2\2\67\65\3\2\2\2\67\66\3\2\2\28\7\3\2"+
		"\2\29:\7\4\2\2:;\5\36\20\2;<\7\5\2\2<=\5\6\4\2=>\7\6\2\2>?\5\6\4\2?\t"+
		"\3\2\2\2@A\7\37\2\2AB\7\7\2\2BC\5\36\20\2C\13\3\2\2\2DE\7\b\2\2EF\5\24"+
		"\13\2FG\7\t\2\2GH\5\6\4\2HI\7\6\2\2IJ\5\6\4\2J\r\3\2\2\2KL\7\n\2\2LM\5"+
		"\24\13\2MN\7\13\2\2NO\5\6\4\2O\17\3\2\2\2PQ\7\f\2\2QR\5\24\13\2R\21\3"+
		"\2\2\2ST\7\r\2\2T\23\3\2\2\2UZ\5\26\f\2VZ\5\30\r\2WZ\5\32\16\2XZ\5\34"+
		"\17\2YU\3\2\2\2YV\3\2\2\2YW\3\2\2\2YX\3\2\2\2Z\25\3\2\2\2[\\\7\16\2\2"+
		"\\]\5\36\20\2]^\t\2\2\2^_\5\36\20\2_`\7\25\2\2`\27\3\2\2\2ab\7\16\2\2"+
		"bc\5\24\13\2cd\t\3\2\2de\5\24\13\2ef\7\25\2\2f\31\3\2\2\2gh\7\30\2\2h"+
		"i\5\24\13\2i\33\3\2\2\2jk\t\4\2\2k\35\3\2\2\2lp\5 \21\2mp\5\"\22\2np\5"+
		"$\23\2ol\3\2\2\2om\3\2\2\2on\3\2\2\2p\37\3\2\2\2qr\7\16\2\2rs\5\36\20"+
		"\2st\t\5\2\2tu\5\36\20\2uv\7\25\2\2v!\3\2\2\2wx\7 \2\2x#\3\2\2\2yz\7\37"+
		"\2\2z%\3\2\2\2\6-\67Yo";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}