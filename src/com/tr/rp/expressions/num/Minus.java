package com.tr.rp.expressions.num;

public class Minus extends AbstractNumOp {

	public Minus(NumExpression e1, NumExpression e2) {
		super(e1, e2);
	}
	
	public Minus(NumExpression e, String var) {
		this(e, new Var(var));
	}
	
	public Minus(String var, NumExpression e) {
		this(new Var(var), e);
	}
	
	public Minus(NumExpression e, int val) {
		this(e, new IntLiteral(val));
	}
	
	public Minus(int val, NumExpression e) {
		this(new IntLiteral(val), e);
	}
	
	public Minus(String var1, String var2) {
		this(new Var(var1), new Var(var2));
	}
	
	public Minus(String var, int val) {
		this(new Var(var), new IntLiteral(val));
	}
	
	public Minus(int val, String var) {
		this(new IntLiteral(val), new Var(var));
	}

	@Override
	public int apply(int a, int b) {
		return a - b;
	}

	@Override
	public AbstractNumOp createInstance(NumExpression e1, NumExpression e2) {
		return new Minus(e1, e2);
	}

}
