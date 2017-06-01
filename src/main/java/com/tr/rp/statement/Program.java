package com.tr.rp.statement;

import java.util.Set;

import com.tr.rp.core.DStatement;
import com.tr.rp.core.Function;
import com.tr.rp.core.FunctionScope;
import com.tr.rp.core.LanguageElement;
import com.tr.rp.core.VarStore;
import com.tr.rp.core.rankediterators.InitialVarStoreIterator;
import com.tr.rp.core.rankediterators.MarginalizingIterator;
import com.tr.rp.core.rankediterators.RankedIterator;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.exceptions.RPLMiscException;

public class Program extends DStatement {

	private final DStatement body;
	private final FunctionScope functionScope;
	
	public Program(DStatement body, FunctionScope functionScope) {
		this.body = body;
		this.functionScope = functionScope;
	}
	
	@Override
	public RankedIterator<VarStore> getIterator(RankedIterator<VarStore> parent) throws RPLException {
		// Execute body, then main
		if (functionScope.hasFunction("main")) {
			Function f = functionScope.getFunction("main");
			if (f.getParameters().length != 0) {
				throw new RPLMiscException("The main function should have zero parameters.");
			}
			return f.getBody().getIterator(body.getIterator(parent));
		} else {
			return body.getIterator(parent);
		}
	}

	@Override
	public void getVariables(Set<String> list) {
		// nop
	}

	@Override
	public LanguageElement replaceVariable(String a, String b) {
		return this;
	}

	@Override
	public DStatement rewriteEmbeddedFunctionCalls() {
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
	public RankedIterator<String> run() throws RPLException {
		RankedIterator<VarStore> i = getIterator(new InitialVarStoreIterator());
		final MarginalizingIterator mi = new MarginalizingIterator(i, "$return");
		return new RankedIterator<String>() {

			@Override
			public boolean next() throws RPLException {
				while (mi.next()) {
					if (mi.getItem().containsVar("$return")) {
						return true;
					}
				}
				return false;
			}

			@Override
			public String getItem() throws RPLException {
				VarStore v = mi.getItem();
				return v.getValue("$return").toString();
			}

			@Override
			public int getRank() {
				return mi.getRank();
			}
			
		};
	}

	public DStatement getBody() {
		return body;
	}

}
