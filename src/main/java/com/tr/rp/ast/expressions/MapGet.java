package com.tr.rp.ast.expressions;

import java.util.Objects;
import java.util.Set;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.exceptions.RPLException;
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
public class MapGet extends AbstractExpression {

	private final AbstractExpression map;
	private final AbstractExpression key;
	
	public MapGet(AbstractExpression map, AbstractExpression value) {
		this.map = map;
		this.key = value;
	}

	@Override
	public void getVariables(Set<String> list) {
		map.getVariables(list);
		key.getVariables(list);
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return new MapGet((AbstractExpression)map.replaceVariable(a, b), 
				(AbstractExpression)key.replaceVariable(a, b));
	}

	@Override
	public boolean hasRankExpression() {
		return map.hasRankExpression() || key.hasRankExpression();
	}

	@Override
	public AbstractExpression transformRankExpressions(VarStore v, int rank) throws RPLException {
		return new MapGet(map.transformRankExpressions(v, rank),
				key.transformRankExpressions(v, rank));
	}

	@Override
	public AbstractFunctionCall getEmbeddedFunctionCall() {
		AbstractFunctionCall fc = map.getEmbeddedFunctionCall();
		if (fc == null) {
			return key.getEmbeddedFunctionCall();
		} else {
			return fc;
		}
	}

	@Override
	public AbstractExpression replaceEmbeddedFunctionCall(AbstractFunctionCall fc, String var) {
		return new MapGet((AbstractExpression)map.replaceEmbeddedFunctionCall(fc, var),
				(AbstractExpression)key.replaceEmbeddedFunctionCall(fc, var));
	}

	@Override
	public Object getValue(VarStore e) throws RPLException {
		Object value = map.getValue(e, Type.MAP).get(key.getValue(e));
		if (value == null) {
			throw new RPLMissingKeyException(this, key.getValue(e));
		}
		return value;
	}

	@Override
	public boolean hasDefiniteValue() {
		return map.hasDefiniteValue() && key.hasDefiniteValue();
	}

	@Override
	public Object getDefiniteValue() throws RPLException {
		Object value = map.getDefiniteValue(Type.MAP).get(key.getDefiniteValue());
		if (value == null) {
			throw new RPLMissingKeyException(this, key.getDefiniteValue());
		}
		return value;
	}

	public String toString() {
		return "get(" + map + ", "+ key +")";
	}
	
	public boolean equals(Object o) {
		return (o instanceof MapGet) && ((MapGet)o).map.equals(map) 
				&& ((MapGet)o).key.equals(key);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(map.hashCode(), key.hashCode());
	}

}
