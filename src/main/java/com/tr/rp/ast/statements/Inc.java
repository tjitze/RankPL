package com.tr.rp.ast.statements;

import java.util.Objects;
import java.util.Set;

import com.tr.rp.ast.AbstractStatement;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.ast.expressions.AssignmentTarget;
import com.tr.rp.ast.statements.FunctionCallForm.ExtractedExpression;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.exec.ExecutionContext;
import com.tr.rp.exec.Executor;
import com.tr.rp.exec.RankTransformer;
import com.tr.rp.exec.State;
import com.tr.rp.varstore.VarStore;
import com.tr.rp.varstore.types.Type;

/**
 * Increase integer variable by one
 */
public class Inc extends AbstractStatement {
	
	private final AssignmentTarget target;
	
	public Inc(AssignmentTarget var) {
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
					newVarStore = transformTarget.get().assign(s.getVarStore(), 
							transformTarget.get().convertToRHSExpression().getValue(s.getVarStore(), Type.INT) + 1);
				} catch (RPLException e) {
					e.setStatement(Inc.this);
					throw e;
				}
				out.push(newVarStore, s.getRank());
			}
		};
		transformTarget.setOutput(exec, this);
		return transformTarget;
	}	
	
	public String toString() {
		return target.toString() + "++";
	}
	
	public boolean equals(Object o) {
		return o instanceof Inc && ((Inc)o).target.equals(target);
	}

	@Override
	public int hashCode() {
		return Objects.hash(target);
	}	

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return new Inc((AssignmentTarget)target.replaceVariable(a, b));
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
					new Inc((AssignmentTarget)rewrittenTarget.getExpression()), 
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
