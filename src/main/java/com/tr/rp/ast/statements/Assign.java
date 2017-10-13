package com.tr.rp.ast.statements;

import java.util.Objects;
import java.util.Set;

import com.tr.rp.ast.AbstractExpression;
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
 * The assign statement takes as input a variable, an (optional) list
 * of array indices, and an expression. 
 * 
 * If the list of array indices is empty, the effect of this statement
 * is that the given variable is assigned the value of the given 
 * expression. If the list of array indices is nonempty, the effect is
 * that the array associated with the variable name (indexed by the 
 * array indices) is assigned the value of the given expression.
 */
public class Assign extends AbstractStatement {
	
	private final AssignmentTarget target;
	private final AbstractExpression value;
	private AbstractStatement exceptionSource;
	
	public Assign(AssignmentTarget target, AbstractExpression exp) {
		this.target = target;
		this.value = exp;
		this.exceptionSource = this;
	}

	public Assign(AssignmentTarget target, AbstractExpression exp, AbstractStatement exceptionSource) {
		this.target = target;
		this.value = exp;
		this.exceptionSource = exceptionSource;
	}
	
	public void setExceptionSource(AbstractStatement exceptionSource) {
		this.exceptionSource = exceptionSource;
	}
	
	@Override
	public Executor getExecutor(Executor out, ExecutionContext c) {
		RankTransformer<AssignmentTarget> transformTarget = RankTransformer.create(target);
		RankTransformer<AbstractExpression> transformValue = RankTransformer.create(value);
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
							transformValue.get().getValue(s.getVarStore()));				
				} catch (RPLException e) {
					e.setStatement(exceptionSource);
					throw e;
				}
				out.push(newVarStore, s.getRank());
			}
		};
		transformTarget.setOutput(transformValue, this);
		transformValue.setOutput(exec, this);
		return transformTarget;
	}		
	
	public String toString() {
		String varString = target.toString();
		String expString = value.toString();
		if (expString.startsWith("(") && expString.endsWith(")")) {
			expString = expString.substring(1, expString.length()-1);
		}
		return varString + " := " + expString;
	}
	
	public boolean equals(Object o) {
		return o instanceof Assign &&
				((Assign)o).target.equals(target) &&
				((Assign)o).value.equals(value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(target, value);
	}	

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return new Assign((AssignmentTarget)target.replaceVariable(a, b), (AbstractExpression)value.replaceVariable(a, b));
	}

	@Override
	public void getVariables(Set<String> list) {
		target.getVariables(list);
		value.getVariables(list);
	}

	@Override
	public AbstractStatement rewriteEmbeddedFunctionCalls() {
		ExtractedExpression rewrittenTarget = FunctionCallForm.extractFunctionCalls(target);
		ExtractedExpression rewrittenValue = FunctionCallForm.extractFunctionCalls(value);
		if (rewrittenTarget.isRewritten() || rewrittenValue.isRewritten()) {
			return new FunctionCallForm(
					new Assign((AssignmentTarget)rewrittenTarget.getExpression(), 
							rewrittenValue.getExpression(), exceptionSource), 
						rewrittenTarget.getAssignments(),
						rewrittenValue.getAssignments());
		} else {
			return this;
		}
	}

	@Override
	public void getAssignedVariables(Set<String> variables) {
		target.getAssignedVariables(variables);
	}

}
