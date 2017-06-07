package com.tr.rp.ast.statements;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;

import com.tr.rp.ast.AbstractStatement;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.ast.expressions.IsSet;
import com.tr.rp.ast.expressions.Variable;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.iterators.ranked.ExecutionContext;
import com.tr.rp.iterators.ranked.InterruptableIterator;
import com.tr.rp.iterators.ranked.RankedIterator;
import com.tr.rp.iterators.ranked.RestrictIterator;
import com.tr.rp.ranks.Rank;
import com.tr.rp.varstore.VarStore;

/**
 * The Composition statement represents the composition of two other 
 * statements. The second statement is skipped if the first statement
 * sets the $return value (see Return statement class).
 * 
 * The composition statement also handles the rank cut-off which is set
 * in the execution context.
 */
public class Composition extends AbstractStatement {

	private final AbstractStatement first, second;
	private final boolean firstContainsReturn;
	
	/**
	 * Construct composition of two or more statements. If more than
	 * two statements are supplied then a nested structure of multiple
	 * compositions is created.
	 * 
	 * @param s Sequence of statements to compose
	 */
	public Composition(AbstractStatement ... s) {
		if (s.length < 2) {
			throw new IllegalArgumentException("Composition requres two or more statements.");
		}
		this.first = s[0];
		if (s.length > 2) {
			this.second = new Composition(Arrays.copyOfRange(s, 1, s.length));
		} else {
			this.second = s[1];
		}
		firstContainsReturn = containsReturnStatement(first);
	}
	
	/**
	 * Construct composition of two or more statements. If more than
	 * two statements are supplied then a nested structure of multiple
	 * compositions is created.
	 * 
	 * @param s List of statements to compose
	 */
	public Composition(List<AbstractStatement> statements) {
		this(statements.toArray(new AbstractStatement[0]));
	}

	@Override
	public RankedIterator<VarStore> getIterator(RankedIterator<VarStore> in, ExecutionContext c) throws RPLException {
		// Execute first
		in = first.getIterator(in, c);
		
		// Handle termination
		in = new InterruptableIterator<VarStore>(in, c::isInterruptRequested);
		
		// Apply rank cut-off if applicable
		if (c.getRankCutOff() < Rank.MAX) {
			in = new RestrictIterator<VarStore>(in, c.getRankCutOff(), new Consumer<Integer>() {
				@Override
				public void accept(Integer t) {
					c.registerCutOffEvent(t);
				}
			});
		}

		// Execute second (but skip if return value is set)
		if (firstContainsReturn) {
			IfElse ifElse = new IfElse(new IsSet(new Variable("$return")), new Skip(), second);
			in = ifElse.getIterator(in, c);
		} else {
			in = second.getIterator(in, c);
		}

		// Apply rank cut-off if applicable
		if (c.getRankCutOff() < Rank.MAX) {
			in = new RestrictIterator<VarStore>(in, c.getRankCutOff(), new Consumer<Integer>() {
				@Override
				public void accept(Integer t) {
					c.registerCutOffEvent(t);
				}
			});
		}

		// Handle termination
		in = new InterruptableIterator<VarStore>(in, c::isInterruptRequested);

		return in;
	}

	private boolean containsReturnStatement(AbstractStatement s) {
		Set<String> assignedVariables = new TreeSet<String>();
		s.getAssignedVariables(assignedVariables);
		return assignedVariables.contains("$return");
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
	public int hashCode() {
		return Objects.hash(first, second);
	}	

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return new Composition((AbstractStatement)this.first.replaceVariable(a, b),
				(AbstractStatement)this.second.replaceVariable(a, b));
	}

	@Override
	public void getVariables(Set<String> list) {
		first.getVariables(list);
		second.getVariables(list);
	}
	
	@Override
	public AbstractStatement rewriteEmbeddedFunctionCalls() {
		return new Composition(first.rewriteEmbeddedFunctionCalls(), second.rewriteEmbeddedFunctionCalls());
	}
	
	public AbstractStatement getFirst() {
		return first;
	}

	public AbstractStatement getSecond() {
		return second;
	}
	
	@Override
	public void getAssignedVariables(Set<String> variables) {
		first.getAssignedVariables(variables);
		second.getAssignedVariables(variables);
	}	

}
