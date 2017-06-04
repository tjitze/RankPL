package com.tr.rp.statement;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import com.tr.rp.core.DStatement;
import com.tr.rp.core.LanguageElement;
import com.tr.rp.core.VarStore;
import com.tr.rp.core.rankediterators.ExecutionContext;
import com.tr.rp.core.rankediterators.RankedIterator;
import com.tr.rp.core.rankediterators.RestrictIterator;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.expressions.Variable;
import com.tr.rp.expressions.IsSet;

/**
 * The Composition statement represents the composition of two other 
 * statements. The second statement is skipped if the first statement
 * sets the $return value (see Return statement class).
 * 
 * The composition statement also handles the rank cut-off which is set
 * in the execution context.
 */
public class Composition extends DStatement {

	private final DStatement first, second;
	
	/**
	 * Construct composition of two or more statements. If more than
	 * two statements are supplied then a nested structure of multiple
	 * compositions is created.
	 * 
	 * @param s Sequence of statements to compose
	 */
	public Composition(DStatement ... s) {
		if (s.length < 2) {
			throw new IllegalArgumentException("Composition requres two or more statements.");
		}
		this.first = s[0];
		if (s.length > 2) {
			this.second = new Composition(Arrays.copyOfRange(s, 1, s.length));
		} else {
			this.second = s[1];
		}
	}
	
	/**
	 * Construct composition of two or more statements. If more than
	 * two statements are supplied then a nested structure of multiple
	 * compositions is created.
	 * 
	 * @param s List of statements to compose
	 */
	public Composition(List<DStatement> statements) {
		this(statements.toArray(new DStatement[0]));
	}

	@Override
	public RankedIterator<VarStore> getIterator(RankedIterator<VarStore> in, ExecutionContext c) throws RPLException {
		// Execute first
		in = first.getIterator(in, c);
		
		// Apply rank cut-off if applicable
		if (c.getRankCutOff() < Integer.MAX_VALUE) {
			in = new RestrictIterator<VarStore>(in, c.getRankCutOff(), new Consumer<Integer>() {
				@Override
				public void accept(Integer t) {
					c.registerCutOffEvent(t);
				}
			});
		}

		// Execute second (but skip if return value is set)
		if (containsReturnStatement(first)) {
			IfElse ifElse = new IfElse(new IsSet(new Variable("$return")), new Skip(), second);
			in = ifElse.getIterator(in, c);
		} else {
			in = second.getIterator(in, c);
		}

		// Apply rank cut-off if applicable
		if (c.getRankCutOff() < Integer.MAX_VALUE) {
			in = new RestrictIterator<VarStore>(in, c.getRankCutOff(), new Consumer<Integer>() {
				@Override
				public void accept(Integer t) {
					c.registerCutOffEvent(t);
				}
			});
		}
		return in;
	}

	private boolean containsReturnStatement(DStatement first2) {
		// TODO: implement this
		return true;
	}

	public String toString() {
		String as = first.toString();
		String bs = second.toString();
		if (as.startsWith("{") && as.endsWith("}")) {
			as = as.substring(1, as.length() - 1);
		}
		if (bs.startsWith("{") && bs.endsWith("}")) {
			bs = bs.substring(1, bs.length() - 1);
		}
		return "{" + as + ";" + bs + "}";
	}
	
	public boolean equals(Object o) {
		return o instanceof Composition &&
				((Composition)o).first.equals(first) &&
				((Composition)o).second.equals(second);
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return new Composition((DStatement)this.first.replaceVariable(a, b),
				(DStatement)this.second.replaceVariable(a, b));
	}

	@Override
	public void getVariables(Set<String> list) {
		first.getVariables(list);
		second.getVariables(list);
	}
	
	@Override
	public DStatement rewriteEmbeddedFunctionCalls() {
		return new Composition(first.rewriteEmbeddedFunctionCalls(), second.rewriteEmbeddedFunctionCalls());
	}
	
	public DStatement getFirst() {
		return first;
	}

	public DStatement getSecond() {
		return second;
	}
}
