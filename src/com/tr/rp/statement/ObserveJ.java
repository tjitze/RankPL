package com.tr.rp.statement;

import java.util.ArrayList;
import java.util.Set;
import java.util.List;

import com.tr.rp.core.DStatement;
import com.tr.rp.core.LanguageElement;
import com.tr.rp.core.VarStore;
import com.tr.rp.core.rankediterators.RankedIterator;
import com.tr.rp.expressions.bool.BoolExpression;
import com.tr.rp.expressions.num.FunctionCall;
import com.tr.rp.expressions.num.IntLiteral;
import com.tr.rp.expressions.num.NumExpression;
import com.tr.rp.tools.Pair;

/**
 * Implements J-conditioning.
 * This is equivalent to 
 *   observe b [x] observe -b
 */
public class ObserveJ implements DStatement {

	private BoolExpression b;
	private NumExpression rank;
	
	public ObserveJ(BoolExpression b, int rank) {
		this(b, new IntLiteral(rank));
	}
	public ObserveJ(BoolExpression b, NumExpression rank) {
		this.b = b;
		this.rank = rank;
	}

	@Override
	public RankedIterator<VarStore> getIterator(RankedIterator<VarStore> in) {
		return new Choose(new Observe(b), new Observe(b.negate()), rank).getIterator(in);
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
	public boolean containsVariable(String var) {
		return b.containsVariable(var) || rank.containsVariable(var);
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return new ObserveJ((BoolExpression)this.b.replaceVariable(a, b), (NumExpression)rank.replaceVariable(a, b));
	}

	@Override
	public void getVariables(Set<String> list) {
		b.getVariables(list);
		rank.getVariables(list);
	}
	
	@Override
	public DStatement rewriteEmbeddedFunctionCalls() {
		Pair<List<Pair<String, FunctionCall>>, BoolExpression> rewrittenB
			= FunctionCallForm.extractFunctionCalls(b);
		Pair<List<Pair<String, FunctionCall>>, NumExpression> rewrittenRank
			= FunctionCallForm.extractFunctionCalls(rank);
		List<Pair<String, FunctionCall>> combined = new ArrayList<Pair<String, FunctionCall>>();
		combined.addAll(rewrittenB.a);
		combined.addAll(rewrittenRank.a);
		if (combined.isEmpty()) {
			return this;
		}
		return new FunctionCallForm(new ObserveJ(rewrittenB.b, rewrittenRank.b), combined);
	}
}
