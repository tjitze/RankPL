package com.tr.rp.expressions.bool;

import com.tr.rp.core.VarStore;

public abstract class AbstractBoolOp extends BoolExp {

	public final BoolExp e1, e2;
	
	public AbstractBoolOp(BoolExp e1, BoolExp e2) {
		this.e1 = e1;
		this.e2 = e2;
	}

	public abstract boolean apply(boolean a, boolean b);
	
	@Override
	public boolean isTrue(VarStore e) {
		return apply(e1.isTrue(e), e2.isTrue(e));
	}

}
