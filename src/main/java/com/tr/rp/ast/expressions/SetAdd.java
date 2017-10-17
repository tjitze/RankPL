package com.tr.rp.ast.expressions;

import java.util.Objects;
import java.util.Set;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.varstore.VarStore;
import com.tr.rp.varstore.types.Type;

/**
 * set = add(set, item) - add item to set
 */
public class SetAdd extends AbstractExpression {

	private final AbstractExpression set;
	private final AbstractExpression value;
	
	public SetAdd(AbstractExpression set, AbstractExpression value) {
		this.set = set;
		this.value = value;
	}

	@Override
	public void getVariables(Set<String> list) {
		set.getVariables(list);
		value.getVariables(list);
	}

	@Override
	public boolean hasRankExpression() {
		return set.hasRankExpression() || value.hasRankExpression();
	}

	@Override
	public AbstractExpression transformRankExpressions(VarStore v, int rank) throws RPLException {
		return new SetAdd(set.transformRankExpressions(v, rank),
				value.transformRankExpressions(v, rank));
	}

	@Override
	public AbstractFunctionCall getEmbeddedFunctionCall() {
		AbstractFunctionCall fc = set.getEmbeddedFunctionCall();
		if (fc == null) {
			return value.getEmbeddedFunctionCall();
		} else {
			return fc;
		}
	}

	@Override
	public AbstractExpression replaceEmbeddedFunctionCall(AbstractFunctionCall fc, String var) {
		return new SetAdd((AbstractExpression)set.replaceEmbeddedFunctionCall(fc, var),
				(AbstractExpression)value.replaceEmbeddedFunctionCall(fc, var));
	}

	@Override
	public Object getValue(VarStore e) throws RPLException {
		return set.getValue(e, Type.SET).add(value.getValue(e));
	}

	@Override
	public boolean hasDefiniteValue() {
		return set.hasDefiniteValue() && value.hasDefiniteValue();
	}

	@Override
	public Object getDefiniteValue() throws RPLException {
		return set.getDefiniteValue(Type.SET).add(value.getDefiniteValue());
	}

	public String toString() {
		return "add(" + set + ", "+ value +")";
	}
	
	public boolean equals(Object o) {
		return (o instanceof SetAdd) && ((SetAdd)o).set.equals(set) 
				&& ((SetAdd)o).value.equals(value);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getClass().hashCode(), set.hashCode(), value.hashCode());
	}

}
