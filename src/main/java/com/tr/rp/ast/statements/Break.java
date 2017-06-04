package com.tr.rp.ast.statements;

import java.util.Set;

import com.tr.rp.ast.AbstractStatement;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.iterators.ranked.ExecutionContext;
import com.tr.rp.iterators.ranked.RankedIterator;
import com.tr.rp.varstore.VarStore;

/**
 * The break statement terminates execution with a runtime exception.
 */
public class Break extends AbstractStatement {

	private String message;
	
	public Break(String message) {
		this.message = message;
	}
	
	@Override
	public RankedIterator<VarStore> getIterator(final RankedIterator<VarStore> in, ExecutionContext c) throws RPLException {
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
	public LanguageElement replaceVariable(String a, String b) {
		return this;
	}

	@Override
	public void getVariables(Set<String> list) { }

	@Override
	public AbstractStatement rewriteEmbeddedFunctionCalls() {
		return this;
	}

	@Override
	public void getAssignedVariables(Set<String> variables) {
		// nop
	}	

}
