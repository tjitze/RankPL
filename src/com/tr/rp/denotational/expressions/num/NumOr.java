package com.tr.rp.denotational.expressions.num;

import com.tr.rp.denotational.core.DExpression;

public class NumOr extends AbstractNumOp {

	public NumOr(DExpression e1, DExpression e2) {
		super(e1, e2);
	}
	
	public NumOr(DExpression e, String var) {
		this(e, new Var(var));
	}
	
	public NumOr(String var, DExpression e) {
		this(new Var(var), e);
	}
	
	public NumOr(DExpression e, int val) {
		this(e, new IntLiteral(val));
	}
	
	public NumOr(int val, DExpression e) {
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

}
