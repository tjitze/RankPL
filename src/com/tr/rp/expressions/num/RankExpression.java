package com.tr.rp.expressions.num;

import com.tr.rp.core.VarStore;
import com.tr.rp.expressions.bool.BoolExpression;

/**
 * The Rank expression is an integer-valued expression
 * that has a boolean expression argument. It evaluates
 * to the rank of its argument. This means that its 
 * actual value depends on a ranking function given which
 * it is evaluated. That's what the transformRankExpression
 * method is for. There are cases where an expression can
 * only be used if all rank expressions have been trans-
 * formed, i.e., replaced with int literals.
 */
public class RankExpression extends NumExpression {

	private final BoolExpression b;
	
	public RankExpression(BoolExpression b) {
		this.b = b;
	}

	@Override
	public int getVal(VarStore e) {
		throw new UnsupportedOperationException();
	}

	@Override
	public NumExpression transformRankExpressions(VarStore v, int rank) {
		// b might be contradiction or tautology.
		// If so, we can rewrite it immediately
		if (b.isTautology()) {
			return new IntLiteral(0);
		}
		if (b.isContradiction()) {
			return new IntLiteral(Integer.MAX_VALUE);
		}
		// Otherwise, rewrite to rank if expression is true
		if (v == null || b.isTrue(v)) {
			return new IntLiteral(rank);
		} else {
			return this;
		}
	}

	@Override
	public boolean hasRankExpression() {
		return true;
	}

	public String toString() {
		return "rank("+b+")";
	}

	@Override
	public boolean hasDefiniteValue() {
		return false;
	}

	@Override
	public int getDefiniteValue() {
		return 0;
	}
	
	public boolean equals(Object o) {
		return o instanceof RankExpression && ((RankExpression)o).b.equals(b);
	}
}
