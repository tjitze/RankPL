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
import com.tr.rp.varstore.types.Type;

/**
 * remove(set, element) - remove element from set
 */
public class Remove extends AbstractStatement {
	
	private final String setVar;
	private final AbstractExpression value;
	
	public Remove(String stackVar, AbstractExpression value) {
		this.setVar = stackVar;
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
				PersistentSet<Object> set = item.getValue(setVar, Type.SET);
				return item.create(setVar, set.remove(value.getValue(item)));
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
		return "remove(" + setVar + ", " + expString + ")";
	}
	
	public boolean equals(Object o) {
		return o instanceof Remove &&
				((Remove)o).setVar.equals(setVar) &&
				((Remove)o).value.equals(value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(setVar, value);
	}	

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return new Remove(setVar.equals(a)? b: setVar, (AbstractExpression)value.replaceVariable(a, b));
	}

	@Override
	public void getVariables(Set<String> list) {
		list.add(setVar);
		value.getVariables(list);
	}

	@Override
	public AbstractStatement rewriteEmbeddedFunctionCalls() {
		ExtractedExpression rewrittenValue = FunctionCallForm.extractFunctionCalls(value);
		if (rewrittenValue.isRewritten()) {
			return new FunctionCallForm(
					new Remove(setVar, rewrittenValue.getExpression()), 
						rewrittenValue.getAssignments());
		} else {
			return this;
		}
	}

	@Override
	public void getAssignedVariables(Set<String> variables) {
		variables.add(setVar);
	}	
}
