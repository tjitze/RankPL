package com.tr.rp.expressions.bool;

import com.tr.rp.core.VarStore;

public abstract class BoolExp {

	public abstract boolean isTrue(VarStore e);

	public BoolExp negate() {
		return new Not(this);
	}

}
