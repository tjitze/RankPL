package com.tr.rp.expressions.bool;

import com.tr.rp.core.Expression;

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

	@Override
	protected AbstractBoolOp createInstance(BoolExp b1, BoolExp b2) {
		return new And(b1, b2);
	}
}
