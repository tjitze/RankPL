package com.tr.rp.denotational.expressions.num;

import com.tr.rp.denotational.core.DExpression;

public class NumXor extends AbstractNumOp {

	public NumXor(DExpression e1, DExpression e2) {
		super(e1, e2);
	}
	
	public NumXor(DExpression e, String var) {
		this(e, new Var(var));
	}
	
	public NumXor(String var, DExpression e) {
		this(new Var(var), e);
	}
	
	public NumXor(DExpression e, int val) {
		this(e, new IntLiteral(val));
	}
	
	public NumXor(int val, DExpression e) {
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

}
