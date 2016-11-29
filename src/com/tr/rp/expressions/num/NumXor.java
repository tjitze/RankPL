package com.tr.rp.expressions.num;

public class NumXor extends AbstractNumOp {

	public NumXor(NumExpression e1, NumExpression e2) {
		super(e1, e2);
	}
	
	public NumXor(NumExpression e, String var) {
		this(e, new Var(var));
	}
	
	public NumXor(String var, NumExpression e) {
		this(new Var(var), e);
	}
	
	public NumXor(NumExpression e, int val) {
		this(e, new IntLiteral(val));
	}
	
	public NumXor(int val, NumExpression e) {
		this(new IntLiteral(val), e);
	}
	
	public NumXor(String var1, String var2) {
		this(new Var(var1), new Var(var2));
	}
	
	public NumXor(String var, int val) {
		this(new Var(var), new IntLiteral(val));
	}
	
	public NumXor(int val, String var) {
		this(new IntLiteral(val), new Var(var));
	}

	@Override
	public int apply(int a, int b) {
		if (a != 0 && b == 0) return 1;
		if (a == 0 && b != 0) return 1;
		return 0;
	}

	@Override
	public AbstractNumOp createInstance(NumExpression e1, NumExpression e2) {
		return new NumXor(e1, e2);
	}

}
