package com.tr.rp.expressions.num;

public class Abs extends AbstractNumOp {

	public Abs(NumExpression e1) {
		super(e1, e1);
	}
	
	public Abs(String var) {
		this(new Var(var));
	}
	
	public Abs(int val, NumExpression e) {
		this(new IntLiteral(val));
	}

	@Override
	public int apply(int a, int b) {
		return Math.abs(a);
	}

	@Override
	public AbstractNumOp createInstance(NumExpression e1, NumExpression e2) {
		return new Abs(e1);
	}

	@Override
	public String getOperator() {
		return "abs";
	}

}
