package com.tr.rp.ast.expressions;

import java.util.Set;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.varstore.VarStore;
import com.tr.rp.varstore.types.Type;

/**
 * The Rank expression is an integer-valued expression
 * that has a boolean expression argument. It evaluates
 * to the rank of its argument. This means that its 
 * actual value depends on a ranking function given which
 * it is evaluated. That's what the transformRankExpression
 * method is for. There are cases where an expression can
 * only be used if all rank expressions have been trans-
 * formed, i.e., replaced with int literals.
 */
public class RankExpr extends AbstractExpression {

	private final AbstractExpression b;

	public RankExpr(AbstractExpression b) {
		this.b = b;
	}

	@Override
	public Object getValue(VarStore e) throws RPLException {
		throw new RuntimeException("Illegal operation (evaluating untransformed rank expression)");
	}

	@Override
	public AbstractExpression transformRankExpressions(VarStore v, int rank) throws RPLException {
		// b might be contradiction or tautology.
		// If so, we can rewrite it immediately
		if (b.hasDefiniteValue()) {
			if (b.getDefiniteValue(Type.BOOL)) {
				return Literal.ZERO;
			} else {
				return Literal.MAX;
			}
		}
		// Otherwise, rewrite to rank if expression is true
		if (v == null || b.getValue(v, Type.BOOL)) {
			return new Literal<Integer>(rank);
		} else {
			return this;
		}
	}

	@Override
	public boolean hasRankExpression() {
		return true;
	}

	public String toString() {
		String bs = b.toString();
		if (bs.startsWith("(") && bs.endsWith(")")) {
			bs = bs.substring(1, bs.length()-1);
		}
		return "rank(" + bs + ")";
	}

	@Override
	public boolean hasDefiniteValue() {
		return false;
	}

	@Override
	public Object getDefiniteValue() throws RPLException {
		return null;
	}
	
	public boolean equals(Object o) {
		return o instanceof RankExpr && ((RankExpr)o).b.equals(b);
	}
	
	@Override
	public int hashCode() {
		return b.hashCode();
	}

	@Override
	public AbstractFunctionCall getEmbeddedFunctionCall() {
		return b.getEmbeddedFunctionCall();
	}

	@Override
	public AbstractExpression replaceEmbeddedFunctionCall(AbstractFunctionCall fc, String var) {
		return new RankExpr((AbstractExpression)b.replaceEmbeddedFunctionCall(fc, var));
	}

	@Override
	public void getVariables(Set<String> list) {
		b.getVariables(list);
	}

}
