package com.tr.rp.denotational.expressions.bool;

public class Or extends AbstractBoolOp {

	public Or(BoolExp e1, BoolExp e2) {
		super(e1, e2);
	}

	@Override
	public boolean apply(boolean a, boolean b) {
		return a || b;
	}	
}
