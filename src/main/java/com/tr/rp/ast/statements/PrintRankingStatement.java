package com.tr.rp.ast.statements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.AbstractStatement;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.ast.expressions.AssignmentTarget;
import com.tr.rp.ast.statements.FunctionCallForm.ExtractedExpression;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.iterators.ranked.BufferingIterator;
import com.tr.rp.iterators.ranked.DuplicateRemovingIterator;
import com.tr.rp.iterators.ranked.ExecutionContext;
import com.tr.rp.iterators.ranked.RankTransformIterator;
import com.tr.rp.iterators.ranked.RankedIterator;
import com.tr.rp.varstore.VarStore;

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
	public RankedIterator<VarStore> getIterator(final RankedIterator<VarStore> in, ExecutionContext c) throws RPLException {
		BufferingIterator<VarStore> b = new BufferingIterator<VarStore>(in);
		Map<Integer, List<String>> items = new HashMap<Integer,List<String>>();
		int maxRank = 0;
		System.out.println("Ranking over " + exp + ":");
		outer:
		while (b.next()) {
			int rank = b.getRank();
			String value = exp.getValue(b.getItem()).toString();
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
		b.reset();
		return b;
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
