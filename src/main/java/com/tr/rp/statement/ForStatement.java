package com.tr.rp.statement;

import java.util.Set;
import java.util.TreeSet;
import java.util.Collections;
import java.util.List;

import com.tr.rp.core.DStatement;
import com.tr.rp.core.Expression;
import com.tr.rp.core.LanguageElement;
import com.tr.rp.core.VarStore;
import com.tr.rp.core.rankediterators.AbsurdIterator;
import com.tr.rp.core.rankediterators.BufferingIterator;
import com.tr.rp.core.rankediterators.ExecutionContext;
import com.tr.rp.core.rankediterators.RankedIterator;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.expressions.FunctionCall;
import com.tr.rp.statement.FunctionCallForm.ExtractedExpression;
import com.tr.rp.tools.Pair;

public class ForStatement extends DStatement {

	/** Optional statement to execute before evaluating condition */
	private DStatement preConditionStatement;

	/** Condition */
	private final Expression forCondition;
	
	private final DStatement init;
	private final DStatement next;
	private final DStatement body;
	
	private final boolean isOptimal;
	
	public ForStatement(DStatement init, Expression forCondition, DStatement next, DStatement body) {
		this.init = init;
		this.forCondition = forCondition;
		this.next = next;
		this.body = body;
		this.preConditionStatement = null;
		isOptimal = checkOptimal();
	}
				
	/**
	 * A for loop is optimal if the variables assigned by the init/next are not
	 * assigned a value by the body.
	 * 
	 * @return True if for loop is optimal
	 */
	private boolean checkOptimal() {
		Set<String> assignedByInitNext = new TreeSet<String>();
		Set<String> assignedByBody = new TreeSet<String>();
		init.getAssignedVariables(assignedByInitNext);
		next.getAssignedVariables(assignedByInitNext);
		body.getAssignedVariables(assignedByBody);
		return Collections.disjoint(assignedByInitNext, assignedByBody);
	}

	private ForStatement(DStatement init, DStatement preConditionStatement, Expression forCondition, DStatement next, DStatement body) {
		this.init = init;
		this.forCondition = forCondition;
		this.next = next;
		this.body = body;
		this.preConditionStatement = null;
		isOptimal = checkOptimal();
	}
					
	public RankedIterator<VarStore> getIterator(RankedIterator<VarStore> in, ExecutionContext c) throws RPLException {
		try {
			// Init
			in = init.getIterator(in, c);

			while (true) {
				
				// Do one iteration
				in = generateIteration(in, c);
				
				// Check if condition is still satisfied
				BufferingIterator<VarStore> bi = new BufferingIterator<VarStore>(in);
				boolean conditionSatisfied = false;
				boolean hasNext = bi.next();
				if (!hasNext) { // Undefined
					return new AbsurdIterator<VarStore>(); 
				}
				
				// If optimal, the for condition is the same in all variable stores,
				// and therefore we only have to check the first.
				if (isOptimal) {
					if (forCondition.getBoolValue(bi.getItem())) {
						conditionSatisfied = true;
					}
				} else {
					while (hasNext) {
						if (forCondition.getBoolValue(bi.getItem())) {
							conditionSatisfied = true;
							break;
						}
						hasNext = bi.next();
					}
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
			e.setStatement(this);
			throw e;
		}
	}

	private RankedIterator<VarStore> generateIteration(RankedIterator<VarStore> in, ExecutionContext c) throws RPLException {
		if (preConditionStatement == null) {
			return (new IfElse(forCondition, new Composition(body, next), new Skip())).getIterator(in, c);
		} else {
			return new Composition(preConditionStatement, (new IfElse(forCondition, new Composition(body, next), new Skip()))).getIterator(in, c);
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
	
	@Override
	public void getAssignedVariables(Set<String> variables) {
		init.getAssignedVariables(variables);
		next.getAssignedVariables(variables);
		body.getAssignedVariables(variables);
	}	


}
