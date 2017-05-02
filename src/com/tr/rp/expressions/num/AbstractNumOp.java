package com.tr.rp.expressions.num;

import java.util.Set;

import com.tr.rp.core.LanguageElement;
import com.tr.rp.core.VarStore;

/**
 * Abstract class for integer-valued expressions that 
 * have two integer-valued operands.
 */
public abstract class AbstractNumOp extends NumExpression {

	private NumExpression e1;
	private NumExpression e2;

	public AbstractNumOp(NumExpression e1, NumExpression e2) {
		this.e1 = e1;
		this.e2 = e2;
	}
	
	@Override
	public int getVal(VarStore e) {
		return apply(getE1().getVal(e), getE2().getVal(e));
	}

	public abstract int apply(int a, int b);
	

	@Override
	public NumExpression transformRankExpressions(VarStore v, int rank) {
		NumExpression t1 = (NumExpression)e1.transformRankExpressions(v, rank);
		NumExpression t2 = (NumExpression)e2.transformRankExpressions(v, rank);
		if (t1 != getE1() || t2 != getE2()) {
			return createInstance(t1, t2);
		}
		return this;
	}

	public NumExpression getE1() {
		return e1;
	}

	public NumExpression getE2() {
		return e2;
	}	
	
	public abstract AbstractNumOp createInstance(NumExpression e1, NumExpression e2);

	public boolean hasRankExpression() {
		return e1.hasRankExpression() || e2.hasRankExpression();
	}
	
	public abstract String getOperator();
	
	public String toString() {
		return "(" + e1 + " " + getOperator() + " " + e2 + ")";
	}
	
	@Override
	public final int getDefiniteValue() {
		return apply(getE1().getDefiniteValue(), getE2().getDefiniteValue());
	}

	@Override
	public boolean hasDefiniteValue() {
		return getE1().hasDefiniteValue() && getE2().hasDefiniteValue();
	}
	
	public final boolean equals(Object o) {
		return o instanceof AbstractNumOp &&
				((AbstractNumOp)o).e1.equals(e1) &&
				((AbstractNumOp)o).e2.equals(e2) &&
				this.getClass().equals(o.getClass());
	}

	@Override
	public boolean containsVariable(String var) {
		return e1.containsVariable(var) || e2.containsVariable(var);
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return createInstance((NumExpression)e1.replaceVariable(a, b), (NumExpression)e2.replaceVariable(a, b));
	}
	
	@Override
	public FunctionCall getEmbeddedFunctionCall() {
		FunctionCall fc = e1.getEmbeddedFunctionCall();
		if (e1 != null) return fc;
		return e2.getEmbeddedFunctionCall();
	}


	@Override
	public NumExpression replaceEmbeddedFunctionCall(FunctionCall fc, String var) {
		return createInstance(e1.replaceEmbeddedFunctionCall(fc, var), e2.replaceEmbeddedFunctionCall(fc, var));
	}

	@Override
	public void getVariables(Set<String> list) {
		e1.getVariables(list);
		e2.getVariables(list);
	}
}
