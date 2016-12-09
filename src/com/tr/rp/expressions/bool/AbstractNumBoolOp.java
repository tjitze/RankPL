package com.tr.rp.expressions.bool;

import com.tr.rp.core.VarStore;
import com.tr.rp.expressions.num.NumExpression;

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

	public final boolean getDefiniteValue() {
		return apply(e1.getDefiniteValue(), e2.getDefiniteValue());
	}

	public final boolean hasDefiniteValue() {
		return e1.hasDefiniteValue() && e2.hasDefiniteValue();
	}

}
