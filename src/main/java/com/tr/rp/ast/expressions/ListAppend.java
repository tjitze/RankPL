package com.tr.rp.ast.expressions;

import java.util.Objects;
import java.util.Set;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.varstore.VarStore;
import com.tr.rp.varstore.types.Type;

/**
 * list = append(list, item) - append item to list
 */
public class ListAppend extends AbstractExpression {

	private final AbstractExpression list;
	private final AbstractExpression value;
	
	public ListAppend(AbstractExpression list, AbstractExpression value) {
		this.list = list;
		this.value = value;
	}

	@Override
	public void getVariables(Set<String> l) {
		list.getVariables(l);
		value.getVariables(l);
	}

	@Override
	public boolean hasRankExpression() {
		return list.hasRankExpression() || value.hasRankExpression();
	}

	@Override
	public AbstractExpression transformRankExpressions(VarStore v, int rank) throws RPLException {
		return new ListAppend(list.transformRankExpressions(v, rank),
				value.transformRankExpressions(v, rank));
	}

	@Override
	public AbstractFunctionCall getEmbeddedFunctionCall() {
		AbstractFunctionCall fc = list.getEmbeddedFunctionCall();
		if (fc == null) {
			return value.getEmbeddedFunctionCall();
		} else {
			return fc;
		}
	}

	@Override
	public AbstractExpression replaceEmbeddedFunctionCall(AbstractFunctionCall fc, String var) {
		return new ListAppend((AbstractExpression)list.replaceEmbeddedFunctionCall(fc, var),
				(AbstractExpression)value.replaceEmbeddedFunctionCall(fc, var));
	}

	@Override
	public Object getValue(VarStore e) throws RPLException {
		return list.getValue(e, Type.LIST).append(value.getValue(e));
	}

	@Override
	public boolean hasDefiniteValue() {
		return list.hasDefiniteValue() && value.hasDefiniteValue();
	}

	@Override
	public Object getDefiniteValue() throws RPLException {
		return list.getDefiniteValue(Type.LIST).append(value.getDefiniteValue());
	}

	public String toString() {
		return "append(" + list + ", "+ value +")";
	}
	
	public boolean equals(Object o) {
		return (o instanceof ListAppend) && ((ListAppend)o).list.equals(list) 
				&& ((ListAppend)o).value.equals(value);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getClass().hashCode(), list.hashCode(), value.hashCode());
	}

}
