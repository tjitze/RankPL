package com.tr.rp.expressions.bool;

import com.tr.rp.core.VarStore;
import com.tr.rp.expressions.num.IntLiteral;
import com.tr.rp.expressions.num.NumExpression;
import com.tr.rp.expressions.num.Var;

public class LessThan extends AbstractNumBoolOp {

	public LessThan(NumExpression e1, NumExpression e2) {
		super(e1, e2);
	}
	
	public LessThan(NumExpression e, String var) {
		this(e, new Var(var));
	}
	
	public LessThan(String var, NumExpression e) {
		this(new Var(var), e);
	}
	
	public LessThan(NumExpression e, int val) {
		this(e, new IntLiteral(val));
	}
	
	public LessThan(int val, NumExpression e) {
		this(new IntLiteral(val), e);
	}
	
	public LessThan(String var1, String var2) {
		this(new Var(var1), new Var(var2));
	}
	
	public LessThan(String var, int val) {
		this(new Var(var), new IntLiteral(val));
	}
	
	public LessThan(int val, String var) {
		this(new IntLiteral(val), new Var(var));
	}

	@Override
	public boolean apply(int a, int b) {
		return a < b;
	}

	@Override
	protected AbstractNumBoolOp createInstance(NumExpression e1, NumExpression e2) {
		return new LessThan(e1, e2);
	}

	@Override
	public String getOperator() {
		return "<";
	}
}
