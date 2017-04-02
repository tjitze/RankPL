package com.tr.rp.expressions.bool;

import com.tr.rp.core.LanguageElement;
import com.tr.rp.core.VarStore;
import com.tr.rp.expressions.num.NumExpression;

/**
 * Abstract class for boolean-valued expressions that 
 * have two integer-valued operands.
 */
public abstract class AbstractNumBoolOp extends BoolExpression {

	public final NumExpression e1, e2;
	
	public AbstractNumBoolOp(NumExpression e1, NumExpression e2) {
		this.e1 = e1;
		this.e2 = e2;
	}

	public abstract boolean apply(int a, int b);
	
	@Override
	public final boolean isTrue(VarStore vs) {
		return apply(e1.getVal(vs), e2.getVal(vs));
	}

	@Override
	public final BoolExpression transformRankExpressions(VarStore v, int rank) {
		NumExpression t1 = e1.transformRankExpressions(v, rank);
		NumExpression t2 = e2.transformRankExpressions(v, rank);
		if (t1 != e1 || t2 != e2) {
			return createInstance(t1, t2);
		}
		return this;
	}

	protected abstract AbstractNumBoolOp createInstance(NumExpression e1, NumExpression e2);

	public final boolean hasRankExpression() {
		return e1.hasRankExpression() || e2.hasRankExpression();
	}

	public abstract String getOperator();
	
	public final String toString() {
		return e1 + getOperator() + e2;
	}

	@Override
	public boolean isContradiction() {
		return e1.hasDefiniteValue() && e2.hasDefiniteValue()
				&& !apply(e1.getDefiniteValue(), e2.getDefiniteValue());
	}

	@Override
	public boolean isTautology() {
		return e1.hasDefiniteValue() && e2.hasDefiniteValue()
				&& apply(e1.getDefiniteValue(), e2.getDefiniteValue());
	}

	public final boolean equals(Object o) {
		return o instanceof AbstractNumBoolOp &&
				((AbstractNumBoolOp)o).e1.equals(e1) &&
				((AbstractNumBoolOp)o).e2.equals(e2) &&
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
}
