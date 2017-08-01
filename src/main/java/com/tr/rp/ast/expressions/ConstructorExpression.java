package com.tr.rp.ast.expressions;

import java.util.Set;
import java.util.function.Supplier;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.varstore.VarStore;

/**
 * Returns a new, empty map
 */
public class ConstructorExpression extends AbstractExpression {
	
	private final Supplier<Object> supplier;
	private final String name;
	
	public ConstructorExpression(String name, Supplier<Object> supplier) {
		this.name = name;
		this.supplier = supplier;
	}
	
	@Override
	public void getVariables(Set<String> list) { }

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return this;
	}

	@Override
	public boolean hasRankExpression() {
		return false;
	}

	@Override
	public AbstractExpression transformRankExpressions(VarStore v, int rank) throws RPLException {
		return this;
	}

	@Override
	public AbstractFunctionCall getEmbeddedFunctionCall() {
		return null;
	}

	@Override
	public AbstractExpression replaceEmbeddedFunctionCall(AbstractFunctionCall fc, String var) {
		return this;
	}

	@Override
	public Object getValue(VarStore e) throws RPLException {
		return supplier.get();
	}

	@Override
	public boolean hasDefiniteValue() {
		return true;
	}

	@Override
	public Object getDefiniteValue() throws RPLException {
		return supplier.get();
	}
	
	public String toString() {
		return name;
	}
	
	public boolean equals(Object o) {
		return o.getClass().equals(getClass());
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

}
