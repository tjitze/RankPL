package com.tr.rp.expressions.num;

import com.tr.rp.core.VarStore;
import com.tr.rp.expressions.bool.BoolExpression;

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
}
