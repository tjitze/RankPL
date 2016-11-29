package com.tr.rp.expressions.num;

import com.tr.rp.core.Expression;
import com.tr.rp.core.VarStore;

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

}
