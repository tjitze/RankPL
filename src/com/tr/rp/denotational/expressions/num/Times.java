package com.tr.rp.denotational.expressions.num;

import com.tr.rp.denotational.core.DExpression;

public class Times extends AbstractNumOp {

	public Times(DExpression e1, DExpression e2) {
		super(e1, e2);
	}
	
	public Times(DExpression e, String var) {
		this(e, new Var(var));
	}
	
	public Times(String var, DExpression e) {
		this(new Var(var), e);
	}
	
	public Times(DExpression e, int val) {
		this(e, new IntLiteral(val));
	}
	
	public Times(int val, DExpression e) {
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

}
