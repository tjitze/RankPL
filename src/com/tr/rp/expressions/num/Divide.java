package com.tr.rp.expressions.num;

import com.tr.rp.core.DExpression;
import com.tr.rp.core.VarStore;

public class Divide extends AbstractNumOp {

	public Divide(DExpression e1, DExpression e2) {
		super(e1, e2);
	}
	
	public Divide(DExpression e, String var) {
		this(e, new Var(var));
	}
	
	public Divide(String var, DExpression e) {
		this(new Var(var), e);
	}
	
	public Divide(DExpression e, int val) {
		this(e, new IntLiteral(val));
	}
	
	public Divide(int val, DExpression e) {
		this(new IntLiteral(val), e);
	}
	
	public Divide(String var1, String var2) {
		this(new Var(var1), new Var(var2));
	}
	
	public Divide(String var, int val) {
		this(new Var(var), new IntLiteral(val));
	}
	
	public Divide(int val, String var) {
		this(new IntLiteral(val), new Var(var));
	}
	
	@Override
	public int apply(int a, int b) {
		return a / b;
	}
	
}
