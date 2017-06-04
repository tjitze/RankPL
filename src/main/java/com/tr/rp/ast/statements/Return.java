package com.tr.rp.ast.statements;

import java.util.Set;
import java.util.List;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.AbstractStatement;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.ast.expressions.FunctionCall;
import com.tr.rp.ast.expressions.Variable;
import com.tr.rp.ast.statements.FunctionCallForm.ExtractedExpression;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.iterators.ranked.ExecutionContext;
import com.tr.rp.iterators.ranked.RankedIterator;
import com.tr.rp.tools.Pair;
import com.tr.rp.varstore.VarStore;

/**
 * The return statement makes a function or program exit and return
 * the value of a given expression.
 * 
 * Internally it is implemented by assigning a value to a special 
 * $return variable. The Composition construct skips execution of
 * the remainder for any state in which this variable is set.
 */
public class Return extends AbstractStatement {

	private final AbstractExpression exp;
	
	public Return(AbstractExpression exp) {
		this.exp = exp;
	}

	public Return(String variable) {
		this.exp = new Variable(variable);
	}

	@Override
	public RankedIterator<VarStore> getIterator(final RankedIterator<VarStore> in, ExecutionContext c) throws RPLException {
		Assign assign = new Assign("$return", exp);
		try {
			RankedIterator<VarStore> it = assign.getIterator(in, c);
			return new RankedIterator<VarStore>() {
				@Override
				public boolean next() throws RPLException {
					try {
						return it.next();
					} catch (RPLException e) {
						e.setStatement(Return.this);
						throw e;
					}
				}

				@Override
				public VarStore getItem() throws RPLException {
					try {
						return it.getItem();
					} catch (RPLException e) {
						e.setStatement(Return.this);
						throw e;
					}
				}

				@Override
				public int getRank() {
					return it.getRank();
				}
				
			};
		} catch (RPLException e) {
			e.setStatement(this);
			throw e;
		}
	}
		
	public String toString() {
		return "return " + exp;
	}
	
	public boolean equals(Object o) {
		return o instanceof Return &&
				((Return)o).exp.equals(exp);
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return new Return((AbstractExpression)exp.replaceVariable(a, b));
	}

	@Override
	public void getVariables(Set<String> list) {
		exp.getVariables(list);
	}

	@Override
	public AbstractStatement rewriteEmbeddedFunctionCalls() {
		ExtractedExpression rewrittenExp = FunctionCallForm.extractFunctionCalls(exp);
		if (rewrittenExp.isRewritten()) {
			return new FunctionCallForm(new Return(rewrittenExp.getExpression()), rewrittenExp.getAssignments());
		} else {
			return this;
		}
	}	
	
	@Override
	public void getAssignedVariables(Set<String> variables) {
		variables.add("$return");
	}	

}
