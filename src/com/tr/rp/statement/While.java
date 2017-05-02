package com.tr.rp.statement;

import java.util.Set;
import java.util.List;

import com.tr.rp.core.DStatement;
import com.tr.rp.core.LanguageElement;
import com.tr.rp.core.VarStore;
import com.tr.rp.core.rankediterators.AbsurdIterator;
import com.tr.rp.core.rankediterators.BufferingIterator;
import com.tr.rp.core.rankediterators.RankedIterator;
import com.tr.rp.expressions.bool.BoolExpression;
import com.tr.rp.expressions.num.FunctionCall;
import com.tr.rp.tools.Pair;

public class While implements DStatement {

	private BoolExpression exp;
	private DStatement pre;
	private DStatement s;
	private int maxIterations = Integer.MAX_VALUE;
	
	public While(BoolExpression exp, DStatement s) {
		this.exp = exp;
		this.s = s;
		this.pre = null;
	}
	
	private While(BoolExpression exp, DStatement s, DStatement pre) {
		this.exp = exp;
		this.s = s;
		this.pre = pre;
	}
		
	public While setMaxDepth(int maxIterations) {
		this.maxIterations = maxIterations;
		return this;
	}

	@Override
	public RankedIterator<VarStore> getIterator(RankedIterator<VarStore> in) {
		int iterations = 0;
		
		while (iterations++ < maxIterations) {

			// Do one iteration
			in = generateIteration(in);
			
			// Check if exp is still satisfied
			BufferingIterator<VarStore> bi = new BufferingIterator<VarStore>(in);
			boolean expSatisfied = false;
			boolean hasNext = bi.next();
			if (!hasNext) { // Undefined
				return new AbsurdIterator<VarStore>(); 
			}
			while (hasNext) {
				if (exp.isTrue(bi.getItem())) {
					expSatisfied = true;
					break;
				}
				hasNext = bi.next();
			}
			
			bi.reset();
			bi.stopBuffering();

			// If exp is not satisfied, we are done
			if (!expSatisfied) {
				return bi;
			}
			
			// Try another iteration
			in = bi;
		}
		
		return null;

	}
	
	private RankedIterator<VarStore> generateIteration(RankedIterator<VarStore> in) {
		if (pre == null) {
			return (new IfElse(exp, s, new Skip())).getIterator(in);
		} else {
			return new Composition(pre, (new IfElse(exp, s, new Skip()))).getIterator(in);
		}
	}
	
	public boolean equals(Object o) {
		return o instanceof While &&
				((While)o).exp.equals(exp) &&
				((While)o).s.equals(s);
	}
	
	@Override
	public boolean containsVariable(String var) {
		return s.containsVariable(var) || exp.containsVariable(var);
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return new While((BoolExpression)exp.replaceVariable(a, b),
				(DStatement)this.s.replaceVariable(a, b));
	}
	
	public String toString() {
		String expString = exp.toString();
		if (!(expString.startsWith("(") && expString.endsWith(")"))) {
			expString = "(" + expString + ")";
		}
		return "while " + expString + " do " + s;
	}
	
	@Override
	public void getVariables(Set<String> list) {
		s.getVariables(list);
		exp.getVariables(list);
	}

	@Override
	public DStatement rewriteEmbeddedFunctionCalls() {
		if (pre != null) {
			throw new UnsupportedOperationException();
		}
		DStatement sr = s.rewriteEmbeddedFunctionCalls();
		Pair<List<Pair<String, FunctionCall>>, BoolExpression> rewrittenExp = FunctionCallForm.extractFunctionCalls(exp);
		if (rewrittenExp.a.isEmpty()) {
			return new While(exp, sr);
		}
		return new While(rewrittenExp.b, sr, new FunctionCallForm(new Skip(), rewrittenExp.a));
	}	

}
