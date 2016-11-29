package com.tr.rp.expressions.num;

public class Plus extends AbstractNumOp {

	public Plus(NumExpression e1, NumExpression e2) {
		super(e1, e2);
	}
	
	public Plus(NumExpression e, String var) {
		this(e, new Var(var));
	}
	
	public Plus(String var, NumExpression e) {
		this(new Var(var), e);
	}
	
	public Plus(NumExpression e, int val) {
		this(e, new IntLiteral(val));
	}
	
	public Plus(int val, NumExpression e) {
		this(new IntLiteral(val), e);
	}
	
	public Plus(String var1, String var2) {
		this(new Var(var1), new Var(var2));
	}
	
	public Plus(String var, int val) {
		this(new Var(var), new IntLiteral(val));
	}
	
	public Plus(int val, String var) {
		this(new IntLiteral(val), new Var(var));
	}
	
	@Override
	public int apply(int a, int b) {
		return a + b;
	}
	
	@Override
	public AbstractNumOp createInstance(NumExpression e1, NumExpression e2) {
		return new Plus(e1, e2);
	}


}
