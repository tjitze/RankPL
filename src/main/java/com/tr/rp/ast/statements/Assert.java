package com.tr.rp.ast.statements;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.AbstractStatement;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.ast.statements.FunctionCallForm.ExtractedExpression;
import com.tr.rp.exceptions.RPLAssertionException;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.exceptions.RPLMiscException;
import com.tr.rp.exceptions.RPLTypeError;
import com.tr.rp.iterators.ranked.BufferingIterator;
import com.tr.rp.iterators.ranked.DuplicateRemovingIterator;
import com.tr.rp.iterators.ranked.ExecutionContext;
import com.tr.rp.iterators.ranked.RankedIterator;
import com.tr.rp.varstore.PersistentList;
import com.tr.rp.varstore.VarStore;

/**
 * The "assert b" statement is intended for testing purposes. Aborts if b is false. 
 */
public class Assert extends AbstractStatement {

	private AbstractExpression expression;
	
	public Assert(AbstractExpression expression) {
		this.expression = expression;
	}
	
	@Override
	public RankedIterator<VarStore> getIterator(RankedIterator<VarStore> parent, ExecutionContext c) throws RPLException {
		return new RankedIterator<VarStore>() {

			@Override
			public boolean next() throws RPLException {
				boolean next = parent.next();
				if (next) {
					if (!expression.getBoolValue(getItem())) {
						throw new RPLAssertionException("Failed assertion: " + expression, Assert.this);
					}
				}
				return next;
			}

			@Override
			public VarStore getItem() throws RPLException {
				return parent.getItem();
			}

			@Override
			public int getRank() {
				return parent.getRank();
			}
			
		};
	}

	@Override
	public void getVariables(Set<String> list) {
		expression.getVariables(list);
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return new Assert((AbstractExpression)expression.replaceVariable(a, b));
	}

	@Override
	public AbstractStatement rewriteEmbeddedFunctionCalls() {
		ExtractedExpression rewrittenExpression = FunctionCallForm.extractFunctionCalls(expression);
		if (rewrittenExpression.isRewritten()) {
			return new FunctionCallForm(
					new Assert(rewrittenExpression.getExpression()), rewrittenExpression.getAssignments());
		} else {
			return this;
		}
	}

	@Override
	public void getAssignedVariables(Set<String> variables) {
		// nop
	}
	
	public String toString() {
		return "assert("+expression+")";
	}

	@Override
	public boolean equals(Object o) {
		return (o instanceof Assert) &&
				((Assert)o).expression.equals(expression);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(expression);
	}	

}
