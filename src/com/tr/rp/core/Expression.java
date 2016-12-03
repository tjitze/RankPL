package com.tr.rp.core;

import com.tr.rp.expressions.num.NumExpression;

public abstract class Expression<T extends Expression> {

	public abstract boolean hasRankExpression();

	public abstract T transformRankExpressions(VarStore v, int rank);
	
	public T transformRankExpressions(int rank) {
		return this.transformRankExpressions(null, rank);
	}
}
