package com.tr.rp.statement;

import java.util.LinkedHashSet;

import com.tr.rp.core.DStatement;
import com.tr.rp.core.Rank;
import com.tr.rp.core.VarStore;
import com.tr.rp.core.rankediterators.IteratorSplitter;
import com.tr.rp.core.rankediterators.MergingIterator;
import com.tr.rp.core.rankediterators.RankedIterator;
import com.tr.rp.expressions.bool.BoolExpression;

public class IfElse implements DStatement {

	private BoolExpression exp;
	private DStatement a, b;
	private RankedIterator ia;
	private RankedIterator ib;
	
	public IfElse(BoolExpression exp, DStatement a, DStatement b) {
		this.exp = exp;
		this.a = a;
		this.b = b;
	}
	
	@Override
	public RankedIterator<VarStore> getIterator(RankedIterator<VarStore> parent) {
		final IteratorSplitter<VarStore> split = new IteratorSplitter<VarStore>(parent);
		final RankedIterator<VarStore> ria1 = new Observe(exp).getIterator(split.getA());
		final RankedIterator<VarStore> rib1 = new Observe(exp.negate()).getIterator(split.getB());
		final RankedIterator<VarStore> ria2 = a.getIterator(ria1);
		final RankedIterator<VarStore> rib2 = b.getIterator(rib1);
		final RankedIterator<VarStore> rc = new MergingIterator<VarStore>(ria2, rib2,
				ria1.getConditioningOffset(), ria2.getConditioningOffset());
		return rc;
	}


	
}
