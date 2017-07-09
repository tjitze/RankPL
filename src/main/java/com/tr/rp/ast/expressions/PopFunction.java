package com.tr.rp.ast.expressions;

import java.util.function.Consumer;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.iterators.ranked.ExecutionContext;
import com.tr.rp.iterators.ranked.RankedIterator;
import com.tr.rp.varstore.VarStore;
import com.tr.rp.varstore.types.Type;

/**
 * pop(stack): pops top element from stack
 */
public class PopFunction extends AbstractFunctionCall {

	private final String stackVar;
	
	public PopFunction(String stackVar) {
		this.stackVar = stackVar;
	}
	
	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return this;
	}

	@Override
	public String toString() {
		return "pop("+stackVar+")";
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof PopFunction) {
			return ((PopFunction)o).stackVar.equals(stackVar);
		}
		return false;
	}

	@Override
	public RankedIterator<VarStore> getIterator(ExecutionContext c, String assignToVar,
			RankedIterator<VarStore> parent) throws RPLException {
		return new RankedIterator<VarStore>() {

			private Object poppedValue;
			
			private void setPoppedValue(Object value) {
				this.poppedValue = value;
			}
			
			private Object getPoppedValue() {
				return poppedValue;
			}
			
			@Override
			public boolean next() throws RPLException {
				return parent.next();
			}

			@Override
			public VarStore getItem() throws RPLException {
				VarStore v = parent.getItem();
				v = v.create(stackVar, v.getValue(stackVar, Type.STACK).pop(this::setPoppedValue));
				v = v.create(assignToVar, getPoppedValue());
				return v;
			}

			@Override
			public int getRank() {
				return parent.getRank();
			}
			
		};
	}

	@Override
	public AbstractExpression transformRankExpressions(VarStore v, int rank) throws RPLException {
		return this;
	}

	@Override
	public AbstractExpression replaceEmbeddedFunctionCall(AbstractFunctionCall fc, String var) {
		return this;
	}

}
