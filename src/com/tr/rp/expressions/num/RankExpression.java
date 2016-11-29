package com.tr.rp.expressions.num;

import com.tr.rp.core.DExpression;
import com.tr.rp.core.VarStore;
import com.tr.rp.expressions.bool.BoolExp;

public class RankExpression extends DExpression {

	private final BoolExp b;
	
	public RankExpression(BoolExp b) {
		this.b = b;
	}

	@Override
	public int getVal(VarStore e) {
		throw new UnsupportedOperationException();
	}

	@Override
	public DExpression transformRankExpressions(VarStore v, int rank) {
		if (b.isTrue(v)) {
			return new IntLiteral(rank);
		} else {
			return this;
		}
	}

	
}
