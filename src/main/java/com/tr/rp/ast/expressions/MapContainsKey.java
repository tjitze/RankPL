package com.tr.rp.ast.expressions;

import java.util.Objects;
import java.util.Set;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.ast.StringTools;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.varstore.VarStore;
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
	public boolean hasRankExpression() {
		return map.hasRankExpression() || value.hasRankExpression();
	}

	@Override
	public AbstractExpression transformRankExpressions(VarStore v, int rank) throws RPLException {
		AbstractExpression e = new MapContainsKey(map.transformRankExpressions(v, rank), value.transformRankExpressions(v, rank));
		e.setLineNumber(getLineNumber());
		return e;
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
		AbstractExpression e = new MapContainsKey((AbstractExpression) map.replaceEmbeddedFunctionCall(fc, var),
				(AbstractExpression) value.replaceEmbeddedFunctionCall(fc, var));
		e.setLineNumber(getLineNumber());
		return e;
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
		return "containsKey(" + StringTools.stripPars(map.toString()) + ", " + StringTools.stripPars(value.toString())
				+ ")";
	}

	public boolean equals(Object o) {
		return (o instanceof MapContainsKey) && ((MapContainsKey) o).map.equals(map)
				&& ((MapContainsKey) o).value.equals(value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(map.hashCode(), value.hashCode());
	}

}
