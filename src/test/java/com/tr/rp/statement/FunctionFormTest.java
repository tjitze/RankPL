package com.tr.rp.statement;

import static com.tr.rp.ast.expressions.Expressions.*;
import static com.tr.rp.ast.statements.Statements.*;

import java.util.ArrayList;
import java.util.List;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.AbstractStatement;
import com.tr.rp.ast.Function;
import com.tr.rp.ast.ProgramBuilder;
import com.tr.rp.ast.expressions.AbstractFunctionCall;
import com.tr.rp.ast.expressions.Expressions;
import com.tr.rp.ast.expressions.FunctionCall;
import com.tr.rp.ast.expressions.Literal;
import com.tr.rp.ast.expressions.Variable;
import com.tr.rp.ast.statements.Assign;
import com.tr.rp.ast.statements.Composition;
import com.tr.rp.ast.statements.FunctionCallForm;
import com.tr.rp.ast.statements.FunctionCallForm.Assignment;
import com.tr.rp.ast.statements.RankedChoice;
import com.tr.rp.ast.statements.Return;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.iterators.ranked.ExecutionContext;
import com.tr.rp.iterators.ranked.InitialVarStoreIterator;
import com.tr.rp.iterators.ranked.RankedIterator;
import com.tr.rp.ranks.FunctionScope;
import com.tr.rp.varstore.VarStore;
import com.tr.rp.varstore.types.Type;

public class FunctionFormTest extends RPLBaseTest {

	private static final FunctionScope scope = new FunctionScope();
	
	static {
		scope.registerFunction(getTestFunction1());
		scope.registerFunction(getTestFunction2());
	}
	
	public static Function getTestFunction1() {
		String[] params = new String[] { "x" };
		return new Function("test1", new Composition(
				assign("y", Expressions.plus(new Variable("x"), Expressions.lit(100))), 
				returnStatement("y")), params);
	}
	
	public static Function getTestFunction2() {
		String[] params = new String[] { "x" };
		return new Function("test2", new Composition(
				new RankedChoice(target("y"), Expressions.plus(new Variable("x"), Expressions.lit(100)), 
						Expressions.plus(new Variable("x"), Expressions.lit(1000)), lit(5)),
				returnStatement("y")), params);
	}
	
	public void testRewrite() {
		AbstractExpression[] args = new AbstractExpression[] { new Variable("a") };
		AbstractStatement s = new ProgramBuilder()
				.add(assign("a", 20))
				.add(assign("b", new FunctionCall("test1", scope, args)))
				.build();
		
		// Construct expected function call form
		List<Assignment> assignments = new ArrayList<Assignment>();
		assignments.add(new Assignment("x", new FunctionCall("test1", scope, args)));
		AbstractStatement rs = assign("b", var("x"));
		FunctionCallForm fcf = new FunctionCallForm(rs, assignments);
		
		// Test
		AbstractStatement rw = s.rewriteEmbeddedFunctionCalls();
		assertEquals(rw.getClass(), Composition.class);
		assertEquals(((Composition)rw).getFirst(), assign("a", 20));
		assertEquals(((Composition)rw).getSecond().getClass(), FunctionCallForm.class);
		assertEquals(true, ((FunctionCallForm)((Composition)rw).getSecond()).semanticEquals(fcf));
	}		
		
	public void testExecuteSingle() throws RPLException {
		AbstractExpression[] args = new AbstractExpression[] { new Variable("a") };
		AbstractStatement s = new ProgramBuilder()
				.add(assign("a", 20))
				.add(assign("b", new FunctionCall("test1", scope, args)))
				.build();
		s = s.rewriteEmbeddedFunctionCalls();
		RankedIterator<VarStore> it = s.getIterator(new InitialVarStoreIterator(), ExecutionContext.createDefault());
		
		assertEquals(true, it.next());
		VarStore v = it.getItem();
		assertEquals(false, it.next());

		assertEquals(20, (int)v.getValue("a", Type.INT));
		assertEquals(120, (int)v.getValue("b", Type.INT));
		assertEquals(null, v.getValue("x"));
		assertEquals(null, v.getValue("y"));
	}
	
	public void testRewriteNested() {
		AbstractExpression[] args1 = new AbstractExpression[] { new Variable("a") };
		AbstractExpression[] args2 = new AbstractExpression[] { new FunctionCall("test1", scope, args1) };
		AbstractStatement s = new ProgramBuilder()
				.add(assign("a", 20))
				.add(assign("b", new FunctionCall("test1", scope, args2)))
				.build();
		
		// Construct expected function call form
		List<Assignment> assignments = new ArrayList<Assignment>();
		AbstractExpression[] args3 = new AbstractExpression[] { new Variable("x") };
		assignments.add(new Assignment("x", new FunctionCall("test1", scope, args1)));
		assignments.add(new Assignment("y", new FunctionCall("test1", scope,  args3)));
		AbstractStatement rs = assign("b", var("y"));
		FunctionCallForm fcf = new FunctionCallForm(rs, assignments);
		
		// Test
		AbstractStatement rw = s.rewriteEmbeddedFunctionCalls();
		assertEquals(rw.getClass(), Composition.class);
		assertEquals(((Composition)rw).getFirst(), assign("a", 20));
		assertEquals(((Composition)rw).getSecond().getClass(), FunctionCallForm.class);
		assertEquals(true, ((FunctionCallForm)((Composition)rw).getSecond()).semanticEquals(fcf));

	}

	public void testExecuteNested() throws RPLException {
		AbstractExpression[] args1 = new AbstractExpression[] { new Variable("a") };
		AbstractExpression[] args2 = new AbstractExpression[] { new FunctionCall("test1", scope, args1) };
		AbstractStatement s = new ProgramBuilder()
				.add(assign("a", 20))
				.add(assign("b", new FunctionCall("test1", scope, args2)))
				.build();
		s = s.rewriteEmbeddedFunctionCalls();
		RankedIterator<VarStore> it = s.getIterator(new InitialVarStoreIterator(), ExecutionContext.createDefault());
		
		assertEquals(true, it.next());
		VarStore v = it.getItem();
		assertEquals(false, it.next());

		assertEquals(20, (int)v.getValue("a", Type.INT));
		assertEquals(220, (int)v.getValue("b", Type.INT));
		assertEquals(null, v.getValue("x"));
		assertEquals(null, v.getValue("y"));
	}
	
	public void testExecuteRanked() throws RPLException {
		AbstractExpression[] args = new AbstractExpression[] { new Variable("a") };
		AbstractStatement s = new ProgramBuilder()
				.add(assign("a", 20))
				.add(assign("b", new FunctionCall("test2", scope, args)))
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

		assertEquals(20, (int)v1.getValue("a", Type.INT));
		assertEquals(120, (int)v1.getValue("b", Type.INT));
		assertEquals(null, v1.getValue("x"));
		assertEquals(null, v1.getValue("y"));

		assertEquals(20, (int)v2.getValue("a", Type.INT));
		assertEquals(1020, (int)v2.getValue("b", Type.INT));
		assertEquals(null, v2.getValue("x"));
		assertEquals(null, v2.getValue("y"));
	}

	
}
