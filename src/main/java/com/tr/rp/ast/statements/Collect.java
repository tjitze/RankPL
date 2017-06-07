package com.tr.rp.ast.statements;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.tr.rp.ast.AbstractStatement;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.ast.expressions.AssignmentTarget;
import com.tr.rp.ast.expressions.Variable;
import com.tr.rp.ast.statements.FunctionCallForm.ExtractedExpression;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.iterators.ranked.DuplicateRemovingIterator;
import com.tr.rp.iterators.ranked.ExecutionContext;
import com.tr.rp.iterators.ranked.RankedIterator;
import com.tr.rp.varstore.PersistentList;
import com.tr.rp.varstore.VarStore;
		
public class Collect extends AbstractStatement {
	
	private final AssignmentTarget target;
	private final Variable variable;
	
	public Collect(AssignmentTarget target, Variable variable) {
		this.target = target;
		this.variable = variable;
	}

	public Collect(AssignmentTarget target, String variable) {
		this(target, new Variable(variable));
	}

	public Collect(String target, String variable) {
		this(new AssignmentTarget(target), new Variable(variable));
	}

	@Override
	public RankedIterator<VarStore> getIterator(final RankedIterator<VarStore> in, ExecutionContext c) throws RPLException {
		// Get all zero ranked variable stores
		List<VarStore> zeroRankVarStores = new ArrayList<VarStore>();
		boolean hasNext = in.next();
		while (hasNext && in.getRank() == 0) {
			zeroRankVarStores.add(in.getItem());
			hasNext = in.next();
		}
		// Construct list of values
		LinkedHashSet<Object> values = new LinkedHashSet<Object>();
		for (VarStore vs: zeroRankVarStores) {
			Object v = variable.getValue(vs);
			if (v != null) {
				values.add(v);
			}
		}
		PersistentList rankZeroValues = new PersistentList(values);
		// Update rank zero var stores
		for (int i = 0; i < zeroRankVarStores.size(); i++) {
			zeroRankVarStores.set(i, target.assign(zeroRankVarStores.get(i), rankZeroValues));
		}
		final Iterator<VarStore> zi = zeroRankVarStores.iterator();
		// First return rank zero var stores, then return rest
		final boolean hasNextFinal = hasNext;
		RankedIterator<VarStore> it = new RankedIterator<VarStore>() {

			private VarStore v;
			private boolean f = false;
			
			@Override
			public boolean next() throws RPLException {
				if (f) {
					return hasNextFinal && in.next();
				} else {
					if (zi.hasNext()) {
						v = zi.next();
						return true;
					} else {
						f = true;
						return hasNextFinal;
					}
				}
			}

			@Override
			public VarStore getItem() throws RPLException {
				if (f) {
					return in.getItem();
				} else {
					return v;
				}
			}
			
			@Override
			public int getRank() {
				if (f) {
					return in.getRank();
				} else {
					return 0;
				}
			}
		};
		return new DuplicateRemovingIterator<VarStore>(it);
	}
	
	
	public String toString() {
		return "collect("+variable+")";
	}
	
	public boolean equals(Object o) {
		return o instanceof Collect &&
				((Collect)o).target.equals(target) && 
				((Collect)o).variable.equals(variable);
	}

	@Override
	public int hashCode() {
		return Objects.hash(target, variable);
	}	

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return new Collect((AssignmentTarget)target.replaceVariable(a, b), (Variable)variable.replaceVariable(a, b));
	}

	@Override
	public void getVariables(Set<String> list) {
		variable.getVariables(list);
	}

	@Override
	public AbstractStatement rewriteEmbeddedFunctionCalls() {
		ExtractedExpression rewrittenTarget = FunctionCallForm.extractFunctionCalls(target);
		ExtractedExpression rewrittenVar = FunctionCallForm.extractFunctionCalls(variable);
		if (rewrittenVar.isRewritten() || rewrittenTarget.isRewritten()) {
			return new FunctionCallForm(new Collect((AssignmentTarget)rewrittenTarget.getExpression(), (Variable)rewrittenVar.getExpression()), rewrittenTarget.getAssignments(), rewrittenVar.getAssignments());
		} else {
			return this;
		}
	}

	@Override
	public void getAssignedVariables(Set<String> variables) {
		variables.add(target.getAssignedVariable());
	}	
}
