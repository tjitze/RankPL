package com.tr.rp.expressions.num;

public class NumAnd extends AbstractNumOp {

	public NumAnd(NumExpression e1, NumExpression e2) {
		super(e1, e2);
	}
	
	public NumAnd(NumExpression e, String var) {
		this(e, new Var(var));
	}
	
	public NumAnd(String var, NumExpression e) {
		this(new Var(var), e);
	}
	
	public NumAnd(NumExpression e, int val) {
		this(e, new IntLiteral(val));
	}
	
	public NumAnd(int val, NumExpression e) {
		this(new IntLiteral(val), e);
	}
	
	public NumAnd(String var1, String var2) {
		this(new Var(var1), new Var(var2));
	}
	
	public NumAnd(String var, int val) {
		this(new Var(var), new IntLiteral(val));
	}
	
	public NumAnd(int val, String var) {
		this(new IntLiteral(val), new Var(var));
	}

	@Override
	public int apply(int a, int b) {
		return a != 0 && b != 0? 1: 0;
	}

	@Override
	public AbstractNumOp createInstance(NumExpression e1, NumExpression e2) {
		return new NumAnd(e1, e2);
	}

	@Override
	public String getOperator() {
		return "&";
	}

}
