package com.tr.rp.expressions.num;

import com.tr.rp.core.VarStore;

public class Mod extends AbstractNumOp {

	public Mod(NumExpression e1, NumExpression e2) {
		super(e1, e2);
	}
	
	public Mod(NumExpression e, String var) {
		this(e, new Var(var));
	}
	
	public Mod(String var, NumExpression e) {
		this(new Var(var), e);
	}
	
	public Mod(NumExpression e, int val) {
		this(e, new IntLiteral(val));
	}
	
	public Mod(int val, NumExpression e) {
		this(new IntLiteral(val), e);
	}
	
	public Divide(String var1, String var2) {
		this(new Var(var1), new Var(var2));
	}
	
	public Mod(String var, int val) {
		this(new Var(var), new IntLiteral(val));
	}
	
	public Mod(int val, String var) {
		this(new IntLiteral(val), new Var(var));
	}
	
	@Override
	public int apply(int a, int b) {
		return a % b;
	}

	@Override
	public AbstractNumOp createInstance(NumExpression e1, NumExpression e2) {
		return new Mod(e1, e2);
	}

	@Override
	public String getOperator() {
		return "%";
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
