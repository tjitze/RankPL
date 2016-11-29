package com.tr.rp.expressions.bool;

import com.tr.rp.core.Expression;
import com.tr.rp.core.VarStore;

public abstract class BoolExp extends Expression {

	public abstract boolean isTrue(VarStore e);

	public BoolExp negate() {
		return new Not(this);
	}

	public abstract BoolExp transformRankExpressions(VarStore v, int rank);

}
