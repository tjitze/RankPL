package com.tr.rp.ast.statements;

import java.util.Set;
import java.util.Objects;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.AbstractStatement;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.ast.expressions.AssignmentTarget;
import com.tr.rp.ast.statements.FunctionCallForm.ExtractedExpression;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.exceptions.RPLIndexOutOfBoundsException;
import com.tr.rp.iterators.ranked.ExecutionContext;
import com.tr.rp.iterators.ranked.RankTransformIterator;
import com.tr.rp.iterators.ranked.RankedIterator;
import com.tr.rp.varstore.VarStore;
import com.tr.rp.varstore.types.PersistentList;
import com.tr.rp.varstore.types.PersistentMap;
import com.tr.rp.varstore.types.PersistentSet;
import com.tr.rp.varstore.types.Type;

/**
 * set(list, index, value) - set element of list
 */
public class ListReplace extends AbstractStatement {
	
	private final AssignmentTarget assignmentTarget;
	private final AbstractExpression index;
	private final AbstractExpression value;
	
	public ListReplace(AssignmentTarget assignmentTarget, AbstractExpression index, AbstractExpression value) {
		this.assignmentTarget = assignmentTarget;
		this.index = index;
		this.value = value;
	}

	@Override
	public RankedIterator<VarStore> getIterator(final RankedIterator<VarStore> in, ExecutionContext c) throws RPLException {
		RankTransformIterator rt = new RankTransformIterator(in, this, assignmentTarget, value);
		final AssignmentTarget listVar = (AssignmentTarget)rt.getExpression(0);
		final AbstractExpression value = (AbstractExpression)rt.getExpression(1);
		RankedIterator<VarStore> ai = new RankedIterator<VarStore>() {

			@Override
			public boolean next() throws RPLException {
				return rt.next();
			}

			@Override
			public VarStore getItem() throws RPLException {
				VarStore item = rt.getItem();
				PersistentList<Object> list = listVar.convertToRHSExpression().getValue(item, Type.LIST);
				try {
					return listVar.assign(item, list.replace(index.getValue(item, Type.INT), value.getValue(item)));
				} catch (IndexOutOfBoundsException ex) {
					throw new RPLIndexOutOfBoundsException(index.getValue(item, Type.INT), list.size(), ListReplace.this);
				}
			}

			@Override
			public int getRank() {
				return rt.getRank();
			}
		};
		return ai;
	}
	
	
	public String toString() {
		String indexString = index.toString();
		String valueString = value.toString();
		return "replace(" + assignmentTarget + ", " + indexString + ", " + valueString + ")";
	}
	
	public boolean equals(Object o) {
		return o instanceof ListReplace &&
				((ListReplace)o).assignmentTarget.equals(assignmentTarget) &&
				((ListReplace)o).index.equals(index) &&
				((ListReplace)o).value.equals(value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(assignmentTarget, index, value);
	}	

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return new ListReplace((AssignmentTarget) assignmentTarget.replaceVariable(a, b), 
				(AbstractExpression)index.replaceVariable(a, b),
				(AbstractExpression)value.replaceVariable(a, b));
	}

	@Override
	public void getVariables(Set<String> list) {
		assignmentTarget.getVariables(list);
		index.getVariables(list);
		value.getVariables(list);
	}

	@Override
	public AbstractStatement rewriteEmbeddedFunctionCalls() {
		ExtractedExpression rewrittenAssignmentTarget = FunctionCallForm.extractFunctionCalls(assignmentTarget);
		ExtractedExpression rewrittenKey = FunctionCallForm.extractFunctionCalls(index);
		ExtractedExpression rewrittenValue = FunctionCallForm.extractFunctionCalls(value);
		if (rewrittenAssignmentTarget.isRewritten() || rewrittenKey.isRewritten() || rewrittenValue.isRewritten()) {
			return new FunctionCallForm(
					new ListReplace((AssignmentTarget) rewrittenAssignmentTarget.getExpression(), 
							rewrittenKey.getExpression(), rewrittenValue.getExpression()), 
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
