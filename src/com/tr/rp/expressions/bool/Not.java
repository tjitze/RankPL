package com.tr.rp.expressions.bool;

import com.tr.rp.core.VarStore;

public class Not extends BoolExp {

	public final BoolExp e;
	
	public Not(BoolExp e) {
		this.e = e;
	}

	@Override
	public boolean isTrue(VarStore env) {
		return !e.isTrue(env);
	}
	
	public BoolExp negate() {
		return e;
	}
}
