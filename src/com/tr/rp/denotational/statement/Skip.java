package com.tr.rp.denotational.statement;

import com.tr.rp.denotational.core.DStatement;
import com.tr.rp.denotational.core.VarStore;
import com.tr.rp.denotational.core.rankediterators.RankedIterator;

public class Skip implements DStatement {

	@Override
	public RankedIterator<VarStore> getIterator(RankedIterator<VarStore> parent) {
		return parent;
	}

}
