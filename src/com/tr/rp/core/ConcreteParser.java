package com.tr.rp.core;

import java.util.List;
import com.tr.rp.expressions.bool.And;
import com.tr.rp.expressions.bool.BoolExpression;
import com.tr.rp.expressions.bool.BoolLiteral;
import com.tr.rp.expressions.bool.Equals;
import com.tr.rp.expressions.bool.LessOrEq;
import com.tr.rp.expressions.bool.LessThan;
import com.tr.rp.expressions.bool.Not;
import com.tr.rp.expressions.bool.Or;
import com.tr.rp.expressions.num.Divide;
import com.tr.rp.expressions.num.IntLiteral;
import com.tr.rp.expressions.num.Minus;
import com.tr.rp.expressions.num.Mod;
import com.tr.rp.expressions.num.NumExpression;
import com.tr.rp.expressions.num.Plus;
import com.tr.rp.expressions.num.Times;
import com.tr.rp.expressions.num.Var;
import com.tr.rp.parser.DefProgBaseVisitor;
import com.tr.rp.parser.DefProgParser.ArithnumexprContext;
import com.tr.rp.parser.DefProgParser.Assignment_statContext;
import com.tr.rp.parser.DefProgParser.BooleanexprContext;
import com.tr.rp.parser.DefProgParser.BoolexprContext;
import com.tr.rp.parser.DefProgParser.CompareexprContext;
import com.tr.rp.parser.DefProgParser.If_statContext;
import com.tr.rp.parser.DefProgParser.LitboolexprContext;
import com.tr.rp.parser.DefProgParser.LitnumexprContext;
import com.tr.rp.parser.DefProgParser.NegateexprContext;
import com.tr.rp.parser.DefProgParser.NumexprContext;
import com.tr.rp.parser.DefProgParser.Observe_statContext;
import com.tr.rp.parser.DefProgParser.ProgramContext;
import com.tr.rp.parser.DefProgParser.Rank_assign_statContext;
import com.tr.rp.parser.DefProgParser.Skip_statContext;
import com.tr.rp.parser.DefProgParser.StatementContext;
import com.tr.rp.parser.DefProgParser.VarnumexprContext;
import com.tr.rp.parser.DefProgParser.While_statContext;
import com.tr.rp.statement.Assign;
import com.tr.rp.statement.Choose;
import com.tr.rp.statement.Composition;
import com.tr.rp.statement.IfElse;
import com.tr.rp.statement.Observe;
import com.tr.rp.statement.Skip;
import com.tr.rp.statement.While;


public class ConcreteParser extends DefProgBaseVisitor<LanguageElement> {

	@Override
	public LanguageElement visitAssignment_stat(Assignment_statContext ctx) {
		String var = ctx.VAR().getText();
		NumExpression value = (NumExpression)visitNumexpr(ctx.numexpr());
		return new Assign(var, value);
	}

	@Override
	public LanguageElement visitIf_stat(If_statContext ctx) {
		BoolexprContext bctx = ctx.boolexpr();
		BoolExpression boolExpr = (BoolExpression)visitBoolexpr(bctx);
		DStatement a = (DStatement)visitStatement(ctx.statement().get(0));
		DStatement b = (DStatement)visitStatement(ctx.statement().get(1));
		return new IfElse(boolExpr, a, b);
	}

	@Override
	public LanguageElement visitObserve_stat(Observe_statContext ctx) {
		BoolexprContext bctx = ctx.boolexpr();
		BoolExpression boolExpr = (BoolExpression)visitBoolexpr(bctx);
		return new Observe(boolExpr);
	}

	@Override
	public LanguageElement visitRank_assign_stat(Rank_assign_statContext ctx) {
		NumexprContext nctx = ctx.numexpr();
		NumExpression rank = (NumExpression)visitNumexpr(nctx);
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
		BoolExpression boolExpr = (BoolExpression)visitBoolexpr(ctx.boolexpr());
		DStatement a = (DStatement)visitStatement(ctx.statement());
		return new While(boolExpr, a);
	}

	@Override
	public LanguageElement visitArithnumexpr(ArithnumexprContext ctx) {
		String aop = ctx.aop.getText();
		NumExpression a = (NumExpression)visitNumexpr(ctx.numexpr(0));
		NumExpression b = (NumExpression)visitNumexpr(ctx.numexpr(1));
		if (aop.equals("+")) return new Plus(a, b);
		if (aop.equals("-")) return new Minus(a, b);
		if (aop.equals("*")) return new Times(a, b);
		if (aop.equals("/")) return new Divide(a, b);
		if (aop.equals("%")) return new Mod(a, b);
		return null;
	}

	@Override
	public LanguageElement visitLitnumexpr(LitnumexprContext ctx) {
		String num = ctx.getText();
		return new IntLiteral(Integer.parseInt(num));
	}
	
	@Override
	public LanguageElement visitVarnumexpr(VarnumexprContext ctx) {
		String var = ctx.getText();
		return new Var(var);
	}
	
	@Override
	public LanguageElement visitBooleanexpr(BooleanexprContext ctx) {
		String aop = ctx.bop.getText();
		BoolExpression a = (BoolExpression)visitBoolexpr(ctx.boolexpr(0));
		BoolExpression b = (BoolExpression)visitBoolexpr(ctx.boolexpr(1));
		if (aop.equals("&")) return new And(a, b);
		if (aop.equals("|")) return new Or(a, b);
		return null;
	}

	@Override
	public LanguageElement visitCompareexpr(CompareexprContext ctx) {
		String aop = ctx.cop.getText();
		NumExpression a = (NumExpression)visitNumexpr(ctx.numexpr(0));
		NumExpression b = (NumExpression)visitNumexpr(ctx.numexpr(1));
		if (aop.equals("<")) return new LessThan(a, b);
		if (aop.equals("<=")) return new LessOrEq(a, b);
		if (aop.equals("==")) return new Equals(a, b);
		if (aop.equals("!=")) return new Not(new Equals(a, b));
		if (aop.equals(">")) return new LessThan(b, a);
		if (aop.equals(">=")) return new LessOrEq(b, a);
		return null;
	}

	@Override
	public LanguageElement visitNegateexpr(NegateexprContext ctx) {
		BoolExpression a = (BoolExpression)visitBoolexpr(ctx.boolexpr());
		return new Not(a);
	}

	@Override
	public LanguageElement visitLitboolexpr(LitboolexprContext ctx) {
		if (ctx.getText().equals("true")) {
			return new BoolLiteral(true);
		} else {
			return new BoolLiteral(false);
		}
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
