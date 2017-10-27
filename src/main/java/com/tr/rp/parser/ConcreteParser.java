package com.tr.rp.parser;

import static com.tr.rp.ast.expressions.Expressions.lit;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Collectors;

import org.antlr.v4.runtime.tree.TerminalNode;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.AbstractStatement;
import com.tr.rp.ast.Function;
import com.tr.rp.ast.FunctionScope;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.ast.expressions.Abs;
import com.tr.rp.ast.expressions.ArrayConstructExpression;
import com.tr.rp.ast.expressions.ArrayInitExpression;
import com.tr.rp.ast.expressions.ArrayRangeConstructExpression;
import com.tr.rp.ast.expressions.AssignmentTarget;
import com.tr.rp.ast.expressions.AssignmentTargetList;
import com.tr.rp.ast.expressions.AssignmentTargetTerminal;
import com.tr.rp.ast.expressions.CharToInt;
import com.tr.rp.ast.expressions.Conditional;
import com.tr.rp.ast.expressions.ConstructorExpression;
import com.tr.rp.ast.expressions.Expressions;
import com.tr.rp.ast.expressions.FunctionCall;
import com.tr.rp.ast.expressions.IndexElementExpression;
import com.tr.rp.ast.expressions.InferringFunctionCall;
import com.tr.rp.ast.expressions.IntToChar;
import com.tr.rp.ast.expressions.IsOfType;
import com.tr.rp.ast.expressions.IsDefined;
import com.tr.rp.ast.expressions.ListAppend;
import com.tr.rp.ast.expressions.ListReplace;
import com.tr.rp.ast.expressions.ListValueAt;
import com.tr.rp.ast.expressions.Literal;
import com.tr.rp.ast.expressions.MapContainsKey;
import com.tr.rp.ast.expressions.MapGet;
import com.tr.rp.ast.expressions.MapPut;
import com.tr.rp.ast.expressions.MapRemoveKey;
import com.tr.rp.ast.expressions.Max;
import com.tr.rp.ast.expressions.Min;
import com.tr.rp.ast.expressions.Negative;
import com.tr.rp.ast.expressions.Not;
import com.tr.rp.ast.expressions.ParseInt;
import com.tr.rp.ast.expressions.RangeChoiceExpression;
import com.tr.rp.ast.expressions.RankExpr;
import com.tr.rp.ast.expressions.RankedChoiceExpression;
import com.tr.rp.ast.expressions.ReadFile;
import com.tr.rp.ast.expressions.SetAdd;
import com.tr.rp.ast.expressions.SetContains;
import com.tr.rp.ast.expressions.SetRemove;
import com.tr.rp.ast.expressions.Size;
import com.tr.rp.ast.expressions.StackPeek;
import com.tr.rp.ast.expressions.StackPop;
import com.tr.rp.ast.expressions.StackPush;
import com.tr.rp.ast.expressions.SubString;
import com.tr.rp.ast.expressions.ToArray;
import com.tr.rp.ast.expressions.Variable;
import com.tr.rp.ast.statements.Assert;
import com.tr.rp.ast.statements.AssertRanked;
import com.tr.rp.ast.statements.Assign;
import com.tr.rp.ast.statements.Composition;
import com.tr.rp.ast.statements.CurrentRank;
import com.tr.rp.ast.statements.Cut;
import com.tr.rp.ast.statements.Dec;
import com.tr.rp.ast.statements.ForInStatement;
import com.tr.rp.ast.statements.ForStatement;
import com.tr.rp.ast.statements.IfElse;
import com.tr.rp.ast.statements.Inc;
import com.tr.rp.ast.statements.Observe;
import com.tr.rp.ast.statements.ObserveJ;
import com.tr.rp.ast.statements.ObserveL;
import com.tr.rp.ast.statements.PrintRankingStatement;
import com.tr.rp.ast.statements.PrintStatement;
import com.tr.rp.ast.statements.Program;
import com.tr.rp.ast.statements.RankedChoice;
import com.tr.rp.ast.statements.Reset;
import com.tr.rp.ast.statements.Return;
import com.tr.rp.ast.statements.Skip;
import com.tr.rp.ast.statements.Block;
import com.tr.rp.ast.statements.While;
import com.tr.rp.parser.RankPLParser.Arithmetic1ExpressionContext;
import com.tr.rp.parser.RankPLParser.Arithmetic2ExpressionContext;
import com.tr.rp.parser.RankPLParser.ArrayConstructExprContext;
import com.tr.rp.parser.RankPLParser.ArrayRangeConstructExprContext;
import com.tr.rp.parser.RankPLParser.AssertRankedStatementContext;
import com.tr.rp.parser.RankPLParser.AssertStatementContext;
import com.tr.rp.parser.RankPLParser.AssignmentStatementContext;
import com.tr.rp.parser.RankPLParser.Assignment_targetContext;
import com.tr.rp.parser.RankPLParser.BoolExpressionContext;
import com.tr.rp.parser.RankPLParser.CompareExprContext;
import com.tr.rp.parser.RankPLParser.ConditionalExpressionContext;
import com.tr.rp.parser.RankPLParser.CurrentRankStatementContext;
import com.tr.rp.parser.RankPLParser.CutStatementContext;
import com.tr.rp.parser.RankPLParser.ExceptionallyStatementContext;
import com.tr.rp.parser.RankPLParser.Expr2Context;
import com.tr.rp.parser.RankPLParser.ForInStatementContext;
import com.tr.rp.parser.RankPLParser.ForStatementContext;
import com.tr.rp.parser.RankPLParser.FunctionCallContext;
import com.tr.rp.parser.RankPLParser.FunctiondefContext;
import com.tr.rp.parser.RankPLParser.Functiondef_or_statementContext;
import com.tr.rp.parser.RankPLParser.IfStatementContext;
import com.tr.rp.parser.RankPLParser.IncDecStatementContext;
import com.tr.rp.parser.RankPLParser.IndexContext;
import com.tr.rp.parser.RankPLParser.IndexedExpressionContext;
import com.tr.rp.parser.RankPLParser.IndifferentChoiceStatementContext;
import com.tr.rp.parser.RankPLParser.InferringFunctionCallContext;
import com.tr.rp.parser.RankPLParser.LiteralBoolExprContext;
import com.tr.rp.parser.RankPLParser.LiteralIntExpressionContext;
import com.tr.rp.parser.RankPLParser.LiteralStringExprContext;
import com.tr.rp.parser.RankPLParser.MinusExprContext;
import com.tr.rp.parser.RankPLParser.Multi_assignment_targetContext;
import com.tr.rp.parser.RankPLParser.NegateExprContext;
import com.tr.rp.parser.RankPLParser.ObserveJStatementContext;
import com.tr.rp.parser.RankPLParser.ObserveLStatementContext;
import com.tr.rp.parser.RankPLParser.ObserveStatementContext;
import com.tr.rp.parser.RankPLParser.OpenStatContext;
import com.tr.rp.parser.RankPLParser.SkipStatContext;
import com.tr.rp.parser.RankPLParser.BlockStatementContext;
import com.tr.rp.parser.RankPLParser.ParExpressionContext;
import com.tr.rp.parser.RankPLParser.PopFunctionCallContext;
import com.tr.rp.parser.RankPLParser.PrintRankingStatementContext;
import com.tr.rp.parser.RankPLParser.PrintStatementContext;
import com.tr.rp.parser.RankPLParser.ProgramContext;
import com.tr.rp.parser.RankPLParser.RankedChoiceExpressionContext;
import com.tr.rp.parser.RankPLParser.RankedChoiceStatementContext;
import com.tr.rp.parser.RankPLParser.RangeChoiceExpressionContext;
import com.tr.rp.parser.RankPLParser.ResetStatementContext;
import com.tr.rp.parser.RankPLParser.ReturnStatementContext;
import com.tr.rp.parser.RankPLParser.SequenceStatContext;
import com.tr.rp.parser.RankPLParser.SkipStatementContext;
import com.tr.rp.parser.RankPLParser.StatContext;
import com.tr.rp.parser.RankPLParser.TermStatContext;
import com.tr.rp.parser.RankPLParser.VariableContext;
import com.tr.rp.parser.RankPLParser.VariableExpressionContext;
import com.tr.rp.parser.RankPLParser.WhileStatementContext;
import com.tr.rp.varstore.datastructures.PersistentList;
import com.tr.rp.varstore.datastructures.PersistentMap;
import com.tr.rp.varstore.datastructures.PersistentSet;
import com.tr.rp.varstore.datastructures.PersistentStack;
import com.tr.rp.varstore.types.Type;

import st4hidden.org.antlr.runtime.Token;

public class ConcreteParser extends RankPLBaseVisitor<LanguageElement> {

	private FunctionScope functionScope = new FunctionScope();
	
	@Override
	public LanguageElement visitTermStat(TermStatContext ctx) {
		return visit(ctx.term_stat());
	}

	@Override
	public LanguageElement visitOpenStat(OpenStatContext ctx) {
		return visit(ctx.open_stat());
	}

	@Override
	public LanguageElement visitSequenceStat(SequenceStatContext ctx) {
		List<AbstractStatement> statements = new ArrayList<AbstractStatement>();
		for (StatContext sc: ctx.stat()) {
			AbstractStatement s = (AbstractStatement)visit(sc);
			if (!Objects.equals(s, new Skip())) {
				statements.add(s);
			}
		}
		if (statements.isEmpty()) {
			return new Skip();
		} else if (statements.size() == 1) {
			return statements.get(0);
		} else {
			Composition c = new Composition(statements);
			c.setLineNumber(ctx.getStart().getLine());
			return c;
		}
	}

	@Override
	public LanguageElement visitSkipStat(SkipStatContext ctx) {
		return new Skip();
	}

	@Override
	public LanguageElement visitAssignmentStatement(AssignmentStatementContext ctx) {
		AssignmentTarget target = (AssignmentTarget)visit(ctx.assignment_target());
		target.setLineNumber(ctx.assignment_target().getStart().getLine());
		AbstractExpression value = (AbstractExpression)visit(ctx.exp());
		value.setLineNumber(ctx.exp().getStart().getLine());
		AbstractStatement s = new Assign(target, value);
		s.setLineNumber(ctx.getStart().getLine());
		return s;
	}

	@Override
	public LanguageElement visitIncDecStatement(IncDecStatementContext ctx) {
		AssignmentTarget target = (AssignmentTarget)visit(ctx.assignment_target());
		target.setLineNumber(ctx.assignment_target().getStart().getLine());
		String aop = ctx.op.getText();
		AbstractStatement s = null;
		if (aop.equals("++")) s = new Inc(target);
		if (aop.equals("--")) s = new Dec(target);
		s.setLineNumber(ctx.getStart().getLine());
		return s;
	}

	@Override
	public LanguageElement visitReturnStatement(ReturnStatementContext ctx) {
		AbstractExpression e = (AbstractExpression)visit(ctx.exp());
		e.setLineNumber(ctx.exp().getStart().getLine());
		AbstractStatement s = new Return(e);
		s.setLineNumber(ctx.getStart().getLine());
		return s;
	}

	@Override
	public LanguageElement visitPrintStatement(PrintStatementContext ctx) {
		AbstractExpression e = (AbstractExpression)visit(ctx.exp());
		e.setLineNumber(ctx.exp().getStart().getLine());
		PrintStatement s = new PrintStatement(e);
		s.setLineNumber(ctx.getStart().getLine());
		return s;
	}

	@Override
	public LanguageElement visitPrintRankingStatement(PrintRankingStatementContext ctx) {
		AbstractExpression e = (AbstractExpression)visit(ctx.exp());
		e.setLineNumber(ctx.exp().getStart().getLine());
		PrintRankingStatement s = new PrintRankingStatement(e);
		s.setLineNumber(ctx.getStart().getLine());
		return s;
	}

	@Override
	public LanguageElement visitCutStatement(CutStatementContext ctx) {
		AbstractExpression e = (AbstractExpression)visit(ctx.exp());
		e.setLineNumber(ctx.exp().getStart().getLine());
		AbstractStatement s = new Cut(e);
		s.setLineNumber(ctx.getStart().getLine());
		return s;
	}

	@Override
	public LanguageElement visitAssertRankedStatement(AssertRankedStatementContext ctx) {
		AbstractExpression arg1 = (AbstractExpression)visit(ctx.exp().get(0));
		arg1.setLineNumber(ctx.exp(0).getStart().getLine());
		AbstractExpression[] argRest = new AbstractExpression[ctx.exp().size() - 1];
		for (int i = 1; i < ctx.exp().size(); i++) {
			argRest[i-1] = (AbstractExpression)visit(ctx.exp().get(i));
			argRest[i-1].setLineNumber(ctx.exp().get(i).getStart().getLine());
		}
		AssertRanked s = new AssertRanked(arg1, argRest);
		s.setLineNumber(ctx.getStart().getLine());
		return s;
	}

	@Override
	public LanguageElement visitAssertStatement(AssertStatementContext ctx) {
		AbstractExpression arg = (AbstractExpression)visit(ctx.exp());
		arg.setLineNumber(ctx.exp().getStart().getLine());
		Assert s = new Assert(arg);
		s.setLineNumber(ctx.getStart().getLine());
		return s;
	}

	@Override
	public LanguageElement visitResetStatement(ResetStatementContext ctx) {
		Reset s = new Reset();
		s.setLineNumber(ctx.getStart().getLine());
		return s;
	}

	@Override
	public LanguageElement visitIndex(IndexContext ctx) {
		return this.visit(ctx.exp());
	}

	@Override
	public LanguageElement visitAssignment_target(Assignment_targetContext ctx) {
		if (ctx.multi_assignment_target() != null) {
			return visit(ctx.multi_assignment_target());
		}
		TerminalNode tn = ctx.VAR();
		String varName = tn.toString();
		AbstractExpression[] index = new AbstractExpression[ctx.index().size()];
		for (int i = 0; i < index.length; i++) {
			index[i] = (AbstractExpression)visit(ctx.index(i));
			index[i].setLineNumber(ctx.index(i).getStart().getLine());
		}
		AssignmentTargetTerminal att = new AssignmentTargetTerminal(varName, index);
		att.setLineNumber(ctx.getStart().getLine());
		return att;
	}

	@Override
	public LanguageElement visitMulti_assignment_target(Multi_assignment_targetContext ctx) {
		AssignmentTarget[] targets = new AssignmentTarget[ctx.assignment_target().size()];
		for (int i = 0; i < targets.length; i++) {
			targets[i] = (AssignmentTarget)visit(ctx.assignment_target(i));
			targets[i].setLineNumber(ctx.assignment_target(i).getStart().getLine());
		}
		AssignmentTargetList atl = new AssignmentTargetList(targets);
		atl.setLineNumber(ctx.getStart().getLine());
		return atl;
	}

	@Override
	public LanguageElement visitIfStatement(IfStatementContext ctx) {
		AbstractExpression boolExpr = (AbstractExpression)visit(ctx.exp());
		boolExpr.setLineNumber(ctx.exp().getStart().getLine());
		AbstractStatement a = (AbstractStatement)visit(ctx.stat().get(0));
		a.setLineNumber(ctx.stat().get(0).getStart().getLine());
		AbstractStatement b;
		if (ctx.stat().size() > 1) {
			b = (AbstractStatement)visit(ctx.stat().get(1));
			b.setLineNumber(ctx.stat().get(1).getStart().getLine());
		} else {
			b = new Skip();
		}
		AbstractStatement s = new IfElse(boolExpr, a, b);
		s.setLineNumber(ctx.getStart().getLine());
		return s;

	}

	@Override
	public LanguageElement visitObserveStatement(ObserveStatementContext ctx) {
		AbstractExpression boolExpr = (AbstractExpression)visit(ctx.exp());
		boolExpr.setLineNumber(ctx.exp().getStart().getLine());
		AbstractStatement s = new Observe(boolExpr);
		s.setLineNumber(ctx.getStart().getLine());
		return s;
	}

	@Override
	public LanguageElement visitObserveJStatement(ObserveJStatementContext ctx) {
		AbstractExpression rank;
		AbstractExpression boolExpr;
		if (ctx.exp().size() == 1) {
			rank = new Literal<Integer>(1);
			rank.setLineNumber(ctx.getStart().getLine());
			boolExpr = (AbstractExpression)visit(ctx.exp(0));
			boolExpr.setLineNumber(ctx.exp(0).getStart().getLine());
		} else {
			rank = (AbstractExpression)visit(ctx.exp(0));
			rank.setLineNumber(ctx.exp(0).getStart().getLine());
			boolExpr = (AbstractExpression)visit(ctx.exp(1));
			boolExpr.setLineNumber(ctx.exp(1).getStart().getLine());
		}
		AbstractStatement s = new ObserveJ(boolExpr, rank);
		s.setLineNumber(ctx.getStart().getLine());
		return s;
	}

	@Override
	public LanguageElement visitObserveLStatement(ObserveLStatementContext ctx) {
		AbstractExpression rank;
		AbstractExpression boolExpr;
		if (ctx.exp().size() == 1) {
			rank = new Literal<Integer>(1);
			rank.setLineNumber(ctx.getStart().getLine());
			boolExpr = (AbstractExpression)visit(ctx.exp(0));
			boolExpr.setLineNumber(ctx.exp(0).getStart().getLine());
		} else {
			rank = (AbstractExpression)visit(ctx.exp(0));
			rank.setLineNumber(ctx.exp(0).getStart().getLine());
			boolExpr = (AbstractExpression)visit(ctx.exp(1));
			boolExpr.setLineNumber(ctx.exp(1).getStart().getLine());
		}
		AbstractStatement s = new ObserveL(boolExpr, rank);
		s.setLineNumber(ctx.getStart().getLine());
		return s;
	}

	@Override
	public LanguageElement visitRankedChoiceStatement(RankedChoiceStatementContext ctx) {
		AbstractExpression rank = ctx.exp() == null? lit(1): (AbstractExpression)visit(ctx.exp());
		rank.setLineNumber(ctx.exp() == null? ctx.getStart().getLine():
			ctx.exp().getStart().getLine());
		AbstractStatement a = (AbstractStatement)visit(ctx.stat().get(0));
		a.setLineNumber(ctx.stat().get(0).getStart().getLine());
		AbstractStatement b;
		if (ctx.stat().size() > 1) {
			b = (AbstractStatement)visit(ctx.stat().get(1));
		} else {
			b = new Skip();
		}
		AbstractStatement s = new RankedChoice(a, b, rank);
		s.setLineNumber(ctx.getStart().getLine());
		return s;
	}

	@Override
	public LanguageElement visitExceptionallyStatement(ExceptionallyStatementContext ctx) {
		AbstractExpression rank = ctx.exp() == null? lit(1): (AbstractExpression)visit(ctx.exp());
		rank.setLineNumber(ctx.exp() == null? ctx.getStart().getLine():
			ctx.exp().getStart().getLine());
		AbstractStatement a = (AbstractStatement)visit(ctx.stat());
		a.setLineNumber(ctx.stat().getStart().getLine());
		AbstractStatement s = new RankedChoice(new Skip(), a, rank);
		s.setLineNumber(ctx.getStart().getLine());
		return s;
	}

	@Override
	public LanguageElement visitIndifferentChoiceStatement(IndifferentChoiceStatementContext ctx) {
		AbstractStatement[] choices = new AbstractStatement[ctx.stat().size()];
		for (int i = 0; i < choices.length; i++) {
			choices[i] = (AbstractStatement)visit(ctx.stat().get(i));
			choices[i].setLineNumber(ctx.stat().get(i).getStart().getLine());
		}
		AbstractStatement s = constructIndifferentChoice(choices, 0);
		s.setLineNumber(ctx.getStart().getLine());
		return s;
	}
	
	public AbstractStatement constructIndifferentChoice(AbstractStatement[] choices, int idx) {
		if (idx >= choices.length) return null;
		AbstractStatement e = choices[idx];
		idx++;
		if (idx == choices.length) {
			return e;
		} else {
			return new RankedChoice(e, constructIndifferentChoice(choices, idx), new Literal<Integer>(0));
		}
	}


	@Override
	public LanguageElement visitSkipStatement(SkipStatementContext ctx) {
		AbstractStatement s = new Skip();
		s.setLineNumber(ctx.getStart().getLine());
		return s;
	}

	@Override
	public LanguageElement visitBlockStatement(BlockStatementContext ctx) {
		AbstractStatement s = new Block();
		s.setLineNumber(ctx.getStart().getLine());
		return s;
	}

	@Override
	public LanguageElement visitWhileStatement(WhileStatementContext ctx) {
		AbstractExpression boolExpr = (AbstractExpression)visit(ctx.exp());
		boolExpr.setLineNumber(ctx.exp().getStart().getLine());
		AbstractStatement body = (AbstractStatement)visit(ctx.stat());
		body.setLineNumber(ctx.stat().getStart().getLine());
		AbstractStatement s = new While(boolExpr, body);
		s.setLineNumber(ctx.getStart().getLine());
		return s;
	}

	@Override
	public LanguageElement visitForStatement(ForStatementContext ctx) {
		AbstractExpression forCondition = (AbstractExpression)visit(ctx.exp());
		forCondition.setLineNumber(ctx.exp().getStart().getLine());
		AbstractStatement init = (AbstractStatement)visit(ctx.term_stat(0));
		init.setLineNumber(ctx.term_stat(0).getStart().getLine());
		AbstractStatement next = (AbstractStatement)visit(ctx.term_stat(1));
		next.setLineNumber(ctx.term_stat(1).getStart().getLine());
		AbstractStatement body = (AbstractStatement)visit(ctx.stat());
		body.setLineNumber(ctx.stat().getStart().getLine());
		AbstractStatement s = new ForStatement(init, forCondition, next, body);
		s.setLineNumber(ctx.getStart().getLine());
		return s;
	}
	
	@Override
	public LanguageElement visitForInStatement(ForInStatementContext ctx) {
		AssignmentTarget target = (AssignmentTarget)visit(ctx.assignment_target());
		AbstractExpression exp = (AbstractExpression)visit(ctx.exp());
		exp.setLineNumber(ctx.exp().getStart().getLine());
		AbstractStatement body = (AbstractStatement)visit(ctx.stat());
		AbstractStatement s = new ForInStatement(target, exp, body);
		s.setLineNumber(ctx.getStart().getLine());
		return s;
	}
	
	@Override
	public LanguageElement visitCurrentRankStatement(CurrentRankStatementContext ctx) {
		AssignmentTarget target = (AssignmentTarget)visit(ctx.assignment_target());
		target.setLineNumber(ctx.assignment_target().getStart().getLine());
		AbstractStatement s = new CurrentRank(target);
		s.setLineNumber(ctx.getStart().getLine());
		return s;
	}

	@Override
	public LanguageElement visitArithmetic1Expression(Arithmetic1ExpressionContext ctx) {
		AbstractExpression a = (AbstractExpression)visit(ctx.expr4());
		if (ctx.expr3() == null) {
			return a;
		} else {
			AbstractExpression b = (AbstractExpression)visit(ctx.expr3());
			String aop = ctx.aop.getText();
			AbstractExpression exp = null;
			if (aop.equals("+")) exp = Expressions.plus(a, b);
			if (aop.equals("-")) exp = Expressions.minus(a, b);
			if (exp != null) {
				exp.setLineNumber(ctx.getStart().getLine());
				return exp;
			}
		}
		throw new RuntimeException("Internal parse error");
	}

	@Override
	public LanguageElement visitArithmetic2Expression(Arithmetic2ExpressionContext ctx) {
		AbstractExpression a = (AbstractExpression)visit(ctx.expr5());
		a.setLineNumber(ctx.expr5().getStart().getLine());
		if (ctx.expr4() == null) {
			return a;
		} else {
			AbstractExpression b = (AbstractExpression)visit(ctx.expr4());
			b.setLineNumber(ctx.expr4().getStart().getLine());
			String aop = ctx.aop.getText();
			AbstractExpression exp = null;
			if (aop.equals("*")) exp = Expressions.times(a, b);
			if (aop.equals("/")) exp = Expressions.div(a, b);
			if (aop.equals("%")) exp = Expressions.mod(a, b);
			if (exp != null) {
				exp.setLineNumber(ctx.getStart().getLine());
				return exp;
			}
		}
		throw new RuntimeException("Internal parse error");
	}

	@Override
	public LanguageElement visitIndexedExpression(IndexedExpressionContext ctx) {
		AbstractExpression a = (AbstractExpression)visit(ctx.expr6());
		a.setLineNumber(ctx.expr6().getStart().getLine());
		if (ctx.index().size() > 0) {
			AbstractExpression[] index = new AbstractExpression[ctx.index().size()];
			for (int i = 0; i < index.length; i++) {
				index[i] = (AbstractExpression)visit(ctx.index(i));
				index[i].setLineNumber(ctx.index(i).getStart().getLine());
			}
			IndexElementExpression iee = new IndexElementExpression(a, index);
			iee.setLineNumber(ctx.getStart().getLine());
			return iee;
		} else {
			return a;
		}
	}

	@Override
	public LanguageElement visitBoolExpression(BoolExpressionContext ctx) {
		AbstractExpression a = (AbstractExpression)visit(ctx.expr2());
		if (ctx.expr1() == null) {
			return a;
		} else {
			AbstractExpression b = (AbstractExpression)visit(ctx.expr1());
			b.setLineNumber(ctx.expr1().getStart().getLine());
			String aop = ctx.aop.getText();
			AbstractExpression exp = null;
			if (aop.equals("&") || aop.equals("&&")) exp = Expressions.and(a, b);
			if (aop.equals("|") || aop.equals("||")) exp = Expressions.or(a, b);
			if (aop.equals("^")) exp = Expressions.xor(a, b);
			if (exp != null) {
				exp.setLineNumber(ctx.getStart().getLine());
				return exp;
			}
		}
		throw new RuntimeException("Internal parse error");
	}

	@Override
	public LanguageElement visitRankedChoiceExpression(RankedChoiceExpressionContext ctx) {
		AbstractExpression exp1 = (AbstractExpression)visit(ctx.expr0());
		exp1.setLineNumber(ctx.expr0().getStart().getLine());
		if (ctx.exprA().size() == 2) {
			AbstractExpression rank = (AbstractExpression)visit(ctx.exprA(0));
			rank.setLineNumber(ctx.exprA(0).getStart().getLine());
			AbstractExpression exp2 = (AbstractExpression)visit(ctx.exprA(1));
			exp2.setLineNumber(ctx.exprA(1).getStart().getLine());
			RankedChoiceExpression rce = new RankedChoiceExpression(exp1, exp2, rank);
			rce.setLineNumber(ctx.getStart().getLine());
			return rce;
		} else {
			return exp1;
		}
	}

	@Override
	public LanguageElement visitRangeChoiceExpression(RangeChoiceExpressionContext ctx) {
		AbstractExpression exp1 = (AbstractExpression)visit(ctx.exp(0));
		exp1.setLineNumber(ctx.exp(0).getStart().getLine());
		AbstractExpression exp2 = (AbstractExpression)visit(ctx.exp(1));
		exp2.setLineNumber(ctx.exp(1).getStart().getLine());
		RangeChoiceExpression rce = new RangeChoiceExpression(exp1, exp2);
		rce.setLineNumber(ctx.getStart().getLine());
		return rce;
	}


	@Override
	public LanguageElement visitConditionalExpression(ConditionalExpressionContext ctx) {
		AbstractExpression e1 = (AbstractExpression)visit(ctx.expr1(0));
		e1.setLineNumber(ctx.expr1(0).getStart().getLine());
		if (ctx.expr1().size() > 1) {
			AbstractExpression e2 = (AbstractExpression)visit(ctx.expr1(1));
			e2.setLineNumber(ctx.expr1(1).getStart().getLine());
			AbstractExpression e3 = (AbstractExpression)visit(ctx.expr1(2));
			e3.setLineNumber(ctx.expr1(2).getStart().getLine());
			Conditional c = new Conditional(e1, e2, e3);
			c.setLineNumber(ctx.getStart().getLine());
			return c;
		} else {
			return e1;
		}
	}

	@Override
	public LanguageElement visitLiteralIntExpression(LiteralIntExpressionContext ctx) {
		String num = ctx.getText();
		Literal<Integer> lit = new Literal<Integer>(Integer.parseInt(num));
		lit.setLineNumber(ctx.getStart().getLine());
		return lit;
	}
	
	@Override
	public LanguageElement visitVariableExpression(VariableExpressionContext ctx) {
		return visit(ctx.variable());
	}
	
	@Override
	public LanguageElement visitFunctionCall(FunctionCallContext ctx) {
		String functionName = ctx.VAR().toString();
		AbstractExpression[] args = new AbstractExpression[ctx.exp().size()];
		for (int i = 0; i < args.length; i++) {
			args[i] = (AbstractExpression)visit(ctx.exp(i));
		}
		
		// Try to parse as built in function
		AbstractExpression builtInFunction = getBuiltInFunction(functionName, args);
		if (builtInFunction != null) {
			builtInFunction.setLineNumber(ctx.getStart().getLine());
			return builtInFunction;
		}
		
		return new FunctionCall(functionName, functionScope, args);
	}
	
	@Override
	public LanguageElement visitPopFunctionCall(PopFunctionCallContext ctx) {
		AssignmentTarget target = (AssignmentTarget)visit(ctx.assignment_target());
		target.setLineNumber(ctx.getStart().getLine());
		return new StackPop(target);
	}
	
	@Override
	public LanguageElement visitInferringFunctionCall(InferringFunctionCallContext ctx) {
		String functionName = ctx.VAR().toString();
		AbstractExpression[] args = new AbstractExpression[ctx.exp().size()];
		for (int i = 0; i < args.length; i++) {
			args[i] = (AbstractExpression)visit(ctx.exp(i));
		}
		FunctionCall fc = new InferringFunctionCall(functionName, functionScope, args);
		fc.setLineNumber(ctx.getStart().getLine());;
		return fc;
	}
	
	@Override
	public LanguageElement visitCompareExpr(CompareExprContext ctx) {
		AbstractExpression a = (AbstractExpression)visit(ctx.expr3());
		a.setLineNumber(ctx.getStart().getLine());
		if (ctx.expr2() == null) {
			return a;
		} else {
			String cop = ctx.cop.getText();
			AbstractExpression b = (AbstractExpression)visit(ctx.expr2());
			b.setLineNumber(ctx.getStart().getLine());
			AbstractExpression exp = null;
			if (cop.equals("<")) exp = Expressions.lt(a, b);
			if (cop.equals("<=")) exp = Expressions.leq(a, b);
			if (cop.equals("==")) exp = Expressions.eq(a, b);
			if (cop.equals("!=")) exp = Expressions.notEquals(a, b);
			if (cop.equals(">")) exp = Expressions.gt(a, b);
			if (cop.equals(">=")) exp = Expressions.geq(a, b);
			if (exp != null) {
				exp.setLineNumber(ctx.getStart().getLine());
				return exp;
			}
		}
		throw new RuntimeException("Internal parse error");
	}

	@Override
	public LanguageElement visitNegateExpr(NegateExprContext ctx) {
		AbstractExpression a = (AbstractExpression)visit(ctx.getChild(1));
		AbstractExpression exp = new Not(a);
		exp.setLineNumber(ctx.getStart().getLine());
		return exp;
	}

	@Override
	public LanguageElement visitMinusExpr(MinusExprContext ctx) {
		AbstractExpression a = (AbstractExpression)visit(ctx.getChild(1));
		// Simplify negative literal
		if (a instanceof Literal && ((Literal)a).getLiteralValue() instanceof Integer) {
			a = new Literal<Integer>(-(int)((Literal)a).getLiteralValue());
		} else {
			a = new Negative(a);
		}
		a.setLineNumber(ctx.getStart().getLine());
		return a;
	}

	@Override
	public LanguageElement visitLiteralBoolExpr(LiteralBoolExprContext ctx) {
		AbstractExpression exp;
		if (ctx.getText().toLowerCase().equals("true")) {
			exp = new Literal<Boolean>(true);
		} else {
			exp = new Literal<Boolean>(false);
		}
		exp.setLineNumber(ctx.getStart().getLine());
		exp.setLineNumber(ctx.getStart().getLine());
		return exp;
	}
	
	@Override
	public LanguageElement visitLiteralStringExpr(LiteralStringExprContext ctx) {
		String s = ctx.getText();
		// remove quotes
		s = s.substring(1, s.length()-1);
		// a hack to process escape characters. This removes trailing spaces so we have to remember them
		int wsCount = 0;
		while (wsCount < s.length() && s.charAt(wsCount) == ' ') wsCount++;
	    Properties prop = new Properties();     
	    try {
			prop.load(new StringReader("x=" + s + "\n"));
		} catch (IOException e) {
			throw new RuntimeException("Internal parser error: " + e);
		}
	    s = prop.getProperty("x");
	    // put back leading spaces
	    for (int i = 0; i < wsCount; i++) s = " " + s;
		Literal<String> lit = new Literal<String>(s);
		lit.setLineNumber(ctx.getStart().getLine());
		return lit;
	}

	@Override
	public LanguageElement visitArrayConstructExpr(ArrayConstructExprContext ctx) {
		AbstractExpression[] values = new AbstractExpression[ctx.exp().size()];
		for (int i = 0; i < values.length; i++) {
			values[i] = (AbstractExpression)visit(ctx.exp(i));
		}
		AbstractExpression exp = new ArrayConstructExpression(values);
		exp.setLineNumber(ctx.getStart().getLine());
		return exp;
	}

	@Override
	public LanguageElement visitArrayRangeConstructExpr(ArrayRangeConstructExprContext ctx) {
		AbstractExpression exp1 = (AbstractExpression)visit(ctx.exp(0));
		exp1.setLineNumber(ctx.exp(0).getStart().getLine());
		AbstractExpression exp2 = (AbstractExpression)visit(ctx.exp(1));
		exp2.setLineNumber(ctx.exp(1).getStart().getLine());
		ArrayRangeConstructExpression arce = new ArrayRangeConstructExpression(exp1, exp2);
		arce.setLineNumber(ctx.getStart().getLine());
		return arce;
	}
	
	public LanguageElement visitParExpression(ParExpressionContext ctx) { 
		return visit(ctx.getChild(1));
	}

	@Override
	public LanguageElement visitProgram(ProgramContext ctx) {
		List<AbstractStatement> statements = new ArrayList<AbstractStatement>();
		for (Functiondef_or_statementContext fsc: ctx.functiondef_or_statement()) {
			if (fsc.stat() != null) {
				AbstractStatement s = (AbstractStatement)visit(fsc.stat());
				if (s == null) {
					return null; 
				}
				if (!Objects.equals(s, new Skip())) {
					s = s.rewriteEmbeddedFunctionCalls();
					s.setLineNumber(fsc.stat().getStart().getLine());
					statements.add(s);
				}
			}
			if (fsc.functiondef() != null) {
				Function f = (Function)visit(fsc.functiondef());
				functionScope.registerFunction(f);
			}
		}
		if (statements.isEmpty()) {
			return new Program(new Skip(), functionScope);
		} else if (statements.size() == 1) {
			return new Program(statements.get(0), functionScope);
		} else {
			return new Program(new Composition(statements), functionScope);
		}
	}
	
	@Override
	public LanguageElement visitVariable(VariableContext ctx) {
		TerminalNode tn = ctx.VAR();
		String varName = tn.toString();
		Variable var = new Variable(varName);
		var.setLineNumber(ctx.getStart().getLine());
		return var;
	}
	
	public LanguageElement visitFunctiondef(FunctiondefContext ctx) {
		String functionName = ctx.VAR(0).getText();
		String[] parameters = new String[ctx.VAR().size()-1];
		for (int i = 0; i < parameters.length; i++) {
			parameters[i] = ctx.VAR(i+1).getText();
		}
		Function function = new Function(functionName, parameters);
		function.setLineNumber(ctx.start.getLine());
		AbstractStatement s = (AbstractStatement)visit(ctx.stat());
		s = s.rewriteEmbeddedFunctionCalls();
		s.setLineNumber(ctx.stat().getStart().getLine());
		function.setBody(s);
		function.setMemoize(ctx.MEMOIZE() != null);
		return function;
	}

	public AbstractExpression getBuiltInFunction(String name, AbstractExpression[] args) {
		name = name.toLowerCase();

		// Type check functions
		for (Type<?> type: Type.ALL_TYPES) {
			if (name.equals("is" + type.getName())) {
				ensureArgSize(name, args, 1);
				return new IsOfType(args[0], type);
			}
		}
		
		// Other functions
		switch (name) {
		case "pop":
			ensureArgSize(name, args, 1);
			return new StackPop(args[0]);
		case "push":
			ensureArgSize(name, args, 2);
			return new StackPush(args[0], args[1]);
		case "peek":
			ensureArgSize(name, args, 1);
			return new StackPeek(args[0]);

		case "add":
			ensureArgSize(name, args, 2);
			return new SetAdd(args[0], args[1]);
		case "contains":
			ensureArgSize(name, args, 2);
			return new SetContains(args[0], args[1]);
		case "remove":
			ensureArgSize(name, args, 2);
			return new SetRemove(args[0], args[1]);

		case "put":
			ensureArgSize(name, args, 3);
			return new MapPut(args[0], args[1], args[2]);
		case "get":
			ensureArgSize(name, args, 2);
			return new MapGet(args[0], args[1]);
		case "containskey":
			ensureArgSize(name, args, 2);
			return new MapContainsKey(args[0], args[1]);
		case "removekey":
			ensureArgSize(name, args, 2);
			return new MapRemoveKey(args[0], args[1]);

		case "append":
			ensureArgSize(name, args, 2);
			return new ListAppend(args[0], args[1]);
		case "replace":
			ensureArgSize(name, args, 3);
			return new ListReplace(args[0], args[1], args[2]);
		case "valueat":
			ensureArgSize(name, args, 2);
			return new ListValueAt(args[0], args[1]);
		
		case "isdefined":
			ensureArgSize(name, args, 1);
			return new IsDefined(args[0]);
		case "abs":
			ensureArgSize(name, args, 1);
			return new Abs(args[0]);
		case "min":
			return new Min(args);
		case "max":
			return new Max(args);
		case "newset":
			ensureArgSize(name, args, 0);
			return new ConstructorExpression("newSet()", () -> new PersistentSet<Object>());
		case "newmap":
			ensureArgSize(name, args, 0);
			return new ConstructorExpression("newSet()", () -> new PersistentMap<Object, Object>());
		case "newstack":
			ensureArgSize(name, args, 0);
			return new ConstructorExpression("newSet()", () -> new PersistentStack<Object>());
		case "newlist":
			ensureArgSize(name, args, 0);
			return new ConstructorExpression("newSet()", () -> new PersistentList<Object>());
		case "parseint":
			ensureArgSize(name, args, 1);
			return new ParseInt(args[0]);
		case "size":
			ensureArgSize(name, args, 1);
			return new Size(args[0]);
		case "substring":
			ensureArgSize(name, args, 3);
			return new SubString(args[0], args[1], args[2]);
		case "rank":
			ensureArgSize(name, args, 1);
			return new RankExpr(args[0]);
		case "array":
			ensureArgSize(name, args, 1, 2);
			return args.length == 1? new ArrayInitExpression(args[0], null): new ArrayInitExpression(args[0], args[1]);
		case "toarray":
			ensureArgSize(name, args, 1);
			return new ToArray(args[0]);
		case "inttochar":
			ensureArgSize(name, args, 1);
			return new IntToChar(args[0]);
			
		case "chartoint":
			ensureArgSize(name, args, 1);
			return new CharToInt(args[0]);
		case "readfile":
			ensureArgSize(name, args, 1);
			return new ReadFile(args[0]);
		}
		return null; 
	}
	
	private void ensureArgSize(String name, AbstractExpression[] args, int ... argSizes) {
		for (int argSize: argSizes) {
			if (args.length == argSize) {
				return;
			}
		}
		throw new IllegalArgumentException("The " + name + " function expects " + 
				Arrays.stream(argSizes).mapToObj(i -> "" + i).collect(Collectors.joining(" or ")) +
				" arguments, not " + args.length);
	}
	
}
