package com.tr.rp.expressions.bool;

import com.tr.rp.core.DExpression;
import com.tr.rp.core.Expression;
import com.tr.rp.core.VarStore;

public abstract class AbstractBoolOp extends BoolExp {

	public final BoolExp b1, b2;
	
	public AbstractBoolOp(BoolExp e1, BoolExp e2) {
		this.b1 = e1;
		this.b2 = e2;
	}

	public abstract boolean apply(boolean a, boolean b);
	
	@Override
	public boolean isTrue(VarStore e) {
		return apply(b1.isTrue(e), b2.isTrue(e));
	}

	@Override
	public BoolExp transformRankExpressions(VarStore v, int rank) {
		BoolExp t1 = b1.transformRankExpressions(v, rank);
		BoolExp t2 = b2.transformRankExpressions(v, rank);
		if (t1 != b1 || t2 != b2) {
			return createInstance(b1, b2);
		}
		return this;
	}

	protected abstract AbstractBoolOp createInstance(BoolExp e1, BoolExp e2);
}
