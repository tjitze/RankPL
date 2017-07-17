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
import com.tr.rp.varstore.types.PersistentMap;
import com.tr.rp.varstore.types.PersistentSet;
import com.tr.rp.varstore.types.Type;

/**
 * put(map, key, value) - add value to map
 */
public class MapPut extends AbstractStatement {
	
	private final AssignmentTarget assignmentTarget;
	private final AbstractExpression key;
	private final AbstractExpression value;
	
	public MapPut(AssignmentTarget assignmentTarget, AbstractExpression key, AbstractExpression value) {
		this.assignmentTarget = assignmentTarget;
		this.key = key;
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
				PersistentMap<Object, Object> map = setVar.convertToRHSExpression().getValue(item, Type.MAP);
				return setVar.assign(item, map.add(key.getValue(item), value.getValue(item)));
			}

			@Override
			public int getRank() {
				return rt.getRank();
			}
		};
		return ai;
	}
	
	
	public String toString() {
		String keyString = key.toString();
		String valueString = value.toString();
		return "put(" + assignmentTarget + ", " + keyString + ", " + valueString + ")";
	}
	
	public boolean equals(Object o) {
		return o instanceof MapPut &&
				((MapPut)o).assignmentTarget.equals(assignmentTarget) &&
				((MapPut)o).key.equals(key) &&
				((MapPut)o).value.equals(value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(assignmentTarget, key, value);
	}	

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return new MapPut((AssignmentTarget) assignmentTarget.replaceVariable(a, b), 
				(AbstractExpression)key.replaceVariable(a, b),
				(AbstractExpression)value.replaceVariable(a, b));
	}

	@Override
	public void getVariables(Set<String> list) {
		assignmentTarget.getVariables(list);
		key.getVariables(list);
		value.getVariables(list);
	}

	@Override
	public AbstractStatement rewriteEmbeddedFunctionCalls() {
		ExtractedExpression rewrittenAssignmentTarget = FunctionCallForm.extractFunctionCalls(assignmentTarget);
		ExtractedExpression rewrittenKey = FunctionCallForm.extractFunctionCalls(key);
		ExtractedExpression rewrittenValue = FunctionCallForm.extractFunctionCalls(value);
		if (rewrittenAssignmentTarget.isRewritten() || rewrittenKey.isRewritten() || rewrittenValue.isRewritten()) {
			return new FunctionCallForm(
					new MapPut((AssignmentTarget) rewrittenAssignmentTarget.getExpression(), 
							rewrittenKey.getExpression(), rewrittenValue.getExpression()), 
					rewrittenValue.getAssignments());
		} else {
			return this;
		}
	}

	@Override
	public void getAssignedVariables(Set<String> variables) {
		variables.add(assignmentTarget.getAssignedVariable());
	}	
}
