package com.tr.rp.parser;

import static com.tr.rp.ast.expressions.Expressions.*;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.antlr.v4.runtime.tree.TerminalNode;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.AbstractStatement;
import com.tr.rp.ast.Function;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.ast.expressions.Abs;
import com.tr.rp.ast.expressions.ArrayConstructExpression;
import com.tr.rp.ast.expressions.ArrayInitExpression;
import com.tr.rp.ast.expressions.AssignmentTarget;
import com.tr.rp.ast.expressions.Conditional;
import com.tr.rp.ast.expressions.Expressions;
import com.tr.rp.ast.expressions.FunctionCall;
import com.tr.rp.ast.expressions.IndexElementExpression;
import com.tr.rp.ast.expressions.InferringFunctionCall;
import com.tr.rp.ast.expressions.IsSet;
import com.tr.rp.ast.expressions.Len;
import com.tr.rp.ast.expressions.Literal;
import com.tr.rp.ast.expressions.Max;
import com.tr.rp.ast.expressions.Min;
import com.tr.rp.ast.expressions.Negative;
import com.tr.rp.ast.expressions.Not;
import com.tr.rp.ast.expressions.ParseInt;
import com.tr.rp.ast.expressions.RankExpr;
import com.tr.rp.ast.expressions.Variable;
import com.tr.rp.ast.statements.Assert;
import com.tr.rp.ast.statements.AssertRanked;
import com.tr.rp.ast.statements.Assign;
import com.tr.rp.ast.statements.Composition;
import com.tr.rp.ast.statements.Cut;
import com.tr.rp.ast.statements.Dec;
import com.tr.rp.ast.statements.ForStatement;
import com.tr.rp.ast.statements.IfElse;
import com.tr.rp.ast.statements.Inc;
import com.tr.rp.ast.statements.Observe;
import com.tr.rp.ast.statements.ObserveJ;
import com.tr.rp.ast.statements.ObserveL;
import com.tr.rp.ast.statements.PrintStatement;
import com.tr.rp.ast.statements.Program;
import com.tr.rp.ast.statements.RangeChoice;
import com.tr.rp.ast.statements.RankedChoice;
import com.tr.rp.ast.statements.ReadFile;
import com.tr.rp.ast.statements.Reset;
import com.tr.rp.ast.statements.Return;
import com.tr.rp.ast.statements.Skip;
import com.tr.rp.ast.statements.While;
import com.tr.rp.parser.RankPLBaseVisitor;
import com.tr.rp.parser.RankPLParser.AbsExprContext;
import com.tr.rp.parser.RankPLParser.Arithmetic1ExpressionContext;
import com.tr.rp.parser.RankPLParser.Arithmetic2ExpressionContext;
import com.tr.rp.parser.RankPLParser.ArrayConstructExprContext;
import com.tr.rp.parser.RankPLParser.ArrayInitExprContext;
import com.tr.rp.parser.RankPLParser.AssertRankedStatementContext;
import com.tr.rp.parser.RankPLParser.AssertStatementContext;
import com.tr.rp.parser.RankPLParser.AssignmentStatementContext;
import com.tr.rp.parser.RankPLParser.Assignment_targetContext;
import com.tr.rp.parser.RankPLParser.BoolExpressionContext;
import com.tr.rp.parser.RankPLParser.ChoiceAssignmentStatementContext;
import com.tr.rp.parser.RankPLParser.CompareExprContext;
import com.tr.rp.parser.RankPLParser.ConditionalExpressionContext;
import com.tr.rp.parser.RankPLParser.CutStatementContext;
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
import com.tr.rp.parser.RankPLParser.IsSetExprContext;
import com.tr.rp.parser.RankPLParser.LenExprContext;
import com.tr.rp.parser.RankPLParser.LiteralBoolExprContext;
import com.tr.rp.parser.RankPLParser.LiteralIntExpressionContext;
import com.tr.rp.parser.RankPLParser.LiteralStringExprContext;
import com.tr.rp.parser.RankPLParser.MaxExprContext;
import com.tr.rp.parser.RankPLParser.MinExprContext;
import com.tr.rp.parser.RankPLParser.MinusExprContext;
import com.tr.rp.parser.RankPLParser.NegateExprContext;
import com.tr.rp.parser.RankPLParser.ObserveStatementContext;
import com.tr.rp.parser.RankPLParser.ObserveJStatementContext;
import com.tr.rp.parser.RankPLParser.ObserveLStatementContext;
import com.tr.rp.parser.RankPLParser.ParExpressionContext;
import com.tr.rp.parser.RankPLParser.ParseIntExprContext;
import com.tr.rp.parser.RankPLParser.PrintStatementContext;
import com.tr.rp.parser.RankPLParser.ProgramContext;
import com.tr.rp.parser.RankPLParser.RangeChoiceStatementContext;
import com.tr.rp.parser.RankPLParser.RankExprContext;
import com.tr.rp.parser.RankPLParser.RankedChoiceStatementContext;
import com.tr.rp.parser.RankPLParser.ReadFileStatementContext;
import com.tr.rp.parser.RankPLParser.ResetStatementContext;
import com.tr.rp.parser.RankPLParser.ReturnStatementContext;
import com.tr.rp.parser.RankPLParser.SkipStatementContext;
import com.tr.rp.parser.RankPLParser.StatContext;
import com.tr.rp.parser.RankPLParser.StatementSequenceContext;
import com.tr.rp.parser.RankPLParser.SubStringExprContext;
import com.tr.rp.parser.RankPLParser.VariableContext;
import com.tr.rp.parser.RankPLParser.VariableExpressionContext;
import com.tr.rp.parser.RankPLParser.WhileStatementContext;
import com.tr.rp.ranks.FunctionScope;

public class ConcreteParser extends RankPLBaseVisitor<LanguageElement> {

	private FunctionScope functionScope = new FunctionScope();
	
	@Override
	public LanguageElement visitAssignmentStatement(AssignmentStatementContext ctx) {
		AssignmentTarget target = (AssignmentTarget)visit(ctx.assignment_target());
		AbstractExpression value = (AbstractExpression)visit(ctx.exp());
		AbstractStatement s = new Assign(target, value);
		s.setLineNumber(ctx.getStart().getLine());
		return s;
	}

	@Override
	public LanguageElement visitIncDecStatement(IncDecStatementContext ctx) {
		AssignmentTarget target = (AssignmentTarget)visit(ctx.assignment_target());
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
		AbstractStatement s = new Return(e);
		s.setLineNumber(ctx.getStart().getLine());
		return s;
	}

	@Override
	public LanguageElement visitPrintStatement(PrintStatementContext ctx) {
		AbstractExpression e = (AbstractExpression)visit(ctx.exp());
		PrintStatement s = new PrintStatement(e);
		s.setLineNumber(ctx.getStart().getLine());
		return s;
	}

	@Override
	public LanguageElement visitCutStatement(CutStatementContext ctx) {
		AbstractExpression e = (AbstractExpression)visit(ctx.exp());
		AbstractStatement s = new Cut(e);
		s.setLineNumber(ctx.getStart().getLine());
		return s;
	}

	@Override
	public LanguageElement visitAssertRankedStatement(AssertRankedStatementContext ctx) {
		AbstractExpression arg1 = (AbstractExpression)visit(ctx.exp().get(0));
		AbstractExpression[] argRest = new AbstractExpression[ctx.exp().size() - 1];
		for (int i = 1; i < ctx.exp().size(); i++) {
			argRest[i-1] = (AbstractExpression)visit(ctx.exp().get(i));
		}
		AssertRanked s = new AssertRanked(arg1, argRest);
		s.setLineNumber(ctx.getStart().getLine());
		return s;
	}

	@Override
	public LanguageElement visitAssertStatement(AssertStatementContext ctx) {
		AbstractExpression arg = (AbstractExpression)visit(ctx.exp());
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
		TerminalNode tn = ctx.VAR();
		String varName = tn.toString();
		AbstractExpression[] index = new AbstractExpression[ctx.index().size()];
		for (int i = 0; i < index.length; i++) {
			index[i] = (AbstractExpression)visit(ctx.index(i));
		}
		return new AssignmentTarget(varName, index);
	}

	@Override
	public LanguageElement visitChoiceAssignmentStatement(ChoiceAssignmentStatementContext ctx) {
		AssignmentTarget target = (AssignmentTarget)visit(ctx.assignment_target());
		AbstractExpression value1 = (AbstractExpression)visit(ctx.exp(0));
		AbstractExpression value2 = (AbstractExpression)visit(ctx.exp(2));
		AbstractExpression rank = (AbstractExpression)visit(ctx.exp(1));
		AbstractStatement s1 = new Assign(target, value1);
		s1.setLineNumber(ctx.exp(0).start.getLine());
		AbstractStatement s2 = new Assign(target, value2);
		s2.setLineNumber(ctx.exp(1).start.getLine());
		AbstractStatement s = new RankedChoice(s1, s2, rank);
		s.setLineNumber(ctx.getStart().getLine());
		return s;
	}

	@Override
	public LanguageElement visitIfStatement(IfStatementContext ctx) {
		AbstractExpression boolExpr = (AbstractExpression)visit(ctx.exp());
		AbstractStatement a = (AbstractStatement)visit(ctx.stat().get(0));
		AbstractStatement b;
		if (ctx.stat().size() > 1) {
			b = (AbstractStatement)visit(ctx.stat().get(1));
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
			boolExpr = (AbstractExpression)visit(ctx.exp(0));
		} else {
			rank = (AbstractExpression)visit(ctx.exp(0));
			boolExpr = (AbstractExpression)visit(ctx.exp(1));
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
			boolExpr = (AbstractExpression)visit(ctx.exp(0));
		} else {
			rank = (AbstractExpression)visit(ctx.exp(0));
			boolExpr = (AbstractExpression)visit(ctx.exp(1));
		}
		AbstractStatement s = new ObserveL(boolExpr, rank);
		s.setLineNumber(ctx.getStart().getLine());
		return s;
	}

	@Override
	public LanguageElement visitRankedChoiceStatement(RankedChoiceStatementContext ctx) {
		AbstractExpression rank = ctx.exp() == null? lit(1): (AbstractExpression)visit(ctx.exp());
		AbstractStatement a = (AbstractStatement)visit(ctx.stat().get(0));
		AbstractStatement b = (AbstractStatement)visit(ctx.stat().get(1));
		AbstractStatement s = new RankedChoice(a, b, rank);
		s.setLineNumber(ctx.getStart().getLine());
		return s;
	}

	@Override
	public LanguageElement visitRangeChoiceStatement(RangeChoiceStatementContext ctx) {
		AssignmentTarget target = (AssignmentTarget)visit(ctx.assignment_target());
		AbstractExpression a = (AbstractExpression)visit(ctx.exp().get(0));
		AbstractExpression b = (AbstractExpression)visit(ctx.exp().get(1));
		AbstractStatement s = new RangeChoice(target, a, b);
		s.setLineNumber(ctx.getStart().getLine());
		return s;
	}

	@Override
	public LanguageElement visitReadFileStatement(ReadFileStatementContext ctx) {
		AssignmentTarget target = (AssignmentTarget)visit(ctx.assignment_target());
		AbstractExpression fileName = (AbstractExpression)visit(ctx.exp());
		AbstractStatement s = new ReadFile(target, fileName, ReadFile.InputMethod.NEWLINE_SEPARATED);
		s.setLineNumber(ctx.getStart().getLine());
		return s;
	}

	@Override
	public LanguageElement visitIndifferentChoiceStatement(IndifferentChoiceStatementContext ctx) {
		AbstractStatement[] choices = new AbstractStatement[ctx.stat().size()];
		for (int i = 0; i < choices.length; i++) {
			choices[i] = (AbstractStatement)visit(ctx.stat().get(i));
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
	public LanguageElement visitWhileStatement(WhileStatementContext ctx) {
		AbstractExpression boolExpr = (AbstractExpression)visit(ctx.exp());
		AbstractStatement body = (AbstractStatement)visit(ctx.stat());
		AbstractStatement s = new While(boolExpr, body);
		s.setLineNumber(ctx.getStart().getLine());
		return s;
	}

	@Override
	public LanguageElement visitForStatement(ForStatementContext ctx) {
		AbstractExpression forCondition = (AbstractExpression)visit(ctx.exp());
		AbstractStatement init = (AbstractStatement)visit(ctx.stat(0));
		AbstractStatement next = (AbstractStatement)visit(ctx.stat(1));
		AbstractStatement body = (AbstractStatement)visit(ctx.stat(2));
		AbstractStatement s = new ForStatement(init, forCondition, next, body);
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
			if (aop.equals("+")) return Expressions.plus(a, b);
			if (aop.equals("-")) return Expressions.minus(a, b);
		}
		throw new RuntimeException("Internal parse error");
	}

	@Override
	public LanguageElement visitArithmetic2Expression(Arithmetic2ExpressionContext ctx) {
		AbstractExpression a = (AbstractExpression)visit(ctx.expr5());
		if (ctx.expr4() == null) {
			return a;
		} else {
			AbstractExpression b = (AbstractExpression)visit(ctx.expr4());
			String aop = ctx.aop.getText();
			if (aop.equals("*")) return Expressions.times(a, b);
			if (aop.equals("/")) return Expressions.div(a, b);
			if (aop.equals("%")) return Expressions.mod(a, b);
		}
		throw new RuntimeException("Internal parse error");
	}

	@Override
	public LanguageElement visitIndexedExpression(IndexedExpressionContext ctx) {
		AbstractExpression a = (AbstractExpression)visit(ctx.expr6());
		if (ctx.index().size() > 0) {
			AbstractExpression[] index = new AbstractExpression[ctx.index().size()];
			for (int i = 0; i < index.length; i++) {
				index[i] = (AbstractExpression)visit(ctx.index(i));
			}
			return new IndexElementExpression(a, index);
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
			String aop = ctx.aop.getText();
			if (aop.equals("&") || aop.equals("&&")) return Expressions.and(a, b);
			if (aop.equals("|") || aop.equals("||")) return Expressions.or(a, b);
			if (aop.equals("^")) return Expressions.xor(a, b);
		}
		throw new RuntimeException("Internal parse error");
	}

	@Override
	public LanguageElement visitConditionalExpression(ConditionalExpressionContext ctx) {
		AbstractExpression e1 = (AbstractExpression)visit(ctx.expr1(0));
		if (ctx.expr1().size() > 1) {
			AbstractExpression e2 = (AbstractExpression)visit(ctx.expr1(1));
			AbstractExpression e3 = (AbstractExpression)visit(ctx.expr1(2));
			return new Conditional(e1, e2, e3);
		} else {
			return e1;
		}
	}

	@Override
	public LanguageElement visitIsSetExpr(IsSetExprContext ctx) {
		AbstractExpression e = (AbstractExpression)visit(ctx.exp());
		return new IsSet(e);
	}

	@Override
	public LanguageElement visitAbsExpr(AbsExprContext ctx) {
		AbstractExpression num = (AbstractExpression)visit(ctx.exp());
		return new Abs(num);
	}

	@Override
	public LanguageElement visitParseIntExpr(ParseIntExprContext ctx) {
		AbstractExpression str = (AbstractExpression)visit(ctx.exp());
		return new ParseInt(str);
	}

	@Override
	public LanguageElement visitMinExpr(MinExprContext ctx) {
		AbstractExpression[] args = new AbstractExpression[ctx.exp().size()];
		for (int i = 0; i < args.length; i++) {
			args[i] = (AbstractExpression)visit(ctx.exp(i));
		}
		return new Min(args);
	}

	@Override
	public LanguageElement visitMaxExpr(MaxExprContext ctx) {
		AbstractExpression[] args = new AbstractExpression[ctx.exp().size()];
		for (int i = 0; i < args.length; i++) {
			args[i] = (AbstractExpression)visit(ctx.exp(i));
		}
		return new Max(args);
	}

	@Override
	public LanguageElement visitLenExpr(LenExprContext ctx) {
		AbstractExpression e = (AbstractExpression)visit(ctx.exp());
		return new Len(e);
	}

	@Override
	public LanguageElement visitSubStringExpr(SubStringExprContext ctx) {
		AbstractExpression s = (AbstractExpression)visit(ctx.exp(0));
		AbstractExpression begin = (AbstractExpression)visit(ctx.exp(1));
		AbstractExpression end = (AbstractExpression)visit(ctx.exp(2));
		return Expressions.subString(s, begin, end);
	}

	@Override
	public LanguageElement visitLiteralIntExpression(LiteralIntExpressionContext ctx) {
		String num = ctx.getText();
		return new Literal<Integer>(Integer.parseInt(num));
	}
	
	@Override
	public LanguageElement visitVariableExpression(VariableExpressionContext ctx) {
		return visit(ctx.variable());
	}
	
	@Override
	public LanguageElement visitRankExpr(RankExprContext ctx) {
		return new RankExpr((AbstractExpression)visit(ctx.exp()));
	}
	
	@Override
	public LanguageElement visitFunctionCall(FunctionCallContext ctx) {
		String functionName = ctx.VAR().toString();
		AbstractExpression[] args = new AbstractExpression[ctx.exp().size()];
		for (int i = 0; i < args.length; i++) {
			args[i] = (AbstractExpression)visit(ctx.exp(i));
		}
		return new FunctionCall(functionName, functionScope, args);
	}
	
	@Override
	public LanguageElement visitInferringFunctionCall(InferringFunctionCallContext ctx) {
		String functionName = ctx.VAR().toString();
		AbstractExpression[] args = new AbstractExpression[ctx.exp().size()];
		for (int i = 0; i < args.length; i++) {
			args[i] = (AbstractExpression)visit(ctx.exp(i));
		}
		return new InferringFunctionCall(functionName, functionScope, args);
	}
	
	@Override
	public LanguageElement visitCompareExpr(CompareExprContext ctx) {
		AbstractExpression a = (AbstractExpression)visit(ctx.expr3());
		if (ctx.expr2() == null) {
			return a;
		} else {
			String cop = ctx.cop.getText();
			AbstractExpression b = (AbstractExpression)visit(ctx.expr2());
			if (cop.equals("<")) return Expressions.lt(a, b);
			if (cop.equals("<=")) return Expressions.leq(a, b);
			if (cop.equals("==")) return Expressions.eq(a, b);
			if (cop.equals("!=")) return Expressions.notEquals(a, b);
			if (cop.equals(">")) return Expressions.gt(a, b);
			if (cop.equals(">=")) return Expressions.geq(a, b);
		}
		throw new RuntimeException("Internal parse error");
	}

	@Override
	public LanguageElement visitNegateExpr(NegateExprContext ctx) {
		AbstractExpression a = (AbstractExpression)visit(ctx.getChild(1));
		return new Not(a);
	}

	@Override
	public LanguageElement visitMinusExpr(MinusExprContext ctx) {
		AbstractExpression a = (AbstractExpression)visit(ctx.getChild(1));
		// Simplify negative literal
		if (a instanceof Literal && ((Literal)a).getLiteralValue() instanceof Integer) {
			return new Literal<Integer>(-(int)((Literal)a).getLiteralValue());
		} else {
			return new Negative(a);
		}
	}

	@Override
	public LanguageElement visitLiteralBoolExpr(LiteralBoolExprContext ctx) {
		if (ctx.getText().toLowerCase().equals("true")) {
			return new Literal<Boolean>(true);
		} else {
			return new Literal<Boolean>(false);
		}
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
		return new Literal<String>(s);
	}
	
	@Override
	public LanguageElement visitArrayInitExpr(ArrayInitExprContext ctx) {
		if (ctx.exp().size() == 2) {
			return new ArrayInitExpression((AbstractExpression)visit(ctx.exp(0)), (AbstractExpression)visit(ctx.exp(1)));
		} else {
			return new ArrayInitExpression((AbstractExpression)visit(ctx.exp(0)), null);		
		}
	}

	@Override
	public LanguageElement visitArrayConstructExpr(ArrayConstructExprContext ctx) {
		AbstractExpression[] values = new AbstractExpression[ctx.exp().size()];
		for (int i = 0; i < values.length; i++) {
			values[i] = (AbstractExpression)visit(ctx.exp(i));
		}
		return new ArrayConstructExpression(values);
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
				s = s.rewriteEmbeddedFunctionCalls();
				s.setLineNumber(fsc.stat().getStart().getLine());
				statements.add(s);
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

	public LanguageElement visitStatementSequence(StatementSequenceContext ctx) {
		List<AbstractStatement> statements = new ArrayList<AbstractStatement>();
		for (StatContext sc: ctx.stat()) {
			AbstractStatement s = (AbstractStatement)visit(sc);
			statements.add(s);
		}
		if (statements.isEmpty()) {
			return new Skip();
		} else if (statements.size() == 1) {
			return statements.get(0);
		} else {
			return new Composition(statements);
		}
	}
	
	@Override
	public LanguageElement visitVariable(VariableContext ctx) {
		TerminalNode tn = ctx.VAR();
		String varName = tn.toString();
		return new Variable(varName);
	}
	
	public LanguageElement visitFunctiondef(FunctiondefContext ctx) {
		String functionName = ctx.VAR(0).getText();
		String[] parameters = new String[ctx.VAR().size()-1];
		for (int i = 0; i < parameters.length; i++) {
			parameters[i] = ctx.VAR(i+1).getText();
		}
		Function function = new Function(functionName, parameters);
		function.setLineNumber(ctx.start.getLine());
		List<AbstractStatement> statements = new ArrayList<AbstractStatement>();
		for (StatContext sc: ctx.stat()) {
			AbstractStatement s = (AbstractStatement)visit(sc);
			s = s.rewriteEmbeddedFunctionCalls();
			s.setLineNumber(sc.getStart().getLine());
			statements.add(s);
		}
		if (statements.isEmpty()) {
			function.setBody(new Skip());
		} else if (statements.size() == 1) {
			function.setBody(statements.get(0));
		} else {
			function.setBody(new Composition(statements));
		}
		return function;
	}

}
