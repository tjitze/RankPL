package com.tr.rp.ast.expressions;

import java.util.Objects;
import java.util.Set;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.varstore.VarStore;
import com.tr.rp.varstore.types.Type;

/**
 * put(map, key, value): returns value assigned to key in map
 */
public class MapPut extends AbstractExpression {

	private final AbstractExpression map;
	private final AbstractExpression key;
	private final AbstractExpression value;
	
	public MapPut(AbstractExpression map, AbstractExpression key, AbstractExpression value) {
		this.map = map;
		this.key = key;
		this.value = value;
	}

	@Override
	public void getVariables(Set<String> list) {
		map.getVariables(list);
		key.getVariables(list);
		value.getVariables(list);
	}

	@Override
	public boolean hasRankExpression() {
		return map.hasRankExpression() || key.hasRankExpression() || value.hasRankExpression();
	}

	@Override
	public AbstractExpression transformRankExpressions(VarStore v, int rank) throws RPLException {
		return new MapPut(map.transformRankExpressions(v, rank),
				key.transformRankExpressions(v, rank),
				value.transformRankExpressions(v, rank));
	}

	@Override
	public AbstractFunctionCall getEmbeddedFunctionCall() {
		AbstractFunctionCall fc = map.getEmbeddedFunctionCall();
		if (fc == null) fc = key.getEmbeddedFunctionCall();
		if (fc == null) fc = value.getEmbeddedFunctionCall();
		return fc;
	}

	@Override
	public AbstractExpression replaceEmbeddedFunctionCall(AbstractFunctionCall fc, String var) {
		return new MapPut((AbstractExpression)map.replaceEmbeddedFunctionCall(fc, var),
				(AbstractExpression)key.replaceEmbeddedFunctionCall(fc, var),
				(AbstractExpression)value.replaceEmbeddedFunctionCall(fc, var));
	}

	@Override
	public Object getValue(VarStore e) throws RPLException {
		return map.getValue(e, Type.MAP).add(key.getValue(e), value.getValue(e));
	}

	@Override
	public boolean hasDefiniteValue() {
		return map.hasDefiniteValue() && key.hasDefiniteValue() && value.hasDefiniteValue();
	}

	@Override
	public Object getDefiniteValue() throws RPLException {
		return map.getDefiniteValue(Type.MAP).add(key.getDefiniteValue(), value.getDefiniteValue());
	}

	public String toString() {
		return "put(" + map + ", "+ key + ", " + value + ")";
	}
	
	public boolean equals(Object o) {
		return (o instanceof MapPut) && ((MapPut)o).map.equals(map) 
				&& ((MapPut)o).key.equals(key)
				&& ((MapPut)o).value.equals(key);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getClass().hashCode(), map.hashCode(), key.hashCode(), value.hashCode());
	}

}
