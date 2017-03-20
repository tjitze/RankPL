package com.tr.rp.statement;

import com.tr.rp.core.DStatement;
import com.tr.rp.core.VarStore;
import com.tr.rp.core.rankediterators.RankedIterator;

/**
 * The Cut statement removes all alternatives with a rank equal to or higher
 * than the given rank. It can be used to speed up execution, or to implement
 * iterative deepening techniques.
 */
public class Cut implements DStatement {
	
	int rank;
	
	/**
	 * Construct Cut statement with given rank.
	 */
	public Cut(int rank) {
		this.rank = rank;
	}

	@Override
	public RankedIterator getIterator(final RankedIterator in) {
		
		return new RankedIterator() {

			@Override
			public boolean next() {
				boolean next = in.next();
				return next && in.getRank() < rank;
			}

			@Override
			public VarStore getVarStore() {
				return in.getVarStore();
			}

			@Override
			public int getRank() {
				return in.getRank();
			}
			
		};

	}

	public String toString() {
		return "cut " + rank;
	}
	
	public boolean equals(Object o) {
		return o instanceof Cut && ((Cut)o).rank == rank;
	}

}
