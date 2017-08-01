package com.tr.rp.ast.expressions;

import java.util.Objects;
import java.util.Set;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.exceptions.RPLIndexOutOfBoundsException;
import com.tr.rp.exceptions.RPLMissingKeyException;
import com.tr.rp.exceptions.RPLTypeError;
import com.tr.rp.varstore.VarStore;
import com.tr.rp.varstore.types.PersistentList;
import com.tr.rp.varstore.types.PersistentMap;
import com.tr.rp.varstore.types.Type;

/**
 * get(a, b): return value of key b of map a or value of index b of list a
 */
public class Get extends AbstractExpression {

	private final AbstractExpression mapOrList;
	private final AbstractExpression keyOrIndex;
	
	public Get(AbstractExpression mapOrList, AbstractExpression keyOrIndex) {
		this.mapOrList = mapOrList;
		this.keyOrIndex = keyOrIndex;
	}

	@Override
	public void getVariables(Set<String> list) {
		mapOrList.getVariables(list);
		keyOrIndex.getVariables(list);
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return new Get((AbstractExpression)mapOrList.replaceVariable(a, b), 
				(AbstractExpression)keyOrIndex.replaceVariable(a, b));
	}

	@Override
	public boolean hasRankExpression() {
		return mapOrList.hasRankExpression() || keyOrIndex.hasRankExpression();
	}

	@Override
	public AbstractExpression transformRankExpressions(VarStore v, int rank) throws RPLException {
		return new Get(mapOrList.transformRankExpressions(v, rank),
				keyOrIndex.transformRankExpressions(v, rank));
	}

	@Override
	public AbstractFunctionCall getEmbeddedFunctionCall() {
		AbstractFunctionCall fc = mapOrList.getEmbeddedFunctionCall();
		if (fc == null) {
			return keyOrIndex.getEmbeddedFunctionCall();
		} else {
			return fc;
		}
	}

	@Override
	public AbstractExpression replaceEmbeddedFunctionCall(AbstractFunctionCall fc, String var) {
		return new Get((AbstractExpression)mapOrList.replaceEmbeddedFunctionCall(fc, var),
				(AbstractExpression)keyOrIndex.replaceEmbeddedFunctionCall(fc, var));
	}

	@Override
	public Object getValue(VarStore e) throws RPLException {
		Object s = mapOrList.getValue(e);
		if (s instanceof PersistentMap) {
			Object value = mapOrList.getValue(e, Type.MAP).get(keyOrIndex.getValue(e));
			if (value == null) {
				throw new RPLMissingKeyException(this, keyOrIndex.getValue(e));
			}
			return value;
		} else if (s instanceof PersistentList) {
			try {
				return mapOrList.getValue(e, Type.LIST).get(keyOrIndex.getValue(e, Type.INT));
			} catch (IndexOutOfBoundsException ex) {
				throw new RPLIndexOutOfBoundsException(keyOrIndex.getValue(e, Type.INT), 
						mapOrList.getValue(e, Type.LIST).size(), this);
			}
		}
		throw new RPLTypeError("map or list", s, this);
	}

	@Override
	public boolean hasDefiniteValue() {
		return mapOrList.hasDefiniteValue() && keyOrIndex.hasDefiniteValue();
	}

	@Override
	public Object getDefiniteValue() throws RPLException {
		Object value = mapOrList.getDefiniteValue(Type.MAP).get(keyOrIndex.getDefiniteValue());
		if (value == null) {
			throw new RPLMissingKeyException(this, keyOrIndex.getDefiniteValue());
		}
		return value;
	}

	public String toString() {
		return "get(" + mapOrList + ", "+ keyOrIndex +")";
	}
	
	public boolean equals(Object o) {
		return (o instanceof Get) && ((Get)o).mapOrList.equals(mapOrList) 
				&& ((Get)o).keyOrIndex.equals(keyOrIndex);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(mapOrList.hashCode(), keyOrIndex.hashCode());
	}

}
