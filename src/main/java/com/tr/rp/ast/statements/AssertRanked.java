package com.tr.rp.ast.statements;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.AbstractStatement;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.ast.statements.FunctionCallForm.ExtractedExpression;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.exceptions.RPLMiscException;
import com.tr.rp.exceptions.RPLTypeError;
import com.tr.rp.exec.ExecutionContext;
import com.tr.rp.exec.Executor;
import com.tr.rp.exec.State;
import com.tr.rp.varstore.types.PersistentArray;
import com.tr.rp.varstore.types.Type;

/**
 * The assert-ranked (e, [n₁, e₁], [n₂, e₂], ...) statement is intended for testing purposes. 
 * Checks that e equals e₁ with rank n₁, e₂ with rank n₂, etc. Aborts otherwise. 
 */
public class AssertRanked extends AbstractStatement {

	private AbstractExpression expression;
	private AbstractExpression[] expected;
	
	public AssertRanked(AbstractExpression expression, AbstractExpression[] expected) {
		this.expression = expression;
		this.expected = expected;
	}
	
	@Override
	public Executor getExecutor(Executor out, ExecutionContext c) {
		// Build map expected value -> expected rank
		final Map<Integer, Set<Object>> expectedValues = new LinkedHashMap<Integer, Set<Object>>();
		for (AbstractExpression e: expected) {
			if (!e.hasDefiniteValue()) {
				throw new RuntimeException(
						new RPLMiscException("Expected expression must be a definite expression", this));
			}
			PersistentArray pl;
			try {
				pl = e.getDefiniteValue(Type.ARRAY);
			} catch (RPLException e1) {
				throw new RuntimeException(e1);
			}
			if (pl.size() != 2) {
				throw new RuntimeException(
					new RPLMiscException("Expected list [rank, value]", e));
			}
			Object ro = pl.get(0);
			if (!(ro instanceof Integer)) {
				throw new RuntimeException(new RPLTypeError("integer", ro, e));
			}
			int rank = (Integer)ro;
			Object value = pl.get(1);
			// Store values per rank
			Set<Object> rankValues = expectedValues.get(rank);
			if (rankValues == null) {
				rankValues = new LinkedHashSet<Object>();
				expectedValues.put(rank, rankValues);
			}
			rankValues.add(value);
		}

		return new Executor() {
			private Set<Object> seen = new LinkedHashSet<Object>();
			private Set<Object> found = new LinkedHashSet<Object>();
			private int currentRank = 0;
			
			@Override
			public void close() throws RPLException {
				checkCurrentRank();
				checkRemainder();
				found.clear();
				out.close();
			}

			@Override
			public void push(State s) throws RPLException {
				Object value = expression.getValue(s.getVarStore());
				if (s.getRank() < currentRank) {
					throw new RPLMiscException("Illegal rank order", AssertRanked.this);
				} else {
					if (s.getRank() > currentRank) {
						checkCurrentRank();
						found.clear();
						currentRank = s.getRank();
					}
					if (!seen.contains(value)) {
						found.add(value);
						seen.add(value);
					}
				}
				out.push(s);
			}
			
			private void checkCurrentRank() throws RPLMiscException {
				Set<Object> expected = expectedValues.get(currentRank);
				if (expected == null) expected = new LinkedHashSet<Object>();
				if (!found.equals(expected)) {
					throw new RPLMiscException("Assertion failed at rank " + currentRank + ": expected " + expected + ", found " + found, AssertRanked.this);
				}
				expectedValues.remove(currentRank);
			}
			
			private void checkRemainder() throws RPLMiscException {
				if (!expectedValues.isEmpty()) {
					throw new RPLMiscException("Missing values: " + expectedValues, AssertRanked.this);
				}
			}
		};
	}

	@Override
	public void getVariables(Set<String> list) {
		expression.getVariables(list);
		Arrays.stream(expected).forEach(e -> e.getVariables(list));
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		AbstractExpression newValue = (AbstractExpression)expression.replaceVariable(a, b);
		AbstractExpression[] newExpected = new AbstractExpression[expected.length];
		for (int i = 0; i < expected.length; i++) {
			newExpected[i] = (AbstractExpression)expected[i].replaceVariable(a, b);
		}
		return new AssertRanked(newValue, newExpected);
	}

	@Override
	public AbstractStatement rewriteEmbeddedFunctionCalls() {
		// We're not rewriting the expected value here because it should not 
		// contain function calls (will throw exception in iterator)
		ExtractedExpression rewrittenExpression = FunctionCallForm.extractFunctionCalls(expression);
		if (rewrittenExpression.isRewritten()) {
			return new FunctionCallForm(
					new AssertRanked(rewrittenExpression.getExpression(), expected), rewrittenExpression.getAssignments());
		} else {
			return this;
		}
	}

	@Override
	public void getAssignedVariables(Set<String> variables) {
		// nop
	}
	
	public String toString() {
		return "assert-ranked("+expression+","+
				Arrays.stream(expected).map(e -> e.toString()).collect(Collectors.joining(", ")) + ")";
	}

	@Override
	public boolean equals(Object o) {
		return (o instanceof AssertRanked) &&
				((AssertRanked)o).expression.equals(expression) &&
				Arrays.deepEquals(((AssertRanked)o).expected, expected);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(expression) + Arrays.hashCode(expected);
	}	

}
