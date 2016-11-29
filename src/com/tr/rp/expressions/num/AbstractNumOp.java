package com.tr.rp.expressions.num;

import com.tr.rp.core.DExpression;
import com.tr.rp.core.Expression;
import com.tr.rp.core.VarStore;

public abstract class AbstractNumOp extends DExpression {

	private DExpression e1;
	private DExpression e2;

	public AbstractNumOp(DExpression e1, DExpression e2) {
		this.e1 = e1;
		this.e2 = e2;
	}
	
	@Override
	public int getVal(VarStore e) {
		return apply(getE1().getVal(e), getE2().getVal(e));
	}

	public abstract int apply(int a, int b);
	

	@Override
	public DExpression transformRankExpressions(VarStore v, int rank) {
		DExpression t1 = (DExpression)e1.transformRankExpressions(v, rank);
		DExpression t2 = (DExpression)e2.transformRankExpressions(v, rank);
		if (t1 != getE1() || t2 != getE2()) {
			return createInstance(t1, t2);
		}
		return this;
	}

	public DExpression getE1() {
		return e1;
	}

	public DExpression getE2() {
		return e2;
	}	
	
	public abstract AbstractNumOp createInstance(DExpression e1, DExpression e2);
}
