package com.tr.rp.ast.expressions;

import java.util.Objects;
import java.util.Set;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.exceptions.RPLIndexOutOfBoundsException;
import com.tr.rp.exceptions.RPLMissingKeyException;
import com.tr.rp.exceptions.RPLTypeError;
import com.tr.rp.exceptions.RPLUndefinedException;
import com.tr.rp.varstore.VarStore;
import com.tr.rp.varstore.types.PersistentMap;
import com.tr.rp.varstore.types.PersistentSet;
import com.tr.rp.varstore.types.Type;

/**
 * get(map, key): return value of key of map
 */
public class ListGet extends AbstractExpression {

	private final AbstractExpression list;
	private final AbstractExpression index;
	
	public ListGet(AbstractExpression map, AbstractExpression value) {
		this.list = map;
		this.index = value;
	}

	@Override
	public void getVariables(Set<String> list) {
		this.list.getVariables(list);
		index.getVariables(list);
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return new ListGet((AbstractExpression)list.replaceVariable(a, b), 
				(AbstractExpression)index.replaceVariable(a, b));
	}

	@Override
	public boolean needsRankExpressionTransformation() {
		return list.needsRankExpressionTransformation() || index.needsRankExpressionTransformation();
	}

	@Override
	public AbstractExpression doRankExpressionTransformation(VarStore v, int rank) throws RPLException {
		return new ListGet(list.doRankExpressionTransformation(v, rank),
				index.doRankExpressionTransformation(v, rank));
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
		return new ListGet((AbstractExpression)list.replaceEmbeddedFunctionCall(fc, var),
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
		Object value = list.getDefiniteValue(Type.MAP).get(index.getDefiniteValue());
		if (value == null) {
			throw new RPLMissingKeyException(this, index.getDefiniteValue());
		}
		return value;
	}

	public String toString() {
		return "get(" + list + ", "+ index +")";
	}
	
	public boolean equals(Object o) {
		return (o instanceof ListGet) && ((ListGet)o).list.equals(list) 
				&& ((ListGet)o).index.equals(index);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(list.hashCode(), index.hashCode());
	}

}
