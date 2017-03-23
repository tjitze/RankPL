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
		RULE_numexpr = 11;
	public static final String[] ruleNames = {
		"parse", "program", "statement", "ranked_choice", "choice_assignment_stat", 
		"assignment_stat", "if_stat", "while_stat", "observe_stat", "skip_stat", 
		"boolexpr", "numexpr"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "';'", "'{'", "'}'", "'<<'", "'>>'", "':='", "'IF'", "'THEN'", "'ELSE'", 
		"'WHILE'", "'DO'", "'OBSERVE'", "'SKIP'", "'('", "')'", "'!'", "'<'", 
		"'<='", "'>'", "'>='", "'=='", "'!='", "'&'", "'|'", "'^'", "'true'", 
		"'false'", "'*'", "'/'", "'%'", "'+'", "'-'"
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
			setState(24);
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
			setState(29); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(26);
				statement();
				setState(27);
				match(T__0);
				}
				}
				setState(31); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << T__6) | (1L << T__9) | (1L << T__11) | (1L << T__12) | (1L << VAR))) != 0) );
			setState(33);
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
			setState(42);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(35);
				ranked_choice();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(36);
				assignment_stat();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(37);
				choice_assignment_stat();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(38);
				if_stat();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(39);
				while_stat();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(40);
				observe_stat();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(41);
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
			setState(44);
			match(T__1);
			setState(45);
			statement();
			setState(46);
			match(T__2);
			setState(47);
			match(T__3);
			setState(48);
			numexpr(0);
			setState(49);
			match(T__4);
			setState(50);
			match(T__1);
			setState(51);
			statement();
			setState(52);
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
			setState(54);
			match(VAR);
			setState(55);
			match(T__5);
			setState(56);
			numexpr(0);
			setState(57);
			match(T__3);
			setState(58);
			numexpr(0);
			setState(59);
			match(T__4);
			setState(60);
			numexpr(0);
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
			setState(62);
			match(VAR);
			setState(63);
			match(T__5);
			setState(64);
			numexpr(0);
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
			setState(66);
			match(T__6);
			setState(67);
			boolexpr(0);
			setState(68);
			match(T__7);
			setState(69);
			statement();
			setState(70);
			match(T__8);
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
		enterRule(_localctx, 14, RULE_while_stat);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(73);
			match(T__9);
			setState(74);
			boolexpr(0);
			setState(75);
			match(T__10);
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
		enterRule(_localctx, 16, RULE_observe_stat);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(78);
			match(T__11);
			setState(79);
			boolexpr(0);
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
			setState(81);
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
		public BoolexprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_boolexpr; }
	 
		public BoolexprContext() { }
		public void copyFrom(BoolexprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class CompareExprContext extends BoolexprContext {
		public Token cop;
		public List<NumexprContext> numexpr() {
			return getRuleContexts(NumexprContext.class);
		}
		public NumexprContext numexpr(int i) {
			return getRuleContext(NumexprContext.class,i);
		}
		public CompareExprContext(BoolexprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).enterCompareExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).exitCompareExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DefProgVisitor ) return ((DefProgVisitor<? extends T>)visitor).visitCompareExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LiteralBoolExprContext extends BoolexprContext {
		public LiteralBoolExprContext(BoolexprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).enterLiteralBoolExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).exitLiteralBoolExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DefProgVisitor ) return ((DefProgVisitor<? extends T>)visitor).visitLiteralBoolExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ParboolExprContext extends BoolexprContext {
		public BoolexprContext boolexpr() {
			return getRuleContext(BoolexprContext.class,0);
		}
		public ParboolExprContext(BoolexprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).enterParboolExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).exitParboolExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DefProgVisitor ) return ((DefProgVisitor<? extends T>)visitor).visitParboolExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BooleanExprContext extends BoolexprContext {
		public Token bop;
		public List<BoolexprContext> boolexpr() {
			return getRuleContexts(BoolexprContext.class);
		}
		public BoolexprContext boolexpr(int i) {
			return getRuleContext(BoolexprContext.class,i);
		}
		public BooleanExprContext(BoolexprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).enterBooleanExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).exitBooleanExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DefProgVisitor ) return ((DefProgVisitor<? extends T>)visitor).visitBooleanExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NegateExprContext extends BoolexprContext {
		public BoolexprContext boolexpr() {
			return getRuleContext(BoolexprContext.class,0);
		}
		public NegateExprContext(BoolexprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).enterNegateExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).exitNegateExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DefProgVisitor ) return ((DefProgVisitor<? extends T>)visitor).visitNegateExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BoolexprContext boolexpr() throws RecognitionException {
		return boolexpr(0);
	}

	private BoolexprContext boolexpr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		BoolexprContext _localctx = new BoolexprContext(_ctx, _parentState);
		BoolexprContext _prevctx = _localctx;
		int _startState = 20;
		enterRecursionRule(_localctx, 20, RULE_boolexpr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(96);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				{
				_localctx = new NegateExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(84);
				match(T__15);
				setState(85);
				boolexpr(7);
				}
				break;
			case 2:
				{
				_localctx = new ParboolExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(86);
				match(T__13);
				setState(87);
				boolexpr(0);
				setState(88);
				match(T__14);
				}
				break;
			case 3:
				{
				_localctx = new CompareExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(90);
				numexpr(0);
				setState(91);
				((CompareExprContext)_localctx).cop = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__16) | (1L << T__17) | (1L << T__18) | (1L << T__19) | (1L << T__20) | (1L << T__21))) != 0)) ) {
					((CompareExprContext)_localctx).cop = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(92);
				numexpr(0);
				}
				break;
			case 4:
				{
				_localctx = new LiteralBoolExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(94);
				match(T__25);
				}
				break;
			case 5:
				{
				_localctx = new LiteralBoolExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(95);
				match(T__26);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(109);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(107);
					switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
					case 1:
						{
						_localctx = new BooleanExprContext(new BoolexprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_boolexpr);
						setState(98);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(99);
						((BooleanExprContext)_localctx).bop = match(T__22);
						setState(100);
						boolexpr(6);
						}
						break;
					case 2:
						{
						_localctx = new BooleanExprContext(new BoolexprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_boolexpr);
						setState(101);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(102);
						((BooleanExprContext)_localctx).bop = match(T__23);
						setState(103);
						boolexpr(5);
						}
						break;
					case 3:
						{
						_localctx = new BooleanExprContext(new BoolexprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_boolexpr);
						setState(104);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(105);
						((BooleanExprContext)_localctx).bop = match(T__24);
						setState(106);
						boolexpr(4);
						}
						break;
					}
					} 
				}
				setState(111);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class NumexprContext extends ParserRuleContext {
		public NumexprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_numexpr; }
	 
		public NumexprContext() { }
		public void copyFrom(NumexprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class LiteralNumExprContext extends NumexprContext {
		public TerminalNode INT() { return getToken(DefProgParser.INT, 0); }
		public LiteralNumExprContext(NumexprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).enterLiteralNumExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).exitLiteralNumExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DefProgVisitor ) return ((DefProgVisitor<? extends T>)visitor).visitLiteralNumExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class VariableNumExprContext extends NumexprContext {
		public TerminalNode VAR() { return getToken(DefProgParser.VAR, 0); }
		public VariableNumExprContext(NumexprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).enterVariableNumExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).exitVariableNumExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DefProgVisitor ) return ((DefProgVisitor<? extends T>)visitor).visitVariableNumExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ArithmeticNumExprContext extends NumexprContext {
		public Token aop;
		public List<NumexprContext> numexpr() {
			return getRuleContexts(NumexprContext.class);
		}
		public NumexprContext numexpr(int i) {
			return getRuleContext(NumexprContext.class,i);
		}
		public ArithmeticNumExprContext(NumexprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).enterArithmeticNumExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).exitArithmeticNumExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DefProgVisitor ) return ((DefProgVisitor<? extends T>)visitor).visitArithmeticNumExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ParNumExprContext extends NumexprContext {
		public NumexprContext numexpr() {
			return getRuleContext(NumexprContext.class,0);
		}
		public ParNumExprContext(NumexprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).enterParNumExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).exitParNumExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DefProgVisitor ) return ((DefProgVisitor<? extends T>)visitor).visitParNumExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NumexprContext numexpr() throws RecognitionException {
		return numexpr(0);
	}

	private NumexprContext numexpr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		NumexprContext _localctx = new NumexprContext(_ctx, _parentState);
		NumexprContext _prevctx = _localctx;
		int _startState = 22;
		enterRecursionRule(_localctx, 22, RULE_numexpr, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(119);
			switch (_input.LA(1)) {
			case T__13:
				{
				_localctx = new ParNumExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(113);
				match(T__13);
				setState(114);
				numexpr(0);
				setState(115);
				match(T__14);
				}
				break;
			case INT:
				{
				_localctx = new LiteralNumExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(117);
				match(INT);
				}
				break;
			case VAR:
				{
				_localctx = new VariableNumExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(118);
				match(VAR);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(147);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,7,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(145);
					switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
					case 1:
						{
						_localctx = new ArithmeticNumExprContext(new NumexprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_numexpr);
						setState(121);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(122);
						((ArithmeticNumExprContext)_localctx).aop = match(T__27);
						setState(123);
						numexpr(11);
						}
						break;
					case 2:
						{
						_localctx = new ArithmeticNumExprContext(new NumexprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_numexpr);
						setState(124);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(125);
						((ArithmeticNumExprContext)_localctx).aop = match(T__28);
						setState(126);
						numexpr(10);
						}
						break;
					case 3:
						{
						_localctx = new ArithmeticNumExprContext(new NumexprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_numexpr);
						setState(127);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(128);
						((ArithmeticNumExprContext)_localctx).aop = match(T__29);
						setState(129);
						numexpr(9);
						}
						break;
					case 4:
						{
						_localctx = new ArithmeticNumExprContext(new NumexprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_numexpr);
						setState(130);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(131);
						((ArithmeticNumExprContext)_localctx).aop = match(T__30);
						setState(132);
						numexpr(8);
						}
						break;
					case 5:
						{
						_localctx = new ArithmeticNumExprContext(new NumexprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_numexpr);
						setState(133);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(134);
						((ArithmeticNumExprContext)_localctx).aop = match(T__31);
						setState(135);
						numexpr(7);
						}
						break;
					case 6:
						{
						_localctx = new ArithmeticNumExprContext(new NumexprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_numexpr);
						setState(136);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(137);
						((ArithmeticNumExprContext)_localctx).aop = match(T__22);
						setState(138);
						numexpr(6);
						}
						break;
					case 7:
						{
						_localctx = new ArithmeticNumExprContext(new NumexprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_numexpr);
						setState(139);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(140);
						((ArithmeticNumExprContext)_localctx).aop = match(T__23);
						setState(141);
						numexpr(5);
						}
						break;
					case 8:
						{
						_localctx = new ArithmeticNumExprContext(new NumexprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_numexpr);
						setState(142);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(143);
						((ArithmeticNumExprContext)_localctx).aop = match(T__24);
						setState(144);
						numexpr(4);
						}
						break;
					}
					} 
				}
				setState(149);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,7,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 10:
			return boolexpr_sempred((BoolexprContext)_localctx, predIndex);
		case 11:
			return numexpr_sempred((NumexprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean boolexpr_sempred(BoolexprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 5);
		case 1:
			return precpred(_ctx, 4);
		case 2:
			return precpred(_ctx, 3);
		}
		return true;
	}
	private boolean numexpr_sempred(NumexprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 3:
			return precpred(_ctx, 10);
		case 4:
			return precpred(_ctx, 9);
		case 5:
			return precpred(_ctx, 8);
		case 6:
			return precpred(_ctx, 7);
		case 7:
			return precpred(_ctx, 6);
		case 8:
			return precpred(_ctx, 5);
		case 9:
			return precpred(_ctx, 4);
		case 10:
			return precpred(_ctx, 3);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\'\u0099\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\3\2\3\2\3\3\3\3\3\3\6\3 \n\3\r\3\16\3!\3\3\3\3\3\4"+
		"\3\4\3\4\3\4\3\4\3\4\3\4\5\4-\n\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5"+
		"\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3"+
		"\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\13\3\13\3\f\3\f\3\f\3\f\3"+
		"\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\5\fc\n\f\3\f\3\f\3\f\3\f\3\f\3\f\3"+
		"\f\3\f\3\f\7\fn\n\f\f\f\16\fq\13\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\5\rz\n"+
		"\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r"+
		"\3\r\3\r\3\r\3\r\3\r\3\r\3\r\7\r\u0094\n\r\f\r\16\r\u0097\13\r\3\r\2\4"+
		"\26\30\16\2\4\6\b\n\f\16\20\22\24\26\30\2\3\3\2\23\30\u00a4\2\32\3\2\2"+
		"\2\4\37\3\2\2\2\6,\3\2\2\2\b.\3\2\2\2\n8\3\2\2\2\f@\3\2\2\2\16D\3\2\2"+
		"\2\20K\3\2\2\2\22P\3\2\2\2\24S\3\2\2\2\26b\3\2\2\2\30y\3\2\2\2\32\33\5"+
		"\4\3\2\33\3\3\2\2\2\34\35\5\6\4\2\35\36\7\3\2\2\36 \3\2\2\2\37\34\3\2"+
		"\2\2 !\3\2\2\2!\37\3\2\2\2!\"\3\2\2\2\"#\3\2\2\2#$\7\2\2\3$\5\3\2\2\2"+
		"%-\5\b\5\2&-\5\f\7\2\'-\5\n\6\2(-\5\16\b\2)-\5\20\t\2*-\5\22\n\2+-\5\24"+
		"\13\2,%\3\2\2\2,&\3\2\2\2,\'\3\2\2\2,(\3\2\2\2,)\3\2\2\2,*\3\2\2\2,+\3"+
		"\2\2\2-\7\3\2\2\2./\7\4\2\2/\60\5\6\4\2\60\61\7\5\2\2\61\62\7\6\2\2\62"+
		"\63\5\30\r\2\63\64\7\7\2\2\64\65\7\4\2\2\65\66\5\6\4\2\66\67\7\5\2\2\67"+
		"\t\3\2\2\289\7#\2\29:\7\b\2\2:;\5\30\r\2;<\7\6\2\2<=\5\30\r\2=>\7\7\2"+
		"\2>?\5\30\r\2?\13\3\2\2\2@A\7#\2\2AB\7\b\2\2BC\5\30\r\2C\r\3\2\2\2DE\7"+
		"\t\2\2EF\5\26\f\2FG\7\n\2\2GH\5\6\4\2HI\7\13\2\2IJ\5\6\4\2J\17\3\2\2\2"+
		"KL\7\f\2\2LM\5\26\f\2MN\7\r\2\2NO\5\6\4\2O\21\3\2\2\2PQ\7\16\2\2QR\5\26"+
		"\f\2R\23\3\2\2\2ST\7\17\2\2T\25\3\2\2\2UV\b\f\1\2VW\7\22\2\2Wc\5\26\f"+
		"\tXY\7\20\2\2YZ\5\26\f\2Z[\7\21\2\2[c\3\2\2\2\\]\5\30\r\2]^\t\2\2\2^_"+
		"\5\30\r\2_c\3\2\2\2`c\7\34\2\2ac\7\35\2\2bU\3\2\2\2bX\3\2\2\2b\\\3\2\2"+
		"\2b`\3\2\2\2ba\3\2\2\2co\3\2\2\2de\f\7\2\2ef\7\31\2\2fn\5\26\f\bgh\f\6"+
		"\2\2hi\7\32\2\2in\5\26\f\7jk\f\5\2\2kl\7\33\2\2ln\5\26\f\6md\3\2\2\2m"+
		"g\3\2\2\2mj\3\2\2\2nq\3\2\2\2om\3\2\2\2op\3\2\2\2p\27\3\2\2\2qo\3\2\2"+
		"\2rs\b\r\1\2st\7\20\2\2tu\5\30\r\2uv\7\21\2\2vz\3\2\2\2wz\7$\2\2xz\7#"+
		"\2\2yr\3\2\2\2yw\3\2\2\2yx\3\2\2\2z\u0095\3\2\2\2{|\f\f\2\2|}\7\36\2\2"+
		"}\u0094\5\30\r\r~\177\f\13\2\2\177\u0080\7\37\2\2\u0080\u0094\5\30\r\f"+
		"\u0081\u0082\f\n\2\2\u0082\u0083\7 \2\2\u0083\u0094\5\30\r\13\u0084\u0085"+
		"\f\t\2\2\u0085\u0086\7!\2\2\u0086\u0094\5\30\r\n\u0087\u0088\f\b\2\2\u0088"+
		"\u0089\7\"\2\2\u0089\u0094\5\30\r\t\u008a\u008b\f\7\2\2\u008b\u008c\7"+
		"\31\2\2\u008c\u0094\5\30\r\b\u008d\u008e\f\6\2\2\u008e\u008f\7\32\2\2"+
		"\u008f\u0094\5\30\r\7\u0090\u0091\f\5\2\2\u0091\u0092\7\33\2\2\u0092\u0094"+
		"\5\30\r\6\u0093{\3\2\2\2\u0093~\3\2\2\2\u0093\u0081\3\2\2\2\u0093\u0084"+
		"\3\2\2\2\u0093\u0087\3\2\2\2\u0093\u008a\3\2\2\2\u0093\u008d\3\2\2\2\u0093"+
		"\u0090\3\2\2\2\u0094\u0097\3\2\2\2\u0095\u0093\3\2\2\2\u0095\u0096\3\2"+
		"\2\2\u0096\31\3\2\2\2\u0097\u0095\3\2\2\2\n!,bmoy\u0093\u0095";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}