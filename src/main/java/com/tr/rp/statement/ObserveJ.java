package com.tr.rp.statement;

import java.util.ArrayList;
import java.util.Set;
import java.util.List;

import com.tr.rp.core.DStatement;
import com.tr.rp.core.Expression;
import com.tr.rp.core.LanguageElement;
import com.tr.rp.core.VarStore;
import com.tr.rp.core.rankediterators.RankedIterator;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.expressions.AbstractFunctionCall;
import com.tr.rp.expressions.FunctionCall;
import com.tr.rp.expressions.Literal;
import com.tr.rp.expressions.Not;
import com.tr.rp.statement.FunctionCallForm.ExtractedExpression;
import com.tr.rp.tools.Pair;

/**
 * Implements J-conditioning.
 * This is equivalent to 
 *   observe b [x] observe -b
 */
public class ObserveJ extends DStatement {

	private Expression b;
	private Expression rank;
	
	public ObserveJ(Expression b, int rank) {
		this(b, new Literal<Integer>(rank));
	}
	public ObserveJ(Expression b, Expression rank) {
		this.b = b;
		this.rank = rank;
	}

	@Override
	public RankedIterator<VarStore> getIterator(RankedIterator<VarStore> in) throws RPLException {
		try {
			return new RankedChoice(new Observe(b), new Observe(new Not(b)), rank).getIterator(in);
		} catch (RPLException e) {
			e.addStatement(this);
			throw e;
		}
	}

	public String toString() {
		String bString = b.toString();
		if (bString.startsWith("(") && bString.endsWith(")")) {
			bString = bString.substring(1, bString.length()-1);
		}
		String rankString = rank.toString();
		if (!(rankString.startsWith("(") && rankString.endsWith(")"))) {
			rankString = "(" + rankString + ")";
		}
		return "observe-j " + rankString + " " + bString;
	}
	
	public boolean equals(Object o) {
		return o instanceof ObserveJ &&
				((ObserveJ)o).b.equals(b);
	}
	
	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return new ObserveJ((Expression)this.b.replaceVariable(a, b), (Expression)rank.replaceVariable(a, b));
	}

	@Override
	public void getVariables(Set<String> list) {
		b.getVariables(list);
		rank.getVariables(list);
	}
	
	@Override
	public DStatement rewriteEmbeddedFunctionCalls() {
		ExtractedExpression rewrittenExp = FunctionCallForm.extractFunctionCalls(b);
		ExtractedExpression rewrittenRank = FunctionCallForm.extractFunctionCalls(rank);
		if (rewrittenExp.isRewritten() || rewrittenRank.isRewritten()) {
			return new FunctionCallForm(
					new ObserveJ(rewrittenExp.getExpression(), rewrittenRank.getExpression()), 
					rewrittenExp.getAssignments(), 
					rewrittenRank.getAssignments());
		} else {
			return this;
		}
	}
}
