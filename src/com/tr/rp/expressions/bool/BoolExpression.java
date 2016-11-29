package com.tr.rp.expressions.bool;

import com.tr.rp.core.Expression;
import com.tr.rp.core.VarStore;

public abstract class BoolExpression extends Expression {

	public abstract boolean isTrue(VarStore e);

	public BoolExpression negate() {
		return new Not(this);
	}

	public abstract BoolExpression transformRankExpressions(VarStore v, int rank);

}
