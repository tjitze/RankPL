package com.tr.rp.denotational.expressions.num;

import com.tr.rp.denotational.core.DExpression;

public class NumAnd extends AbstractNumOp {

	public NumAnd(DExpression e1, DExpression e2) {
		super(e1, e2);
	}
	
	public NumAnd(DExpression e, String var) {
		this(e, new Var(var));
	}
	
	public NumAnd(String var, DExpression e) {
		this(new Var(var), e);
	}
	
	public NumAnd(DExpression e, int val) {
		this(e, new IntLiteral(val));
	}
	
	public NumAnd(int val, DExpression e) {
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

}
