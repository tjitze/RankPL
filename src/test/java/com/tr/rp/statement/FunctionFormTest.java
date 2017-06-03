package com.tr.rp.statement;

import static com.tr.rp.expressions.Expressions.*;

import java.util.ArrayList;
import java.util.List;

import com.tr.rp.core.DStatement;
import com.tr.rp.core.Expression;
import com.tr.rp.core.Function;
import com.tr.rp.core.FunctionScope;
import com.tr.rp.core.ProgramBuilder;
import com.tr.rp.core.VarStore;
import com.tr.rp.core.rankediterators.ExecutionContext;
import com.tr.rp.core.rankediterators.InitialVarStoreIterator;
import com.tr.rp.core.rankediterators.RankedIterator;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.expressions.AbstractFunctionCall;
import com.tr.rp.expressions.Expressions;
import com.tr.rp.expressions.FunctionCall;
import com.tr.rp.expressions.Literal;
import com.tr.rp.expressions.Variable;
import com.tr.rp.tools.Pair;

public class FunctionFormTest extends RPLBaseTest {

	private static final FunctionScope scope = new FunctionScope();
	
	static {
		scope.registerFunction(getTestFunction1());
		scope.registerFunction(getTestFunction2());
	}
	
	public static Function getTestFunction1() {
		String[] params = new String[] { "x" };
		return new Function("test1", new Composition(
				new Assign("y", Expressions.plus(new Variable("x"), Expressions.lit(100))), 
				new Return("y")), params);
	}
	
	public static Function getTestFunction2() {
		String[] params = new String[] { "x" };
		return new Function("test2", new Composition(
				new RankedChoice(target("y"), Expressions.plus(new Variable("x"), Expressions.lit(100)), Expressions.plus(new Variable("x"), Expressions.lit(1000)), 5),
				new Return("y")), params);
	}
	
	public void testRewrite() {
		Expression[] args = new Expression[] { new Variable("a") };
		DStatement s = new ProgramBuilder()
				.add(new Assign("a", 20))
				.add(new Assign("b", new FunctionCall("test1", scope, args)))
				.build();
		
		// Construct expected function call form
		List<Pair<String, AbstractFunctionCall>> assignments = new ArrayList<Pair<String, AbstractFunctionCall>>();
		assignments.add(new Pair<String, AbstractFunctionCall>("x", new FunctionCall("test1", scope, args)));
		DStatement rs = new Assign("b", "x");
		FunctionCallForm fcf = new FunctionCallForm(rs, assignments);
		
		// Test
		DStatement rw = s.rewriteEmbeddedFunctionCalls();
		assertEquals(rw.getClass(), Composition.class);
		assertEquals(((Composition)rw).getFirst(), new Assign("a", 20));
		assertEquals(((Composition)rw).getSecond().getClass(), FunctionCallForm.class);
		assertEquals(true, ((FunctionCallForm)((Composition)rw).getSecond()).semanticEquals(fcf));
	}		
		
	public void testExecuteSingle() throws RPLException {
		Expression[] args = new Expression[] { new Variable("a") };
		DStatement s = new ProgramBuilder()
				.add(new Assign("a", 20))
				.add(new Assign("b", new FunctionCall("test1", scope, args)))
				.build();
		s = s.rewriteEmbeddedFunctionCalls();
		RankedIterator<VarStore> it = s.getIterator(new InitialVarStoreIterator(), ExecutionContext.createDefault());
		
		assertEquals(true, it.next());
		VarStore v = it.getItem();
		assertEquals(false, it.next());

		assertEquals(20, v.getIntValue("a"));
		assertEquals(120, v.getIntValue("b"));
		assertEquals(null, v.getValue("x"));
		assertEquals(null, v.getValue("y"));
	}
	
	public void testRewriteNested() {
		Expression[] args1 = new Expression[] { new Variable("a") };
		Expression[] args2 = new Expression[] { new FunctionCall("test1", scope, args1) };
		DStatement s = new ProgramBuilder()
				.add(new Assign("a", 20))
				.add(new Assign("b", new FunctionCall("test1", scope, args2)))
				.build();
		
		// Construct expected function call form
		List<Pair<String, AbstractFunctionCall>> assignments = new ArrayList<Pair<String, AbstractFunctionCall>>();
		Expression[] args3 = new Expression[] { new Variable("x") };
		assignments.add(new Pair<String, AbstractFunctionCall>("x", new FunctionCall("test1", scope, args1)));
		assignments.add(new Pair<String, AbstractFunctionCall>("y", new FunctionCall("test1", scope,  args3)));
		DStatement rs = new Assign("b", "y");
		FunctionCallForm fcf = new FunctionCallForm(rs, assignments);
		
		// Test
		DStatement rw = s.rewriteEmbeddedFunctionCalls();
		assertEquals(rw.getClass(), Composition.class);
		assertEquals(((Composition)rw).getFirst(), new Assign("a", 20));
		assertEquals(((Composition)rw).getSecond().getClass(), FunctionCallForm.class);
		assertEquals(true, ((FunctionCallForm)((Composition)rw).getSecond()).semanticEquals(fcf));

	}

	public void testExecuteNested() throws RPLException {
		Expression[] args1 = new Expression[] { new Variable("a") };
		Expression[] args2 = new Expression[] { new FunctionCall("test1", scope, args1) };
		DStatement s = new ProgramBuilder()
				.add(new Assign("a", 20))
				.add(new Assign("b", new FunctionCall("test1", scope, args2)))
				.build();
		s = s.rewriteEmbeddedFunctionCalls();
		RankedIterator<VarStore> it = s.getIterator(new InitialVarStoreIterator(), ExecutionContext.createDefault());
		
		assertEquals(true, it.next());
		VarStore v = it.getItem();
		assertEquals(false, it.next());

		assertEquals(20, v.getIntValue("a"));
		assertEquals(220, v.getIntValue("b"));
		assertEquals(null, v.getValue("x"));
		assertEquals(null, v.getValue("y"));
	}
	
	public void testExecuteRanked() throws RPLException {
		Expression[] args = new Expression[] { new Variable("a") };
		DStatement s = new ProgramBuilder()
				.add(new Assign("a", 20))
				.add(new Assign("b", new FunctionCall("test2", scope, args)))
				.build();
		s = s.rewriteEmbeddedFunctionCalls();
		RankedIterator<VarStore> it = s.getIterator(new InitialVarStoreIterator(), ExecutionContext.createDefault());
		
		assertEquals(true, it.next());
		assertEquals(0, it.getRank());
		VarStore v1 = it.getItem();
		assertEquals(true, it.next());
		assertEquals(5, it.getRank());
		VarStore v2 = it.getItem();
		assertEquals(false, it.next());

		assertEquals(20, v1.getIntValue("a"));
		assertEquals(120, v1.getIntValue("b"));
		assertEquals(null, v1.getValue("x"));
		assertEquals(null, v1.getValue("y"));

		assertEquals(20, v2.getIntValue("a"));
		assertEquals(1020, v2.getIntValue("b"));
		assertEquals(null, v2.getValue("x"));
		assertEquals(null, v2.getValue("y"));
	}

	
}
