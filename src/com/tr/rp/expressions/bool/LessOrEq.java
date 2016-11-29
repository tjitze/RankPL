package com.tr.rp.expressions.bool;

import com.tr.rp.core.Expression;
import com.tr.rp.core.VarStore;
import com.tr.rp.expressions.num.IntLiteral;
import com.tr.rp.expressions.num.NumExpression;
import com.tr.rp.expressions.num.Var;

public class LessOrEq extends BoolExpression {

	public final NumExpression e1, e2;

	public LessOrEq(NumExpression e1, NumExpression e2) {
		this.e1 = e1;
		this.e2 = e2;
	}
	
	public LessOrEq(NumExpression e, String var) {
		this(e, new Var(var));
	}
	
	public LessOrEq(String var, NumExpression e) {
		this(new Var(var), e);
	}
	
	public LessOrEq(NumExpression e, int val) {
		this(e, new IntLiteral(val));
	}
	
	public LessOrEq(int val, NumExpression e) {
		this(new IntLiteral(val), e);
	}
	
	public LessOrEq(String var1, String var2) {
		this(new Var(var1), new Var(var2));
	}
	
	public LessOrEq(String var, int val) {
		this(new Var(var), new IntLiteral(val));
	}
	
	public LessOrEq(int val, String var) {
		this(new IntLiteral(val), new Var(var));
	}

	@Override
	public boolean isTrue(VarStore e) {
		return e1.getVal(e) <= e2.getVal(e);
	}
	@Override
	public BoolExpression transformRankExpressions(VarStore v, int rank) {
		NumExpression t1 = e1.transformRankExpressions(v, rank);
		NumExpression t2 = e2.transformRankExpressions(v, rank);
		if (t1 != e1 || t2 != e2) {
			return new LessOrEq(t1, t2);
		} else {
			return this;
		}
	}


}
