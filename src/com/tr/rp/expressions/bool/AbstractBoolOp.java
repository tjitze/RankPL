package com.tr.rp.expressions.bool;

import com.tr.rp.core.LanguageElement;
import com.tr.rp.core.VarStore;

/**
 * Abstract class for boolean-valued expressions that 
 * have two boolean-valued operands.
 */
public abstract class AbstractBoolOp extends BoolExpression {

	public final BoolExpression b1, b2;
	
	public AbstractBoolOp(BoolExpression e1, BoolExpression e2) {
		this.b1 = e1;
		this.b2 = e2;
	}

	public abstract boolean apply(boolean a, boolean b);
	
	@Override
	public boolean isTrue(VarStore e) {
		return apply(b1.isTrue(e), b2.isTrue(e));
	}

	@Override
	public BoolExpression transformRankExpressions(VarStore v, int rank) {
		BoolExpression t1 = b1.transformRankExpressions(v, rank);
		BoolExpression t2 = b2.transformRankExpressions(v, rank);
		if (t1 != b1 || t2 != b2) {
			return createInstance(t1, t2);
		}
		return this;
	}

	protected abstract AbstractBoolOp createInstance(BoolExpression e1, BoolExpression e2);

	public boolean hasRankExpression() {
		return b1.hasRankExpression() || b2.hasRankExpression();
	}

	public abstract String getOperator();
	
	public String toString() {
		return "(" + b1 + getOperator() + b2 + ")";
	}
	
	public final boolean equals(Object o) {
		return o instanceof AbstractBoolOp &&
				((AbstractBoolOp)o).b1.equals(b1) &&
				((AbstractBoolOp)o).b2.equals(b2) &&
				this.getClass().equals(o.getClass());
	}
	

	@Override
	public boolean containsVariable(String var) {
		return b1.containsVariable(var) || b2.containsVariable(var);
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return createInstance((BoolExpression)b1.replaceVariable(a, b), (BoolExpression)b2.replaceVariable(a, b));
	}
}
