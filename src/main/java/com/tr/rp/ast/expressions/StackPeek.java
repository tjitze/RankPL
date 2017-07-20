package com.tr.rp.ast.expressions;

import java.util.Set;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.exceptions.RPLEmptyStackException;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.varstore.VarStore;
import com.tr.rp.varstore.types.Type;

/**
 * peek(stack): returns top of stack.
 */
public class StackPeek extends AbstractExpression {

	private final AbstractExpression e;
	
	public StackPeek(AbstractExpression e) {
		this.e = e;
	}

	@Override
	public void getVariables(Set<String> list) {
		e.getVariables(list);
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return new StackPeek((AbstractExpression)e.replaceVariable(a, b));
	}

	@Override
	public boolean needsRankExpressionTransformation() {
		return e.needsRankExpressionTransformation();
	}

	@Override
	public AbstractExpression doRankExpressionTransformation(VarStore v, int rank) throws RPLException {
		return new StackPeek(e.doRankExpressionTransformation(v, rank));
	}

	@Override
	public AbstractFunctionCall getEmbeddedFunctionCall() {
		return e.getEmbeddedFunctionCall();
	}

	@Override
	public AbstractExpression replaceEmbeddedFunctionCall(AbstractFunctionCall fc, String var) {
		return new StackPeek((AbstractExpression)e.replaceEmbeddedFunctionCall(fc, var));
	}

	@Override
	public Object getValue(VarStore e) throws RPLException {
		Object peekedValue = this.e.getValue(e, Type.STACK).peek();
		if (peekedValue == null) {
			throw new RPLEmptyStackException();
		}
		return peekedValue;
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
		return "peek("+e+")";
	}
	
	public boolean equals(Object o) {
		return (o instanceof StackPeek) && ((StackPeek)o).e.equals(e);
	}
	
	@Override
	public int hashCode() {
		return e.hashCode();
	}

}
