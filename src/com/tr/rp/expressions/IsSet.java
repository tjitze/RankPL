package com.tr.rp.expressions;

import java.util.Arrays;
import java.util.Set;

import com.tr.rp.core.Expression;
import com.tr.rp.core.LanguageElement;
import com.tr.rp.core.VarStore;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.exceptions.RPLIndexOutOfBoundsException;
import com.tr.rp.exceptions.RPLTypeError;

/**
 * Returns true iff given variable is set.
 */
public class IsSet extends Expression {

	private final Variable var;

	public IsSet(Variable var) {
		this.var = var;
	}
	
	@Override
	public boolean containsVariable(String var) {
		return this.var.containsVariable(var);
	}

	@Override
	public void getVariables(Set<String> list) {
		var.getVariables(list);
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return new IsSet((Variable)var.replaceVariable(a, b));
	}

	@Override
	public boolean hasRankExpression() {
		return var.hasRankExpression();
	}

	@Override
	public Expression transformRankExpressions(VarStore v, int rank) throws RPLException {
		return new IsSet((Variable)var.transformRankExpressions(v, rank));
	}

	@Override
	public AbstractFunctionCall getEmbeddedFunctionCall() {
		return var.getEmbeddedFunctionCall();
	}

	@Override
	public Expression replaceEmbeddedFunctionCall(AbstractFunctionCall fc, String var) {
		return new IsSet((Variable)this.var.replaceEmbeddedFunctionCall(fc, var));
	}

	@Override
	public Object getValue(VarStore e) throws RPLException {
		return var.isDefined(e);
	}

	@Override
	public boolean hasDefiniteValue() {
		return var.hasDefiniteValue();
	}

	@Override
	public Object getDefiniteValue() throws RPLException {
		return var.getDefiniteValue() != null;
	}

	public String toString() {
		return "isset(" + var + ")";
	}

	public boolean equals(Object o) {
		return (o instanceof IsSet) && ((IsSet)o).var.equals(var);
	}

}
