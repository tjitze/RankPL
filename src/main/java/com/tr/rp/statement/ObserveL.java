package com.tr.rp.statement;

import java.util.ArrayList;
import java.util.Set;
import java.util.List;

import com.tr.rp.core.DStatement;
import com.tr.rp.core.Expression;
import com.tr.rp.core.LanguageElement;
import com.tr.rp.core.ProgramBuilder;
import com.tr.rp.core.VarStore;
import com.tr.rp.core.rankediterators.AbsurdIterator;
import com.tr.rp.core.rankediterators.RankTransformIterator;
import com.tr.rp.core.rankediterators.RankedIterator;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.expressions.AbstractFunctionCall;
import com.tr.rp.expressions.Expressions;
import com.tr.rp.expressions.FunctionCall;
import com.tr.rp.expressions.Literal;
import com.tr.rp.expressions.Not;
import com.tr.rp.expressions.Plus;
import com.tr.rp.expressions.RankExpr;
import com.tr.rp.statement.FunctionCallForm.ExtractedExpression;
import com.tr.rp.tools.Pair;

/**
 * Implements L-conditioning.
 * This is equivalent to:
 * 	if rank(b) <= x then
 *		observe b [x-rank(b)+rank(-b)] observe -b
 *	else
 *		observe -b [rank(b)-x] observe b
 */
public class ObserveL extends DStatement {

	private Expression b;
	private Expression rank;

	public ObserveL(Expression b, int rank) {
		this(b, new Literal<Integer>(rank));
	}
	
	public ObserveL(Expression b, Expression rank) {
		this.b = b;
		this.rank = rank;
	}

	@Override
	public RankedIterator<VarStore> getIterator(RankedIterator<VarStore> in) throws RPLException {
		try {
			Expression rb = new RankExpr(b);
			Expression rnb = new RankExpr(new Not(b));
			// Do rank transformation here
			RankTransformIterator rt = 
					new RankTransformIterator(in, rb, rnb);
			rb = rt.getExpression(0);
			rnb = rt.getExpression(1);
			Expression cond = Expressions.leq(rb, rank);
			Expression r1 = Expressions.minus(Expressions.plus(rank, rnb), rb);
			Expression r2 = Expressions.minus(rb, rank);
			DStatement c1 = new RankedChoice(
					new Observe(b),
					new Observe(new Not(b)),
					r1);
			DStatement c2 = new RankedChoice(
					new Observe(new Not(b)),
					new Observe(b),
					r2);
			DStatement statement = new ProgramBuilder()
					.add(new IfElse(cond, c1, c2))
					.build();
			return statement.getIterator(rt);
		} catch (RPLException e) {
			e.addStatement(this);
			throw e;
		}
	}

	public String toString() {
		String bString = b.toString();
		if (bString.startsWith("(") && bString.endsWith(")")) {
			bString = bString.substring(1, bString.length()-1);
		}
		String rankString = rank.toString();
		if (!(rankString.startsWith("(") && rankString.endsWith(")"))) {
			rankString = "(" + rankString + ")";
		}
		return "observe-l " + rankString + " " + bString;
	}
	
	public boolean equals(Object o) {
		return o instanceof ObserveL &&
				((ObserveL)o).b.equals(b) &&
				((ObserveL)o).rank == rank;
	}	

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return new ObserveL((Expression)this.b.replaceVariable(a, b), (Expression)rank.replaceVariable(a, b));
	}
	
	@Override
	public void getVariables(Set<String> list) {
		b.getVariables(list);
		rank.getVariables(list);
	}

	@Override
	public DStatement rewriteEmbeddedFunctionCalls() {
		ExtractedExpression rewrittenExp = FunctionCallForm.extractFunctionCalls(b);
		ExtractedExpression rewrittenRank = FunctionCallForm.extractFunctionCalls(rank);
		if (rewrittenExp.isRewritten() || rewrittenRank.isRewritten()) {
			return new FunctionCallForm(
					new ObserveL(rewrittenExp.getExpression(), rewrittenRank.getExpression()), 
					rewrittenExp.getAssignments(), 
					rewrittenRank.getAssignments());
		} else {
			return this;
		}
	}

}
