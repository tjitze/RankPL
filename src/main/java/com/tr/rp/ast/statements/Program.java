package com.tr.rp.ast.statements;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

import com.tr.rp.ast.AbstractStatement;
import com.tr.rp.ast.FunctionScope;
import com.tr.rp.ast.LanguageElement;
import com.tr.rp.base.ExecutionContext;
import com.tr.rp.base.RankedItem;
import com.tr.rp.base.State;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.exceptions.RPLFunctionUndefinedException;
import com.tr.rp.exceptions.RPLInterruptedException;
import com.tr.rp.exceptions.RPLMiscException;
import com.tr.rp.executors.Executor;
import com.tr.rp.varstore.VarStoreFactory;

public class Program extends AbstractStatement {

	private final AbstractStatement body;
	private final FunctionScope functionScope;
	
	public Program(AbstractStatement body, FunctionScope functionScope) {
		this.body = body;
		this.functionScope = functionScope;
	}
	
	@Override
	public Executor getExecutor(Executor out, ExecutionContext c) {
		// TODO: properly handle exceptions
		// Execute body, then main
		if (functionScope.hasFunction("main")) {
			if (!body.equals(new Skip())) {
				throw new RuntimeException(
						new RPLMiscException("Found statements outside main() function scope."));
			}
			com.tr.rp.ast.Function f;
			try {
				f = functionScope.getFunction("main");
			} catch (RPLFunctionUndefinedException e) {
				throw new RuntimeException(e);
			}
			if (f.getParameters().length != 0) {
				throw new RuntimeException(
						new RPLMiscException("The main function should have zero parameters."));
			}
			return body.getExecutor(f.getBody().getExecutor(out, c), c);
		} else {
			return body.getExecutor(out, c);
		}
	}

	@Override
	public void getVariables(Set<String> list) {
		// nop
	}
	
	@Override
	public AbstractStatement rewriteEmbeddedFunctionCalls() {
		return this;
	}

	public String toString() {
		return body.toString();
	}
	
	/**
	 * Run program. Returns a ranked iterator that produces the values returned
	 * by this program's return statement. If there is no return statement, no
	 * values are returned.
	 */
	public void run(ExecutionContext c, Function<RankedItem<String>, Boolean> out) throws RPLException {
		HashSet<String> seen = new HashSet<String>();
		Executor e = getExecutor(new Executor() {

			@Override
			public void close() throws RPLException {
				seen.clear();
				out.apply(null);
			}

			@Override
			public void push(State s) throws RPLException {
				if (s.getVarStore().containsVar("$return")) {
					String returnValue = s.getVarStore().getValue("$return").toString();
					if (!seen.contains(returnValue)) {
						seen.add(returnValue);
						if (!out.apply(new RankedItem<String>(returnValue, s.getRank()))) {
							throw new RPLInterruptedException();
						}
					}
				}
			}
			
		}, c);
		e.push(VarStoreFactory.getInitialvarStore(), 0);
		e.close();
	}
	
	public AbstractStatement getBody() {
		return body;
	}

	public void stop() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void getAssignedVariables(Set<String> variables) {
		getAssignedVariables(variables);
	}

	@Override
	public boolean equals(Object o) {
		return o instanceof Program &&
				((Program)o).body.equals(body) &&
				((Program)o).functionScope.equals(functionScope);
	}

	@Override
	public int hashCode() {
		return Objects.hash(getClass(), body, functionScope);
	}	


}
