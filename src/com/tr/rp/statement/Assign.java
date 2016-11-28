package com.tr.rp.statement;

import com.tr.rp.core.DExpression;
import com.tr.rp.core.DStatement;
import com.tr.rp.core.VarStore;
import com.tr.rp.core.rankediterators.RankedIterator;
import com.tr.rp.expressions.num.IntLiteral;
import com.tr.rp.expressions.num.Var;

public class Assign implements DStatement {

	private DExpression exp;
	private String var;
	
	public Assign(String var, DExpression exp) {
		this.var = var;
		this.exp = exp;
	}
	
	public Assign(String var, int value) {
		this(var, new IntLiteral(value));
	}

	public Assign(String var1, String var2) {
		this(var1, new Var(var2));
	}

	int rank = 0;

	@Override
	public RankedIterator<VarStore> getIterator(final RankedIterator<VarStore> parent) {
		return new RankedIterator<VarStore>() {

			@Override
			public boolean next() {
				return parent.next();
			}

			@Override
			public VarStore getItem() {
				return parent.getItem().create(var, exp.getVal(parent.getItem()));
			}

			@Override
			public int getRank() {
				return parent.getRank();
			}
		};
	}
	
}
