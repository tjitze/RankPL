package com.tr.rp.ast.expressions;

import java.util.Set;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.varstore.VarStore;
import com.tr.rp.varstore.types.PersistentMap;
import com.tr.rp.varstore.types.PersistentSet;
import com.tr.rp.varstore.types.PersistentStack;

/**
 * Returns a new, empty map
 */
public class MapNew extends AbstractExpression {
	
	public MapNew() {
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
		return new PersistentMap<Object, Object>();
	}

	@Override
	public boolean hasDefiniteValue() {
		return true;
	}

	@Override
	public Object getDefiniteValue() throws RPLException {
		return new PersistentMap<Object, Object>();
	}
	
	public String toString() {
		return "newMap()";
	}
	
	public boolean equals(Object o) {
		return o.getClass().equals(getClass());
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

}
