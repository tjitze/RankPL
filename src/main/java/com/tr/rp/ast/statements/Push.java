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
import com.tr.rp.varstore.types.PersistentStack;
import com.tr.rp.varstore.types.Type;

/**
 * push(stack, element) - pushes element on stack
 */
public class Push extends AbstractStatement {
	
	private final String stackVar;
	private final AbstractExpression value;
	
	public Push(String stackVar, AbstractExpression value) {
		this.stackVar = stackVar;
		this.value = value;
	}

	@Override
	public RankedIterator<VarStore> getIterator(final RankedIterator<VarStore> in, ExecutionContext c) throws RPLException {
		RankTransformIterator rt = new RankTransformIterator(in, this, value);
		final AssignmentTarget value = (AssignmentTarget)rt.getExpression(0);
		RankedIterator<VarStore> ai = new RankedIterator<VarStore>() {

			@Override
			public boolean next() throws RPLException {
				return rt.next();
			}

			@Override
			public VarStore getItem() throws RPLException {
				VarStore item = rt.getItem();
				PersistentStack<Object> stack = item.getValue(stackVar, Type.STACK);
				return item.create(stackVar, stack.push(value.getValue(item)));
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
		return "push(" + stackVar + ", " + expString + ")";
	}
	
	public boolean equals(Object o) {
		return o instanceof Push &&
				((Push)o).stackVar.equals(stackVar) &&
				((Push)o).value.equals(value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(stackVar, value);
	}	

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return new Push(stackVar.equals(a)? b: stackVar, (AbstractExpression)value.replaceVariable(a, b));
	}

	@Override
	public void getVariables(Set<String> list) {
		list.add(stackVar);
		value.getVariables(list);
	}

	@Override
	public AbstractStatement rewriteEmbeddedFunctionCalls() {
		ExtractedExpression rewrittenValue = FunctionCallForm.extractFunctionCalls(value);
		if (rewrittenValue.isRewritten()) {
			return new FunctionCallForm(
					new Push(stackVar, rewrittenValue.getExpression()), 
						rewrittenValue.getAssignments());
		} else {
			return this;
		}
	}

	@Override
	public void getAssignedVariables(Set<String> variables) {
		variables.add(stackVar);
	}	
}
