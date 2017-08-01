package com.tr.rp.ast.expressions;

import java.util.Objects;
import java.util.Set;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.exceptions.RPLIndexOutOfBoundsException;
import com.tr.rp.varstore.VarStore;
import com.tr.rp.varstore.types.Type;

/**
 * replace(list, index, value): replace value in list
 */
public class ListReplace extends AbstractExpression {

	private final AbstractExpression list;
	private final AbstractExpression index;
	private final AbstractExpression value;
	
	public ListReplace(AbstractExpression list, AbstractExpression index, AbstractExpression value) {
		this.list = list;
		this.index = index;
		this.value = value;
	}

	@Override
	public void getVariables(Set<String> l) {
		list.getVariables(l);
		index.getVariables(l);
		value.getVariables(l);
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return new ListReplace((AbstractExpression)list.replaceVariable(a, b), 
				(AbstractExpression)index.replaceVariable(a, b),
				(AbstractExpression)value.replaceVariable(a, b));
	}

	@Override
	public boolean hasRankExpression() {
		return list.hasRankExpression() || index.hasRankExpression() || value.hasRankExpression();
	}

	@Override
	public AbstractExpression transformRankExpressions(VarStore v, int rank) throws RPLException {
		return new ListReplace(list.transformRankExpressions(v, rank),
				index.transformRankExpressions(v, rank),
				value.transformRankExpressions(v, rank));
	}

	@Override
	public AbstractFunctionCall getEmbeddedFunctionCall() {
		AbstractFunctionCall fc = list.getEmbeddedFunctionCall();
		if (fc == null) fc = index.getEmbeddedFunctionCall();
		if (fc == null) fc = value.getEmbeddedFunctionCall();
		return fc;
	}

	@Override
	public AbstractExpression replaceEmbeddedFunctionCall(AbstractFunctionCall fc, String var) {
		return new ListReplace((AbstractExpression)list.replaceEmbeddedFunctionCall(fc, var),
				(AbstractExpression)index.replaceEmbeddedFunctionCall(fc, var),
				(AbstractExpression)value.replaceEmbeddedFunctionCall(fc, var));
	}

	@Override
	public Object getValue(VarStore e) throws RPLException {
		try {
			return list.getValue(e, Type.LIST).replace(index.getValue(e, Type.INT), value.getValue(e));
		} catch (IndexOutOfBoundsException ex) {
			throw new RPLIndexOutOfBoundsException(index.getValue(e, Type.INT), list.getValue(e, Type.LIST).size(), this);
		}
	}

	@Override
	public boolean hasDefiniteValue() {
		return list.hasDefiniteValue() && index.hasDefiniteValue() && value.hasDefiniteValue();
	}

	@Override
	public Object getDefiniteValue() throws RPLException {
		try {
			return list.getDefiniteValue(Type.LIST).replace(index.getDefiniteValue(Type.INT), value.getDefiniteValue());
		} catch (IndexOutOfBoundsException ex) {
			throw new RPLIndexOutOfBoundsException(index.getDefiniteValue(Type.INT), list.getDefiniteValue(Type.LIST).size(), this);
		}
	}

	public String toString() {
		return "replace(" + list + ", "+ index + ", " + value + ")";
	}
	
	public boolean equals(Object o) {
		return (o instanceof ListReplace) && ((ListReplace)o).list.equals(list) 
				&& ((ListReplace)o).index.equals(index)
				&& ((ListReplace)o).value.equals(index);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getClass().hashCode(), list.hashCode(), index.hashCode(), value.hashCode());
	}

}
