package com.tr.rp.expressions.bool;

public class Or extends AbstractBoolOp {

	public Or(BoolExp e1, BoolExp e2) {
		super(e1, e2);
	}

	@Override
	public boolean apply(boolean a, boolean b) {
		return a || b;
	}	
	
	@Override
	protected AbstractBoolOp createInstance(BoolExp b1, BoolExp b2) {
		return new Or(b1, b2);
	}

}
