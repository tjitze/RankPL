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
		T__31=32, T__32=33, T__33=34, T__34=35, T__35=36, T__36=37, T__37=38, 
		T__38=39, T__39=40, T__40=41, T__41=42, T__42=43, T__43=44, T__44=45, 
		T__45=46, T__46=47, T__47=48, T__48=49, T__49=50, T__50=51, T__51=52, 
		VAR=53, INT=54, COMMENT=55, SPACE=56, OTHER=57;
	public static final int
		RULE_parse = 0, RULE_program = 1, RULE_statement = 2, RULE_boolexpr = 3, 
		RULE_numexpr = 4, RULE_index = 5;
	public static final String[] ruleNames = {
		"parse", "program", "statement", "boolexpr", "numexpr", "index"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "';'", "':='", "'['", "','", "']'", "'<<'", "'>>'", "'IF'", "'if'", 
		"'THEN'", "'then'", "'ELSE'", "'else'", "'WHILE'", "'while'", "'DO'", 
		"'do'", "'OBSERVE'", "'observe'", "'OBSERVE-L'", "'observe-l'", "'('", 
		"')'", "'OBSERVE-J'", "'observe-j'", "'SKIP'", "'skip'", "'{'", "'}'", 
		"'!'", "'<'", "'<='", "'>'", "'>='", "'=='", "'!='", "'&'", "'|'", "'^'", 
		"'TRUE'", "'true'", "'FALSE'", "'false'", "'*'", "'/'", "'%'", "'+'", 
		"'-'", "'ABS'", "'abs'", "'RANK'", "'rank'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
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
			setState(12);
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
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public TerminalNode EOF() { return getToken(DefProgParser.EOF, 0); }
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
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(14);
			statement(0);
			setState(19);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,0,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(15);
					match(T__0);
					setState(16);
					statement(0);
					}
					} 
				}
				setState(21);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,0,_ctx);
			}
			setState(23);
			_la = _input.LA(1);
			if (_la==T__0) {
				{
				setState(22);
				match(T__0);
				}
			}

			setState(25);
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
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
	 
		public StatementContext() { }
		public void copyFrom(StatementContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ObserveLContext extends StatementContext {
		public NumexprContext numexpr() {
			return getRuleContext(NumexprContext.class,0);
		}
		public BoolexprContext boolexpr() {
			return getRuleContext(BoolexprContext.class,0);
		}
		public ObserveLContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).enterObserveL(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).exitObserveL(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DefProgVisitor ) return ((DefProgVisitor<? extends T>)visitor).visitObserveL(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Array_assignment_statContext extends StatementContext {
		public TerminalNode VAR() { return getToken(DefProgParser.VAR, 0); }
		public List<IndexContext> index() {
			return getRuleContexts(IndexContext.class);
		}
		public IndexContext index(int i) {
			return getRuleContext(IndexContext.class,i);
		}
		public List<NumexprContext> numexpr() {
			return getRuleContexts(NumexprContext.class);
		}
		public NumexprContext numexpr(int i) {
			return getRuleContext(NumexprContext.class,i);
		}
		public Array_assignment_statContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).enterArray_assignment_stat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).exitArray_assignment_stat(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DefProgVisitor ) return ((DefProgVisitor<? extends T>)visitor).visitArray_assignment_stat(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class While_statContext extends StatementContext {
		public BoolexprContext boolexpr() {
			return getRuleContext(BoolexprContext.class,0);
		}
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public While_statContext(StatementContext ctx) { copyFrom(ctx); }
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
	public static class Statement_sequenceContext extends StatementContext {
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public Statement_sequenceContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).enterStatement_sequence(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).exitStatement_sequence(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DefProgVisitor ) return ((DefProgVisitor<? extends T>)visitor).visitStatement_sequence(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ObserveContext extends StatementContext {
		public BoolexprContext boolexpr() {
			return getRuleContext(BoolexprContext.class,0);
		}
		public ObserveContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).enterObserve(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).exitObserve(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DefProgVisitor ) return ((DefProgVisitor<? extends T>)visitor).visitObserve(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Ranked_choiceContext extends StatementContext {
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public NumexprContext numexpr() {
			return getRuleContext(NumexprContext.class,0);
		}
		public Ranked_choiceContext(StatementContext ctx) { copyFrom(ctx); }
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
	public static class Choice_assignment_statContext extends StatementContext {
		public TerminalNode VAR() { return getToken(DefProgParser.VAR, 0); }
		public List<NumexprContext> numexpr() {
			return getRuleContexts(NumexprContext.class);
		}
		public NumexprContext numexpr(int i) {
			return getRuleContext(NumexprContext.class,i);
		}
		public List<IndexContext> index() {
			return getRuleContexts(IndexContext.class);
		}
		public IndexContext index(int i) {
			return getRuleContext(IndexContext.class,i);
		}
		public Choice_assignment_statContext(StatementContext ctx) { copyFrom(ctx); }
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
	public static class If_statContext extends StatementContext {
		public BoolexprContext boolexpr() {
			return getRuleContext(BoolexprContext.class,0);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public If_statContext(StatementContext ctx) { copyFrom(ctx); }
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
	public static class Assignment_statContext extends StatementContext {
		public TerminalNode VAR() { return getToken(DefProgParser.VAR, 0); }
		public NumexprContext numexpr() {
			return getRuleContext(NumexprContext.class,0);
		}
		public List<IndexContext> index() {
			return getRuleContexts(IndexContext.class);
		}
		public IndexContext index(int i) {
			return getRuleContext(IndexContext.class,i);
		}
		public Assignment_statContext(StatementContext ctx) { copyFrom(ctx); }
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
	public static class Skip_statContext extends StatementContext {
		public Skip_statContext(StatementContext ctx) { copyFrom(ctx); }
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
	public static class ObserveJContext extends StatementContext {
		public NumexprContext numexpr() {
			return getRuleContext(NumexprContext.class,0);
		}
		public BoolexprContext boolexpr() {
			return getRuleContext(BoolexprContext.class,0);
		}
		public ObserveJContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).enterObserveJ(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).exitObserveJ(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DefProgVisitor ) return ((DefProgVisitor<? extends T>)visitor).visitObserveJ(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		return statement(0);
	}

	private StatementContext statement(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		StatementContext _localctx = new StatementContext(_ctx, _parentState);
		StatementContext _prevctx = _localctx;
		int _startState = 4;
		enterRecursionRule(_localctx, 4, RULE_statement, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(111);
			switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
			case 1:
				{
				_localctx = new While_statContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(28);
				_la = _input.LA(1);
				if ( !(_la==T__13 || _la==T__14) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(29);
				boolexpr(0);
				setState(30);
				_la = _input.LA(1);
				if ( !(_la==T__15 || _la==T__16) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(31);
				statement(7);
				}
				break;
			case 2:
				{
				_localctx = new Assignment_statContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(33);
				match(VAR);
				setState(37);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__2) {
					{
					{
					setState(34);
					index();
					}
					}
					setState(39);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(40);
				match(T__1);
				setState(41);
				numexpr(0);
				}
				break;
			case 3:
				{
				_localctx = new Array_assignment_statContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(42);
				match(VAR);
				setState(46);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__2) {
					{
					{
					setState(43);
					index();
					}
					}
					setState(48);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(49);
				match(T__1);
				setState(50);
				match(T__2);
				setState(57);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__21) | (1L << T__48) | (1L << T__49) | (1L << T__50) | (1L << T__51) | (1L << VAR) | (1L << INT))) != 0)) {
					{
					{
					setState(51);
					numexpr(0);
					setState(53);
					_la = _input.LA(1);
					if (_la==T__3) {
						{
						setState(52);
						match(T__3);
						}
					}

					}
					}
					setState(59);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(60);
				match(T__4);
				}
				break;
			case 4:
				{
				_localctx = new Choice_assignment_statContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(61);
				match(VAR);
				setState(65);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__2) {
					{
					{
					setState(62);
					index();
					}
					}
					setState(67);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(68);
				match(T__1);
				setState(69);
				numexpr(0);
				setState(70);
				match(T__5);
				setState(71);
				numexpr(0);
				setState(72);
				match(T__6);
				setState(73);
				numexpr(0);
				}
				break;
			case 5:
				{
				_localctx = new If_statContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(75);
				_la = _input.LA(1);
				if ( !(_la==T__7 || _la==T__8) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(76);
				boolexpr(0);
				setState(77);
				_la = _input.LA(1);
				if ( !(_la==T__9 || _la==T__10) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(78);
				statement(0);
				setState(79);
				_la = _input.LA(1);
				if ( !(_la==T__11 || _la==T__12) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(80);
				statement(0);
				}
				break;
			case 6:
				{
				_localctx = new ObserveContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(82);
				_la = _input.LA(1);
				if ( !(_la==T__17 || _la==T__18) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(83);
				boolexpr(0);
				}
				break;
			case 7:
				{
				_localctx = new ObserveLContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(84);
				_la = _input.LA(1);
				if ( !(_la==T__19 || _la==T__20) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(85);
				match(T__21);
				setState(86);
				numexpr(0);
				setState(87);
				match(T__22);
				setState(88);
				boolexpr(0);
				}
				break;
			case 8:
				{
				_localctx = new ObserveJContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(90);
				_la = _input.LA(1);
				if ( !(_la==T__23 || _la==T__24) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(91);
				match(T__21);
				setState(92);
				numexpr(0);
				setState(93);
				match(T__22);
				setState(94);
				boolexpr(0);
				}
				break;
			case 9:
				{
				_localctx = new Skip_statContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(96);
				_la = _input.LA(1);
				if ( !(_la==T__25 || _la==T__26) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				}
				break;
			case 10:
				{
				_localctx = new Statement_sequenceContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(97);
				match(T__27);
				setState(98);
				statement(0);
				setState(103);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,7,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(99);
						match(T__0);
						setState(100);
						statement(0);
						}
						} 
					}
					setState(105);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,7,_ctx);
				}
				setState(107);
				_la = _input.LA(1);
				if (_la==T__0) {
					{
					setState(106);
					match(T__0);
					}
				}

				setState(109);
				match(T__28);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(121);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new Ranked_choiceContext(new StatementContext(_parentctx, _parentState));
					pushNewRecursionContext(_localctx, _startState, RULE_statement);
					setState(113);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(114);
					match(T__5);
					setState(115);
					numexpr(0);
					setState(116);
					match(T__6);
					setState(117);
					statement(3);
					}
					} 
				}
				setState(123);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
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
	public static class NumBoolExprContext extends BoolexprContext {
		public NumexprContext numexpr() {
			return getRuleContext(NumexprContext.class,0);
		}
		public NumBoolExprContext(BoolexprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).enterNumBoolExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).exitNumBoolExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DefProgVisitor ) return ((DefProgVisitor<? extends T>)visitor).visitNumBoolExpr(this);
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
		int _startState = 6;
		enterRecursionRule(_localctx, 6, RULE_boolexpr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(138);
			switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
			case 1:
				{
				_localctx = new NegateExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(125);
				match(T__29);
				setState(126);
				boolexpr(8);
				}
				break;
			case 2:
				{
				_localctx = new ParboolExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(127);
				match(T__21);
				setState(128);
				boolexpr(0);
				setState(129);
				match(T__22);
				}
				break;
			case 3:
				{
				_localctx = new CompareExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(131);
				numexpr(0);
				setState(132);
				((CompareExprContext)_localctx).cop = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__30) | (1L << T__31) | (1L << T__32) | (1L << T__33) | (1L << T__34) | (1L << T__35))) != 0)) ) {
					((CompareExprContext)_localctx).cop = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(133);
				numexpr(0);
				}
				break;
			case 4:
				{
				_localctx = new LiteralBoolExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(135);
				_la = _input.LA(1);
				if ( !(_la==T__39 || _la==T__40) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				}
				break;
			case 5:
				{
				_localctx = new LiteralBoolExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(136);
				_la = _input.LA(1);
				if ( !(_la==T__41 || _la==T__42) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				}
				break;
			case 6:
				{
				_localctx = new NumBoolExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(137);
				numexpr(0);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(151);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(149);
					switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
					case 1:
						{
						_localctx = new BooleanExprContext(new BoolexprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_boolexpr);
						setState(140);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(141);
						((BooleanExprContext)_localctx).bop = match(T__36);
						setState(142);
						boolexpr(7);
						}
						break;
					case 2:
						{
						_localctx = new BooleanExprContext(new BoolexprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_boolexpr);
						setState(143);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(144);
						((BooleanExprContext)_localctx).bop = match(T__37);
						setState(145);
						boolexpr(6);
						}
						break;
					case 3:
						{
						_localctx = new BooleanExprContext(new BoolexprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_boolexpr);
						setState(146);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(147);
						((BooleanExprContext)_localctx).bop = match(T__38);
						setState(148);
						boolexpr(5);
						}
						break;
					}
					} 
				}
				setState(153);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
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
	public static class AbsExprContext extends NumexprContext {
		public NumexprContext numexpr() {
			return getRuleContext(NumexprContext.class,0);
		}
		public AbsExprContext(NumexprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).enterAbsExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).exitAbsExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DefProgVisitor ) return ((DefProgVisitor<? extends T>)visitor).visitAbsExpr(this);
			else return visitor.visitChildren(this);
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
		public List<IndexContext> index() {
			return getRuleContexts(IndexContext.class);
		}
		public IndexContext index(int i) {
			return getRuleContext(IndexContext.class,i);
		}
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
	public static class RankExprContext extends NumexprContext {
		public BoolexprContext boolexpr() {
			return getRuleContext(BoolexprContext.class,0);
		}
		public RankExprContext(NumexprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).enterRankExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).exitRankExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DefProgVisitor ) return ((DefProgVisitor<? extends T>)visitor).visitRankExpr(this);
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
		int _startState = 8;
		enterRecursionRule(_localctx, 8, RULE_numexpr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(177);
			switch (_input.LA(1)) {
			case T__21:
				{
				_localctx = new ParNumExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(155);
				match(T__21);
				setState(156);
				numexpr(0);
				setState(157);
				match(T__22);
				}
				break;
			case T__48:
			case T__49:
				{
				_localctx = new AbsExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(159);
				_la = _input.LA(1);
				if ( !(_la==T__48 || _la==T__49) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(160);
				match(T__21);
				setState(161);
				numexpr(0);
				setState(162);
				match(T__22);
				}
				break;
			case INT:
				{
				_localctx = new LiteralNumExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(164);
				match(INT);
				}
				break;
			case VAR:
				{
				_localctx = new VariableNumExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(165);
				match(VAR);
				setState(169);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(166);
						index();
						}
						} 
					}
					setState(171);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
				}
				}
				break;
			case T__50:
			case T__51:
				{
				_localctx = new RankExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(172);
				_la = _input.LA(1);
				if ( !(_la==T__50 || _la==T__51) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(173);
				match(T__21);
				setState(174);
				boolexpr(0);
				setState(175);
				match(T__22);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(205);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,17,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(203);
					switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
					case 1:
						{
						_localctx = new ArithmeticNumExprContext(new NumexprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_numexpr);
						setState(179);
						if (!(precpred(_ctx, 12))) throw new FailedPredicateException(this, "precpred(_ctx, 12)");
						setState(180);
						((ArithmeticNumExprContext)_localctx).aop = match(T__43);
						setState(181);
						numexpr(13);
						}
						break;
					case 2:
						{
						_localctx = new ArithmeticNumExprContext(new NumexprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_numexpr);
						setState(182);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(183);
						((ArithmeticNumExprContext)_localctx).aop = match(T__44);
						setState(184);
						numexpr(12);
						}
						break;
					case 3:
						{
						_localctx = new ArithmeticNumExprContext(new NumexprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_numexpr);
						setState(185);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(186);
						((ArithmeticNumExprContext)_localctx).aop = match(T__45);
						setState(187);
						numexpr(11);
						}
						break;
					case 4:
						{
						_localctx = new ArithmeticNumExprContext(new NumexprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_numexpr);
						setState(188);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(189);
						((ArithmeticNumExprContext)_localctx).aop = match(T__46);
						setState(190);
						numexpr(10);
						}
						break;
					case 5:
						{
						_localctx = new ArithmeticNumExprContext(new NumexprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_numexpr);
						setState(191);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(192);
						((ArithmeticNumExprContext)_localctx).aop = match(T__47);
						setState(193);
						numexpr(9);
						}
						break;
					case 6:
						{
						_localctx = new ArithmeticNumExprContext(new NumexprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_numexpr);
						setState(194);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(195);
						((ArithmeticNumExprContext)_localctx).aop = match(T__36);
						setState(196);
						numexpr(8);
						}
						break;
					case 7:
						{
						_localctx = new ArithmeticNumExprContext(new NumexprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_numexpr);
						setState(197);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(198);
						((ArithmeticNumExprContext)_localctx).aop = match(T__37);
						setState(199);
						numexpr(7);
						}
						break;
					case 8:
						{
						_localctx = new ArithmeticNumExprContext(new NumexprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_numexpr);
						setState(200);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(201);
						((ArithmeticNumExprContext)_localctx).aop = match(T__38);
						setState(202);
						numexpr(6);
						}
						break;
					}
					} 
				}
				setState(207);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,17,_ctx);
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

	public static class IndexContext extends ParserRuleContext {
		public NumexprContext numexpr() {
			return getRuleContext(NumexprContext.class,0);
		}
		public IndexContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_index; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).enterIndex(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).exitIndex(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DefProgVisitor ) return ((DefProgVisitor<? extends T>)visitor).visitIndex(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IndexContext index() throws RecognitionException {
		IndexContext _localctx = new IndexContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_index);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(208);
			match(T__2);
			setState(209);
			numexpr(0);
			setState(210);
			match(T__4);
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

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 2:
			return statement_sempred((StatementContext)_localctx, predIndex);
		case 3:
			return boolexpr_sempred((BoolexprContext)_localctx, predIndex);
		case 4:
			return numexpr_sempred((NumexprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean statement_sempred(StatementContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 2);
		}
		return true;
	}
	private boolean boolexpr_sempred(BoolexprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 1:
			return precpred(_ctx, 6);
		case 2:
			return precpred(_ctx, 5);
		case 3:
			return precpred(_ctx, 4);
		}
		return true;
	}
	private boolean numexpr_sempred(NumexprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 4:
			return precpred(_ctx, 12);
		case 5:
			return precpred(_ctx, 11);
		case 6:
			return precpred(_ctx, 10);
		case 7:
			return precpred(_ctx, 9);
		case 8:
			return precpred(_ctx, 8);
		case 9:
			return precpred(_ctx, 7);
		case 10:
			return precpred(_ctx, 6);
		case 11:
			return precpred(_ctx, 5);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3;\u00d7\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\3\2\3\2\3\3\3\3\3\3\7\3\24\n\3"+
		"\f\3\16\3\27\13\3\3\3\5\3\32\n\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3"+
		"\4\7\4&\n\4\f\4\16\4)\13\4\3\4\3\4\3\4\3\4\7\4/\n\4\f\4\16\4\62\13\4\3"+
		"\4\3\4\3\4\3\4\5\48\n\4\7\4:\n\4\f\4\16\4=\13\4\3\4\3\4\3\4\7\4B\n\4\f"+
		"\4\16\4E\13\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4"+
		"\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3"+
		"\4\3\4\7\4h\n\4\f\4\16\4k\13\4\3\4\5\4n\n\4\3\4\3\4\5\4r\n\4\3\4\3\4\3"+
		"\4\3\4\3\4\3\4\7\4z\n\4\f\4\16\4}\13\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5"+
		"\3\5\3\5\3\5\3\5\3\5\3\5\5\5\u008d\n\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5"+
		"\3\5\7\5\u0098\n\5\f\5\16\5\u009b\13\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6"+
		"\3\6\3\6\3\6\3\6\3\6\7\6\u00aa\n\6\f\6\16\6\u00ad\13\6\3\6\3\6\3\6\3\6"+
		"\3\6\5\6\u00b4\n\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6"+
		"\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\7\6\u00ce\n\6\f\6\16\6\u00d1"+
		"\13\6\3\7\3\7\3\7\3\7\3\7\2\5\6\b\n\b\2\4\6\b\n\f\2\20\3\2\20\21\3\2\22"+
		"\23\3\2\n\13\3\2\f\r\3\2\16\17\3\2\24\25\3\2\26\27\3\2\32\33\3\2\34\35"+
		"\3\2!&\3\2*+\3\2,-\3\2\63\64\3\2\65\66\u00f8\2\16\3\2\2\2\4\20\3\2\2\2"+
		"\6q\3\2\2\2\b\u008c\3\2\2\2\n\u00b3\3\2\2\2\f\u00d2\3\2\2\2\16\17\5\4"+
		"\3\2\17\3\3\2\2\2\20\25\5\6\4\2\21\22\7\3\2\2\22\24\5\6\4\2\23\21\3\2"+
		"\2\2\24\27\3\2\2\2\25\23\3\2\2\2\25\26\3\2\2\2\26\31\3\2\2\2\27\25\3\2"+
		"\2\2\30\32\7\3\2\2\31\30\3\2\2\2\31\32\3\2\2\2\32\33\3\2\2\2\33\34\7\2"+
		"\2\3\34\5\3\2\2\2\35\36\b\4\1\2\36\37\t\2\2\2\37 \5\b\5\2 !\t\3\2\2!\""+
		"\5\6\4\t\"r\3\2\2\2#\'\7\67\2\2$&\5\f\7\2%$\3\2\2\2&)\3\2\2\2\'%\3\2\2"+
		"\2\'(\3\2\2\2(*\3\2\2\2)\'\3\2\2\2*+\7\4\2\2+r\5\n\6\2,\60\7\67\2\2-/"+
		"\5\f\7\2.-\3\2\2\2/\62\3\2\2\2\60.\3\2\2\2\60\61\3\2\2\2\61\63\3\2\2\2"+
		"\62\60\3\2\2\2\63\64\7\4\2\2\64;\7\5\2\2\65\67\5\n\6\2\668\7\6\2\2\67"+
		"\66\3\2\2\2\678\3\2\2\28:\3\2\2\29\65\3\2\2\2:=\3\2\2\2;9\3\2\2\2;<\3"+
		"\2\2\2<>\3\2\2\2=;\3\2\2\2>r\7\7\2\2?C\7\67\2\2@B\5\f\7\2A@\3\2\2\2BE"+
		"\3\2\2\2CA\3\2\2\2CD\3\2\2\2DF\3\2\2\2EC\3\2\2\2FG\7\4\2\2GH\5\n\6\2H"+
		"I\7\b\2\2IJ\5\n\6\2JK\7\t\2\2KL\5\n\6\2Lr\3\2\2\2MN\t\4\2\2NO\5\b\5\2"+
		"OP\t\5\2\2PQ\5\6\4\2QR\t\6\2\2RS\5\6\4\2Sr\3\2\2\2TU\t\7\2\2Ur\5\b\5\2"+
		"VW\t\b\2\2WX\7\30\2\2XY\5\n\6\2YZ\7\31\2\2Z[\5\b\5\2[r\3\2\2\2\\]\t\t"+
		"\2\2]^\7\30\2\2^_\5\n\6\2_`\7\31\2\2`a\5\b\5\2ar\3\2\2\2br\t\n\2\2cd\7"+
		"\36\2\2di\5\6\4\2ef\7\3\2\2fh\5\6\4\2ge\3\2\2\2hk\3\2\2\2ig\3\2\2\2ij"+
		"\3\2\2\2jm\3\2\2\2ki\3\2\2\2ln\7\3\2\2ml\3\2\2\2mn\3\2\2\2no\3\2\2\2o"+
		"p\7\37\2\2pr\3\2\2\2q\35\3\2\2\2q#\3\2\2\2q,\3\2\2\2q?\3\2\2\2qM\3\2\2"+
		"\2qT\3\2\2\2qV\3\2\2\2q\\\3\2\2\2qb\3\2\2\2qc\3\2\2\2r{\3\2\2\2st\f\4"+
		"\2\2tu\7\b\2\2uv\5\n\6\2vw\7\t\2\2wx\5\6\4\5xz\3\2\2\2ys\3\2\2\2z}\3\2"+
		"\2\2{y\3\2\2\2{|\3\2\2\2|\7\3\2\2\2}{\3\2\2\2~\177\b\5\1\2\177\u0080\7"+
		" \2\2\u0080\u008d\5\b\5\n\u0081\u0082\7\30\2\2\u0082\u0083\5\b\5\2\u0083"+
		"\u0084\7\31\2\2\u0084\u008d\3\2\2\2\u0085\u0086\5\n\6\2\u0086\u0087\t"+
		"\13\2\2\u0087\u0088\5\n\6\2\u0088\u008d\3\2\2\2\u0089\u008d\t\f\2\2\u008a"+
		"\u008d\t\r\2\2\u008b\u008d\5\n\6\2\u008c~\3\2\2\2\u008c\u0081\3\2\2\2"+
		"\u008c\u0085\3\2\2\2\u008c\u0089\3\2\2\2\u008c\u008a\3\2\2\2\u008c\u008b"+
		"\3\2\2\2\u008d\u0099\3\2\2\2\u008e\u008f\f\b\2\2\u008f\u0090\7\'\2\2\u0090"+
		"\u0098\5\b\5\t\u0091\u0092\f\7\2\2\u0092\u0093\7(\2\2\u0093\u0098\5\b"+
		"\5\b\u0094\u0095\f\6\2\2\u0095\u0096\7)\2\2\u0096\u0098\5\b\5\7\u0097"+
		"\u008e\3\2\2\2\u0097\u0091\3\2\2\2\u0097\u0094\3\2\2\2\u0098\u009b\3\2"+
		"\2\2\u0099\u0097\3\2\2\2\u0099\u009a\3\2\2\2\u009a\t\3\2\2\2\u009b\u0099"+
		"\3\2\2\2\u009c\u009d\b\6\1\2\u009d\u009e\7\30\2\2\u009e\u009f\5\n\6\2"+
		"\u009f\u00a0\7\31\2\2\u00a0\u00b4\3\2\2\2\u00a1\u00a2\t\16\2\2\u00a2\u00a3"+
		"\7\30\2\2\u00a3\u00a4\5\n\6\2\u00a4\u00a5\7\31\2\2\u00a5\u00b4\3\2\2\2"+
		"\u00a6\u00b4\78\2\2\u00a7\u00ab\7\67\2\2\u00a8\u00aa\5\f\7\2\u00a9\u00a8"+
		"\3\2\2\2\u00aa\u00ad\3\2\2\2\u00ab\u00a9\3\2\2\2\u00ab\u00ac\3\2\2\2\u00ac"+
		"\u00b4\3\2\2\2\u00ad\u00ab\3\2\2\2\u00ae\u00af\t\17\2\2\u00af\u00b0\7"+
		"\30\2\2\u00b0\u00b1\5\b\5\2\u00b1\u00b2\7\31\2\2\u00b2\u00b4\3\2\2\2\u00b3"+
		"\u009c\3\2\2\2\u00b3\u00a1\3\2\2\2\u00b3\u00a6\3\2\2\2\u00b3\u00a7\3\2"+
		"\2\2\u00b3\u00ae\3\2\2\2\u00b4\u00cf\3\2\2\2\u00b5\u00b6\f\16\2\2\u00b6"+
		"\u00b7\7.\2\2\u00b7\u00ce\5\n\6\17\u00b8\u00b9\f\r\2\2\u00b9\u00ba\7/"+
		"\2\2\u00ba\u00ce\5\n\6\16\u00bb\u00bc\f\f\2\2\u00bc\u00bd\7\60\2\2\u00bd"+
		"\u00ce\5\n\6\r\u00be\u00bf\f\13\2\2\u00bf\u00c0\7\61\2\2\u00c0\u00ce\5"+
		"\n\6\f\u00c1\u00c2\f\n\2\2\u00c2\u00c3\7\62\2\2\u00c3\u00ce\5\n\6\13\u00c4"+
		"\u00c5\f\t\2\2\u00c5\u00c6\7\'\2\2\u00c6\u00ce\5\n\6\n\u00c7\u00c8\f\b"+
		"\2\2\u00c8\u00c9\7(\2\2\u00c9\u00ce\5\n\6\t\u00ca\u00cb\f\7\2\2\u00cb"+
		"\u00cc\7)\2\2\u00cc\u00ce\5\n\6\b\u00cd\u00b5\3\2\2\2\u00cd\u00b8\3\2"+
		"\2\2\u00cd\u00bb\3\2\2\2\u00cd\u00be\3\2\2\2\u00cd\u00c1\3\2\2\2\u00cd"+
		"\u00c4\3\2\2\2\u00cd\u00c7\3\2\2\2\u00cd\u00ca\3\2\2\2\u00ce\u00d1\3\2"+
		"\2\2\u00cf\u00cd\3\2\2\2\u00cf\u00d0\3\2\2\2\u00d0\13\3\2\2\2\u00d1\u00cf"+
		"\3\2\2\2\u00d2\u00d3\7\5\2\2\u00d3\u00d4\5\n\6\2\u00d4\u00d5\7\7\2\2\u00d5"+
		"\r\3\2\2\2\24\25\31\'\60\67;Cimq{\u008c\u0097\u0099\u00ab\u00b3\u00cd"+
		"\u00cf";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}