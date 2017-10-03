package com.tr.rp.ast.statements;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import com.tr.rp.ast.AbstractStatement;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.ast.expressions.AssignmentTargetTerminal;
import com.tr.rp.ast.expressions.IsSet;
import com.tr.rp.base.ExecutionContext;
import com.tr.rp.base.Rank;
import com.tr.rp.base.State;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.exceptions.RPLInterruptedException;
import com.tr.rp.exceptions.RPLMiscException;
import com.tr.rp.executors.Executor;
import com.tr.rp.executors.InterruptableExecutor;
import com.tr.rp.executors.RestrictExecutor;

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
	public Executor getExecutor(Executor out, ExecutionContext c) {

		// Handle interrupt
		Executor e = new InterruptableExecutor(out, c::isInterruptRequested);

		// Apply rank cutoff
		if (c.getRankCutOff() < Rank.MAX) {
			e = new RestrictExecutor(c.getRankCutOff(), e, c::registerCutOffEvent);
		}

		// Second statement
		e = second.getExecutor(e, c);
		
		if (firstContainsReturn) {
			final Executor ex = e;
			e = new Executor() {

				private int shift = -1;
				
				@Override
				public void close() throws RPLException {
					ex.close();
				}

				@Override
				public void push(State s) throws RPLException {
					if (!s.getVarStore().containsVar("$return")) {
						if (shift == -1) {
							shift = s.getRank();
						}
						ex.push(s.shiftDown(shift));
					}
				}
			};
		}

		// Handle interrupt
		e = new InterruptableExecutor(e, c::isInterruptRequested);

		// Apply rank cutoff
		if (c.getRankCutOff() < Rank.MAX) {
			e = new RestrictExecutor(c.getRankCutOff(), e, c::registerCutOffEvent);
		}
		
		// First
		e = first.getExecutor(e, c);
		
		return e;
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
