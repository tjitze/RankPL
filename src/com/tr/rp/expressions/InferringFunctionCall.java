package com.tr.rp.expressions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.tr.rp.core.Expression;
import com.tr.rp.core.FunctionScope;
import com.tr.rp.core.LanguageElement;
import com.tr.rp.core.VarStore;
import com.tr.rp.core.rankediterators.InitialVarStoreIterator;
import com.tr.rp.core.rankediterators.MultiMergeIterator;
import com.tr.rp.core.rankediterators.RankedIterator;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.exceptions.RPLMissingReturnValueException;
import com.tr.rp.exceptions.RPLWrongNumberOfArgumentsException;

/**
 * Function call expression. Represents a function call constructed with
 * a function name, a function scope (to retrieve the function) and a 
 * sequence of argument expressions. The arguments must match the function's
 * parameters. Otherwise an exception is thrown at runtime.
 */
public class InferringFunctionCall extends AbstractFunctionCall {
	
	public InferringFunctionCall(String functionName, FunctionScope functionScope, Expression ... arguments) {
		super(functionName, functionScope, arguments);
	}
	
	@Override
	public LanguageElement replaceVariable(String a, String b) {
		Expression[] newArgs = new Expression[getArguments().length];
		for (int i = 0; i < newArgs.length; i++) {
			newArgs[i] = (Expression)getArguments()[i].replaceVariable(a, b);
		}
		return new InferringFunctionCall(getFunctionName(), getFunctionScope(), newArgs);
	}

	@Override
	public Expression transformRankExpressions(VarStore v, int rank) throws RPLException {
		Expression[] newArgs = new Expression[getArguments().length];
		for (int i = 0; i < newArgs.length; i++) {
			newArgs[i] = (Expression)getArguments()[i].transformRankExpressions(v, rank);
		}
		return new InferringFunctionCall(getFunctionName(), getFunctionScope(), newArgs);
	}

	@Override
	public Expression replaceEmbeddedFunctionCall(AbstractFunctionCall fc, String var) {
		if (fc == this) {
			return new Variable(var);
		} else {
			Expression[] newArgs = new Expression[getArguments().length];
			for (int i = 0; i < newArgs.length; i++) {
				newArgs[i] = (Expression)getArguments()[i].replaceEmbeddedFunctionCall(fc, var);
			}
			return new InferringFunctionCall(getFunctionName(), getFunctionScope(), newArgs);
		}
	}

	public String toString() {
		return "infer("+getFunctionName() + "(" + 
				Arrays.stream(getArguments()).map(e -> e.toString()).collect(Collectors.joining(", ")) + "))";
	}
	
	public boolean equals(Object o) {
		if (o instanceof InferringFunctionCall) {
			return ((InferringFunctionCall)o).getFunctionName().equals(getFunctionName()) &&
					Arrays.equals(((InferringFunctionCall)o).getArguments(), getArguments());
		}
		return false;
	}

	public RankedIterator<VarStore> getIterator(Expression[] arguments, String assignToVar, RankedIterator<VarStore> parent) throws RPLException {
		return new MultiMergeIterator<VarStore>(parent) {

			@Override
			public RankedIterator<VarStore> transform(VarStore in) throws RPLException {
				String[] parameters = getFunction().getParameters();
				if (parameters.length != arguments.length) {
					throw new RPLWrongNumberOfArgumentsException(getFunction().getName(), parameters.length, arguments.length);
				}
				VarStore closure = in.createClosure(getFunction().getParameters(), arguments);
				RankedIterator<VarStore> i = new InitialVarStoreIterator(closure);
				RankedIterator<VarStore> pre = getFunction().getBody().getIterator(i);
				List<Object> values = new ArrayList<Object>();
				while (pre.next() && pre.getRank() == 0) {
					if (!pre.getItem().containsVar("$return")) {
						throw new RPLMissingReturnValueException(getFunction());
					}
					values.add(pre.getItem().getValue("$return"));
				}
				return new InitialVarStoreIterator(in.create(assignToVar, new PersistentList(values)));
			}
		};
	}

}
