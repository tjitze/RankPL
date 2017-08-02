package com.tr.rp.ast.statements;

import java.util.Objects;
import java.util.Set;

import com.tr.rp.ast.AbstractStatement;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.ast.expressions.AssignmentTarget;
import com.tr.rp.ast.statements.FunctionCallForm.ExtractedExpression;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.iterators.ranked.ExecutionContext;
import com.tr.rp.iterators.ranked.RankTransformIterator;
import com.tr.rp.iterators.ranked.RankedIterator;
import com.tr.rp.varstore.VarStore;
import com.tr.rp.varstore.types.Type;

/**
 * Assign current rank to target
 */
public class CurrentRank extends AbstractStatement {
	
	private final AssignmentTarget target;
	
	public CurrentRank(AssignmentTarget var) {
		this.target = var;
	}

	@Override
	public RankedIterator<VarStore> getIterator(final RankedIterator<VarStore> in, ExecutionContext c) throws RPLException {
		return new RankedIterator<VarStore>() {

			@Override
			public boolean next() throws RPLException {
				return in.next();
			}

			@Override
			public VarStore getItem() throws RPLException {
				return target.assign(in.getItem(), in.getRank());
			}

			@Override
			public int getRank() {
				return in.getRank();
			}
			
		};
	}
	
	
	public String toString() {
		return "CurrentRank(" + target + ")";
	}
	
	public boolean equals(Object o) {
		return o instanceof CurrentRank && ((CurrentRank)o).target.equals(target);
	}

	@Override
	public int hashCode() {
		return Objects.hash(getClass(), target);
	}	

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return new CurrentRank((AssignmentTarget)target.replaceVariable(a, b));
	}

	@Override
	public void getVariables(Set<String> list) {
		target.getVariables(list);
	}

	@Override
	public AbstractStatement rewriteEmbeddedFunctionCalls() {
		ExtractedExpression rewrittenTarget = FunctionCallForm.extractFunctionCalls(target);
		if (rewrittenTarget.isRewritten()) {
			return new FunctionCallForm(
					new CurrentRank((AssignmentTarget)rewrittenTarget.getExpression()), 
						rewrittenTarget.getAssignments());
		} else {
			return this;
		}
	}

	@Override
	public void getAssignedVariables(Set<String> variables) {
		target.getAssignedVariables(variables);
	}	
}
