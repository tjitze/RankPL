package com.tr.rp.expressions.bool;

public class Or extends AbstractBoolOp {

	public Or(BoolExpression e1, BoolExpression e2) {
		super(e1, e2);
	}

	@Override
	public boolean apply(boolean a, boolean b) {
		return a || b;
	}	
	
	@Override
	protected AbstractBoolOp createInstance(BoolExpression b1, BoolExpression b2) {
		return new Or(b1, b2);
	}
	@Override
	public String getOperator() {
		return "|";
	}
	
	@Override
	public boolean isContradiction() {
		return b1.isContradiction() && b2.isContradiction();
	}

	@Override
	public boolean isTautology() {
		return b1.isTautology() || b2.isTautology();
	}


}
