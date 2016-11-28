package com.tr.rp.denotational.expressions.num;

import com.tr.rp.denotational.core.DExpression;

public class Plus extends AbstractNumOp {

	public Plus(DExpression e1, DExpression e2) {
		super(e1, e2);
	}
	
	public Plus(DExpression e, String var) {
		this(e, new Var(var));
	}
	
	public Plus(String var, DExpression e) {
		this(new Var(var), e);
	}
	
	public Plus(DExpression e, int val) {
		this(e, new IntLiteral(val));
	}
	
	public Plus(int val, DExpression e) {
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

}
