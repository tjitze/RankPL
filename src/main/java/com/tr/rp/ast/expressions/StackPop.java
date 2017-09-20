package com.tr.rp.ast.expressions;

import java.util.Objects;
import java.util.Set;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.exceptions.RPLEmptyStackException;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.varstore.VarStore;
import com.tr.rp.varstore.arrays.PersistentArray;
import com.tr.rp.varstore.datastructures.PersistentStack;
import com.tr.rp.varstore.datastructures.PersistentStack.PopResult;
import com.tr.rp.varstore.types.Type;

/**
 * [stack, item] = pop(stack): pop item from stack.
 */
public class StackPop extends AbstractExpression {

	private final AbstractExpression e;
	
	public StackPop(AbstractExpression e) {
		this.e = e;
	}

	@Override
	public void getVariables(Set<String> list) {
		e.getVariables(list);
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return new StackPop((AbstractExpression)e.replaceVariable(a, b));
	}

	@Override
	public boolean hasRankExpression() {
		return e.hasRankExpression();
	}

	@Override
	public AbstractFunctionCall getEmbeddedFunctionCall() {
		return e.getEmbeddedFunctionCall();
	}

	public AbstractExpression transformRankExpressions(VarStore v, int rank) throws RPLException {
		return new StackPop(e.transformRankExpressions(v, rank));
	}

	@Override
	public AbstractExpression replaceEmbeddedFunctionCall(AbstractFunctionCall fc, String var) {
		return new StackPop(e.replaceEmbeddedFunctionCall(fc, var));
	}

	@Override
	public Object getValue(VarStore e) throws RPLException {
		PopResult<?> popResult = this.e.getValue(e, Type.STACK).pop();
		return new PersistentArray(popResult.mutatedStack, popResult.poppedElement);
	}

	@Override
	public boolean hasDefiniteValue() {
		return e.hasDefiniteValue();
	}

	@Override
	public Object getDefiniteValue() throws RPLException {
		return this.e.getDefiniteValue(Type.STACK).peek();
	}

	public String toString() {
		return "pop("+e+")";
	}
	
	public boolean equals(Object o) {
		return (o instanceof StackPop) && ((StackPop)o).e.equals(e);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getClass().hashCode(), e.hashCode());
	}

}
