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
import com.tr.rp.executors.BranchingExecutor;
import com.tr.rp.executors.Deduplicator;
import com.tr.rp.executors.Executor;
import com.tr.rp.executors.RankTransformer;

public class IfElse extends AbstractStatement {

	private AbstractExpression exp;
	private AbstractStatement a, b;
	
	public IfElse(AbstractExpression exp, AbstractStatement a, AbstractStatement b) {
		this.exp = exp;
		this.a = a;
		this.b = b;
	}

	@Override
	public Executor getExecutor(Executor out, ExecutionContext c) {
		RankTransformer<AbstractExpression> transformExp = new RankTransformer<AbstractExpression>(exp) {
			protected void handleRankTransformException(RPLException e) throws RPLException {
				IfElse.this.handleRankExpressionException(e);
			}
		};
		Executor e = new BranchingExecutor(transformExp, a, b, out, c) {
			public void handleConditionException(RPLException e) throws RPLException {
				IfElse.this.handleConditionException(e);
			}
		};
		return transformExp.getExecutor(e, this);
	}

	public String toString() {
		return "if " + StringTools.addPars(exp.toString()) + " then " + a + " else " + b;
	}
	
	public boolean equals(Object o) {
		return o instanceof IfElse &&
				((IfElse)o).a.equals(a) &&
				((IfElse)o).b.equals(b) &&
				((IfElse)o).exp.equals(exp);
	}

	@Override
	public int hashCode() {
		return Objects.hash(a, b, exp);
	}	

	@Override
	public void getVariables(Set<String> list) {
		a.getVariables(list);
		b.getVariables(list);
		exp.getVariables(list);
	}

	@Override
	public AbstractStatement rewriteEmbeddedFunctionCalls() {
		AbstractStatement ar = a.rewriteEmbeddedFunctionCalls();
		AbstractStatement br = b.rewriteEmbeddedFunctionCalls();
		ExtractedExpression rewrittenExp = FunctionCallForm.extractFunctionCalls(exp);
		if (rewrittenExp.isRewritten()) {
			IfElse ifelse = new IfElse(rewrittenExp.getExpression(), ar, br);
			ifelse.setLineNumber(getLineNumber());
			return new FunctionCallForm(ifelse, rewrittenExp.getAssignments());
		} else {
			IfElse ifelse = new IfElse(exp, ar, br);
			ifelse.setLineNumber(getLineNumber());
			return ifelse;
		}
	}	
	
	@Override
	public void getAssignedVariables(Set<String> variables) {
		a.getAssignedVariables(variables);
		b.getAssignedVariables(variables);
	}

	/**
	 * Override to handle exception resulting from condition evaluation
	 */
	public void handleConditionException(RPLException e) throws RPLException {
		e.setStatement(IfElse.this);
		throw e;
	}

	/**
	 * Override to handle exception resulting from rank transformation of condition
	 */
	public void handleRankExpressionException(RPLException e) throws RPLException {
		e.setStatement(IfElse.this);
		throw e;
	}

}
