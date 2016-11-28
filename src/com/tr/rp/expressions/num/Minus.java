package com.tr.rp.expressions.num;

import com.tr.rp.core.DExpression;

public class Minus extends AbstractNumOp {

	public Minus(DExpression e1, DExpression e2) {
		super(e1, e2);
	}
	
	public Minus(DExpression e, String var) {
		this(e, new Var(var));
	}
	
	public Minus(String var, DExpression e) {
		this(new Var(var), e);
	}
	
	public Minus(DExpression e, int val) {
		this(e, new IntLiteral(val));
	}
	
	public Minus(int val, DExpression e) {
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

}
