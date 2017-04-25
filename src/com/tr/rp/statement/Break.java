package com.tr.rp.statement;

import com.tr.rp.core.DStatement;
import com.tr.rp.core.LanguageElement;
import com.tr.rp.core.VarStore;
import com.tr.rp.core.rankediterators.RankedIterator;

/**
 * Terminates execution with run time exception
 */
public class Break implements DStatement {

	private String message;
	
	public Break(String message) {
		this.message = message;
	}
	
	@Override
	public RankedIterator<VarStore> getIterator(final RankedIterator<VarStore> in) {
		return new RankedIterator<VarStore>() {

			@Override
			public boolean next() {
				throw new RuntimeException(message);
			}

			@Override
			public VarStore getVarStore() {
				return null;
			}

			@Override
			public int getRank() {
				return 0;
			}
		};
	}
	
	public String toString() {
		return "Break :" + message;
	}
	
	public boolean equals(Object o) {
		return o instanceof Break && ((Break)o).message.equals(message);
	}

	@Override
	public boolean containsVariable(String var) {
		return false;
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return this;
	}

}
