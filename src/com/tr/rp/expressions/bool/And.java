package com.tr.rp.expressions.bool;

public class And extends AbstractBoolOp {

	public And(BoolExp e1, BoolExp e2) {
		super(e1, e2);
	}

	@Override
	public boolean apply(boolean a, boolean b) {
		return a && b;
	}

	public And and(BoolExp e1) {
		return new And(this, e1);
	}
}
