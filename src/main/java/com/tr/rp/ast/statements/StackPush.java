package com.tr.rp.ast.statements;

import java.util.Set;
import java.util.Objects;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.AbstractStatement;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.ast.expressions.AssignmentTarget;
import com.tr.rp.ast.statements.FunctionCallForm.ExtractedExpression;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.iterators.ranked.ExecutionContext;
import com.tr.rp.iterators.ranked.RankTransformIterator;
import com.tr.rp.iterators.ranked.RankedIterator;
import com.tr.rp.varstore.VarStore;
import com.tr.rp.varstore.types.PersistentSet;
import com.tr.rp.varstore.types.PersistentStack;
import com.tr.rp.varstore.types.Type;

/**
 * push(stack, element) - push element on stack
 */
public class StackPush extends AbstractStatement {
	
	private final AssignmentTarget assignmentTarget;
	private final AbstractExpression value;
	
	public StackPush(AssignmentTarget assignmentTarget, AbstractExpression value) {
		this.assignmentTarget = assignmentTarget;
		this.value = value;
	}

	@Override
	public RankedIterator<VarStore> getIterator(final RankedIterator<VarStore> in, ExecutionContext c) throws RPLException {
		RankTransformIterator rt = new RankTransformIterator(in, this, assignmentTarget, value);
		final AssignmentTarget setVar = (AssignmentTarget)rt.getExpression(0);
		final AbstractExpression value = (AbstractExpression)rt.getExpression(1);
		RankedIterator<VarStore> ai = new RankedIterator<VarStore>() {

			@Override
			public boolean next() throws RPLException {
				return rt.next();
			}

			@Override
			public VarStore getItem() throws RPLException {
				VarStore item = rt.getItem();
				PersistentStack<Object> set = setVar.convertToRHSExpression().getValue(item, Type.STACK);
				return setVar.assign(item, set.push(value.getValue(item)));
			}

			@Override
			public int getRank() {
				return rt.getRank();
			}
		};
		return ai;
	}
	
	
	public String toString() {
		String expString = value.toString();
		return "push(" + assignmentTarget + ", " + expString + ")";
	}
	
	public boolean equals(Object o) {
		return o instanceof StackPush &&
				((StackPush)o).assignmentTarget.equals(assignmentTarget) &&
				((StackPush)o).value.equals(value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(assignmentTarget, value);
	}	

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return new StackPush((AssignmentTarget) assignmentTarget.replaceVariable(a, b), 
				(AbstractExpression)value.replaceVariable(a, b));
	}

	@Override
	public void getVariables(Set<String> list) {
		assignmentTarget.getVariables(list);
		value.getVariables(list);
	}

	@Override
	public AbstractStatement rewriteEmbeddedFunctionCalls() {
		ExtractedExpression rewrittenAssignmentTarget = FunctionCallForm.extractFunctionCalls(assignmentTarget);
		ExtractedExpression rewrittenValue = FunctionCallForm.extractFunctionCalls(value);
		if (rewrittenAssignmentTarget.isRewritten() || rewrittenValue.isRewritten()) {
			return new FunctionCallForm(
					new StackPush((AssignmentTarget) rewrittenAssignmentTarget.getExpression(), 
							rewrittenValue.getExpression()), 
					rewrittenValue.getAssignments());
		} else {
			return this;
		}
	}

	@Override
	public void getAssignedVariables(Set<String> variables) {
		assignmentTarget.getAssignedVariables(variables);
	}	
}
