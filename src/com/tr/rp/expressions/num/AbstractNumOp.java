package com.tr.rp.expressions.num;

import com.tr.rp.core.DExpression;
import com.tr.rp.core.VarStore;

public abstract class AbstractNumOp extends DExpression {

	public final DExpression e1, e2;

	public AbstractNumOp(DExpression e1, DExpression e2) {
		this.e1 = e1;
		this.e2 = e2;
	}
	
	@Override
	public int getVal(VarStore e) {
		return apply(e1.getVal(e), e2.getVal(e));
	}

	public abstract int apply(int a, int b);
}
