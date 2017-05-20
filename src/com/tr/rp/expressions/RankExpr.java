package com.tr.rp.expressions;

import java.util.Set;

import com.tr.rp.core.Expression;
import com.tr.rp.core.LanguageElement;
import com.tr.rp.core.VarStore;
import com.tr.rp.exceptions.RPLException;

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
public class RankExpr extends Expression {

	private final Expression b;

	public RankExpr(Expression b) {
		this.b = b;
	}

	@Override
	public Object getValue(VarStore e) throws RPLException {
		throw new RuntimeException("Illegal operation (evaluating untransformed rank expression)");
	}

	@Override
	public Expression transformRankExpressions(VarStore v, int rank) throws RPLException {
		// b might be contradiction or tautology.
		// If so, we can rewrite it immediately
		if (b.hasDefiniteValue()) {
			if (b.getDefiniteBoolValue()) {
				return new Literal<Integer>(0);
			} else {
				return new Literal<Integer>(Integer.MAX_VALUE);
			}
		}
		// Otherwise, rewrite to rank if expression is true
		if (v == null || b.getBoolValue(v)) {
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
	public boolean containsVariable(String var) {
		return b.containsVariable(var);
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return new RankExpr((Expression)this.b.replaceVariable(a, b));
	}

	@Override
	public AbstractFunctionCall getEmbeddedFunctionCall() {
		return b.getEmbeddedFunctionCall();
	}

	@Override
	public Expression replaceEmbeddedFunctionCall(AbstractFunctionCall fc, String var) {
		return new RankExpr((Expression)b.replaceEmbeddedFunctionCall(fc, var));
	}

	@Override
	public void getVariables(Set<String> list) {
		b.getVariables(list);
	}

}
