package com.tr.rp.ast.expressions;

import java.util.Objects;
import java.util.Set;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.varstore.VarStore;
import com.tr.rp.varstore.types.Type;

/**
 * stack = push(stack, value) - push value onto stack
 */
public class StackPush extends AbstractExpression {

	private final AbstractExpression stack;
	private final AbstractExpression value;
	
	public StackPush(AbstractExpression stack, AbstractExpression value) {
		this.stack = stack;
		this.value = value;
	}

	@Override
	public void getVariables(Set<String> list) {
		stack.getVariables(list);
		value.getVariables(list);
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return new StackPush((AbstractExpression)stack.replaceVariable(a, b), 
				(AbstractExpression)value.replaceVariable(a, b));
	}

	@Override
	public boolean hasRankExpression() {
		return stack.hasRankExpression() || value.hasRankExpression();
	}

	@Override
	public AbstractExpression transformRankExpressions(VarStore v, int rank) throws RPLException {
		return new StackPush(stack.transformRankExpressions(v, rank),
				value.transformRankExpressions(v, rank));
	}

	@Override
	public AbstractFunctionCall getEmbeddedFunctionCall() {
		AbstractFunctionCall fc = stack.getEmbeddedFunctionCall();
		if (fc == null) {
			return value.getEmbeddedFunctionCall();
		} else {
			return fc;
		}
	}

	@Override
	public AbstractExpression replaceEmbeddedFunctionCall(AbstractFunctionCall fc, String var) {
		return new StackPush((AbstractExpression)stack.replaceEmbeddedFunctionCall(fc, var),
				(AbstractExpression)value.replaceEmbeddedFunctionCall(fc, var));
	}

	@Override
	public Object getValue(VarStore e) throws RPLException {
		return stack.getValue(e, Type.STACK).push(value.getValue(e));
	}

	@Override
	public boolean hasDefiniteValue() {
		return stack.hasDefiniteValue() && value.hasDefiniteValue();
	}

	@Override
	public Object getDefiniteValue() throws RPLException {
		return stack.getDefiniteValue(Type.STACK).push(value.getDefiniteValue());
	}

	public String toString() {
		return "push(" + stack + ", "+ value +")";
	}
	
	public boolean equals(Object o) {
		return (o instanceof StackPush) && ((StackPush)o).stack.equals(stack) 
				&& ((StackPush)o).value.equals(value);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getClass().hashCode(), stack.hashCode(), value.hashCode());
	}

}
