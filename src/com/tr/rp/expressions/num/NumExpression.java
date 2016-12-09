package com.tr.rp.expressions.num;

import com.tr.rp.core.Expression;
import com.tr.rp.core.VarStore;

public abstract class NumExpression extends Expression<NumExpression> {

	public abstract int getVal(VarStore e);

	public abstract boolean hasDefiniteValue();

	public abstract int getDefiniteValue();

}
