package com.tr.rp.ast.statements;

import java.util.Objects;
import java.util.Set;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.AbstractStatement;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.ast.StringTools;
import com.tr.rp.ast.statements.FunctionCallForm.ExtractedExpression;
import com.tr.rp.base.ExecutionContext;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.executors.Executor;

public class ForStatement extends AbstractStatement {

	/** Optional statement to execute before evaluating condition */
	private AbstractStatement preStatement;

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
		this.preStatement = null;
	}
				
	private ForStatement(AbstractStatement init, AbstractStatement preStatement, AbstractExpression forCondition, AbstractStatement next, AbstractStatement body) {
		this.init = init;
		this.forCondition = forCondition;
		this.next = next;
		this.body = body;
		this.preStatement = preStatement;
	}
			
	@Override
	public Executor getExecutor(Executor out, ExecutionContext c) {
		While w = new While(forCondition, new Composition(body, next)) {
			public void handleConditionException(RPLException e) throws RPLException {
				e.setExpression(forCondition);
				e.setStatement(ForStatement.this);
				throw e;
			}
		};

		AbstractStatement s;
		if (preStatement != null) {
			s = new Composition(preStatement, init, w);
		} else {
			s = new Composition(init, w);
		}
		
		return s.getExecutor(out, c);
	}	
	
	public boolean equals(Object o) {
		return o instanceof ForStatement &&
				Objects.equals(((ForStatement)o).preStatement, preStatement) &&
				((ForStatement)o).forCondition.equals(forCondition) &&
				((ForStatement)o).init.equals(init) &&
				((ForStatement)o).body.equals(body) &&
				((ForStatement)o).next.equals(next);
	}


	@Override
	public int hashCode() {
		return Objects.hash(getClass(), preStatement, forCondition, init, body, next);
	}
	
	public String toString() {
		return "for (" + init + "; " + StringTools.stripPars(forCondition.toString()) + "; " + next + ") " + body;
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
		if (preStatement != null) {
			throw new UnsupportedOperationException();
		}
		AbstractStatement rewrittenInit = init.rewriteEmbeddedFunctionCalls();
		AbstractStatement rewrittenBody = body.rewriteEmbeddedFunctionCalls();
		AbstractStatement rewrittenNext = next.rewriteEmbeddedFunctionCalls();
		ExtractedExpression rewrittenForCondition = FunctionCallForm.extractFunctionCalls(forCondition);
		if (rewrittenForCondition.isRewritten()) {
			ForStatement fs = new ForStatement(rewrittenInit,
					new FunctionCallForm(new Skip(), rewrittenForCondition.getAssignments()),
					rewrittenForCondition.getExpression(),
					rewrittenNext,
					rewrittenBody);
			fs.setLineNumber(getLineNumber());
			return fs;
		} else {
			ForStatement fs = new ForStatement(rewrittenInit, forCondition, rewrittenNext, rewrittenBody);
			fs.setLineNumber(getLineNumber());
			return fs;
		}
	}	
	
	@Override
	public void getAssignedVariables(Set<String> variables) {
		init.getAssignedVariables(variables);
		next.getAssignedVariables(variables);
		body.getAssignedVariables(variables);
	}

}
