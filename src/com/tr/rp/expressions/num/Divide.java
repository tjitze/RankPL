package com.tr.rp.expressions.num;

import com.tr.rp.core.VarStore;

public class Divide extends AbstractNumOp {

	public Divide(NumExpression e1, NumExpression e2) {
		super(e1, e2);
	}
	
	public Divide(NumExpression e, String var) {
		this(e, new Var(var));
	}
	
	public Divide(String var, NumExpression e) {
		this(new Var(var), e);
	}
	
	public Divide(NumExpression e, int val) {
		this(e, new IntLiteral(val));
	}
	
	public Divide(int val, NumExpression e) {
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

	@Override
	public AbstractNumOp createInstance(NumExpression e1, NumExpression e2) {
		return new Divide(e1, e2);
	}

	@Override
	public String getOperator() {
		return "/";
	}
	
	@Override
	public boolean hasDefiniteValue() {
		boolean e1Definite = getE1().hasDefiniteValue();
		boolean e2Definite = getE2().hasDefiniteValue();
		if (e1Definite && e2Definite) return true;
		// Division: if numerator is zero, the result is definite
		if (e1Definite && getE1().getDefiniteValue() == 0) return true;
		return false;
	}

}
