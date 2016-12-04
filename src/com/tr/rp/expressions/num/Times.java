package com.tr.rp.expressions.num;

public class Times extends AbstractNumOp {

	public Times(NumExpression e1, NumExpression e2) {
		super(e1, e2);
	}
	
	public Times(NumExpression e, String var) {
		this(e, new Var(var));
	}
	
	public Times(String var, NumExpression e) {
		this(new Var(var), e);
	}
	
	public Times(NumExpression e, int val) {
		this(e, new IntLiteral(val));
	}
	
	public Times(int val, NumExpression e) {
		this(new IntLiteral(val), e);
	}
	
	public Times(String var1, String var2) {
		this(new Var(var1), new Var(var2));
	}
	
	public Times(String var, int val) {
		this(new Var(var), new IntLiteral(val));
	}
	
	public Times(int val, String var) {
		this(new IntLiteral(val), new Var(var));
	}


	@Override
	public int apply(int a, int b) {
		return a * b;
	}

	@Override
	public AbstractNumOp createInstance(NumExpression e1, NumExpression e2) {
		return new Times(e1, e2);
	}

	@Override
	public String getOperator() {
		return "*";
	}

}
