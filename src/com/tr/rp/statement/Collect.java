package com.tr.rp.statement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.tr.rp.core.DStatement;
import com.tr.rp.core.LanguageElement;
import com.tr.rp.core.VarStore;
import com.tr.rp.core.rankediterators.DuplicateRemovingIterator;
import com.tr.rp.core.rankediterators.RankedIterator;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.expressions.PersistentList;
import com.tr.rp.expressions.Variable;
import com.tr.rp.statement.FunctionCallForm.ExtractedExpression;
		
public class Collect extends DStatement {
	
	private final Variable variable;
	
	public Collect(Variable variable) {
		this.variable = variable;
	}

	public Collect(String variable) {
		this(new Variable(variable));
	}

	@Override
	public RankedIterator<VarStore> getIterator(final RankedIterator<VarStore> in) throws RPLException {
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
			zeroRankVarStores.set(i, variable.assign(zeroRankVarStores.get(i), rankZeroValues));
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
				((Collect)o).variable.equals(variable);
	}

	@Override
	public boolean containsVariable(String name) {
		return variable.containsVariable(name);
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return new Collect((Variable)variable.replaceVariable(a, b));
	}

	@Override
	public void getVariables(Set<String> list) {
		variable.getVariables(list);
	}

	@Override
	public DStatement rewriteEmbeddedFunctionCalls() {
		ExtractedExpression rewrittenVar = FunctionCallForm.extractFunctionCalls(variable);
		if (rewrittenVar.isRewritten()) {
			return new FunctionCallForm(new Collect((Variable)rewrittenVar.getExpression()), rewrittenVar.getAssignments());
		} else {
			return this;
		}
	}	
}
