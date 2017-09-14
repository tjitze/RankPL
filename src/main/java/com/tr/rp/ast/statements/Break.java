package com.tr.rp.ast.statements;

import java.util.Objects;
import java.util.Set;

import com.tr.rp.ast.AbstractStatement;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.ast.expressions.AssignmentTargetTerminal;
import com.tr.rp.ast.expressions.Expressions;
import com.tr.rp.exec.ExecutionContext;
import com.tr.rp.exec.Executor;

/**
 * The break statement makes a while or for loop exit.
 * 
 * Internally it is implemented by assigning a value to a special 
 * $break variable. The Composition construct skips execution of
 * the remainder for any state in which this variable is set, and
 * the while and for loops exit when this variable is set.
 */
public class Break extends AbstractStatement {

	@Override
	public Executor getExecutor(Executor out, ExecutionContext c) {
		Assign assign = new Assign(new AssignmentTargetTerminal("$break"), Expressions.lit(true));
		return assign.getExecutor(out, c);
	}	
		
	public String toString() {
		return "break";
	}
	
	public boolean equals(Object o) {
		return o instanceof Break;
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return new Break();
	}

	@Override
	public void getVariables(Set<String> list) { }

	@Override
	public AbstractStatement rewriteEmbeddedFunctionCalls() {
		return this;
	}	
	
	@Override
	public void getAssignedVariables(Set<String> variables) {
		variables.add("$break");
	}	
	
	@Override
	public int hashCode() {
		return Objects.hash(getClass());
	}
	
}
