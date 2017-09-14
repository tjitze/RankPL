package com.tr.rp.ast.expressions;

import java.util.Arrays;
import java.util.stream.Collectors;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.exec.ExecutionContext;
import com.tr.rp.exec.Executor;
import com.tr.rp.ranks.FunctionScope;
import com.tr.rp.varstore.VarStore;

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
		throw new NotImplementedException();
//		return new MultiMergeIterator<VarStore>(parent) {
//
//			@Override
//			public RankedIterator<VarStore> transform(VarStore in) throws RPLException {
//				String var = FreeVarNameProvider.getFreeVariable("acc");
//				RankedIterator<VarStore> it = getIteratorForFunctionCall(var, in, c);
//				List<Object> values = new ArrayList<Object>();
//				while (it.next() && it.getRank() == 0) {
//					values.add(it.getItem().getValue(var));
//				}
//				return new InitialVarStoreIterator(in.create(assignToVar, new PersistentArray(values)));
//			}
//		};
//	}
	}
}
