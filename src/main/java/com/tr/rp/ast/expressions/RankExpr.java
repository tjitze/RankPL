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

	private final AbstractExpression a;
	private final AbstractExpression b;

	private final int rankB;
	private final int rankAB;
	
	public RankExpr(AbstractExpression a) {
		this.a = a;
		this.b = Expressions.lit(true);
		this.rankB = -1;
		this.rankAB = -1;
	}

	public RankExpr(AbstractExpression a, AbstractExpression b) {
		this.a = a;
		this.b = b;
		this.rankB = -1;
		this.rankAB = -1;
	}

	private RankExpr(AbstractExpression a, AbstractExpression b, int rankB, int rankAB) {
		this.a = a;
		this.b = b;
		this.rankB = rankB;
		this.rankAB = rankAB;
	}

	@Override
	public Object getValue(VarStore e) throws RPLException {
		if (needsRankExpressionTransformation()) {
			throw new RuntimeException("Illegal operation (evaluating uninstantiated rank expression)");
		}
		// if rankB is infinity, the conditional rank is undefined,
		// but we have to return something, so we return infinity.
		if (rankB == Rank.MAX) {
			return Rank.MAX;
		}
		return Rank.sub(rankAB, rankB);
	}

	@Override
	public AbstractExpression doRankExpressionTransformation(VarStore v, int rank) throws RPLException {
		
		int newRankB = rankB;
		int newRankAB = rankAB;
		
		// conditions might be contradiction or tautology.
		// If so, we can rewrite them immediately
		if (a.hasDefiniteValue() && b.hasDefiniteValue()) {
			if (a.getDefiniteValue(Type.BOOL) && b.getDefiniteValue(Type.BOOL)) {
				newRankAB = 0;
			} else {
				newRankAB = Rank.MAX;
			}
		}
		if (b.hasDefiniteValue()) {
			if (b.getDefiniteValue(Type.BOOL)) {
				newRankB = 0;
			} else {
				newRankB = Rank.MAX;
			}
		}
		
		// Otherwise, rewrite to rank if expression is true
		if (newRankAB < 0 && (v == null || (newRankAB < 0 && a.getValue(v, Type.BOOL) && b.getValue(v, Type.BOOL)))) {
			newRankAB = rank;
		}
		if (newRankB < 0 && (v == null || (newRankB < 0 && b.getValue(v, Type.BOOL)))) {
			newRankB = rank;
		}

		if (newRankAB != rankAB || newRankB != rankB) {
			return new RankExpr(a, b, newRankB, newRankAB);
		} else {
			return this;
		}
	}

	@Override
	public boolean needsRankExpressionTransformation() {
		return rankB < 0 || rankAB < 0;
	}

	public String toString() {
		String as = b.toString();
		if (as.startsWith("(") && as.endsWith(")")) {
			as = as.substring(1, as.length()-1);
		}
		String bs = b.toString();
		if (bs.startsWith("(") && bs.endsWith(")")) {
			bs = bs.substring(1, bs.length()-1);
		}
		return "rank(" + as + ", " + bs + ")";
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
		return o instanceof RankExpr 
				&& ((RankExpr)o).a.equals(a) 
				&& ((RankExpr)o).b.equals(b)
				&& ((RankExpr)o).rankAB == rankAB
				&& ((RankExpr)o).rankB == rankB;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(a, b, rankAB, rankB);
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return new RankExpr((AbstractExpression)this.a.replaceVariable(a, b), (AbstractExpression)this.b.replaceVariable(a, b));
	}

	@Override
	public AbstractFunctionCall getEmbeddedFunctionCall() {
		AbstractFunctionCall afc = a.getEmbeddedFunctionCall();
		if (afc != null) {
			return afc;
		}
		return b.getEmbeddedFunctionCall();
	}

	@Override
	public AbstractExpression replaceEmbeddedFunctionCall(AbstractFunctionCall fc, String var) {
		return new RankExpr((AbstractExpression)a.replaceEmbeddedFunctionCall(fc, var),
				(AbstractExpression)b.replaceEmbeddedFunctionCall(fc, var));
	}

	@Override
	public void getVariables(Set<String> list) {
		a.getVariables(list);
		b.getVariables(list);
	}

}
