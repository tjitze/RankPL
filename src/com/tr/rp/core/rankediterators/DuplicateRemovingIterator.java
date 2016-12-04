package com.tr.rp.core.rankediterators;

import java.util.HashSet;

import com.tr.rp.core.VarStore;

public class DuplicateRemovingIterator implements RankedIterator {

	private final HashSet<VarStore> seen = new HashSet<VarStore>();
	private final RankedIterator in;
	
	public DuplicateRemovingIterator(RankedIterator in) {
		this.in = in;
	}

	@Override
	public boolean next() {
		boolean hasNext = in.next();
		while (hasNext && seen.contains(in.getItem())) {
			hasNext = in.next();
		}
		if (hasNext) {
			seen.add(in.getItem());
			return true;
		}
		return false;
	}

	@Override
	public VarStore getItem() {
		return in.getItem();
	}

	@Override
	public int getRank() {
		return in.getRank();
	}

}
