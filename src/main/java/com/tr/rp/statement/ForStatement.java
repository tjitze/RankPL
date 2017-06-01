package com.tr.rp.statement;

import java.util.Set;
import java.util.List;

import com.tr.rp.core.DStatement;
import com.tr.rp.core.Expression;
import com.tr.rp.core.LanguageElement;
import com.tr.rp.core.VarStore;
import com.tr.rp.core.rankediterators.AbsurdIterator;
import com.tr.rp.core.rankediterators.BufferingIterator;
import com.tr.rp.core.rankediterators.RankedIterator;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.expressions.FunctionCall;
import com.tr.rp.statement.FunctionCallForm.ExtractedExpression;
import com.tr.rp.tools.Pair;

public class ForStatement extends DStatement {

	/** Optional statement to execute before evaluating condition */
	private DStatement preConditionStatement;

	/** Condition */
	private Expression forCondition;
	
	private DStatement init;
	private DStatement next;
	private DStatement body;
	
	public ForStatement(DStatement init, Expression forCondition, DStatement next, DStatement body) {
		this.init = init;
		this.forCondition = forCondition;
		this.next = next;
		this.body = body;
		this.preConditionStatement = null;
	}
				
	private ForStatement(DStatement init, DStatement preConditionStatement, Expression forCondition, DStatement next, DStatement body) {
		this.init = init;
		this.forCondition = forCondition;
		this.next = next;
		this.body = body;
		this.preConditionStatement = null;
	}
				
	public RankedIterator<VarStore> getIterator(RankedIterator<VarStore> in) throws RPLException {
		try {
			// Init
			in = init.getIterator(in);

			while (true) {
				
				// Do one iteration
				in = generateIteration(in);
				
				// Check if condition is still satisfied
				BufferingIterator<VarStore> bi = new BufferingIterator<VarStore>(in);
				boolean conditionSatisfied = false;
				boolean hasNext = bi.next();
				if (!hasNext) { // Undefined
					return new AbsurdIterator<VarStore>(); 
				}
				while (hasNext) {
					if (forCondition.getBoolValue(bi.getItem())) {
						conditionSatisfied = true;
						break;
					}
					hasNext = bi.next();
				}
				
				bi.reset();
				bi.stopBuffering();
	
				// If exp is not satisfied, we are done
				if (!conditionSatisfied) {
					return bi;
				}
				
				// Try another iteration
				in = bi;
			}
			
		} catch (RPLException e) {
			e.addStatement(this);
			throw e;
		}
	}

	private RankedIterator<VarStore> generateIteration(RankedIterator<VarStore> in) throws RPLException {
		if (preConditionStatement == null) {
			return (new IfElse(forCondition, new Composition(body, next), new Skip())).getIterator(in);
		} else {
			return new Composition(preConditionStatement, (new IfElse(forCondition, new Composition(body, next), new Skip()))).getIterator(in);
		}
	}
	
	public boolean equals(Object o) {
		return o instanceof ForStatement &&
				((ForStatement)o).forCondition.equals(forCondition) &&
				((ForStatement)o).init.equals(init) &&
				((ForStatement)o).body.equals(body) &&
				((ForStatement)o).next.equals(next);
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return new ForStatement(
				(DStatement)init.replaceVariable(a, b), 
				(Expression)forCondition.replaceVariable(a, b), 
				(DStatement)next.replaceVariable(a, b), 
				(DStatement)body.replaceVariable(a, b));
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
	public DStatement rewriteEmbeddedFunctionCalls() {
		if (preConditionStatement != null) {
			throw new UnsupportedOperationException();
		}
		DStatement rewrittenInit = init.rewriteEmbeddedFunctionCalls();
		DStatement rewrittenBody = body.rewriteEmbeddedFunctionCalls();
		DStatement rewrittenNext = next.rewriteEmbeddedFunctionCalls();
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

}
