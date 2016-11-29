package com.tr.rp.core;

public abstract class DExpression extends Expression {

	public abstract int getVal(VarStore e);

	public abstract DExpression transformRankExpressions(VarStore v, int rank);

}
