package com.tr.rp.denotational.expressions.bool;

import com.tr.rp.denotational.core.VarStore;

public abstract class BoolExp {

	public abstract boolean isTrue(VarStore e);

	public BoolExp negate() {
		return new Not(this);
	}

}
