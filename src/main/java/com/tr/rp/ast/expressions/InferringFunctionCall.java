package com.tr.rp.ast.expressions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.exceptions.RPLStopExecutionException;
import com.tr.rp.exec.ExecutionContext;
import com.tr.rp.exec.Executor;
import com.tr.rp.exec.MultiMergeExecutor;
import com.tr.rp.exec.State;
import com.tr.rp.ranks.FunctionScope;
import com.tr.rp.varstore.FreeVarNameProvider;
import com.tr.rp.varstore.VarStore;
import com.tr.rp.varstore.types.PersistentArray;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class InferringFunctionCall extends FunctionCall {
	
	public InferringFunctionCall(String functionName, FunctionScope functionScope, AbstractExpression ... arguments) {
		super(functionName, functionScope, arguments);
	}
	
	@Override
	public LanguageElement replaceVariable(String a, String b) {
		AbstractExpression[] newArgs = new AbstractExpression[getArguments().length];
		for (int i = 0; i < newArgs.length; i++) {
			newArgs[i] = (AbstractExpression)getArguments()[i].replaceVariable(a, b);
		}
		return new InferringFunctionCall(getFunctionName(), getFunctionScope(), newArgs);
	}

	@Override
	public AbstractExpression transformRankExpressions(VarStore v, int rank) throws RPLException {
		AbstractExpression[] newArgs = new AbstractExpression[getArguments().length];
		for (int i = 0; i < newArgs.length; i++) {
			newArgs[i] = (AbstractExpression)getArguments()[i].transformRankExpressions(v, rank);
		}
		return new InferringFunctionCall(getFunctionName(), getFunctionScope(), newArgs);
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

	public Executor getExecutor(ExecutionContext c, String assignToVar, Executor out) {
		return new MultiMergeExecutor(out) {

			@Override
			public void transform(VarStore in, Executor out) throws RPLException {
				String var = FreeVarNameProvider.getFreeVariable("acc");
				List<Object> values = new ArrayList<Object>();
				final RPLException stop = new RPLStopExecutionException();;
				try {
					InferringFunctionCall.this.getExecutorForFunctionCall(var, in, c, new Executor() {
						@Override
						public void close() throws RPLException {
							throw stop;
						}
	
						@Override
						public void push(State s) throws RPLException {
							if (s.getRank() == 0) {
								values.add(s.getVarStore().getValue(var));
							} else {
								throw stop;
							}
						}
					});
				} catch (RPLException ex) {
					if (ex != stop) throw ex;
				}
				out.push(new State(in.create(assignToVar, new PersistentArray(values)), 0));
				out.close();
			}
			
		};
	}
}
