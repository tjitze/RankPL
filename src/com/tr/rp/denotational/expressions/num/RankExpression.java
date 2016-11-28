package com.tr.rp.denotational.expressions.num;

import com.tr.rp.denotational.core.DExpression;
import com.tr.rp.denotational.core.VarStore;
import com.tr.rp.denotational.expressions.bool.BoolExp;

public class RankExpression extends DExpression {

	private BoolExp b;
	
	public RankExpression(BoolExp b) {
		this.b = b;
	}

	@Override
	public int getVal(VarStore e) {
		throw new UnsupportedOperationException();
	}

}
