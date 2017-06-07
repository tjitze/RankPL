package com.tr.rp.ast.statements;

import java.util.Objects;
import java.util.Set;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.AbstractStatement;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.iterators.ranked.ExecutionContext;
import com.tr.rp.iterators.ranked.RankedIterator;
import com.tr.rp.varstore.VarStore;

/**
 * The Cut statement removes all alternatives with a rank equal to or higher
 * than the given rank. It can be used to speed up execution, or to implement
 * iterative deepening techniques.
 */
public class Cut extends AbstractStatement {
	
	private AbstractExpression rank;
	
	/**
	 * Construct Cut statement with given rank.
	 */
	public Cut(AbstractExpression rank) {
		this.rank = rank;
	}

	@Override
	public RankedIterator<VarStore> getIterator(final RankedIterator<VarStore> in, ExecutionContext c) throws RPLException {
		
		return new RankedIterator<VarStore>() {

			@Override
			public boolean next() throws RPLException {
				boolean next = in.next();
				return next && in.getRank() < rank.getIntValue(in.getItem());
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
	public int hashCode() {
		return Objects.hash(rank);
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
