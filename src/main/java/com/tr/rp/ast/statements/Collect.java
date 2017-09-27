package com.tr.rp.ast.statements;

import java.util.Objects;
import java.util.Set;

import com.tr.rp.ast.AbstractStatement;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.ast.expressions.AssignmentTarget;
import com.tr.rp.ast.expressions.AssignmentTargetTerminal;
import com.tr.rp.ast.expressions.Variable;
import com.tr.rp.ast.statements.FunctionCallForm.ExtractedExpression;
import com.tr.rp.base.ExecutionContext;
import com.tr.rp.executors.Executor;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
		
public class Collect extends AbstractStatement {
	
	private final AssignmentTarget target;
	private final Variable variable;
	
	public Collect(AssignmentTarget target, Variable variable) {
		this.target = target;
		this.variable = variable;
	}

	public Collect(AssignmentTarget target, String variable) {
		this(target, new Variable(variable));
	}

	public Collect(String target, String variable) {
		this(new AssignmentTargetTerminal(target), new Variable(variable));
	}

	@Override
	public Executor getExecutor(Executor out, ExecutionContext c) {
		throw new NotImplementedException();
	}	

	
	public String toString() {
		return "collect("+variable+")";
	}
	
	public boolean equals(Object o) {
		return o instanceof Collect &&
				((Collect)o).target.equals(target) && 
				((Collect)o).variable.equals(variable);
	}

	@Override
	public int hashCode() {
		return Objects.hash(target, variable);
	}	

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return new Collect((AssignmentTarget)target.replaceVariable(a, b), (Variable)variable.replaceVariable(a, b));
	}

	@Override
	public void getVariables(Set<String> list) {
		variable.getVariables(list);
	}

	@Override
	public AbstractStatement rewriteEmbeddedFunctionCalls() {
		ExtractedExpression rewrittenTarget = FunctionCallForm.extractFunctionCalls(target);
		ExtractedExpression rewrittenVar = FunctionCallForm.extractFunctionCalls(variable);
		if (rewrittenVar.isRewritten() || rewrittenTarget.isRewritten()) {
			return new FunctionCallForm(new Collect((AssignmentTarget)rewrittenTarget.getExpression(), (Variable)rewrittenVar.getExpression()), rewrittenTarget.getAssignments(), rewrittenVar.getAssignments());
		} else {
			return this;
		}
	}

	@Override
	public void getAssignedVariables(Set<String> variables) {
		target.getAssignedVariables(variables);
	}
}
