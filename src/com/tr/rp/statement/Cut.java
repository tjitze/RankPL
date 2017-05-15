package com.tr.rp.statement;

import java.util.Set;

import com.tr.rp.core.DStatement;
import com.tr.rp.core.LanguageElement;
import com.tr.rp.core.VarStore;
import com.tr.rp.core.rankediterators.RankedIterator;
import com.tr.rp.exceptions.RPLException;

/**
 * The Cut statement removes all alternatives with a rank equal to or higher
 * than the given rank. It can be used to speed up execution, or to implement
 * iterative deepening techniques.
 */
public class Cut extends DStatement {
	
	private int rank;
	
	/**
	 * Construct Cut statement with given rank.
	 */
	public Cut(int rank) {
		this.rank = rank;
	}

	@Override
	public RankedIterator<VarStore> getIterator(final RankedIterator<VarStore> in) throws RPLException {
		
		return new RankedIterator<VarStore>() {

			@Override
			public boolean next() throws RPLException {
				boolean next = in.next();
				return next && in.getRank() < rank;
			}

			@Override
			public VarStore getItem() throws RPLException {
				return in.getItem();
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

	@Override
	public boolean containsVariable(String var) {
		return false;
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return this;
	}

	@Override
	public void getVariables(Set<String> list) { }

	@Override
	public DStatement rewriteEmbeddedFunctionCalls() {
		return this;
	}
}
