package com.tr.rp.core;

import java.util.Arrays;
import java.util.List;
import com.tr.rp.expressions.bool.And;
import com.tr.rp.expressions.bool.BoolExpression;
import com.tr.rp.expressions.bool.BoolLiteral;
import com.tr.rp.expressions.bool.Equals;
import com.tr.rp.expressions.bool.LessOrEq;
import com.tr.rp.expressions.bool.LessThan;
import com.tr.rp.expressions.bool.Not;
import com.tr.rp.expressions.bool.Or;
import com.tr.rp.expressions.bool.Xor;
import com.tr.rp.expressions.num.Divide;
import com.tr.rp.expressions.num.IntLiteral;
import com.tr.rp.expressions.num.Minus;
import com.tr.rp.expressions.num.Mod;
import com.tr.rp.expressions.num.NumAnd;
import com.tr.rp.expressions.num.NumExpression;
import com.tr.rp.expressions.num.NumOr;
import com.tr.rp.expressions.num.NumXor;
import com.tr.rp.expressions.num.Plus;
import com.tr.rp.expressions.num.RankExpression;
import com.tr.rp.expressions.num.Times;
import com.tr.rp.expressions.num.Var;
import com.tr.rp.parser.DefProgBaseVisitor;
import com.tr.rp.parser.DefProgParser;
import com.tr.rp.parser.DefProgParser.ArithmeticNumExprContext;
import com.tr.rp.parser.DefProgParser.Array_assignment_statContext;
import com.tr.rp.parser.DefProgParser.Assignment_statContext;
import com.tr.rp.parser.DefProgParser.BooleanExprContext;
import com.tr.rp.parser.DefProgParser.BoolexprContext;
import com.tr.rp.parser.DefProgParser.Choice_assignment_statContext;
import com.tr.rp.parser.DefProgParser.CompareExprContext;
import com.tr.rp.parser.DefProgParser.If_statContext;
import com.tr.rp.parser.DefProgParser.IndexContext;
import com.tr.rp.parser.DefProgParser.LiteralBoolExprContext;
import com.tr.rp.parser.DefProgParser.LiteralNumExprContext;
import com.tr.rp.parser.DefProgParser.NegateExprContext;
import com.tr.rp.parser.DefProgParser.NumexprContext;
import com.tr.rp.parser.DefProgParser.ObserveContext;
import com.tr.rp.parser.DefProgParser.ObserveJContext;
import com.tr.rp.parser.DefProgParser.ObserveLContext;
import com.tr.rp.parser.DefProgParser.Observe_statContext;
import com.tr.rp.parser.DefProgParser.ProgramContext;
import com.tr.rp.parser.DefProgParser.RankExprContext;
import com.tr.rp.parser.DefProgParser.Ranked_choiceContext;
import com.tr.rp.parser.DefProgParser.Skip_statContext;
import com.tr.rp.parser.DefProgParser.StatementContext;
import com.tr.rp.parser.DefProgParser.VariableNumExprContext;
import com.tr.rp.parser.DefProgParser.While_statContext;
import com.tr.rp.statement.Assign;
import com.tr.rp.statement.Choose;
import com.tr.rp.statement.Composition;
import com.tr.rp.statement.IfElse;
import com.tr.rp.statement.Observe;
import com.tr.rp.statement.ObserveJ;
import com.tr.rp.statement.ObserveL;
import com.tr.rp.statement.Skip;
import com.tr.rp.statement.While;


public class ConcreteParser extends DefProgBaseVisitor<LanguageElement> {

	@Override
	public LanguageElement visitAssignment_stat(Assignment_statContext ctx) {
		String var = ctx.VAR().getText();
		NumExpression[] index = new NumExpression[ctx.index().size()];
		for (int i = 0; i < index.length; i++) {
			index[i] = (NumExpression)visit(ctx.index(i));
		}
		NumExpression value = (NumExpression)visit(ctx.numexpr());
		return new Assign(var, index, value);
	}

	@Override
	public LanguageElement visitIndex(IndexContext ctx) {
		return this.visit(ctx.numexpr());
	}
	
	@Override
	public LanguageElement visitArray_assignment_stat(Array_assignment_statContext ctx) {
		String var = ctx.VAR().getText();
		NumExpression[] index = new NumExpression[ctx.index().size()];
		for (int i = 0; i < index.length; i++) {
			index[i] = (NumExpression)visit(ctx.index(i));
		}
		ProgramBuilder b = new ProgramBuilder();
		for (int i = 0; i < ctx.numexpr().size(); i++) {
			NumExpression[] subIndex = Arrays.copyOf(index, index.length + 1);
			subIndex[index.length] = new IntLiteral(i);
			b.add(new Assign(var, subIndex, (NumExpression)visit(ctx.numexpr(i))));
		}
		return b.build();
	}

	@Override
	public LanguageElement visitChoice_assignment_stat(Choice_assignment_statContext ctx) {
		String var = ctx.VAR().getText();
		NumExpression[] index = new NumExpression[ctx.index().size()];
		for (int i = 0; i < index.length; i++) {
			index[i] = (NumExpression)visit(ctx.index(i));
		}
		NumExpression value1 = (NumExpression)visit(ctx.numexpr(0));
		NumExpression value2 = (NumExpression)visit(ctx.numexpr(2));
		NumExpression rank = (NumExpression)visit(ctx.numexpr(1));
		return new Choose(var, value1, value2, rank);
	}

	@Override
	public LanguageElement visitIf_stat(If_statContext ctx) {
		BoolexprContext bctx = ctx.boolexpr();
		BoolExpression boolExpr = (BoolExpression)visit(bctx);
		DStatement a = (DStatement)visitStatement(ctx.statement().get(0));
		DStatement b = (DStatement)visitStatement(ctx.statement().get(1));
		return new IfElse(boolExpr, a, b);
	}

	@Override
	public LanguageElement visitObserve(ObserveContext ctx) {
		BoolExpression boolExpr = (BoolExpression)visit(ctx.boolexpr());
		return new Observe(boolExpr);
	}

	@Override
	public LanguageElement visitObserveJ(ObserveJContext ctx) {
		NumExpression rank = (NumExpression)visit(ctx.numexpr());
		BoolExpression boolExpr = (BoolExpression)visit(ctx.boolexpr());
		return new ObserveJ(boolExpr, rank);
	}

	@Override
	public LanguageElement visitObserveL(ObserveLContext ctx) {
		NumExpression rank = (NumExpression)visit(ctx.numexpr());
		BoolExpression boolExpr = (BoolExpression)visit(ctx.boolexpr());
		return new ObserveL(boolExpr, rank);
	}

	@Override
	public LanguageElement visitRanked_choice(Ranked_choiceContext ctx) {
		NumexprContext nctx = ctx.numexpr();
		NumExpression rank = (NumExpression)visit(nctx);
		DStatement a = (DStatement)visitStatement(ctx.statement().get(0));
		DStatement b = (DStatement)visitStatement(ctx.statement().get(1));
		return new Choose(a, b, rank);
	}

	@Override
	public LanguageElement visitSkip_stat(Skip_statContext ctx) {
		return new Skip();
	}
	
	@Override
	public LanguageElement visitWhile_stat(While_statContext ctx) {
		BoolExpression boolExpr = (BoolExpression)visit(ctx.boolexpr());
		DStatement a = (DStatement)visitStatement(ctx.statement());
		return new While(boolExpr, a);
	}

	@Override
	public LanguageElement visitArithmeticNumExpr(ArithmeticNumExprContext ctx) {
		String aop = ctx.aop.getText();
		NumExpression a = (NumExpression)visit(ctx.numexpr(0));
		NumExpression b = (NumExpression)visit(ctx.numexpr(1));
		if (aop.equals("+")) return new Plus(a, b);
		if (aop.equals("-")) return new Minus(a, b);
		if (aop.equals("*")) return new Times(a, b);
		if (aop.equals("/")) return new Divide(a, b);
		if (aop.equals("%")) return new Mod(a, b);
		if (aop.equals("&")) return new NumAnd(a, b);
		if (aop.equals("|")) return new NumOr(a, b);
		if (aop.equals("^")) return new NumXor(a, b);
		return null;
	}

	@Override
	public LanguageElement visitLiteralNumExpr(LiteralNumExprContext ctx) {
		String num = ctx.getText();
		return new IntLiteral(Integer.parseInt(num));
	}
	
	@Override
	public LanguageElement visitVariableNumExpr(VariableNumExprContext ctx) {
		String var = ctx.VAR().getText();
		NumExpression[] index = new NumExpression[ctx.index().size()];
		for (int i = 0; i < index.length; i++) {
			index[i] = (NumExpression)visit(ctx.index(i));
		}
		return new Var(var, index);
	}
	
	@Override
	public LanguageElement visitRankExpr(RankExprContext ctx) {
		return new RankExpression((BoolExpression)visit(ctx.boolexpr()));
	}
	
	@Override
	public LanguageElement visitBooleanExpr(BooleanExprContext ctx) {
		String aop = ctx.bop.getText();
		BoolExpression a = (BoolExpression)visit(ctx.boolexpr(0));
		BoolExpression b = (BoolExpression)visit(ctx.boolexpr(1));
		if (aop.equals("&")) return new And(a, b);
		if (aop.equals("|")) return new Or(a, b);
		if (aop.equals("^")) return new Xor(a, b);
		return null;
	}

	@Override
	public LanguageElement visitCompareExpr(CompareExprContext ctx) {
		String aop = ctx.cop.getText();
		NumExpression a = (NumExpression)this.visit(ctx.numexpr(0));
		NumExpression b = (NumExpression)visit(ctx.numexpr(1));
		if (aop.equals("<")) return new LessThan(a, b);
		if (aop.equals("<=")) return new LessOrEq(a, b);
		if (aop.equals("==")) return new Equals(a, b);
		if (aop.equals("!=")) return new Not(new Equals(a, b));
		if (aop.equals(">")) return new LessThan(b, a);
		if (aop.equals(">=")) return new LessOrEq(b, a);
		return null;
	}

	@Override
	public LanguageElement visitNegateExpr(NegateExprContext ctx) {
		BoolExpression a = (BoolExpression)visit(ctx.getChild(1));
		return new Not(a);
	}

	@Override
	public LanguageElement visitLiteralBoolExpr(LiteralBoolExprContext ctx) {
		if (ctx.getText().toLowerCase().equals("true")) {
			return new BoolLiteral(true);
		} else {
			return new BoolLiteral(false);
		}
	}
	
	public LanguageElement visitParboolExpr(DefProgParser.ParboolExprContext ctx) { 
		return visit(ctx.getChild(1));
	}

	public LanguageElement visitParNumExpr(DefProgParser.ParNumExprContext ctx) { 
		return visit(ctx.getChild(1));
	}

	@Override
	public LanguageElement visitProgram(ProgramContext ctx) {
		return createList(ctx.statement(), 0);
	}

	public DStatement createList(List<StatementContext> sc, int idx) {
		if (idx >= sc.size()) return null;
		DStatement s = (DStatement)visit(sc.get(idx));
		idx++;
		if (idx == sc.size()) {
			return s;
		} else {
			return new Composition(s, createList(sc, idx));
		}
	}
}
