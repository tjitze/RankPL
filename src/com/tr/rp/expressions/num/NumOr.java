package com.tr.rp.expressions.num;

public class NumOr extends AbstractNumOp {

	public NumOr(NumExpression e1, NumExpression e2) {
		super(e1, e2);
	}
	
	public NumOr(NumExpression e, String var) {
		this(e, new Var(var));
	}
	
	public NumOr(String var, NumExpression e) {
		this(new Var(var), e);
	}
	
	public NumOr(NumExpression e, int val) {
		this(e, new IntLiteral(val));
	}
	
	public NumOr(int val, NumExpression e) {
		this(new IntLiteral(val), e);
	}
	
	public NumOr(String var1, String var2) {
		this(new Var(var1), new Var(var2));
	}
	
	public NumOr(String var, int val) {
		this(new Var(var), new IntLiteral(val));
	}
	
	public NumOr(int val, String var) {
		this(new IntLiteral(val), new Var(var));
	}

	@Override
	public int apply(int a, int b) {
		return a != 0 || b != 0? 1: 0;
	}
	
	@Override
	public AbstractNumOp createInstance(NumExpression e1, NumExpression e2) {
		return new NumOr(e1, e2);
	}


}
