package com.tr.rp.expressions.bool;

import com.tr.rp.core.Expression;
import com.tr.rp.core.VarStore;
import com.tr.rp.expressions.num.NumExpression;

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
			return createInstance(b1, b2);
		}
		return this;
	}

	protected abstract AbstractBoolOp createInstance(BoolExpression e1, BoolExpression e2);

	public boolean hasRankExpression() {
		return b1.hasRankExpression() || b2.hasRankExpression();
	}

	public abstract String getOperator();
	
	public String toString() {
		return b1 + getOperator() + b2;
	}
}
