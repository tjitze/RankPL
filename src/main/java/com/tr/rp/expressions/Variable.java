package com.tr.rp.expressions;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.tr.rp.core.Expression;
import com.tr.rp.core.LanguageElement;
import com.tr.rp.core.VarStore;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.exceptions.RPLIndexOutOfBoundsException;
import com.tr.rp.exceptions.RPLTypeError;
import com.tr.rp.exceptions.RPLUndefinedException;

/**
 * A variable
 */
public class Variable extends Expression {

	private final String name;
	
	public Variable(String name) {
		this.name = name;
	}
	
	@Override
	public boolean containsVariable(String var) {
		return var.equals(name);
	}

	@Override
	public void getVariables(Set<String> list) {
		list.add(name);
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return new Variable(a.equals(name)? b: name);
	}

	@Override
	public boolean hasRankExpression() {
		return false;
	}

	@Override
	public Expression transformRankExpressions(VarStore v, int rank) throws RPLException {
		return this;
	}

	@Override
	public AbstractFunctionCall getEmbeddedFunctionCall() {
		return null;
	}

	@Override
	public Expression replaceEmbeddedFunctionCall(AbstractFunctionCall fc, String var) {
		return new Variable(name);
	}

	@Override
	public Object getValue(VarStore e) throws RPLException {
		Object o = e.getValue(name);
		if (o == null) {
			throw new RPLUndefinedException(this);
		}
		return o;
	}

	/**
	 * @return True iff value of this variable is defined (not null)
	 */
	public boolean isDefined(VarStore e) throws RPLException {
		try {
			getValue(e);
		} catch (RPLUndefinedException ex) {
			return false;
		}
		return true;
	}
	
	@Override
	public boolean hasDefiniteValue() {
		return false;
	}

	@Override
	public Object getDefiniteValue() throws RPLException {
		return null;
	}
	
	public String toString() {
		return name;
	}
	
	public boolean equals(Object o) {
		return (o instanceof Variable) && ((Variable)o).name.equals(name);
	}

}
