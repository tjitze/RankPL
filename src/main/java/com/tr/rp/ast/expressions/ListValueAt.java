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
 * list = valueAt(list, index) - get item in list
 */
public class ListValueAt extends AbstractExpression {

	private final AbstractExpression list;
	private final AbstractExpression index;
	
	public ListValueAt(AbstractExpression list, AbstractExpression index) {
		this.list = list;
		this.index = index;
	}

	@Override
	public void getVariables(Set<String> l) {
		list.getVariables(l);
		index.getVariables(l);
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return new ListValueAt((AbstractExpression)list.replaceVariable(a, b), 
				(AbstractExpression)index.replaceVariable(a, b));
	}

	@Override
	public boolean hasRankExpression() {
		return list.hasRankExpression() || index.hasRankExpression();
	}

	@Override
	public AbstractExpression transformRankExpressions(VarStore v, int rank) throws RPLException {
		return new ListValueAt(list.transformRankExpressions(v, rank),
				index.transformRankExpressions(v, rank));
	}

	@Override
	public AbstractFunctionCall getEmbeddedFunctionCall() {
		AbstractFunctionCall fc = list.getEmbeddedFunctionCall();
		if (fc == null) {
			return index.getEmbeddedFunctionCall();
		} else {
			return fc;
		}
	}

	@Override
	public AbstractExpression replaceEmbeddedFunctionCall(AbstractFunctionCall fc, String var) {
		return new ListValueAt((AbstractExpression)list.replaceEmbeddedFunctionCall(fc, var),
				(AbstractExpression)index.replaceEmbeddedFunctionCall(fc, var));
	}

	@Override
	public Object getValue(VarStore e) throws RPLException {
		try {
			return list.getValue(e, Type.LIST).get(index.getValue(e, Type.INT));
		} catch (IndexOutOfBoundsException ex) {
			throw new RPLIndexOutOfBoundsException(index.getValue(e, Type.INT), list.getValue(e, Type.LIST).size(), this);
		}
	}

	@Override
	public boolean hasDefiniteValue() {
		return list.hasDefiniteValue() && index.hasDefiniteValue();
	}

	@Override
	public Object getDefiniteValue() throws RPLException {
		try {
			return list.getDefiniteValue(Type.LIST).get(index.getDefiniteValue(Type.INT));
		} catch (IndexOutOfBoundsException ex) {
			throw new RPLIndexOutOfBoundsException(index.getDefiniteValue(Type.INT), list.getDefiniteValue(Type.LIST).size(), this);
		}
	}

	public String toString() {
		return "valueAt(" + list + ", "+ index +")";
	}
	
	public boolean equals(Object o) {
		return (o instanceof ListValueAt) && ((ListValueAt)o).list.equals(list) 
				&& ((ListValueAt)o).index.equals(index);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getClass().hashCode(), list.hashCode(), index.hashCode());
	}

}
