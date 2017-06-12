package com.tr.rp.ast.expressions;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.Function;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.exceptions.RPLFunctionUndefinedException;
import com.tr.rp.exceptions.RPLMissingReturnValueException;
import com.tr.rp.exceptions.RPLWrongNumberOfArgumentsException;
import com.tr.rp.iterators.ranked.ExecutionContext;
import com.tr.rp.iterators.ranked.InitialVarStoreIterator;
import com.tr.rp.iterators.ranked.MultiMergeIterator;
import com.tr.rp.iterators.ranked.RankedIterator;
import com.tr.rp.ranks.FunctionScope;
import com.tr.rp.varstore.VarStore;

/**
 * Function call expression. Represents a function call constructed with
 * a function name, a function scope (to retrieve the function) and a 
 * sequence of argument expressions. The arguments must match the function's
 * parameters. Otherwise an exception is thrown at runtime.
 */
public class FunctionCall extends AbstractFunctionCall {
	
	public FunctionCall(String functionName, FunctionScope functionScope, AbstractExpression ... arguments) {
		super(functionName, functionScope, arguments);
	}
	
	@Override
	public LanguageElement replaceVariable(String a, String b) {
		AbstractExpression[] newArgs = new AbstractExpression[getArguments().length];
		for (int i = 0; i < newArgs.length; i++) {
			newArgs[i] = (AbstractExpression)getArguments()[i].replaceVariable(a, b);
		}
		return new FunctionCall(getFunctionName(), getFunctionScope(), newArgs);
	}

	@Override
	public AbstractExpression transformRankExpressions(VarStore v, int rank) throws RPLException {
		AbstractExpression[] newArgs = new AbstractExpression[getArguments().length];
		for (int i = 0; i < newArgs.length; i++) {
			newArgs[i] = (AbstractExpression)getArguments()[i].transformRankExpressions(v, rank);
		}
		return new FunctionCall(getFunctionName(), getFunctionScope(), newArgs);
	}

	@Override
	public AbstractExpression replaceEmbeddedFunctionCall(AbstractFunctionCall fc, String var) {
		if (fc == this) {
			return new Variable(var);
		} else {
			AbstractExpression[] newArgs = new AbstractExpression[getArguments().length];
			for (int i = 0; i < newArgs.length; i++) {
				newArgs[i] = (AbstractExpression)getArguments()[i].replaceEmbeddedFunctionCall(fc, var);
			}
			return new FunctionCall(getFunctionName(), getFunctionScope(), newArgs);
		}
	}

	public String toString() {
		return getFunctionName() + "(" + 
				Arrays.stream(getArguments()).map(e -> e.toString()).collect(Collectors.joining(", ")) + ")";
	}
	
	public boolean equals(Object o) {
		if (o instanceof FunctionCall) {
			return ((FunctionCall)o).getFunctionName().equals(getFunctionName()) &&
					Arrays.equals(((FunctionCall)o).getArguments(), getArguments());
		}
		return false;
	}

	public RankedIterator<VarStore> getIterator(ExecutionContext c,AbstractExpression[] arguments, String assignToVar, RankedIterator<VarStore> parent) throws RPLException {
		return new MultiMergeIterator<VarStore>(parent) {

			@Override
			public RankedIterator<VarStore> transform(VarStore in) throws RPLException {
				String[] parameters = getFunction().getParameters();
				if (parameters.length != arguments.length) {
					throw new RPLWrongNumberOfArgumentsException(getFunction().getName(), parameters.length, arguments.length);
				}
				// execute function
				in = in.createClosure(getFunction().getParameters(), arguments);
				RankedIterator<VarStore> i = new InitialVarStoreIterator(in);
				final RankedIterator<VarStore> pre = getFunction().getBody().getIterator(i, c);
				return new RankedIterator<VarStore>() {

					@Override
					public boolean next() throws RPLException {
						return pre.next();
					}

					@Override
					public VarStore getItem() throws RPLException {
						VarStore v = pre.getItem();
						if (!v.containsVar("$return")) {
							throw new RPLMissingReturnValueException(getFunction());
						}
						return v.getParentOfClosure(assignToVar, new Variable("$return"));
					}

					@Override
					public int getRank() {
						return pre.getRank();
					}
				};
			}
		};
	}

}
