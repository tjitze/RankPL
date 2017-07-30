package com.tr.rp.ast.expressions;

import java.util.Objects;
import java.util.Set;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.exceptions.RPLTypeError;
import com.tr.rp.varstore.VarStore;
import com.tr.rp.varstore.types.PersistentMap;
import com.tr.rp.varstore.types.PersistentSet;
import com.tr.rp.varstore.types.Type;

/**
 * containsKey(map, value): returns true iff map contains key
 */
public class MapContainsKey extends AbstractExpression {

	private final AbstractExpression map;
	private final AbstractExpression value;
	
	public MapContainsKey(AbstractExpression map, AbstractExpression value) {
		this.map = map;
		this.value = value;
	}

	@Override
	public void getVariables(Set<String> list) {
		map.getVariables(list);
		value.getVariables(list);
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return new MapContainsKey((AbstractExpression)map.replaceVariable(a, b), 
				(AbstractExpression)value.replaceVariable(a, b));
	}

	@Override
	public boolean needsRankExpressionTransformation() {
		return map.needsRankExpressionTransformation() || value.needsRankExpressionTransformation();
	}

	@Override
	public AbstractExpression doRankExpressionTransformation(VarStore v, int rank) throws RPLException {
		return new MapContainsKey(map.doRankExpressionTransformation(v, rank),
				value.doRankExpressionTransformation(v, rank));
	}

	@Override
	public AbstractFunctionCall getEmbeddedFunctionCall() {
		AbstractFunctionCall fc = map.getEmbeddedFunctionCall();
		if (fc == null) {
			return value.getEmbeddedFunctionCall();
		} else {
			return fc;
		}
	}

	@Override
	public AbstractExpression replaceEmbeddedFunctionCall(AbstractFunctionCall fc, String var) {
		return new MapContainsKey((AbstractExpression)map.replaceEmbeddedFunctionCall(fc, var),
				(AbstractExpression)value.replaceEmbeddedFunctionCall(fc, var));
	}

	@Override
	public Object getValue(VarStore e) throws RPLException {
		return map.getValue(e, Type.MAP).containsKey(value.getValue(e));
	}

	@Override
	public boolean hasDefiniteValue() {
		return map.hasDefiniteValue() && value.hasDefiniteValue();
	}

	@Override
	public Object getDefiniteValue() throws RPLException {
		return map.getDefiniteValue(Type.MAP).containsKey(value.getDefiniteValue());
	}

	public String toString() {
		return "containsKey(" + map + ", "+ value +")";
	}
	
	public boolean equals(Object o) {
		return (o instanceof MapContainsKey) && ((MapContainsKey)o).map.equals(map) 
				&& ((MapContainsKey)o).value.equals(value);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(map.hashCode(), value.hashCode());
	}

}
