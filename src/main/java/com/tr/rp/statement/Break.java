package com.tr.rp.statement;

import java.util.Set;

import com.tr.rp.core.DStatement;
import com.tr.rp.core.LanguageElement;
import com.tr.rp.core.VarStore;
import com.tr.rp.core.rankediterators.RankedIterator;
import com.tr.rp.exceptions.RPLException;

/**
 * The break statement terminates execution with a runtime exception.
 */
public class Break extends DStatement {

	private String message;
	
	public Break(String message) {
		this.message = message;
	}
	
	@Override
	public RankedIterator<VarStore> getIterator(final RankedIterator<VarStore> in) throws RPLException {
		return new RankedIterator<VarStore>() {

			@Override
			public boolean next() throws RPLException {
				throw new RuntimeException(message);
			}

			@Override
			public VarStore getItem() throws RPLException {
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

	@Override
	public void getVariables(Set<String> list) { }

	@Override
	public DStatement rewriteEmbeddedFunctionCalls() {
		return this;
	}

}
