package com.tr.rp.ast.statements;

import static com.tr.rp.ast.statements.Statements.*;
import static com.tr.rp.ast.expressions.Expressions.*;

import java.util.Objects;
import java.util.Set;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.AbstractStatement;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.ast.expressions.AssignmentTarget;
import com.tr.rp.ast.statements.FunctionCallForm.ExtractedExpression;
import com.tr.rp.base.ExecutionContext;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.executors.Executor;
import com.tr.rp.varstore.FreeVarNameProvider;

/**
 * TODO: handle preStatement?
 * TODO: handle exceptions
 */
public class ForInStatement extends AbstractStatement {
	
	private final AbstractStatement preStatement;
	private final AssignmentTarget target;
	private final AbstractExpression exp;
	private final AbstractStatement body;
		
	public ForInStatement(AssignmentTarget target, AbstractExpression exp, AbstractStatement body) {
		this.target = target;
		this.exp = exp;
		this.body = body;
		this.preStatement = null;
	}
			
	private ForInStatement(AssignmentTarget target, AbstractExpression exp, AbstractStatement body, AbstractStatement preStatement) {
		this.target = target;
		this.exp = exp;
		this.body = body;
		this.preStatement = preStatement;
	}
			
	@Override
	public Executor getExecutor(Executor out, ExecutionContext c) {
		String x = FreeVarNameProvider.getFreeVariable("it");
		
		// Init: x := 0;
		AbstractStatement init = assign(target(x), lit(0));
		
		// Condition: x < size(exp)
		AbstractExpression cond = lt(var(x), size(exp));
		
		// Body: target := exp[x]; body; x := x + 1;
		AbstractStatement b = comp(assign(target, indexedExp(exp, var(x))), body, inc(x));

		While w = new While(cond, b) {
			public void handleConditionException(RPLException e) throws RPLException {
				// Should not happen 
				throw new IllegalStateException();
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
		return o instanceof ForInStatement &&
				Objects.equals(((ForInStatement)o).preStatement, preStatement) &&
				((ForInStatement)o).target.equals(target) &&
				((ForInStatement)o).exp.equals(exp) &&
				((ForInStatement)o).body.equals(body);
	}


	@Override
	public int hashCode() {
		return Objects.hash(getClass(), preStatement, target, exp, body);
	}	

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return new ForInStatement(
				(AssignmentTarget)target.replaceVariable(a, b), 
				(AbstractExpression)exp.replaceVariable(a, b), 
				(AbstractStatement)body.replaceVariable(a, b),
				(AbstractStatement)preStatement.replaceVariable(a, b));
	}
	
	public String toString() {
		return "for (" + target + " in " + exp + ") " + body;
	}
	
	@Override
	public void getVariables(Set<String> list) {
		target.getVariables(list);
		exp.getVariables(list);
		body.getVariables(list);
	}

	@Override
	public AbstractStatement rewriteEmbeddedFunctionCalls() {
		if (preStatement != null) {
			throw new UnsupportedOperationException();
		}
		ExtractedExpression rewrittenTarget = FunctionCallForm.extractFunctionCalls(target);
		ExtractedExpression rewrittenExp = FunctionCallForm.extractFunctionCalls(exp);
		AbstractStatement rewrittenBody = body.rewriteEmbeddedFunctionCalls();
		if (rewrittenTarget.isRewritten() || rewrittenExp.isRewritten()) {
			return new ForInStatement(
					(AssignmentTarget)rewrittenTarget.getExpression(), 
					rewrittenExp.getExpression(), 
					rewrittenBody,
					new FunctionCallForm(new Skip(), rewrittenExp.getAssignments(), rewrittenTarget.getAssignments()));
		} else {
			return new ForInStatement(target, exp, rewrittenBody);
		}
	}	
	
	@Override
	public void getAssignedVariables(Set<String> variables) {
		target.getAssignedVariables(variables);
		body.getAssignedVariables(variables);
	}

}
