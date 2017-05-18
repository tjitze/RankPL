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
		T__52=53, T__53=54, T__54=55, T__55=56, T__56=57, T__57=58, T__58=59, 
		T__59=60, T__60=61, T__61=62, T__62=63, T__63=64, T__64=65, T__65=66, 
		T__66=67, T__67=68, T__68=69, T__69=70, T__70=71, T__71=72, T__72=73, 
		T__73=74, T__74=75, T__75=76, T__76=77, T__77=78, T__78=79, T__79=80, 
		T__80=81, T__81=82, T__82=83, T__83=84, T__84=85, VAR=86, INT=87, QUOTED_STRING=88, 
		COMMENT=89, SPACE=90, OTHER=91;
	public static final int
		RULE_parse = 0, RULE_program = 1, RULE_functiondef_or_statement = 2, RULE_functiondef = 3, 
		RULE_statement = 4, RULE_expression = 5, RULE_expr1 = 6, RULE_expr2 = 7, 
		RULE_expr3 = 8, RULE_expr4 = 9, RULE_expr5 = 10, RULE_variable = 11, RULE_index = 12;
	public static final String[] ruleNames = {
		"parse", "program", "functiondef_or_statement", "functiondef", "statement", 
		"expression", "expr1", "expr2", "expr3", "expr4", "expr5", "variable", 
		"index"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "';'", "'DEFINE'", "'define'", "'()'", "'('", "','", "')'", "'{'", 
		"'}'", "':='", "'<<'", "'>>'", "'...'", "'IF'", "'if'", "'THEN'", "'then'", 
		"'ELSE'", "'else'", "'WHILE'", "'while'", "'DO'", "'do'", "'OBSERVE'", 
		"'observe'", "'OBSERVE-L'", "'observe-l'", "'OBSERVE-J'", "'observe-j'", 
		"'SKIP'", "'skip'", "'NORMALLY'", "'normally'", "'NRM'", "'nrm'", "'EXCEPTIONALLY'", 
		"'exceptionally'", "'EXC'", "'exc'", "'EITHER'", "'either'", "'OR'", "'or'", 
		"'RETURN'", "'return'", "'&'", "'|'", "'^'", "'<'", "'<='", "'>'", "'>='", 
		"'=='", "'!='", "'+'", "'-'", "'*'", "'/'", "'%'", "'TRUE'", "'true'", 
		"'FALSE'", "'false'", "'infer'", "'INFER'", "'!'", "'ISSET'", "'isSet'", 
		"'isset'", "'ABS'", "'abs'", "'LEN'", "'len'", "'SUBSTRING'", "'substring'", 
		"'subString'", "'RANK'", "'rank'", "'ARRAY'", "'array'", "'['", "']'", 
		"'@'", "'?'", "':'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, "VAR", "INT", "QUOTED_STRING", "COMMENT", "SPACE", "OTHER"
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
			setState(26);
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
		public List<Functiondef_or_statementContext> functiondef_or_statement() {
			return getRuleContexts(Functiondef_or_statementContext.class);
		}
		public Functiondef_or_statementContext functiondef_or_statement(int i) {
			return getRuleContext(Functiondef_or_statementContext.class,i);
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
			enterOuterAlt(_localctx, 1);
			{
			setState(28);
			functiondef_or_statement();
			setState(29);
			match(T__0);
			setState(35);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << T__2) | (1L << T__7) | (1L << T__13) | (1L << T__14) | (1L << T__19) | (1L << T__20) | (1L << T__23) | (1L << T__24) | (1L << T__25) | (1L << T__26) | (1L << T__27) | (1L << T__28) | (1L << T__29) | (1L << T__30) | (1L << T__31) | (1L << T__32) | (1L << T__33) | (1L << T__34) | (1L << T__39) | (1L << T__40) | (1L << T__43) | (1L << T__44))) != 0) || _la==VAR) {
				{
				{
				setState(30);
				functiondef_or_statement();
				setState(31);
				match(T__0);
				}
				}
				setState(37);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(38);
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

	public static class Functiondef_or_statementContext extends ParserRuleContext {
		public FunctiondefContext functiondef() {
			return getRuleContext(FunctiondefContext.class,0);
		}
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public Functiondef_or_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functiondef_or_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).enterFunctiondef_or_statement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).exitFunctiondef_or_statement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DefProgVisitor ) return ((DefProgVisitor<? extends T>)visitor).visitFunctiondef_or_statement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Functiondef_or_statementContext functiondef_or_statement() throws RecognitionException {
		Functiondef_or_statementContext _localctx = new Functiondef_or_statementContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_functiondef_or_statement);
		try {
			setState(42);
			switch (_input.LA(1)) {
			case T__1:
			case T__2:
				enterOuterAlt(_localctx, 1);
				{
				setState(40);
				functiondef();
				}
				break;
			case T__7:
			case T__13:
			case T__14:
			case T__19:
			case T__20:
			case T__23:
			case T__24:
			case T__25:
			case T__26:
			case T__27:
			case T__28:
			case T__29:
			case T__30:
			case T__31:
			case T__32:
			case T__33:
			case T__34:
			case T__39:
			case T__40:
			case T__43:
			case T__44:
			case VAR:
				enterOuterAlt(_localctx, 2);
				{
				setState(41);
				statement();
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

	public static class FunctiondefContext extends ParserRuleContext {
		public List<TerminalNode> VAR() { return getTokens(DefProgParser.VAR); }
		public TerminalNode VAR(int i) {
			return getToken(DefProgParser.VAR, i);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public FunctiondefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functiondef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).enterFunctiondef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).exitFunctiondef(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DefProgVisitor ) return ((DefProgVisitor<? extends T>)visitor).visitFunctiondef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctiondefContext functiondef() throws RecognitionException {
		FunctiondefContext _localctx = new FunctiondefContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_functiondef);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(44);
			_la = _input.LA(1);
			if ( !(_la==T__1 || _la==T__2) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			setState(45);
			match(VAR);
			setState(57);
			switch (_input.LA(1)) {
			case T__3:
				{
				setState(46);
				match(T__3);
				}
				break;
			case T__4:
				{
				setState(47);
				match(T__4);
				setState(48);
				match(VAR);
				setState(53);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__5) {
					{
					{
					setState(49);
					match(T__5);
					setState(50);
					match(VAR);
					}
					}
					setState(55);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(56);
				match(T__6);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(59);
			match(T__7);
			setState(65);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__7) | (1L << T__13) | (1L << T__14) | (1L << T__19) | (1L << T__20) | (1L << T__23) | (1L << T__24) | (1L << T__25) | (1L << T__26) | (1L << T__27) | (1L << T__28) | (1L << T__29) | (1L << T__30) | (1L << T__31) | (1L << T__32) | (1L << T__33) | (1L << T__34) | (1L << T__39) | (1L << T__40) | (1L << T__43) | (1L << T__44))) != 0) || _la==VAR) {
				{
				{
				setState(60);
				statement();
				setState(61);
				match(T__0);
				}
				}
				setState(67);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(68);
			match(T__8);
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
	public static class Indifferent_choiceContext extends StatementContext {
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public Indifferent_choiceContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).enterIndifferent_choice(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).exitIndifferent_choice(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DefProgVisitor ) return ((DefProgVisitor<? extends T>)visitor).visitIndifferent_choice(this);
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
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
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
	public static class ObserveLContext extends StatementContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
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
	public static class While_statContext extends StatementContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
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
	public static class ObserveContext extends StatementContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
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
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
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
		public VariableContext variable() {
			return getRuleContext(VariableContext.class,0);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
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
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
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
		public VariableContext variable() {
			return getRuleContext(VariableContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
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
	public static class Range_choiceContext extends StatementContext {
		public VariableContext variable() {
			return getRuleContext(VariableContext.class,0);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public Range_choiceContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).enterRange_choice(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).exitRange_choice(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DefProgVisitor ) return ((DefProgVisitor<? extends T>)visitor).visitRange_choice(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class Return_statementContext extends StatementContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Return_statementContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).enterReturn_statement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).exitReturn_statement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DefProgVisitor ) return ((DefProgVisitor<? extends T>)visitor).visitReturn_statement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_statement);
		int _la;
		try {
			int _alt;
			setState(152);
			switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
			case 1:
				_localctx = new Assignment_statContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(70);
				variable();
				setState(71);
				match(T__9);
				setState(72);
				expression();
				}
				break;
			case 2:
				_localctx = new Choice_assignment_statContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(74);
				variable();
				setState(75);
				match(T__9);
				setState(76);
				expression();
				setState(77);
				match(T__10);
				setState(78);
				expression();
				setState(79);
				match(T__11);
				setState(80);
				expression();
				}
				break;
			case 3:
				_localctx = new Range_choiceContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(82);
				variable();
				setState(83);
				match(T__9);
				setState(84);
				match(T__10);
				setState(85);
				expression();
				setState(86);
				match(T__12);
				setState(87);
				expression();
				setState(88);
				match(T__11);
				}
				break;
			case 4:
				_localctx = new If_statContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(90);
				_la = _input.LA(1);
				if ( !(_la==T__13 || _la==T__14) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(91);
				expression();
				setState(92);
				_la = _input.LA(1);
				if ( !(_la==T__15 || _la==T__16) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(93);
				statement();
				setState(94);
				_la = _input.LA(1);
				if ( !(_la==T__17 || _la==T__18) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(95);
				statement();
				}
				break;
			case 5:
				_localctx = new While_statContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(97);
				_la = _input.LA(1);
				if ( !(_la==T__19 || _la==T__20) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(98);
				expression();
				setState(99);
				_la = _input.LA(1);
				if ( !(_la==T__21 || _la==T__22) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(100);
				statement();
				}
				break;
			case 6:
				_localctx = new ObserveContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(102);
				_la = _input.LA(1);
				if ( !(_la==T__23 || _la==T__24) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(103);
				expression();
				}
				break;
			case 7:
				_localctx = new ObserveLContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(104);
				_la = _input.LA(1);
				if ( !(_la==T__25 || _la==T__26) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(105);
				match(T__4);
				setState(106);
				expression();
				setState(107);
				match(T__6);
				setState(108);
				expression();
				}
				break;
			case 8:
				_localctx = new ObserveJContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(110);
				_la = _input.LA(1);
				if ( !(_la==T__27 || _la==T__28) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(111);
				match(T__4);
				setState(112);
				expression();
				setState(113);
				match(T__6);
				setState(114);
				expression();
				}
				break;
			case 9:
				_localctx = new Skip_statContext(_localctx);
				enterOuterAlt(_localctx, 9);
				{
				setState(116);
				_la = _input.LA(1);
				if ( !(_la==T__29 || _la==T__30) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				}
				break;
			case 10:
				_localctx = new Ranked_choiceContext(_localctx);
				enterOuterAlt(_localctx, 10);
				{
				setState(117);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__31) | (1L << T__32) | (1L << T__33) | (1L << T__34))) != 0)) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(122);
				_la = _input.LA(1);
				if (_la==T__4) {
					{
					setState(118);
					match(T__4);
					setState(119);
					expression();
					setState(120);
					match(T__6);
					}
				}

				setState(124);
				statement();
				setState(125);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__35) | (1L << T__36) | (1L << T__37) | (1L << T__38))) != 0)) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(126);
				statement();
				}
				break;
			case 11:
				_localctx = new Indifferent_choiceContext(_localctx);
				enterOuterAlt(_localctx, 11);
				{
				setState(128);
				_la = _input.LA(1);
				if ( !(_la==T__39 || _la==T__40) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(129);
				statement();
				setState(132); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(130);
						_la = _input.LA(1);
						if ( !(_la==T__41 || _la==T__42) ) {
						_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(131);
						statement();
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(134); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				}
				break;
			case 12:
				_localctx = new Statement_sequenceContext(_localctx);
				enterOuterAlt(_localctx, 12);
				{
				setState(136);
				match(T__7);
				setState(137);
				statement();
				setState(142);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,7,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(138);
						match(T__0);
						setState(139);
						statement();
						}
						} 
					}
					setState(144);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,7,_ctx);
				}
				setState(146);
				_la = _input.LA(1);
				if (_la==T__0) {
					{
					setState(145);
					match(T__0);
					}
				}

				setState(148);
				match(T__8);
				}
				break;
			case 13:
				_localctx = new Return_statementContext(_localctx);
				enterOuterAlt(_localctx, 13);
				{
				setState(150);
				_la = _input.LA(1);
				if ( !(_la==T__43 || _la==T__44) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(151);
				expression();
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

	public static class ExpressionContext extends ParserRuleContext {
		public Expr1Context expr1() {
			return getRuleContext(Expr1Context.class,0);
		}
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).enterExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).exitExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DefProgVisitor ) return ((DefProgVisitor<? extends T>)visitor).visitExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(154);
			expr1();
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

	public static class Expr1Context extends ParserRuleContext {
		public Expr1Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr1; }
	 
		public Expr1Context() { }
		public void copyFrom(Expr1Context ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class BoolExpressionContext extends Expr1Context {
		public Token aop;
		public Expr2Context expr2() {
			return getRuleContext(Expr2Context.class,0);
		}
		public Expr1Context expr1() {
			return getRuleContext(Expr1Context.class,0);
		}
		public BoolExpressionContext(Expr1Context ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).enterBoolExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).exitBoolExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DefProgVisitor ) return ((DefProgVisitor<? extends T>)visitor).visitBoolExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Expr1Context expr1() throws RecognitionException {
		Expr1Context _localctx = new Expr1Context(_ctx, getState());
		enterRule(_localctx, 12, RULE_expr1);
		int _la;
		try {
			_localctx = new BoolExpressionContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(156);
			expr2();
			setState(159);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				{
				setState(157);
				((BoolExpressionContext)_localctx).aop = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__45) | (1L << T__46) | (1L << T__47))) != 0)) ) {
					((BoolExpressionContext)_localctx).aop = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(158);
				expr1();
				}
				break;
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

	public static class Expr2Context extends ParserRuleContext {
		public Expr2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr2; }
	 
		public Expr2Context() { }
		public void copyFrom(Expr2Context ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class CompareExprContext extends Expr2Context {
		public Token cop;
		public Expr3Context expr3() {
			return getRuleContext(Expr3Context.class,0);
		}
		public Expr2Context expr2() {
			return getRuleContext(Expr2Context.class,0);
		}
		public CompareExprContext(Expr2Context ctx) { copyFrom(ctx); }
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

	public final Expr2Context expr2() throws RecognitionException {
		Expr2Context _localctx = new Expr2Context(_ctx, getState());
		enterRule(_localctx, 14, RULE_expr2);
		int _la;
		try {
			_localctx = new CompareExprContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(161);
			expr3();
			setState(164);
			switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
			case 1:
				{
				setState(162);
				((CompareExprContext)_localctx).cop = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__48) | (1L << T__49) | (1L << T__50) | (1L << T__51) | (1L << T__52) | (1L << T__53))) != 0)) ) {
					((CompareExprContext)_localctx).cop = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(163);
				expr2();
				}
				break;
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

	public static class Expr3Context extends ParserRuleContext {
		public Expr3Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr3; }
	 
		public Expr3Context() { }
		public void copyFrom(Expr3Context ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class Arithmetic1ExpressionContext extends Expr3Context {
		public Token aop;
		public Expr4Context expr4() {
			return getRuleContext(Expr4Context.class,0);
		}
		public Expr3Context expr3() {
			return getRuleContext(Expr3Context.class,0);
		}
		public Arithmetic1ExpressionContext(Expr3Context ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).enterArithmetic1Expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).exitArithmetic1Expression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DefProgVisitor ) return ((DefProgVisitor<? extends T>)visitor).visitArithmetic1Expression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Expr3Context expr3() throws RecognitionException {
		Expr3Context _localctx = new Expr3Context(_ctx, getState());
		enterRule(_localctx, 16, RULE_expr3);
		int _la;
		try {
			_localctx = new Arithmetic1ExpressionContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(166);
			expr4();
			setState(169);
			switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
			case 1:
				{
				setState(167);
				((Arithmetic1ExpressionContext)_localctx).aop = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==T__54 || _la==T__55) ) {
					((Arithmetic1ExpressionContext)_localctx).aop = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(168);
				expr3();
				}
				break;
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

	public static class Expr4Context extends ParserRuleContext {
		public Expr4Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr4; }
	 
		public Expr4Context() { }
		public void copyFrom(Expr4Context ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class Arithmetic2ExpressionContext extends Expr4Context {
		public Token aop;
		public Expr5Context expr5() {
			return getRuleContext(Expr5Context.class,0);
		}
		public Expr4Context expr4() {
			return getRuleContext(Expr4Context.class,0);
		}
		public Arithmetic2ExpressionContext(Expr4Context ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).enterArithmetic2Expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).exitArithmetic2Expression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DefProgVisitor ) return ((DefProgVisitor<? extends T>)visitor).visitArithmetic2Expression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Expr4Context expr4() throws RecognitionException {
		Expr4Context _localctx = new Expr4Context(_ctx, getState());
		enterRule(_localctx, 18, RULE_expr4);
		int _la;
		try {
			_localctx = new Arithmetic2ExpressionContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(171);
			expr5();
			setState(174);
			switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
			case 1:
				{
				setState(172);
				((Arithmetic2ExpressionContext)_localctx).aop = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__56) | (1L << T__57) | (1L << T__58))) != 0)) ) {
					((Arithmetic2ExpressionContext)_localctx).aop = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(173);
				expr4();
				}
				break;
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

	public static class Expr5Context extends ParserRuleContext {
		public Expr5Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr5; }
	 
		public Expr5Context() { }
		public void copyFrom(Expr5Context ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class IsSetExprContext extends Expr5Context {
		public VariableContext variable() {
			return getRuleContext(VariableContext.class,0);
		}
		public IsSetExprContext(Expr5Context ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).enterIsSetExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).exitIsSetExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DefProgVisitor ) return ((DefProgVisitor<? extends T>)visitor).visitIsSetExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AbsExprContext extends Expr5Context {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public AbsExprContext(Expr5Context ctx) { copyFrom(ctx); }
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
	public static class SubStringExprContext extends Expr5Context {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public SubStringExprContext(Expr5Context ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).enterSubStringExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).exitSubStringExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DefProgVisitor ) return ((DefProgVisitor<? extends T>)visitor).visitSubStringExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ArrayInitExprContext extends Expr5Context {
		public List<IndexContext> index() {
			return getRuleContexts(IndexContext.class);
		}
		public IndexContext index(int i) {
			return getRuleContext(IndexContext.class,i);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ArrayInitExprContext(Expr5Context ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).enterArrayInitExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).exitArrayInitExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DefProgVisitor ) return ((DefProgVisitor<? extends T>)visitor).visitArrayInitExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ConditionalExpressionContext extends Expr5Context {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public ConditionalExpressionContext(Expr5Context ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).enterConditionalExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).exitConditionalExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DefProgVisitor ) return ((DefProgVisitor<? extends T>)visitor).visitConditionalExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LenExprContext extends Expr5Context {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public LenExprContext(Expr5Context ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).enterLenExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).exitLenExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DefProgVisitor ) return ((DefProgVisitor<? extends T>)visitor).visitLenExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class VariableExpressionContext extends Expr5Context {
		public VariableContext variable() {
			return getRuleContext(VariableContext.class,0);
		}
		public VariableExpressionContext(Expr5Context ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).enterVariableExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).exitVariableExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DefProgVisitor ) return ((DefProgVisitor<? extends T>)visitor).visitVariableExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LiteralIntExpressionContext extends Expr5Context {
		public TerminalNode INT() { return getToken(DefProgParser.INT, 0); }
		public LiteralIntExpressionContext(Expr5Context ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).enterLiteralIntExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).exitLiteralIntExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DefProgVisitor ) return ((DefProgVisitor<? extends T>)visitor).visitLiteralIntExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LiteralStringExprContext extends Expr5Context {
		public TerminalNode QUOTED_STRING() { return getToken(DefProgParser.QUOTED_STRING, 0); }
		public LiteralStringExprContext(Expr5Context ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).enterLiteralStringExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).exitLiteralStringExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DefProgVisitor ) return ((DefProgVisitor<? extends T>)visitor).visitLiteralStringExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NegateExprContext extends Expr5Context {
		public Expr5Context expr5() {
			return getRuleContext(Expr5Context.class,0);
		}
		public NegateExprContext(Expr5Context ctx) { copyFrom(ctx); }
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
	public static class InferringFunctionCallContext extends Expr5Context {
		public TerminalNode VAR() { return getToken(DefProgParser.VAR, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public InferringFunctionCallContext(Expr5Context ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).enterInferringFunctionCall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).exitInferringFunctionCall(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DefProgVisitor ) return ((DefProgVisitor<? extends T>)visitor).visitInferringFunctionCall(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LiteralBoolExprContext extends Expr5Context {
		public LiteralBoolExprContext(Expr5Context ctx) { copyFrom(ctx); }
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
	public static class RankExprContext extends Expr5Context {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public RankExprContext(Expr5Context ctx) { copyFrom(ctx); }
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
	public static class ArrayConstructExprContext extends Expr5Context {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public ArrayConstructExprContext(Expr5Context ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).enterArrayConstructExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).exitArrayConstructExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DefProgVisitor ) return ((DefProgVisitor<? extends T>)visitor).visitArrayConstructExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class FunctionCallContext extends Expr5Context {
		public TerminalNode VAR() { return getToken(DefProgParser.VAR, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public FunctionCallContext(Expr5Context ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).enterFunctionCall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).exitFunctionCall(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DefProgVisitor ) return ((DefProgVisitor<? extends T>)visitor).visitFunctionCall(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class MinusExprContext extends Expr5Context {
		public Expr5Context expr5() {
			return getRuleContext(Expr5Context.class,0);
		}
		public MinusExprContext(Expr5Context ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).enterMinusExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).exitMinusExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DefProgVisitor ) return ((DefProgVisitor<? extends T>)visitor).visitMinusExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ParExpressionContext extends Expr5Context {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ParExpressionContext(Expr5Context ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).enterParExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).exitParExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DefProgVisitor ) return ((DefProgVisitor<? extends T>)visitor).visitParExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Expr5Context expr5() throws RecognitionException {
		Expr5Context _localctx = new Expr5Context(_ctx, getState());
		enterRule(_localctx, 20, RULE_expr5);
		int _la;
		try {
			int _alt;
			setState(281);
			switch ( getInterpreter().adaptivePredict(_input,22,_ctx) ) {
			case 1:
				_localctx = new LiteralIntExpressionContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(176);
				match(INT);
				}
				break;
			case 2:
				_localctx = new LiteralBoolExprContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(177);
				_la = _input.LA(1);
				if ( !(_la==T__59 || _la==T__60) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				}
				break;
			case 3:
				_localctx = new LiteralBoolExprContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(178);
				_la = _input.LA(1);
				if ( !(_la==T__61 || _la==T__62) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				}
				break;
			case 4:
				_localctx = new LiteralStringExprContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(179);
				match(QUOTED_STRING);
				}
				break;
			case 5:
				_localctx = new LiteralBoolExprContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(180);
				_la = _input.LA(1);
				if ( !(_la==T__59 || _la==T__60) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				}
				break;
			case 6:
				_localctx = new LiteralBoolExprContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(181);
				_la = _input.LA(1);
				if ( !(_la==T__61 || _la==T__62) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				}
				break;
			case 7:
				_localctx = new VariableExpressionContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(182);
				variable();
				}
				break;
			case 8:
				_localctx = new InferringFunctionCallContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(183);
				_la = _input.LA(1);
				if ( !(_la==T__63 || _la==T__64) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(184);
				match(T__4);
				setState(185);
				match(VAR);
				setState(198);
				switch (_input.LA(1)) {
				case T__3:
					{
					setState(186);
					match(T__3);
					}
					break;
				case T__4:
					{
					setState(187);
					match(T__4);
					setState(188);
					expression();
					setState(193);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__5) {
						{
						{
						setState(189);
						match(T__5);
						setState(190);
						expression();
						}
						}
						setState(195);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					setState(196);
					match(T__6);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(200);
				match(T__6);
				}
				break;
			case 9:
				_localctx = new FunctionCallContext(_localctx);
				enterOuterAlt(_localctx, 9);
				{
				setState(201);
				match(VAR);
				setState(214);
				switch (_input.LA(1)) {
				case T__3:
					{
					setState(202);
					match(T__3);
					}
					break;
				case T__4:
					{
					setState(203);
					match(T__4);
					setState(204);
					expression();
					setState(209);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__5) {
						{
						{
						setState(205);
						match(T__5);
						setState(206);
						expression();
						}
						}
						setState(211);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					setState(212);
					match(T__6);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				break;
			case 10:
				_localctx = new NegateExprContext(_localctx);
				enterOuterAlt(_localctx, 10);
				{
				setState(216);
				match(T__65);
				setState(217);
				expr5();
				}
				break;
			case 11:
				_localctx = new MinusExprContext(_localctx);
				enterOuterAlt(_localctx, 11);
				{
				setState(218);
				match(T__55);
				setState(219);
				expr5();
				}
				break;
			case 12:
				_localctx = new IsSetExprContext(_localctx);
				enterOuterAlt(_localctx, 12);
				{
				setState(220);
				_la = _input.LA(1);
				if ( !(((((_la - 67)) & ~0x3f) == 0 && ((1L << (_la - 67)) & ((1L << (T__66 - 67)) | (1L << (T__67 - 67)) | (1L << (T__68 - 67)))) != 0)) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(221);
				match(T__4);
				setState(222);
				variable();
				setState(223);
				match(T__6);
				}
				break;
			case 13:
				_localctx = new AbsExprContext(_localctx);
				enterOuterAlt(_localctx, 13);
				{
				setState(225);
				_la = _input.LA(1);
				if ( !(_la==T__69 || _la==T__70) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(226);
				match(T__4);
				setState(227);
				expression();
				setState(228);
				match(T__6);
				}
				break;
			case 14:
				_localctx = new LenExprContext(_localctx);
				enterOuterAlt(_localctx, 14);
				{
				setState(230);
				_la = _input.LA(1);
				if ( !(_la==T__71 || _la==T__72) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(231);
				match(T__4);
				setState(232);
				expression();
				setState(233);
				match(T__6);
				}
				break;
			case 15:
				_localctx = new SubStringExprContext(_localctx);
				enterOuterAlt(_localctx, 15);
				{
				setState(235);
				_la = _input.LA(1);
				if ( !(((((_la - 74)) & ~0x3f) == 0 && ((1L << (_la - 74)) & ((1L << (T__73 - 74)) | (1L << (T__74 - 74)) | (1L << (T__75 - 74)))) != 0)) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(236);
				match(T__4);
				setState(237);
				expression();
				setState(238);
				match(T__5);
				setState(239);
				expression();
				setState(240);
				match(T__5);
				setState(241);
				expression();
				setState(242);
				match(T__6);
				}
				break;
			case 16:
				_localctx = new RankExprContext(_localctx);
				enterOuterAlt(_localctx, 16);
				{
				setState(244);
				_la = _input.LA(1);
				if ( !(_la==T__76 || _la==T__77) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(245);
				match(T__4);
				setState(246);
				expression();
				setState(247);
				match(T__6);
				}
				break;
			case 17:
				_localctx = new ArrayInitExprContext(_localctx);
				enterOuterAlt(_localctx, 17);
				{
				setState(249);
				_la = _input.LA(1);
				if ( !(_la==T__78 || _la==T__79) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(253);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,18,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(250);
						index();
						}
						} 
					}
					setState(255);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,18,_ctx);
				}
				setState(257);
				switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
				case 1:
					{
					setState(256);
					expression();
					}
					break;
				}
				}
				break;
			case 18:
				_localctx = new ArrayConstructExprContext(_localctx);
				enterOuterAlt(_localctx, 18);
				{
				setState(259);
				match(T__80);
				setState(266);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__4) | (1L << T__55) | (1L << T__59) | (1L << T__60) | (1L << T__61) | (1L << T__62))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (T__63 - 64)) | (1L << (T__64 - 64)) | (1L << (T__65 - 64)) | (1L << (T__66 - 64)) | (1L << (T__67 - 64)) | (1L << (T__68 - 64)) | (1L << (T__69 - 64)) | (1L << (T__70 - 64)) | (1L << (T__71 - 64)) | (1L << (T__72 - 64)) | (1L << (T__73 - 64)) | (1L << (T__74 - 64)) | (1L << (T__75 - 64)) | (1L << (T__76 - 64)) | (1L << (T__77 - 64)) | (1L << (T__78 - 64)) | (1L << (T__79 - 64)) | (1L << (T__80 - 64)) | (1L << (T__82 - 64)) | (1L << (VAR - 64)) | (1L << (INT - 64)) | (1L << (QUOTED_STRING - 64)))) != 0)) {
					{
					{
					setState(260);
					expression();
					setState(262);
					_la = _input.LA(1);
					if (_la==T__5) {
						{
						setState(261);
						match(T__5);
						}
					}

					}
					}
					setState(268);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(269);
				match(T__81);
				}
				break;
			case 19:
				_localctx = new ConditionalExpressionContext(_localctx);
				enterOuterAlt(_localctx, 19);
				{
				setState(270);
				match(T__82);
				setState(271);
				expression();
				setState(272);
				match(T__83);
				setState(273);
				expression();
				setState(274);
				match(T__84);
				setState(275);
				expression();
				}
				break;
			case 20:
				_localctx = new ParExpressionContext(_localctx);
				enterOuterAlt(_localctx, 20);
				{
				setState(277);
				match(T__4);
				setState(278);
				expression();
				setState(279);
				match(T__6);
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

	public static class VariableContext extends ParserRuleContext {
		public TerminalNode VAR() { return getToken(DefProgParser.VAR, 0); }
		public List<IndexContext> index() {
			return getRuleContexts(IndexContext.class);
		}
		public IndexContext index(int i) {
			return getRuleContext(IndexContext.class,i);
		}
		public VariableContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variable; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).enterVariable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DefProgListener ) ((DefProgListener)listener).exitVariable(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DefProgVisitor ) return ((DefProgVisitor<? extends T>)visitor).visitVariable(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VariableContext variable() throws RecognitionException {
		VariableContext _localctx = new VariableContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_variable);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(283);
			match(VAR);
			setState(287);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,23,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(284);
					index();
					}
					} 
				}
				setState(289);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,23,_ctx);
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

	public static class IndexContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
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
		enterRule(_localctx, 24, RULE_index);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(290);
			match(T__80);
			setState(291);
			expression();
			setState(292);
			match(T__81);
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3]\u0129\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\3\2\3\2\3\3\3\3\3\3\3\3\3\3\7\3$\n\3\f\3"+
		"\16\3\'\13\3\3\3\3\3\3\4\3\4\5\4-\n\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\7\5"+
		"\66\n\5\f\5\16\59\13\5\3\5\5\5<\n\5\3\5\3\5\3\5\3\5\7\5B\n\5\f\5\16\5"+
		"E\13\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6"+
		"\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3"+
		"\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6"+
		"\3\6\3\6\3\6\5\6}\n\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\6\6\u0087\n\6\r"+
		"\6\16\6\u0088\3\6\3\6\3\6\3\6\7\6\u008f\n\6\f\6\16\6\u0092\13\6\3\6\5"+
		"\6\u0095\n\6\3\6\3\6\3\6\3\6\5\6\u009b\n\6\3\7\3\7\3\b\3\b\3\b\5\b\u00a2"+
		"\n\b\3\t\3\t\3\t\5\t\u00a7\n\t\3\n\3\n\3\n\5\n\u00ac\n\n\3\13\3\13\3\13"+
		"\5\13\u00b1\n\13\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3"+
		"\f\3\f\7\f\u00c2\n\f\f\f\16\f\u00c5\13\f\3\f\3\f\5\f\u00c9\n\f\3\f\3\f"+
		"\3\f\3\f\3\f\3\f\3\f\7\f\u00d2\n\f\f\f\16\f\u00d5\13\f\3\f\3\f\5\f\u00d9"+
		"\n\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3"+
		"\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f"+
		"\3\f\7\f\u00fe\n\f\f\f\16\f\u0101\13\f\3\f\5\f\u0104\n\f\3\f\3\f\3\f\5"+
		"\f\u0109\n\f\7\f\u010b\n\f\f\f\16\f\u010e\13\f\3\f\3\f\3\f\3\f\3\f\3\f"+
		"\3\f\3\f\3\f\3\f\3\f\3\f\5\f\u011c\n\f\3\r\3\r\7\r\u0120\n\r\f\r\16\r"+
		"\u0123\13\r\3\16\3\16\3\16\3\16\3\16\2\2\17\2\4\6\b\n\f\16\20\22\24\26"+
		"\30\32\2\36\3\2\4\5\3\2\20\21\3\2\22\23\3\2\24\25\3\2\26\27\3\2\30\31"+
		"\3\2\32\33\3\2\34\35\3\2\36\37\3\2 !\3\2\"%\3\2&)\3\2*+\3\2,-\3\2./\3"+
		"\2\60\62\3\2\638\3\29:\3\2;=\3\2>?\3\2@A\3\2BC\3\2EG\3\2HI\3\2JK\3\2L"+
		"N\3\2OP\3\2QR\u0150\2\34\3\2\2\2\4\36\3\2\2\2\6,\3\2\2\2\b.\3\2\2\2\n"+
		"\u009a\3\2\2\2\f\u009c\3\2\2\2\16\u009e\3\2\2\2\20\u00a3\3\2\2\2\22\u00a8"+
		"\3\2\2\2\24\u00ad\3\2\2\2\26\u011b\3\2\2\2\30\u011d\3\2\2\2\32\u0124\3"+
		"\2\2\2\34\35\5\4\3\2\35\3\3\2\2\2\36\37\5\6\4\2\37%\7\3\2\2 !\5\6\4\2"+
		"!\"\7\3\2\2\"$\3\2\2\2# \3\2\2\2$\'\3\2\2\2%#\3\2\2\2%&\3\2\2\2&(\3\2"+
		"\2\2\'%\3\2\2\2()\7\2\2\3)\5\3\2\2\2*-\5\b\5\2+-\5\n\6\2,*\3\2\2\2,+\3"+
		"\2\2\2-\7\3\2\2\2./\t\2\2\2/;\7X\2\2\60<\7\6\2\2\61\62\7\7\2\2\62\67\7"+
		"X\2\2\63\64\7\b\2\2\64\66\7X\2\2\65\63\3\2\2\2\669\3\2\2\2\67\65\3\2\2"+
		"\2\678\3\2\2\28:\3\2\2\29\67\3\2\2\2:<\7\t\2\2;\60\3\2\2\2;\61\3\2\2\2"+
		"<=\3\2\2\2=C\7\n\2\2>?\5\n\6\2?@\7\3\2\2@B\3\2\2\2A>\3\2\2\2BE\3\2\2\2"+
		"CA\3\2\2\2CD\3\2\2\2DF\3\2\2\2EC\3\2\2\2FG\7\13\2\2G\t\3\2\2\2HI\5\30"+
		"\r\2IJ\7\f\2\2JK\5\f\7\2K\u009b\3\2\2\2LM\5\30\r\2MN\7\f\2\2NO\5\f\7\2"+
		"OP\7\r\2\2PQ\5\f\7\2QR\7\16\2\2RS\5\f\7\2S\u009b\3\2\2\2TU\5\30\r\2UV"+
		"\7\f\2\2VW\7\r\2\2WX\5\f\7\2XY\7\17\2\2YZ\5\f\7\2Z[\7\16\2\2[\u009b\3"+
		"\2\2\2\\]\t\3\2\2]^\5\f\7\2^_\t\4\2\2_`\5\n\6\2`a\t\5\2\2ab\5\n\6\2b\u009b"+
		"\3\2\2\2cd\t\6\2\2de\5\f\7\2ef\t\7\2\2fg\5\n\6\2g\u009b\3\2\2\2hi\t\b"+
		"\2\2i\u009b\5\f\7\2jk\t\t\2\2kl\7\7\2\2lm\5\f\7\2mn\7\t\2\2no\5\f\7\2"+
		"o\u009b\3\2\2\2pq\t\n\2\2qr\7\7\2\2rs\5\f\7\2st\7\t\2\2tu\5\f\7\2u\u009b"+
		"\3\2\2\2v\u009b\t\13\2\2w|\t\f\2\2xy\7\7\2\2yz\5\f\7\2z{\7\t\2\2{}\3\2"+
		"\2\2|x\3\2\2\2|}\3\2\2\2}~\3\2\2\2~\177\5\n\6\2\177\u0080\t\r\2\2\u0080"+
		"\u0081\5\n\6\2\u0081\u009b\3\2\2\2\u0082\u0083\t\16\2\2\u0083\u0086\5"+
		"\n\6\2\u0084\u0085\t\17\2\2\u0085\u0087\5\n\6\2\u0086\u0084\3\2\2\2\u0087"+
		"\u0088\3\2\2\2\u0088\u0086\3\2\2\2\u0088\u0089\3\2\2\2\u0089\u009b\3\2"+
		"\2\2\u008a\u008b\7\n\2\2\u008b\u0090\5\n\6\2\u008c\u008d\7\3\2\2\u008d"+
		"\u008f\5\n\6\2\u008e\u008c\3\2\2\2\u008f\u0092\3\2\2\2\u0090\u008e\3\2"+
		"\2\2\u0090\u0091\3\2\2\2\u0091\u0094\3\2\2\2\u0092\u0090\3\2\2\2\u0093"+
		"\u0095\7\3\2\2\u0094\u0093\3\2\2\2\u0094\u0095\3\2\2\2\u0095\u0096\3\2"+
		"\2\2\u0096\u0097\7\13\2\2\u0097\u009b\3\2\2\2\u0098\u0099\t\20\2\2\u0099"+
		"\u009b\5\f\7\2\u009aH\3\2\2\2\u009aL\3\2\2\2\u009aT\3\2\2\2\u009a\\\3"+
		"\2\2\2\u009ac\3\2\2\2\u009ah\3\2\2\2\u009aj\3\2\2\2\u009ap\3\2\2\2\u009a"+
		"v\3\2\2\2\u009aw\3\2\2\2\u009a\u0082\3\2\2\2\u009a\u008a\3\2\2\2\u009a"+
		"\u0098\3\2\2\2\u009b\13\3\2\2\2\u009c\u009d\5\16\b\2\u009d\r\3\2\2\2\u009e"+
		"\u00a1\5\20\t\2\u009f\u00a0\t\21\2\2\u00a0\u00a2\5\16\b\2\u00a1\u009f"+
		"\3\2\2\2\u00a1\u00a2\3\2\2\2\u00a2\17\3\2\2\2\u00a3\u00a6\5\22\n\2\u00a4"+
		"\u00a5\t\22\2\2\u00a5\u00a7\5\20\t\2\u00a6\u00a4\3\2\2\2\u00a6\u00a7\3"+
		"\2\2\2\u00a7\21\3\2\2\2\u00a8\u00ab\5\24\13\2\u00a9\u00aa\t\23\2\2\u00aa"+
		"\u00ac\5\22\n\2\u00ab\u00a9\3\2\2\2\u00ab\u00ac\3\2\2\2\u00ac\23\3\2\2"+
		"\2\u00ad\u00b0\5\26\f\2\u00ae\u00af\t\24\2\2\u00af\u00b1\5\24\13\2\u00b0"+
		"\u00ae\3\2\2\2\u00b0\u00b1\3\2\2\2\u00b1\25\3\2\2\2\u00b2\u011c\7Y\2\2"+
		"\u00b3\u011c\t\25\2\2\u00b4\u011c\t\26\2\2\u00b5\u011c\7Z\2\2\u00b6\u011c"+
		"\t\25\2\2\u00b7\u011c\t\26\2\2\u00b8\u011c\5\30\r\2\u00b9\u00ba\t\27\2"+
		"\2\u00ba\u00bb\7\7\2\2\u00bb\u00c8\7X\2\2\u00bc\u00c9\7\6\2\2\u00bd\u00be"+
		"\7\7\2\2\u00be\u00c3\5\f\7\2\u00bf\u00c0\7\b\2\2\u00c0\u00c2\5\f\7\2\u00c1"+
		"\u00bf\3\2\2\2\u00c2\u00c5\3\2\2\2\u00c3\u00c1\3\2\2\2\u00c3\u00c4\3\2"+
		"\2\2\u00c4\u00c6\3\2\2\2\u00c5\u00c3\3\2\2\2\u00c6\u00c7\7\t\2\2\u00c7"+
		"\u00c9\3\2\2\2\u00c8\u00bc\3\2\2\2\u00c8\u00bd\3\2\2\2\u00c9\u00ca\3\2"+
		"\2\2\u00ca\u011c\7\t\2\2\u00cb\u00d8\7X\2\2\u00cc\u00d9\7\6\2\2\u00cd"+
		"\u00ce\7\7\2\2\u00ce\u00d3\5\f\7\2\u00cf\u00d0\7\b\2\2\u00d0\u00d2\5\f"+
		"\7\2\u00d1\u00cf\3\2\2\2\u00d2\u00d5\3\2\2\2\u00d3\u00d1\3\2\2\2\u00d3"+
		"\u00d4\3\2\2\2\u00d4\u00d6\3\2\2\2\u00d5\u00d3\3\2\2\2\u00d6\u00d7\7\t"+
		"\2\2\u00d7\u00d9\3\2\2\2\u00d8\u00cc\3\2\2\2\u00d8\u00cd\3\2\2\2\u00d9"+
		"\u011c\3\2\2\2\u00da\u00db\7D\2\2\u00db\u011c\5\26\f\2\u00dc\u00dd\7:"+
		"\2\2\u00dd\u011c\5\26\f\2\u00de\u00df\t\30\2\2\u00df\u00e0\7\7\2\2\u00e0"+
		"\u00e1\5\30\r\2\u00e1\u00e2\7\t\2\2\u00e2\u011c\3\2\2\2\u00e3\u00e4\t"+
		"\31\2\2\u00e4\u00e5\7\7\2\2\u00e5\u00e6\5\f\7\2\u00e6\u00e7\7\t\2\2\u00e7"+
		"\u011c\3\2\2\2\u00e8\u00e9\t\32\2\2\u00e9\u00ea\7\7\2\2\u00ea\u00eb\5"+
		"\f\7\2\u00eb\u00ec\7\t\2\2\u00ec\u011c\3\2\2\2\u00ed\u00ee\t\33\2\2\u00ee"+
		"\u00ef\7\7\2\2\u00ef\u00f0\5\f\7\2\u00f0\u00f1\7\b\2\2\u00f1\u00f2\5\f"+
		"\7\2\u00f2\u00f3\7\b\2\2\u00f3\u00f4\5\f\7\2\u00f4\u00f5\7\t\2\2\u00f5"+
		"\u011c\3\2\2\2\u00f6\u00f7\t\34\2\2\u00f7\u00f8\7\7\2\2\u00f8\u00f9\5"+
		"\f\7\2\u00f9\u00fa\7\t\2\2\u00fa\u011c\3\2\2\2\u00fb\u00ff\t\35\2\2\u00fc"+
		"\u00fe\5\32\16\2\u00fd\u00fc\3\2\2\2\u00fe\u0101\3\2\2\2\u00ff\u00fd\3"+
		"\2\2\2\u00ff\u0100\3\2\2\2\u0100\u0103\3\2\2\2\u0101\u00ff\3\2\2\2\u0102"+
		"\u0104\5\f\7\2\u0103\u0102\3\2\2\2\u0103\u0104\3\2\2\2\u0104\u011c\3\2"+
		"\2\2\u0105\u010c\7S\2\2\u0106\u0108\5\f\7\2\u0107\u0109\7\b\2\2\u0108"+
		"\u0107\3\2\2\2\u0108\u0109\3\2\2\2\u0109\u010b\3\2\2\2\u010a\u0106\3\2"+
		"\2\2\u010b\u010e\3\2\2\2\u010c\u010a\3\2\2\2\u010c\u010d\3\2\2\2\u010d"+
		"\u010f\3\2\2\2\u010e\u010c\3\2\2\2\u010f\u011c\7T\2\2\u0110\u0111\7U\2"+
		"\2\u0111\u0112\5\f\7\2\u0112\u0113\7V\2\2\u0113\u0114\5\f\7\2\u0114\u0115"+
		"\7W\2\2\u0115\u0116\5\f\7\2\u0116\u011c\3\2\2\2\u0117\u0118\7\7\2\2\u0118"+
		"\u0119\5\f\7\2\u0119\u011a\7\t\2\2\u011a\u011c\3\2\2\2\u011b\u00b2\3\2"+
		"\2\2\u011b\u00b3\3\2\2\2\u011b\u00b4\3\2\2\2\u011b\u00b5\3\2\2\2\u011b"+
		"\u00b6\3\2\2\2\u011b\u00b7\3\2\2\2\u011b\u00b8\3\2\2\2\u011b\u00b9\3\2"+
		"\2\2\u011b\u00cb\3\2\2\2\u011b\u00da\3\2\2\2\u011b\u00dc\3\2\2\2\u011b"+
		"\u00de\3\2\2\2\u011b\u00e3\3\2\2\2\u011b\u00e8\3\2\2\2\u011b\u00ed\3\2"+
		"\2\2\u011b\u00f6\3\2\2\2\u011b\u00fb\3\2\2\2\u011b\u0105\3\2\2\2\u011b"+
		"\u0110\3\2\2\2\u011b\u0117\3\2\2\2\u011c\27\3\2\2\2\u011d\u0121\7X\2\2"+
		"\u011e\u0120\5\32\16\2\u011f\u011e\3\2\2\2\u0120\u0123\3\2\2\2\u0121\u011f"+
		"\3\2\2\2\u0121\u0122\3\2\2\2\u0122\31\3\2\2\2\u0123\u0121\3\2\2\2\u0124"+
		"\u0125\7S\2\2\u0125\u0126\5\f\7\2\u0126\u0127\7T\2\2\u0127\33\3\2\2\2"+
		"\32%,\67;C|\u0088\u0090\u0094\u009a\u00a1\u00a6\u00ab\u00b0\u00c3\u00c8"+
		"\u00d3\u00d8\u00ff\u0103\u0108\u010c\u011b\u0121";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}