package com.tr.rp.statement;

import com.tr.rp.core.DStatement;
import com.tr.rp.core.VarStore;
import com.tr.rp.core.rankediterators.RankTransformIterator;
import com.tr.rp.core.rankediterators.RankedIterator;
import com.tr.rp.expressions.num.IntLiteral;
import com.tr.rp.expressions.num.NumExpression;
import com.tr.rp.expressions.num.Var;

public class Assign implements DStatement {

	private NumExpression exp;
	private String var;
	
	public Assign(String var, NumExpression exp) {
		this.var = var;
		this.exp = exp;
	}
	
	public Assign(String var, int value) {
		this(var, new IntLiteral(value));
	}

	public Assign(String var1, String var2) {
		this(var1, new Var(var2));
	}

	@Override
	public RankedIterator<VarStore> getIterator(final RankedIterator<VarStore> in) {
		RankTransformIterator<NumExpression> rt = 
				new RankTransformIterator<NumExpression>(in, this.exp);
		NumExpression exp2 = rt.getExpression();
		return new RankedIterator<VarStore>() {

			@Override
			public boolean next() {
				return rt.next();
			}

			@Override
			public VarStore getItem() {
				if (rt.getItem() == null) return null;
				return rt.getItem().create(var, exp2.getVal(rt.getItem()));
			}

			@Override
			public int getRank() {
				return rt.getRank();
			}
		};
	}
	
	public String toString() {
		return var + " := " + exp;
	}
	
}
