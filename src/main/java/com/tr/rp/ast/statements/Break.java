package com.tr.rp.ast.statements;

import java.util.Set;
import java.util.List;
import java.util.Objects;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.AbstractStatement;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.ast.expressions.AssignmentTarget;
import com.tr.rp.ast.expressions.AssignmentTargetTerminal;
import com.tr.rp.ast.expressions.Expressions;
import com.tr.rp.ast.expressions.FunctionCall;
import com.tr.rp.ast.statements.FunctionCallForm.ExtractedExpression;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.iterators.ranked.ExecutionContext;
import com.tr.rp.iterators.ranked.RankedIterator;
import com.tr.rp.varstore.VarStore;

/**
 * The break statement makes a while or for loop exit.
 * 
 * Internally it is implemented by assigning a value to a special 
 * $break variable. The Composition construct skips execution of
 * the remainder for any state in which this variable is set, and
 * the while and for loops exit when this variable is set.
 */
public class Break extends AbstractStatement {

	@Override
	public RankedIterator<VarStore> getIterator(final RankedIterator<VarStore> in, ExecutionContext c) throws RPLException {
		Assign assign = new Assign(new AssignmentTargetTerminal("$break"), Expressions.lit(true));
		RankedIterator<VarStore> it = assign.getIterator(in, c);
		return new RankedIterator<VarStore>() {
			@Override
			public boolean next() throws RPLException {
				return it.next();
			}

			@Override
			public VarStore getItem() throws RPLException {
				return it.getItem();
			}

			@Override
			public int getRank() {
				return it.getRank();
			}
			
		};
	}
		
	public String toString() {
		return "break";
	}
	
	public boolean equals(Object o) {
		return o instanceof Break;
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return new Break();
	}

	@Override
	public void getVariables(Set<String> list) { }

	@Override
	public AbstractStatement rewriteEmbeddedFunctionCalls() {
		return this;
	}	
	
	@Override
	public void getAssignedVariables(Set<String> variables) {
		variables.add("$break");
	}	
	
	@Override
	public int hashCode() {
		return Objects.hash(getClass());
	}	

}
