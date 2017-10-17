package com.tr.rp.ast.statements;

import java.util.Objects;
import java.util.Set;

import com.tr.rp.ast.AbstractStatement;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.ast.expressions.AssignmentTarget;
import com.tr.rp.ast.statements.FunctionCallForm.ExtractedExpression;
import com.tr.rp.base.ExecutionContext;
import com.tr.rp.base.State;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.executors.Executor;
import com.tr.rp.executors.RankTransformer;
import com.tr.rp.varstore.VarStore;

/**
 * Assign current rank to target
 */
public class CurrentRank extends AbstractStatement {
	
	private final AssignmentTarget target;
	
	public CurrentRank(AssignmentTarget var) {
		this.target = var;
	}

	@Override
	public Executor getExecutor(Executor out, ExecutionContext c) {
		RankTransformer<AssignmentTarget> transformTarget = RankTransformer.create(target);
		Executor exec = new Executor() {
			@Override
			public void close() throws RPLException {
				out.close();
			}

			@Override
			public void push(State s) throws RPLException {
				VarStore newVarStore = null;
				try {
					newVarStore = transformTarget.get().assign(s.getVarStore(), s.getRank());
				} catch (RPLException e) {
					e.setStatement(CurrentRank.this);
					throw e;
				}
				out.push(newVarStore, s.getRank());
			}
		};
		return transformTarget.getExecutor(exec, this);
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
			CurrentRank cr = new CurrentRank((AssignmentTarget)rewrittenTarget.getExpression());
			cr.setLineNumber(getLineNumber());
			return new FunctionCallForm(cr, rewrittenTarget.getAssignments());
		} else {
			return this;
		}
	}

	@Override
	public void getAssignedVariables(Set<String> variables) {
		target.getAssignedVariables(variables);
	}	
}
