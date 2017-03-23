package com.tr.rp.expressions.bool;

public class Xor extends AbstractBoolOp {

	public Xor(BoolExpression e1, BoolExpression e2) {
		super(e1, e2);
	}

	@Override
	public boolean apply(boolean a, boolean b) {
		return a ^ b;
	}	
	
	@Override
	protected AbstractBoolOp createInstance(BoolExpression b1, BoolExpression b2) {
		return new Xor(b1, b2);
	}
	@Override
	public String getOperator() {
		return "^";
	}

	@Override
	public boolean hasDefiniteValue() {
		boolean b1Definite = b1.hasDefiniteValue();
		boolean b2Definite = b2.hasDefiniteValue();
		if (b1Definite && b2Definite) return true;
		// OR: If one operand is true, the result is definite
		if (b1Definite && b1.getDefiniteValue()) return true;
		if (b2Definite && b2.getDefiniteValue()) return true;
		return false;
	}

}
