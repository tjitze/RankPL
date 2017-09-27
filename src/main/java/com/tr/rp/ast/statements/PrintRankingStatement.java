package com.tr.rp.ast.statements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.AbstractStatement;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.ast.statements.FunctionCallForm.ExtractedExpression;
import com.tr.rp.base.ExecutionContext;
import com.tr.rp.base.State;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.executors.Executor;

/**
 * Print complete marginalized ranking over values of a variable.
 * Used for debug purposes.
 */
public class PrintRankingStatement extends AbstractStatement {
	
	private final AbstractExpression exp;
	
	public PrintRankingStatement(AbstractExpression exp) {
		this.exp = exp;
	}
	
	@Override
	public Executor getExecutor(Executor out, ExecutionContext c) {
		return new Executor() {
			
			private boolean closed = false;
			private LinkedList<State> queue = new LinkedList<State>();
			
			@Override
			public void close() throws RPLException {
				closed = true;
				Map<Integer, List<String>> items = new HashMap<Integer,List<String>>();
				int maxRank = 0;
				System.out.println("Ranking over " + exp + ":");
				outer:
				for (State s: queue) {
					int rank = s.getRank();
					String value = exp.getValue(s.getVarStore()).toString();
					if (rank > maxRank) maxRank = rank;
					if (!items.containsKey(rank)) {
						items.put(rank, new ArrayList<String>());
					}
					for (int i = 0; i <= maxRank; i++) {
						if (items.containsKey(i) && items.get(i).contains(value)) {
							continue outer;
						}
					}
					items.get(rank).add(value);
				}
				for (int i = 0; i <= maxRank; i++) {
					if (items.containsKey(i)) {
						List<String> values = items.get(i);
						if (values.size() > 5) {
							System.out.println("Rank " + i + ": " + items.get(i).subList(0, 5) + " (size: " + values.size() + ")");
						} else {
							System.out.println("Rank " + i + ": " + items.get(i));
						}
					}
				}
				while (!queue.isEmpty()) {
					out.push(queue.removeFirst());
				}
				out.close();
			}

			@Override
			public void push(State s) throws RPLException {
				if (closed) {
					throw new IllegalStateException();
				}
				queue.addLast(s);
			}
			
		};
	}		
	
	public String toString() {
		return "print-ranked("+exp+")";
	}
	
	public boolean equals(Object o) {
		return o instanceof PrintRankingStatement && ((PrintRankingStatement)o).exp.equals(exp);
	}

	@Override
	public int hashCode() {
		return Objects.hash(exp);
	}	

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return new PrintRankingStatement((AbstractExpression)exp.replaceVariable(a, b));
	}

	@Override
	public void getVariables(Set<String> list) {
		exp.getVariables(list);
	}

	@Override
	public AbstractStatement rewriteEmbeddedFunctionCalls() {
		ExtractedExpression rewrittenExp = FunctionCallForm.extractFunctionCalls(exp);
		if (rewrittenExp.isRewritten()) {
			return new FunctionCallForm(new PrintRankingStatement((AbstractExpression)rewrittenExp.getExpression()), rewrittenExp.getAssignments());
		} else {
			return this;
		}
	}	
	
	@Override
	public void getAssignedVariables(Set<String> variables) {
		// nop
	}

}
