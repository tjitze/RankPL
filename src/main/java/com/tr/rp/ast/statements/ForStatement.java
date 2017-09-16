package com.tr.rp.ast.statements;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.AbstractStatement;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.ast.statements.FunctionCallForm.ExtractedExpression;
import com.tr.rp.exec.ExecutionContext;
import com.tr.rp.exec.Executor;

public class ForStatement extends AbstractStatement {

	/** Optional statement to execute before evaluating condition */
	private AbstractStatement preConditionStatement;

	/** Condition */
	private final AbstractExpression forCondition;
	
	private final AbstractStatement init;
	private final AbstractStatement next;
	private final AbstractStatement body;
		
	public ForStatement(AbstractStatement init, AbstractExpression forCondition, AbstractStatement next, AbstractStatement body) {
		this.init = init;
		this.forCondition = forCondition;
		this.next = next;
		this.body = body;
		this.preConditionStatement = null;
	}
				
	private ForStatement(AbstractStatement init, AbstractStatement preConditionStatement, AbstractExpression forCondition, AbstractStatement next, AbstractStatement body) {
		this.init = init;
		this.forCondition = forCondition;
		this.next = next;
		this.body = body;
		this.preConditionStatement = null;
	}
			
	@Override
	public Executor getExecutor(Executor out, ExecutionContext c) {
		AbstractStatement s = new Composition(init, new While(forCondition, new Composition(body, next)));
		return s.getExecutor(out, c);
	}	
	
	public boolean equals(Object o) {
		return o instanceof ForStatement &&
				Objects.equals(((ForStatement)o).preConditionStatement, preConditionStatement) &&
				((ForStatement)o).forCondition.equals(forCondition) &&
				((ForStatement)o).init.equals(init) &&
				((ForStatement)o).body.equals(body) &&
				((ForStatement)o).next.equals(next);
	}


	@Override
	public int hashCode() {
		return Objects.hash(preConditionStatement, forCondition, init, body, next);
	}	

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return new ForStatement(
				(AbstractStatement)init.replaceVariable(a, b), 
				(AbstractExpression)forCondition.replaceVariable(a, b), 
				(AbstractStatement)next.replaceVariable(a, b), 
				(AbstractStatement)body.replaceVariable(a, b));
	}
	
	public String toString() {
		return "for (" + init + "; " + forCondition + "; " + next + ")" + body;
	}
	
	@Override
	public void getVariables(Set<String> list) {
		init.getVariables(list);
		body.getVariables(list);
		next.getVariables(list);
		forCondition.getVariables(list);
	}

	@Override
	public AbstractStatement rewriteEmbeddedFunctionCalls() {
		if (preConditionStatement != null) {
			throw new UnsupportedOperationException();
		}
		AbstractStatement rewrittenInit = init.rewriteEmbeddedFunctionCalls();
		AbstractStatement rewrittenBody = body.rewriteEmbeddedFunctionCalls();
		AbstractStatement rewrittenNext = next.rewriteEmbeddedFunctionCalls();
		ExtractedExpression rewrittenForCondition = FunctionCallForm.extractFunctionCalls(forCondition);
		if (rewrittenForCondition.isRewritten()) {
			return new ForStatement(rewrittenInit,
					new FunctionCallForm(new Skip(), rewrittenForCondition.getAssignments()),
					rewrittenForCondition.getExpression(),
					rewrittenNext,
					rewrittenBody);
		} else {
			return new ForStatement(rewrittenInit, forCondition, rewrittenNext, rewrittenBody);
		}
	}	
	
	@Override
	public void getAssignedVariables(Set<String> variables) {
		init.getAssignedVariables(variables);
		next.getAssignedVariables(variables);
		body.getAssignedVariables(variables);
	}

}
