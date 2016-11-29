package com.tr.rp.expressions.bool;

import com.tr.rp.core.VarStore;
import com.tr.rp.expressions.num.IntLiteral;
import com.tr.rp.expressions.num.NumExpression;
import com.tr.rp.expressions.num.Var;

public class LessThan extends BoolExpression {

	public final NumExpression e1, e2;

	public LessThan(NumExpression e1, NumExpression e2) {
		this.e1 = e1;
		this.e2 = e2;
	}
	
	public LessThan(NumExpression e, String var) {
		this(e, new Var(var));
	}
	
	public LessThan(String var, NumExpression e) {
		this(new Var(var), e);
	}
	
	public LessThan(NumExpression e, int val) {
		this(e, new IntLiteral(val));
	}
	
	public LessThan(int val, NumExpression e) {
		this(new IntLiteral(val), e);
	}
	
	public LessThan(String var1, String var2) {
		this(new Var(var1), new Var(var2));
	}
	
	public LessThan(String var, int val) {
		this(new Var(var), new IntLiteral(val));
	}
	
	public LessThan(int val, String var) {
		this(new IntLiteral(val), new Var(var));
	}

	@Override
	public boolean isTrue(VarStore e) {
		return e1.getVal(e) < e2.getVal(e);
	}

	@Override
	public BoolExpression transformRankExpressions(VarStore v, int rank) {
		NumExpression t1 = e1.transformRankExpressions(v, rank);
		NumExpression t2 = e2.transformRankExpressions(v, rank);
		if (t1 != e1 || t2 != e2) {
			return new LessThan(t1, t2);
		} else {
			return this;
		}
	}

	@Override
	public boolean hasRankExpression() {
		return e1.hasRankExpression() || e2.hasRankExpression();
	}
}
