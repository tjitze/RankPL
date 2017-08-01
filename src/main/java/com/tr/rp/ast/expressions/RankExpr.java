package com.tr.rp.ast.expressions;

import java.util.Objects;
import java.util.Set;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.ranks.Rank;
import com.tr.rp.varstore.VarStore;
import com.tr.rp.varstore.types.Type;

public class RankExpr extends AbstractExpression {

	private final AbstractExpression b;

	private final int rankB;
	
	public RankExpr(AbstractExpression b) {
		this.b = b;
		this.rankB = -1;
	}

	private RankExpr(AbstractExpression b, int rankB) {
		this.b = b;
		this.rankB = rankB;
	}

	@Override
	public Object getValue(VarStore e) throws RPLException {
		throw new RuntimeException("Illegal operation (evaluating uninstantiated rank expression)");
	}

	@Override
	public AbstractExpression doRankExpressionTransformation(VarStore v, int rank) throws RPLException {
		// b might be contradiction or tautology.
		// If so, we can rewrite it immediately
		if (b.hasDefiniteValue()) {
			if (b.getDefiniteValue(Type.BOOL)) {
				return new Literal(0);
			} else {
				return new Literal(Rank.MAX);
			}
		}
		// Otherwise, rewrite to rank if expression is true
		if (v == null || b.getValue(v, Type.BOOL)) {
			return new Literal(rank);
		} else {
			return this;
		}
	}

	@Override
	public boolean needsRankExpressionTransformation() {
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
		throw new RuntimeException("Illegal operation (evaluating uninstantiated rank expression)");
	}
	
	public boolean equals(Object o) {
		return o instanceof RankExpr 
				&& ((RankExpr)o).b.equals(b)
				&& ((RankExpr)o).rankB == rankB;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(b, rankB);
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return new RankExpr((AbstractExpression)this.b.replaceVariable(a, b), rankB);
	}

	@Override
	public AbstractFunctionCall getEmbeddedFunctionCall() {
		return b.getEmbeddedFunctionCall();
	}

	@Override
	public AbstractExpression replaceEmbeddedFunctionCall(AbstractFunctionCall fc, String var) {
		return new RankExpr((AbstractExpression)b.replaceEmbeddedFunctionCall(fc, var), rankB);
	}

	@Override
	public void getVariables(Set<String> list) {
		b.getVariables(list);
	}

}
