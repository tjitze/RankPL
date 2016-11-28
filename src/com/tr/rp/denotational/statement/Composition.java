package com.tr.rp.denotational.statement;

import com.tr.rp.denotational.core.DStatement;
import com.tr.rp.denotational.core.VarStore;
import com.tr.rp.denotational.core.rankediterators.RankedIterator;

public class Composition implements DStatement {

	private DStatement a, b;
	
	public Composition(DStatement a, DStatement b) {
		this.a = a;
		this.b = b;
	}
	
	@Override
	public RankedIterator<VarStore> getIterator(RankedIterator<VarStore> parent) {
		return b.getIterator(a.getIterator(parent));
	}

}
