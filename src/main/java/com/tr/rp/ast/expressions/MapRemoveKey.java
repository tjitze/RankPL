package com.tr.rp.ast.expressions;

import java.util.Objects;
import java.util.Set;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.varstore.VarStore;
import com.tr.rp.varstore.types.Type;

/**
 * removeKey(map, key): remove given key (and associated value) from map
 */
public class MapRemoveKey extends AbstractExpression {

	private final AbstractExpression map;
	private final AbstractExpression key;
	
	public MapRemoveKey(AbstractExpression map, AbstractExpression value) {
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
		return new MapRemoveKey((AbstractExpression)map.replaceVariable(a, b), 
				(AbstractExpression)key.replaceVariable(a, b));
	}

	@Override
	public boolean needsRankExpressionTransformation() {
		return map.needsRankExpressionTransformation() || key.needsRankExpressionTransformation();
	}

	@Override
	public AbstractExpression doRankExpressionTransformation(VarStore v, int rank) throws RPLException {
		return new MapRemoveKey(map.doRankExpressionTransformation(v, rank),
				key.doRankExpressionTransformation(v, rank));
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
		return new MapRemoveKey((AbstractExpression)map.replaceEmbeddedFunctionCall(fc, var),
				(AbstractExpression)key.replaceEmbeddedFunctionCall(fc, var));
	}

	@Override
	public Object getValue(VarStore e) throws RPLException {
		return map.getValue(e, Type.MAP).remove(key.getValue(e));
	}

	@Override
	public boolean hasDefiniteValue() {
		return map.hasDefiniteValue() && key.hasDefiniteValue();
	}

	@Override
	public Object getDefiniteValue() throws RPLException {
		return map.getDefiniteValue(Type.MAP).remove(key.getDefiniteValue());
	}

	public String toString() {
		return "removeKey(" + map + ", "+ key +")";
	}
	
	public boolean equals(Object o) {
		return (o instanceof MapRemoveKey) && ((MapRemoveKey)o).map.equals(map) 
				&& ((MapRemoveKey)o).key.equals(key);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getClass().hashCode(), map.hashCode(), key.hashCode());
	}

}
