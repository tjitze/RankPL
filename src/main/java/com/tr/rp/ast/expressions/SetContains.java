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
 * contains(mapOrSet, value): returns true iff set contains value or map contains ket
 */
public class SetContains extends AbstractExpression {

	private final AbstractExpression mapOrSet;
	private final AbstractExpression value;
	
	public SetContains(AbstractExpression mapOrSet, AbstractExpression value) {
		this.mapOrSet = mapOrSet;
		this.value = value;
	}

	@Override
	public void getVariables(Set<String> list) {
		mapOrSet.getVariables(list);
		value.getVariables(list);
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return new SetContains((AbstractExpression)mapOrSet.replaceVariable(a, b), 
				(AbstractExpression)value.replaceVariable(a, b));
	}

	@Override
	public boolean needsRankExpressionTransformation() {
		return mapOrSet.needsRankExpressionTransformation() || value.needsRankExpressionTransformation();
	}

	@Override
	public AbstractExpression doRankExpressionTransformation(VarStore v, int rank) throws RPLException {
		return new SetContains(mapOrSet.doRankExpressionTransformation(v, rank),
				value.doRankExpressionTransformation(v, rank));
	}

	@Override
	public AbstractFunctionCall getEmbeddedFunctionCall() {
		AbstractFunctionCall fc = mapOrSet.getEmbeddedFunctionCall();
		if (fc == null) {
			return value.getEmbeddedFunctionCall();
		} else {
			return fc;
		}
	}

	@Override
	public AbstractExpression replaceEmbeddedFunctionCall(AbstractFunctionCall fc, String var) {
		return new SetContains((AbstractExpression)mapOrSet.replaceEmbeddedFunctionCall(fc, var),
				(AbstractExpression)value.replaceEmbeddedFunctionCall(fc, var));
	}

	@Override
	public Object getValue(VarStore e) throws RPLException {
		if (Type.SET.test(mapOrSet.getValue(e))) {
			return mapOrSet.getValue(e, Type.SET).contains(value.getValue(e));
		} else if (Type.MAP.test(mapOrSet.getValue(e))) {
			return mapOrSet.getValue(e, Type.MAP).get(value.getValue(e)) != null;
		} else {
			throw new RPLTypeError("map or set", mapOrSet.getValue(e), this);
		}
	}

	@Override
	public boolean hasDefiniteValue() {
		return mapOrSet.hasDefiniteValue() && value.hasDefiniteValue();
	}

	@Override
	public Object getDefiniteValue() throws RPLException {
		if (Type.SET.test(mapOrSet.getDefiniteValue())) {
			return mapOrSet.getDefiniteValue(Type.SET).contains(value.getDefiniteValue());
		} else if (Type.MAP.test(mapOrSet.getDefiniteValue())) {
			return mapOrSet.getDefiniteValue(Type.MAP).get(value.getDefiniteValue()) != null;
		} else {
			throw new RPLTypeError("map or set", mapOrSet.getDefiniteValue(), this);
		}
	}

	public String toString() {
		return "contains(" + mapOrSet + ", "+ value +")";
	}
	
	public boolean equals(Object o) {
		return (o instanceof SetContains) && ((SetContains)o).mapOrSet.equals(mapOrSet) 
				&& ((SetContains)o).value.equals(value);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(mapOrSet.hashCode(), value.hashCode());
	}

}
