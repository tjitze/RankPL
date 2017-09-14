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

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class ForStatement extends AbstractStatement {

	/** Optional statement to execute before evaluating condition */
	private AbstractStatement preConditionStatement;

	/** Condition */
	private final AbstractExpression forCondition;
	
	private final AbstractStatement init;
	private final AbstractStatement next;
	private final AbstractStatement body;
	
	private final boolean isOptimal;
	
	public ForStatement(AbstractStatement init, AbstractExpression forCondition, AbstractStatement next, AbstractStatement body) {
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

	private ForStatement(AbstractStatement init, AbstractStatement preConditionStatement, AbstractExpression forCondition, AbstractStatement next, AbstractStatement body) {
		this.init = init;
		this.forCondition = forCondition;
		this.next = next;
		this.body = body;
		this.preConditionStatement = null;
		isOptimal = checkOptimal();
	}
			
	@Override
	public Executor getExecutor(Executor out, ExecutionContext c) {
		throw new NotImplementedException();
	}	

//	public RankedIterator<VarStore> getIterator(RankedIterator<VarStore> in, ExecutionContext c) throws RPLException {
//		// Init
//		in = init.getIterator(in, c);
//
//		while (true) {
//			
//			// Do one iteration
//			in = generateIteration(in, c);
//			
//			// Check if condition is still satisfied
//			BufferingIterator<VarStore> bi = new BufferingIterator<VarStore>(in);
//			boolean conditionSatisfied = false;
//			boolean hasNext = bi.next();
//			if (!hasNext) { // Undefined
//				return new AbsurdIterator<VarStore>(); 
//			}
//			
//			// If optimal, the for condition is the same in all variable stores,
//			// and therefore we only have to check the first.
//			if (isOptimal) {
//				if (forCondition.getValue(bi.getItem(), Type.BOOL)) {
//					conditionSatisfied = true;
//				}
//			} else {
//				while (hasNext) {
//					if (forCondition.getValue(bi.getItem(), Type.BOOL)) {
//						conditionSatisfied = true;
//						break;
//					}
//					hasNext = bi.next();
//				}
//			}
//			
//			bi.reset();
//			bi.stopBuffering();
//
//			// If exp is not satisfied, we are done
//			if (!conditionSatisfied) {
//				return bi;
//			}
//			
//			// Try another iteration
//			in = bi;
//		}
//	}
//
//	private RankedIterator<VarStore> generateIteration(RankedIterator<VarStore> in, ExecutionContext c) throws RPLException {
//		if (preConditionStatement == null) {
//			return (new IfElse(forCondition, new Composition(body, next), new Skip())).getIterator(in, c);
//		} else {
//			return new Composition(preConditionStatement, (new IfElse(forCondition, new Composition(body, next), new Skip()))).getIterator(in, c);
//		}
//	}
	
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
