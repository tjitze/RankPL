package com.tr.rp.denotational.expressions.num;

import com.tr.rp.denotational.core.DExpression;
import com.tr.rp.denotational.core.VarStore;

public class IntLiteral extends DExpression {

	public final int value;
	
	public IntLiteral(int value) {
		this.value = value;
	}

	@Override
	public int getVal(VarStore e) {
		return value;
	}
	
}
