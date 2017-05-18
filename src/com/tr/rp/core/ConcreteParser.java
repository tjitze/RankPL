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
import com.tr.rp.expressions.Conditional;
import com.tr.rp.expressions.Expressions;
import com.tr.rp.expressions.FunctionCall;
import com.tr.rp.expressions.InferringFunctionCall;
import com.tr.rp.expressions.Variable;
import com.tr.rp.expressions.IsSet;
import com.tr.rp.expressions.Len;
import com.tr.rp.expressions.Literal;
import com.tr.rp.expressions.Negative;
import com.tr.rp.expressions.Not;
import com.tr.rp.expressions.RankExpr;
import com.tr.rp.parser.DefProgBaseVisitor;
import com.tr.rp.parser.DefProgParser.AbsExprContext;
import com.tr.rp.parser.DefProgParser.Arithmetic1ExpressionContext;
import com.tr.rp.parser.DefProgParser.Arithmetic2ExpressionContext;
import com.tr.rp.parser.DefProgParser.ArrayConstructExprContext;
import com.tr.rp.parser.DefProgParser.ArrayInitExprContext;
import com.tr.rp.parser.DefProgParser.Assignment_statContext;
import com.tr.rp.parser.DefProgParser.BoolExpressionContext;
import com.tr.rp.parser.DefProgParser.Choice_assignment_statContext;
import com.tr.rp.parser.DefProgParser.CompareExprContext;
import com.tr.rp.parser.DefProgParser.ConditionalExpressionContext;
import com.tr.rp.parser.DefProgParser.FunctionCallContext;
import com.tr.rp.parser.DefProgParser.FunctiondefContext;
import com.tr.rp.parser.DefProgParser.Functiondef_or_statementContext;
import com.tr.rp.parser.DefProgParser.If_statContext;
import com.tr.rp.parser.DefProgParser.IndexContext;
import com.tr.rp.parser.DefProgParser.Indifferent_choiceContext;
import com.tr.rp.parser.DefProgParser.InferringFunctionCallContext;
import com.tr.rp.parser.DefProgParser.IsSetExprContext;
import com.tr.rp.parser.DefProgParser.LenExprContext;
import com.tr.rp.parser.DefProgParser.LiteralBoolExprContext;
import com.tr.rp.parser.DefProgParser.LiteralIntExpressionContext;
import com.tr.rp.parser.DefProgParser.LiteralStringExprContext;
import com.tr.rp.parser.DefProgParser.MinusExprContext;
import com.tr.rp.parser.DefProgParser.NegateExprContext;
import com.tr.rp.parser.DefProgParser.ObserveContext;
import com.tr.rp.parser.DefProgParser.ObserveJContext;
import com.tr.rp.parser.DefProgParser.ObserveLContext;
import com.tr.rp.parser.DefProgParser.ParExpressionContext;
import com.tr.rp.parser.DefProgParser.ProgramContext;
import com.tr.rp.parser.DefProgParser.Range_choiceContext;
import com.tr.rp.parser.DefProgParser.RankExprContext;
import com.tr.rp.parser.DefProgParser.Ranked_choiceContext;
import com.tr.rp.parser.DefProgParser.Return_statementContext;
import com.tr.rp.parser.DefProgParser.Skip_statContext;
import com.tr.rp.parser.DefProgParser.StatementContext;
import com.tr.rp.parser.DefProgParser.Statement_sequenceContext;
import com.tr.rp.parser.DefProgParser.SubStringExprContext;
import com.tr.rp.parser.DefProgParser.VariableContext;
import com.tr.rp.parser.DefProgParser.VariableExpressionContext;
import com.tr.rp.parser.DefProgParser.While_statContext;
import com.tr.rp.statement.Assign;
import com.tr.rp.statement.RankedChoice;
import com.tr.rp.statement.Return;
import com.tr.rp.statement.Composition;
import com.tr.rp.statement.IfElse;
import com.tr.rp.statement.Observe;
import com.tr.rp.statement.ObserveJ;
import com.tr.rp.statement.ObserveL;
import com.tr.rp.statement.Program;
import com.tr.rp.statement.RangeChoice;
import com.tr.rp.statement.Skip;
import com.tr.rp.statement.While;

public class ConcreteParser extends DefProgBaseVisitor<LanguageElement> {

	private FunctionScope functionScope = new FunctionScope();
	
	@Override
	public LanguageElement visitAssignment_stat(Assignment_statContext ctx) {
		Variable var = (Variable)visit(ctx.variable());
		Expression value = (Expression)visit(ctx.expression());
		return new Assign(var, value);
	}

	@Override
	public LanguageElement visitReturn_statement(Return_statementContext ctx) {
		Expression e = (Expression)visit(ctx.expression());
		return new Return(e);
	}

	@Override
	public LanguageElement visitIndex(IndexContext ctx) {
		return this.visit(ctx.expression());
	}

	@Override
	public LanguageElement visitChoice_assignment_stat(Choice_assignment_statContext ctx) {
		Variable var = (Variable)visit(ctx.variable());
		Expression value1 = (Expression)visit(ctx.expression(0));
		Expression value2 = (Expression)visit(ctx.expression(2));
		Expression rank = (Expression)visit(ctx.expression(1));
		return new RankedChoice(var, value1, value2, rank);
	}

	@Override
	public LanguageElement visitIf_stat(If_statContext ctx) {
		Expression boolExpr = (Expression)visit(ctx.expression());
		DStatement a = (DStatement)visit(ctx.statement().get(0));
		a.setLineNumber(ctx.statement().get(0).getStart().getLine());
		DStatement b = (DStatement)visit(ctx.statement().get(1));
		b.setLineNumber(ctx.statement().get(1).getStart().getLine());
		return new IfElse(boolExpr, a, b);
	}

	@Override
	public LanguageElement visitObserve(ObserveContext ctx) {
		Expression boolExpr = (Expression)visit(ctx.expression());
		return new Observe(boolExpr);
	}

	@Override
	public LanguageElement visitObserveJ(ObserveJContext ctx) {
		Expression rank = (Expression)visit(ctx.expression(0));
		Expression boolExpr = (Expression)visit(ctx.expression(1));
		return new ObserveJ(boolExpr, rank);
	}

	@Override
	public LanguageElement visitObserveL(ObserveLContext ctx) {
		Expression rank = (Expression)visit(ctx.expression(0));
		Expression boolExpr = (Expression)visit(ctx.expression(1));
		return new ObserveL(boolExpr, rank);
	}

	@Override
	public LanguageElement visitRanked_choice(Ranked_choiceContext ctx) {
		Expression rank = ctx.expression() == null? lit(1): (Expression)visit(ctx.expression());
		DStatement a = (DStatement)visit(ctx.statement().get(0));
		a.setLineNumber(ctx.statement().get(0).getStart().getLine());
		DStatement b = (DStatement)visit(ctx.statement().get(1));
		b.setLineNumber(ctx.statement().get(1).getStart().getLine());
		return new RankedChoice(a, b, rank);
	}

	@Override
	public LanguageElement visitRange_choice(Range_choiceContext ctx) {
		Variable var = (Variable)visit(ctx.variable());
		Expression a = (Expression)visit(ctx.expression().get(0));
		Expression b = (Expression)visit(ctx.expression().get(1));
		return new RangeChoice(var.toString(), a, b);
	}

	@Override
	public LanguageElement visitIndifferent_choice(Indifferent_choiceContext ctx) {
		DStatement[] choices = new DStatement[ctx.statement().size()];
		for (int i = 0; i < choices.length; i++) {
			choices[i] = (DStatement)visit(ctx.statement().get(i));
			choices[i].setLineNumber(ctx.statement().get(i).getStart().getLine());
		}
		return constructIndifferentChoice(choices, 0);
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
	public LanguageElement visitSkip_stat(Skip_statContext ctx) {
		return new Skip();
	}
	
	@Override
	public LanguageElement visitWhile_stat(While_statContext ctx) {
		Expression boolExpr = (Expression)visit(ctx.expression());
		DStatement a = (DStatement)visit(ctx.statement());
		a.setLineNumber(ctx.statement().getStart().getLine());
		return new While(boolExpr, a);
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
		Expression condition = (Expression)visit(ctx.expression(0));
		Expression e1 = (Expression)visit(ctx.expression(1));
		Expression e2 = (Expression)visit(ctx.expression(2));
		return new Conditional(condition, e1, e2);
	}

	@Override
	public LanguageElement visitIsSetExpr(IsSetExprContext ctx) {
		Variable var = (Variable)visit(ctx.variable());
		return new IsSet(var);
	}

	@Override
	public LanguageElement visitAbsExpr(AbsExprContext ctx) {
		Expression num = (Expression)visit(ctx.expression());
		return new Abs(num);
	}

	@Override
	public LanguageElement visitLenExpr(LenExprContext ctx) {
		Expression e = (Expression)visit(ctx.expression());
		return new Len(e);
	}

	@Override
	public LanguageElement visitSubStringExpr(SubStringExprContext ctx) {
		Expression s = (Expression)visit(ctx.expression(0));
		Expression begin = (Expression)visit(ctx.expression(1));
		Expression end = (Expression)visit(ctx.expression(2));
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
		return new RankExpr((Expression)visit(ctx.expression()));
	}
	
	@Override
	public LanguageElement visitFunctionCall(FunctionCallContext ctx) {
		String functionName = ctx.VAR().toString();
		Expression[] args = new Expression[ctx.expression().size()];
		for (int i = 0; i < args.length; i++) {
			args[i] = (Expression)visit(ctx.expression(i));
		}
		return new FunctionCall(functionName, functionScope, args);
	}
	
	@Override
	public LanguageElement visitInferringFunctionCall(InferringFunctionCallContext ctx) {
		String functionName = ctx.VAR().toString();
		Expression[] args = new Expression[ctx.expression().size()];
		for (int i = 0; i < args.length; i++) {
			args[i] = (Expression)visit(ctx.expression(i));
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
		String s = ctx.getText().toLowerCase();
		// remove quotes
		s = s.substring(1, s.length()-1);
		// a hack to process escape characters
	    Properties prop = new Properties();     
	    try {
			prop.load(new StringReader("x=" + s + "\n"));
		} catch (IOException e) {
			throw new RuntimeException("Internal parser error: " + e);
		}
	    s = prop.getProperty("x");

		return new Literal<String>(s);
	}
	
	@Override
	public LanguageElement visitArrayInitExpr(ArrayInitExprContext ctx) {
		Expression[] index = new Expression[ctx.index().size()];
		for (int i = 0; i < index.length; i++) {
			index[i] = (Expression)visit(ctx.index(i));
		}
		Expression initValue = ctx.expression() == null? null: (Expression)visit(ctx.expression());
		return new ArrayInitExpression(initValue, index);
	}

	@Override
	public LanguageElement visitArrayConstructExpr(ArrayConstructExprContext ctx) {
		Expression[] values = new Expression[ctx.expression().size()];
		for (int i = 0; i < values.length; i++) {
			values[i] = (Expression)visit(ctx.expression(i));
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
			if (fsc.statement() != null) {
				DStatement s = (DStatement)visit(fsc.statement());
				s = s.rewriteEmbeddedFunctionCalls();
				s.setLineNumber(fsc.statement().getStart().getLine());
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

	public LanguageElement visitStatement_sequence(Statement_sequenceContext ctx) {
		List<DStatement> statements = new ArrayList<DStatement>();
		for (StatementContext sc: ctx.statement()) {
			DStatement s = (DStatement)visit(sc);
			s.setLineNumber(sc.getStart().getLine());
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
		Expression[] index = new Expression[ctx.index().size()];
		for (int i = 0; i < index.length; i++) {
			index[i] = (Expression)visit(ctx.index(i));
		}
		return new Variable(varName, index);
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
		for (StatementContext sc: ctx.statement()) {
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
