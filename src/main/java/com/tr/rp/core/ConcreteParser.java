package com.tr.rp.core;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.antlr.v4.runtime.tree.TerminalNode;

import static com.tr.rp.expressions.Expressions.*;

import com.tr.rp.expressions.Abs;
import com.tr.rp.expressions.ArrayConstructExpression;
import com.tr.rp.expressions.ArrayInitExpression;
import com.tr.rp.expressions.AssignmentTarget;
import com.tr.rp.expressions.Conditional;
import com.tr.rp.expressions.Expressions;
import com.tr.rp.expressions.FunctionCall;
import com.tr.rp.expressions.IndexElementExpression;
import com.tr.rp.expressions.InferringFunctionCall;
import com.tr.rp.expressions.Variable;
import com.tr.rp.expressions.IsSet;
import com.tr.rp.expressions.Len;
import com.tr.rp.expressions.Literal;
import com.tr.rp.expressions.Negative;
import com.tr.rp.expressions.Not;
import com.tr.rp.expressions.RankExpr;
import com.tr.rp.parser.RankPLBaseVisitor;
import com.tr.rp.parser.RankPLParser.AbsExprContext;
import com.tr.rp.parser.RankPLParser.Arithmetic1ExpressionContext;
import com.tr.rp.parser.RankPLParser.Arithmetic2ExpressionContext;
import com.tr.rp.parser.RankPLParser.ArrayConstructExprContext;
import com.tr.rp.parser.RankPLParser.ArrayInitExprContext;
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
import com.tr.rp.parser.RankPLParser.IndexContext;
import com.tr.rp.parser.RankPLParser.IndexedExpressionContext;
import com.tr.rp.parser.RankPLParser.IndifferentChoiceStatementContext;
import com.tr.rp.parser.RankPLParser.InferringFunctionCallContext;
import com.tr.rp.parser.RankPLParser.IsSetExprContext;
import com.tr.rp.parser.RankPLParser.LenExprContext;
import com.tr.rp.parser.RankPLParser.LiteralBoolExprContext;
import com.tr.rp.parser.RankPLParser.LiteralIntExpressionContext;
import com.tr.rp.parser.RankPLParser.LiteralStringExprContext;
import com.tr.rp.parser.RankPLParser.MinusExprContext;
import com.tr.rp.parser.RankPLParser.NegateExprContext;
import com.tr.rp.parser.RankPLParser.ObserveStatementContext;
import com.tr.rp.parser.RankPLParser.ObserveJStatementContext;
import com.tr.rp.parser.RankPLParser.ObserveLStatementContext;
import com.tr.rp.parser.RankPLParser.ParExpressionContext;
import com.tr.rp.parser.RankPLParser.PrintStatementContext;
import com.tr.rp.parser.RankPLParser.ProgramContext;
import com.tr.rp.parser.RankPLParser.RangeChoiceStatementContext;
import com.tr.rp.parser.RankPLParser.RankExprContext;
import com.tr.rp.parser.RankPLParser.RankedChoiceStatementContext;
import com.tr.rp.parser.RankPLParser.ReadFileStatementContext;
import com.tr.rp.parser.RankPLParser.ReturnStatementContext;
import com.tr.rp.parser.RankPLParser.SkipStatementContext;
import com.tr.rp.parser.RankPLParser.StatContext;
import com.tr.rp.parser.RankPLParser.StatementSequenceContext;
import com.tr.rp.parser.RankPLParser.SubStringExprContext;
import com.tr.rp.parser.RankPLParser.VariableContext;
import com.tr.rp.parser.RankPLParser.VariableExpressionContext;
import com.tr.rp.parser.RankPLParser.WhileStatementContext;
import com.tr.rp.statement.Assign;
import com.tr.rp.statement.RankedChoice;
import com.tr.rp.statement.ReadFile;
import com.tr.rp.statement.Return;
import com.tr.rp.statement.Composition;
import com.tr.rp.statement.Cut;
import com.tr.rp.statement.ForStatement;
import com.tr.rp.statement.IfElse;
import com.tr.rp.statement.Observe;
import com.tr.rp.statement.ObserveJ;
import com.tr.rp.statement.ObserveL;
import com.tr.rp.statement.PrintStatement;
import com.tr.rp.statement.Program;
import com.tr.rp.statement.RangeChoice;
import com.tr.rp.statement.Skip;
import com.tr.rp.statement.While;

public class ConcreteParser extends RankPLBaseVisitor<LanguageElement> {

	private FunctionScope functionScope = new FunctionScope();
	
	@Override
	public LanguageElement visitAssignmentStatement(AssignmentStatementContext ctx) {
		AssignmentTarget target = (AssignmentTarget)visit(ctx.assignment_target());
		Expression value = (Expression)visit(ctx.exp());
		DStatement s = new Assign(target, value);
		s.setLineNumber(ctx.getStart().getLine());
		return s;
	}

	@Override
	public LanguageElement visitReturnStatement(ReturnStatementContext ctx) {
		Expression e = (Expression)visit(ctx.exp());
		DStatement s = new Return(e);
		s.setLineNumber(ctx.getStart().getLine());
		return s;
	}

	@Override
	public LanguageElement visitPrintStatement(PrintStatementContext ctx) {
		Expression e = (Expression)visit(ctx.exp());
		PrintStatement s = new PrintStatement(e);
		s.setLineNumber(ctx.getStart().getLine());
		return s;
	}

	@Override
	public LanguageElement visitCutStatement(CutStatementContext ctx) {
		Expression e = (Expression)visit(ctx.exp());
		DStatement s = new Cut(e);
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
		Expression[] index = new Expression[ctx.index().size()];
		for (int i = 0; i < index.length; i++) {
			index[i] = (Expression)visit(ctx.index(i));
		}
		return new AssignmentTarget(varName, index);
	}

	@Override
	public LanguageElement visitChoiceAssignmentStatement(ChoiceAssignmentStatementContext ctx) {
		AssignmentTarget target = (AssignmentTarget)visit(ctx.assignment_target());
		Expression value1 = (Expression)visit(ctx.exp(0));
		Expression value2 = (Expression)visit(ctx.exp(2));
		Expression rank = (Expression)visit(ctx.exp(1));
		DStatement s1 = new Assign(target, value1);
		s1.setLineNumber(ctx.exp(0).start.getLine());
		DStatement s2 = new Assign(target, value2);
		s2.setLineNumber(ctx.exp(1).start.getLine());
		DStatement s = new RankedChoice(s1, s2, rank);
		s.setLineNumber(ctx.getStart().getLine());
		return s;
	}

	@Override
	public LanguageElement visitIfStatement(IfStatementContext ctx) {
		Expression boolExpr = (Expression)visit(ctx.exp());
		DStatement a = (DStatement)visit(ctx.stat().get(0));
		DStatement b;
		if (ctx.stat().size() > 1) {
			b = (DStatement)visit(ctx.stat().get(1));
		} else {
			b = new Skip();
		}
		DStatement s = new IfElse(boolExpr, a, b);
		s.setLineNumber(ctx.getStart().getLine());
		return s;

	}

	@Override
	public LanguageElement visitObserveStatement(ObserveStatementContext ctx) {
		Expression boolExpr = (Expression)visit(ctx.exp());
		DStatement s = new Observe(boolExpr);
		s.setLineNumber(ctx.getStart().getLine());
		return s;
	}

	@Override
	public LanguageElement visitObserveJStatement(ObserveJStatementContext ctx) {
		Expression rank;
		Expression boolExpr;
		if (ctx.exp().size() == 1) {
			rank = new Literal<Integer>(1);
			boolExpr = (Expression)visit(ctx.exp(0));
		} else {
			rank = (Expression)visit(ctx.exp(0));
			boolExpr = (Expression)visit(ctx.exp(1));
		}
		DStatement s = new ObserveJ(boolExpr, rank);
		s.setLineNumber(ctx.getStart().getLine());
		return s;
	}

	@Override
	public LanguageElement visitObserveLStatement(ObserveLStatementContext ctx) {
		Expression rank;
		Expression boolExpr;
		if (ctx.exp().size() == 1) {
			rank = new Literal<Integer>(1);
			boolExpr = (Expression)visit(ctx.exp(0));
		} else {
			rank = (Expression)visit(ctx.exp(0));
			boolExpr = (Expression)visit(ctx.exp(1));
		}
		DStatement s = new ObserveL(boolExpr, rank);
		s.setLineNumber(ctx.getStart().getLine());
		return s;
	}

	@Override
	public LanguageElement visitRankedChoiceStatement(RankedChoiceStatementContext ctx) {
		Expression rank = ctx.exp() == null? lit(1): (Expression)visit(ctx.exp());
		DStatement a = (DStatement)visit(ctx.stat().get(0));
		DStatement b = (DStatement)visit(ctx.stat().get(1));
		DStatement s = new RankedChoice(a, b, rank);
		s.setLineNumber(ctx.getStart().getLine());
		return s;
	}

	@Override
	public LanguageElement visitRangeChoiceStatement(RangeChoiceStatementContext ctx) {
		AssignmentTarget target = (AssignmentTarget)visit(ctx.assignment_target());
		Expression a = (Expression)visit(ctx.exp().get(0));
		Expression b = (Expression)visit(ctx.exp().get(1));
		DStatement s = new RangeChoice(target, a, b);
		s.setLineNumber(ctx.getStart().getLine());
		return s;
	}

	@Override
	public LanguageElement visitReadFileStatement(ReadFileStatementContext ctx) {
		AssignmentTarget target = (AssignmentTarget)visit(ctx.assignment_target());
		Expression fileName = (Expression)visit(ctx.exp());
		DStatement s = new ReadFile(target, fileName, ReadFile.InputMethod.OnePerLine);
		s.setLineNumber(ctx.getStart().getLine());
		return s;
	}

	@Override
	public LanguageElement visitIndifferentChoiceStatement(IndifferentChoiceStatementContext ctx) {
		DStatement[] choices = new DStatement[ctx.stat().size()];
		for (int i = 0; i < choices.length; i++) {
			choices[i] = (DStatement)visit(ctx.stat().get(i));
		}
		DStatement s = constructIndifferentChoice(choices, 0);
		s.setLineNumber(ctx.getStart().getLine());
		return s;
	}
	
	public DStatement constructIndifferentChoice(DStatement[] choices, int idx) {
		if (idx >= choices.length) return null;
		DStatement e = choices[idx];
		idx++;
		if (idx == choices.length) {
			return e;
		} else {
			return new RankedChoice(e, constructIndifferentChoice(choices, idx), 0);
		}
	}


	@Override
	public LanguageElement visitSkipStatement(SkipStatementContext ctx) {
		DStatement s = new Skip();
		s.setLineNumber(ctx.getStart().getLine());
		return s;
	}
	
	@Override
	public LanguageElement visitWhileStatement(WhileStatementContext ctx) {
		Expression boolExpr = (Expression)visit(ctx.exp());
		DStatement body = (DStatement)visit(ctx.stat());
		DStatement s = new While(boolExpr, body);
		s.setLineNumber(ctx.getStart().getLine());
		return s;
	}

	@Override
	public LanguageElement visitForStatement(ForStatementContext ctx) {
		Expression forCondition = (Expression)visit(ctx.exp());
		DStatement init = (DStatement)visit(ctx.stat(0));
		DStatement next = (DStatement)visit(ctx.stat(1));
		DStatement body = (DStatement)visit(ctx.stat(2));
		DStatement s = new ForStatement(init, forCondition, next, body);
		s.setLineNumber(ctx.getStart().getLine());
		return s;
	}

	@Override
	public LanguageElement visitArithmetic1Expression(Arithmetic1ExpressionContext ctx) {
		Expression a = (Expression)visit(ctx.expr4());
		if (ctx.expr3() == null) {
			return a;
		} else {
			Expression b = (Expression)visit(ctx.expr3());
			String aop = ctx.aop.getText();
			if (aop.equals("+")) return Expressions.plus(a, b);
			if (aop.equals("-")) return Expressions.minus(a, b);
		}
		throw new RuntimeException("Internal parse error");
	}

	@Override
	public LanguageElement visitArithmetic2Expression(Arithmetic2ExpressionContext ctx) {
		Expression a = (Expression)visit(ctx.expr5());
		if (ctx.expr4() == null) {
			return a;
		} else {
			Expression b = (Expression)visit(ctx.expr4());
			String aop = ctx.aop.getText();
			if (aop.equals("*")) return Expressions.times(a, b);
			if (aop.equals("/")) return Expressions.div(a, b);
			if (aop.equals("%")) return Expressions.mod(a, b);
		}
		throw new RuntimeException("Internal parse error");
	}

	@Override
	public LanguageElement visitIndexedExpression(IndexedExpressionContext ctx) {
		Expression a = (Expression)visit(ctx.expr6());
		if (ctx.index().size() > 0) {
			Expression[] index = new Expression[ctx.index().size()];
			for (int i = 0; i < index.length; i++) {
				index[i] = (Expression)visit(ctx.index(i));
			}
			return new IndexElementExpression(a, index);
		} else {
			return a;
		}
	}

	@Override
	public LanguageElement visitBoolExpression(BoolExpressionContext ctx) {
		Expression a = (Expression)visit(ctx.expr2());
		if (ctx.expr1() == null) {
			return a;
		} else {
			Expression b = (Expression)visit(ctx.expr1());
			String aop = ctx.aop.getText();
			if (aop.equals("&")) return Expressions.and(a, b);
			if (aop.equals("|")) return Expressions.or(a, b);
			if (aop.equals("^")) return Expressions.xor(a, b);
		}
		throw new RuntimeException("Internal parse error");
	}

	@Override
	public LanguageElement visitConditionalExpression(ConditionalExpressionContext ctx) {
		Expression e1 = (Expression)visit(ctx.expr1(0));
		if (ctx.expr1().size() > 1) {
			Expression e2 = (Expression)visit(ctx.expr1(1));
			Expression e3 = (Expression)visit(ctx.expr1(2));
			return new Conditional(e1, e2, e3);
		} else {
			return e1;
		}
	}

	@Override
	public LanguageElement visitIsSetExpr(IsSetExprContext ctx) {
		Expression e = (Expression)visit(ctx.exp());
		return new IsSet(e);
	}

	@Override
	public LanguageElement visitAbsExpr(AbsExprContext ctx) {
		Expression num = (Expression)visit(ctx.exp());
		return new Abs(num);
	}

	@Override
	public LanguageElement visitLenExpr(LenExprContext ctx) {
		Expression e = (Expression)visit(ctx.exp());
		return new Len(e);
	}

	@Override
	public LanguageElement visitSubStringExpr(SubStringExprContext ctx) {
		Expression s = (Expression)visit(ctx.exp(0));
		Expression begin = (Expression)visit(ctx.exp(1));
		Expression end = (Expression)visit(ctx.exp(2));
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
		return new RankExpr((Expression)visit(ctx.exp()));
	}
	
	@Override
	public LanguageElement visitFunctionCall(FunctionCallContext ctx) {
		String functionName = ctx.VAR().toString();
		Expression[] args = new Expression[ctx.exp().size()];
		for (int i = 0; i < args.length; i++) {
			args[i] = (Expression)visit(ctx.exp(i));
		}
		return new FunctionCall(functionName, functionScope, args);
	}
	
	@Override
	public LanguageElement visitInferringFunctionCall(InferringFunctionCallContext ctx) {
		String functionName = ctx.VAR().toString();
		Expression[] args = new Expression[ctx.exp().size()];
		for (int i = 0; i < args.length; i++) {
			args[i] = (Expression)visit(ctx.exp(i));
		}
		return new InferringFunctionCall(functionName, functionScope, args);
	}
	
	@Override
	public LanguageElement visitCompareExpr(CompareExprContext ctx) {
		Expression a = (Expression)visit(ctx.expr3());
		if (ctx.expr2() == null) {
			return a;
		} else {
			String cop = ctx.cop.getText();
			Expression b = (Expression)visit(ctx.expr2());
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
		Expression a = (Expression)visit(ctx.getChild(1));
		return new Not(a);
	}

	@Override
	public LanguageElement visitMinusExpr(MinusExprContext ctx) {
		Expression a = (Expression)visit(ctx.getChild(1));
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
			return new ArrayInitExpression((Expression)visit(ctx.exp(0)), (Expression)visit(ctx.exp(1)));
		} else {
			return new ArrayInitExpression((Expression)visit(ctx.exp(0)), null);		
		}
	}

	@Override
	public LanguageElement visitArrayConstructExpr(ArrayConstructExprContext ctx) {
		Expression[] values = new Expression[ctx.exp().size()];
		for (int i = 0; i < values.length; i++) {
			values[i] = (Expression)visit(ctx.exp(i));
		}
		return new ArrayConstructExpression(values);
	}

	
	public LanguageElement visitParExpression(ParExpressionContext ctx) { 
		return visit(ctx.getChild(1));
	}

	@Override
	public LanguageElement visitProgram(ProgramContext ctx) {
		List<DStatement> statements = new ArrayList<DStatement>();
		for (Functiondef_or_statementContext fsc: ctx.functiondef_or_statement()) {
			if (fsc.stat() != null) {
				DStatement s = (DStatement)visit(fsc.stat());
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
		List<DStatement> statements = new ArrayList<DStatement>();
		for (StatContext sc: ctx.stat()) {
			DStatement s = (DStatement)visit(sc);
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
		List<DStatement> statements = new ArrayList<DStatement>();
		for (StatContext sc: ctx.stat()) {
			DStatement s = (DStatement)visit(sc);
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
