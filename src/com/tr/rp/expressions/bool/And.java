package com.tr.rp.expressions.bool;

import com.tr.rp.core.Expression;

public class And extends AbstractBoolOp {

	public And(BoolExpression e1, BoolExpression e2) {
		super(e1, e2);
	}

	@Override
	public boolean apply(boolean a, boolean b) {
		return a && b;
	}

	public And and(BoolExpression e1) {
		return new And(this, e1);
	}

	@Override
	protected AbstractBoolOp createInstance(BoolExpression b1, BoolExpression b2) {
		return new And(b1, b2);
	}

	@Override
	public String getOperator() {
		return "&&";
	}

//	@Override
//	public boolean hasDefiniteValue() {
//		boolean b1Definite = b1.hasDefiniteValue();
//		boolean b2Definite = b2.hasDefiniteValue();
//		if (b1Definite && b2Definite) return true;
//		if (b1Definite && !b1.getDefiniteValue()) return true;
//		if (b2Definite && !b2.getDefiniteValue()) return true;
//		return false;
//	}

}
