package com.tr.rp.ast.expressions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.Function;
import com.tr.rp.ast.FunctionScope;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.base.ExecutionContext;
import com.tr.rp.base.RankedItem;
import com.tr.rp.base.State;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.exceptions.RPLMissingReturnValueException;
import com.tr.rp.exceptions.RPLStopExecutionException;
import com.tr.rp.exceptions.RPLWrongNumberOfArgumentsException;
import com.tr.rp.executors.Executor;
import com.tr.rp.executors.MultiMergeExecutor;
import com.tr.rp.varstore.VarStore;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Function call expression. Represents a function call constructed with
 * a function name, a function scope (to retrieve the function) and a 
 * sequence of argument expressions. The arguments must match the function's
 * parameters. Otherwise an exception is thrown at runtime.
 */
public class FunctionCall extends AbstractFunctionCall {

	private final FunctionScope functionScope;
	private final String functionName;
	private String[] parameters;
	
	public FunctionCall(String functionName, FunctionScope functionScope, AbstractExpression ... arguments) {
		super(arguments);
		this.functionScope = functionScope;
		this.functionName = functionName;
	}
	
	public final String getFunctionName() {
		return functionName;
	}

	public FunctionScope getFunctionScope() {
		return functionScope;
	}

	public final Function getFunction() throws RPLException {
		try {
			return functionScope.getFunction(functionName);
		} catch (RPLException e) {
			e.setExpression(this);
			throw e;
		}
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

	protected void getExecutorForFunctionCall(String assignToVar, VarStore in, ExecutionContext c, Executor out) throws RPLException {
		if (parameters == null) {
			parameters = getFunction().getParameters();
			if (parameters.length != getArguments().length) {
				throw new RPLWrongNumberOfArgumentsException(getFunction().getName(), parameters.length, getArguments().length);
			}
		}
		Function function = getFunction();
		List<Object> values = getArgumentValues(in);
		if (function.containsCachedValue(values)) {
			for (RankedItem<Object> ci: getFunction().getCachedValue(values)) {
				out.push(in.create(assignToVar, ci.item), ci.rank);
			}
			out.close();
		} else {
			List<RankedItem<Object>> returnValues = new LinkedList<RankedItem<Object>>();
			RPLStopExecutionException stop = new RPLStopExecutionException();
			Executor post = new Executor() {
				@Override
				public void close() throws RPLException {
					out.close();
					function.addCached(values, returnValues);
				}

				@Override
				public void push(State s) throws RPLException {
					VarStore v = s.getVarStore();
					if (!v.containsVar("$return")) {
						throw new RPLMissingReturnValueException(function);
					}
					returnValues.add(new RankedItem<Object>(v.getValue("$return"), s.getRank()));
					out.push(v.getParentOfClosure(assignToVar, new Variable("$return")), s.getRank());
				}
				
			};
			try {
				Executor pre = function.getBody().getExecutor(post, c);
				pre.push(in.createClosureWith(parameters, values), 0);
				pre.close();
			} catch (RPLException e) {
				if (e != stop) throw e;
			}
		}
			
	}
		
	private final List<Object> getArgumentValues(VarStore vs) throws RPLException {
		List<Object> values = new ArrayList<Object>();
		for (int i = 0; i < getArguments().length; i++) {
			values.add(getArguments()[i].getValue(vs));
		}
		return values;
	}
	
	public Executor getExecutor(ExecutionContext c, String assignToVar, Executor out) {
		return new MultiMergeExecutor(out) {
			@Override
			public void transform(VarStore in, Executor out2) throws RPLException {
				getExecutorForFunctionCall(assignToVar, in, c, out2);
			}
		};
	}

}
