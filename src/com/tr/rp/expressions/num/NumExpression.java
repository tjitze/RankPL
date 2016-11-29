package com.tr.rp.expressions.num;

import com.tr.rp.core.Expression;
import com.tr.rp.core.VarStore;

public abstract class NumExpression extends Expression {

	public abstract int getVal(VarStore e);

	public abstract NumExpression transformRankExpressions(VarStore v, int rank);

}
