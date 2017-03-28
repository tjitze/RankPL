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
	public boolean isContradiction() {
		return (b1.isContradiction() && b2.isContradiction())
				|| (b1.isTautology() && b2.isTautology());
	}

	@Override
	public boolean isTautology() {
		return (b1.isTautology() && b2.isContradiction())
				|| (b1.isContradiction() && b2.isTautology());
	}

}
