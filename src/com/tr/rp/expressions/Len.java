package com.tr.rp.expressions;

import java.util.Set;

import com.tr.rp.core.Expression;
import com.tr.rp.core.LanguageElement;
import com.tr.rp.core.VarStore;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.exceptions.RPLTypeError;

/**
 * The len expression. Returns the length of the given array or string.
 */
public class Len extends Expression {

	private final Expression e;
	
	public Len(Expression e) {
		this.e = e;
	}
	
	@Override
	public boolean containsVariable(String var) {
		return e.containsVariable(var);
	}

	@Override
	public void getVariables(Set<String> list) {
		e.getVariables(list);
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return new Len((Expression)e.replaceVariable(a, b));
	}

	@Override
	public boolean hasRankExpression() {
		return e.hasRankExpression();
	}

	@Override
	public Expression transformRankExpressions(VarStore v, int rank) throws RPLException {
		return new Len(e.transformRankExpressions(v, rank));
	}

	@Override
	public FunctionCall getEmbeddedFunctionCall() {
		return e.getEmbeddedFunctionCall();
	}

	@Override
	public Expression replaceEmbeddedFunctionCall(FunctionCall fc, String var) {
		return new Len((Expression)e.replaceEmbeddedFunctionCall(fc, var));
	}

	@Override
	public Object getValue(VarStore v) throws RPLException {
		Object o = e.getValue(v);
		if (o != null) {
			if (o instanceof String) {
				return ((String)o).length();
			} else if (o instanceof PersistentList) {
				return ((PersistentList)o).size();
			}
		}
		throw new RPLTypeError("list or string", o);
	}

	@Override
	public boolean hasDefiniteValue() {
		return e.hasDefiniteValue();
	}

	@Override
	public Object getDefiniteValue() throws RPLException {
		return this.e.getDefiniteListValue().size();
	}

	public String tostring() {
		String es = e.toString();
		if (es.startsWith("(") && es.endsWith(")")) {
			es = es.substring(1, es.length()-1);
		}
		return "len(" + es + ")";
	}

	public boolean equals(Object o) {
		return (o instanceof Len) && ((Len)o).e.equals(e);
	}

}
