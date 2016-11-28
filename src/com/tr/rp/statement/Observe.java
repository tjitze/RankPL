package com.tr.rp.statement;

import com.tr.rp.core.DStatement;
import com.tr.rp.core.VarStore;
import com.tr.rp.core.rankediterators.AbsurdIterator;
import com.tr.rp.core.rankediterators.RankedIterator;
import com.tr.rp.expressions.bool.BoolExp;

public class Observe implements DStatement {

	private BoolExp exp;
	
	public Observe(BoolExp exp) {
		this.exp = exp;
	}

	@Override
	public RankedIterator<VarStore> getIterator(final RankedIterator<VarStore> parent) {
		parent.next();
		VarStore initialV = parent.getItem();
		int initialR = parent.getRank();
		while (!exp.isTrue(initialV)) {
			if (!parent.next()) return new AbsurdIterator<VarStore>();
			initialV = parent.getItem();
			initialR = parent.getRank();
		}
		final VarStore initialVf = initialV;
		final int initialRf = initialR;
		final int conditioningOffset = initialR;
		return new RankedIterator<VarStore>() {
			
			VarStore v = initialVf;
			int r = initialRf;
			
			@Override
			public boolean next() {
				boolean next = parent.next();
				v = parent.getItem();
				r = parent.getRank();
				while (next && !exp.isTrue(v)) {
					next = parent.next();
					v = parent.getItem();
					r = parent.getRank();
				}
				if (!next) {
					v = null;
					r = Integer.MAX_VALUE;
				}
				return next;
			}

			@Override
			public VarStore getItem() {
				return v;
			}

			@Override
			public int getRank() {
				return r - conditioningOffset;
			}
			
			@Override
			public int getConditioningOffset() {
				return conditioningOffset;
			}
		};
	}

	
}
