package com.tr.rp.statement;

import static com.tr.rp.ast.expressions.Expressions.*;
import static com.tr.rp.ast.statements.Statements.*;

import com.tr.rp.ast.ProgramBuilder;
import com.tr.rp.ast.expressions.ArrayInitExpression;
import com.tr.rp.ast.expressions.Literal;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.iterators.ranked.ExecutionContext;
import com.tr.rp.iterators.ranked.InitialVarStoreIterator;
import com.tr.rp.iterators.ranked.RankedIterator;
import com.tr.rp.varstore.VarStore;
import com.tr.rp.varstore.types.PersistentList;
import com.tr.rp.varstore.types.Type;

public class ArraysTest extends RPLBaseTest {

	public void testArrayInitialization() throws RPLException {
		
		final int d1 = 5;
		final int d2 = 4;
		final int d3 = 3;
		
		// 1D array of null
		RankedIterator<VarStore> result = assign("x", 
				new ArrayInitExpression(lit(d1), null))
				.getIterator(new InitialVarStoreIterator(), ExecutionContext.createDefault());
		assert(result.next());
		PersistentList list = (PersistentList)result.getItem().getValue("x");
		assertEquals(d1, list.size());
		for (int i = 0; i < d1; i++) {
			assertEquals(null, list.get(i));
		}

		// 1D array of int
		result = assign("x", 
				new ArrayInitExpression(lit(d1), lit(0)))
				.getIterator(new InitialVarStoreIterator(), ExecutionContext.createDefault());
		assert(result.next());
		list = (PersistentList)result.getItem().getValue("x");
		assertEquals(d1, list.size());
		for (int i = 0; i < d1; i++) {
			assertEquals(0, list.get(i));
		}

		// 1D array of strings
		result = assign("x", 
				new ArrayInitExpression(lit(d1), new Literal<String>("")))
				.getIterator(new InitialVarStoreIterator(), ExecutionContext.createDefault());
		assert(result.next());
		assert(result.getItem().getValue("x") instanceof PersistentList);
		list = (PersistentList)result.getItem().getValue("x");
		assertEquals(d1, list.size());
		for (int i = 0; i < d1; i++) {
			assertEquals("", list.get(i));
		}

		// 2D array of null
		result = assign("x", 
				new ArrayInitExpression(lit(d1), new ArrayInitExpression(lit(d2), null)))
				.getIterator(new InitialVarStoreIterator(), ExecutionContext.createDefault());
		assert(result.next());
		assert(result.getItem().getValue("x") instanceof PersistentList);
		list = (PersistentList)result.getItem().getValue("x");
		assertEquals(5, list.size());
		for (int i = 0; i < d1; i++) {
			assert(list.get(i) instanceof PersistentList);
			PersistentList list2 = (PersistentList)list.get(i);
			assert(list2.size() == d2);
			for (int j = 0; j < d2; j++) {
				assertEquals(null, list2.get(j));
			}
		}

		// 2D array of ints
		result = assign("x", 
				new ArrayInitExpression(lit(d1), new ArrayInitExpression(lit(d2), lit(0))))
				.getIterator(new InitialVarStoreIterator(), ExecutionContext.createDefault());
		assert(result.next());
		assert(result.getItem().getValue("x") instanceof PersistentList);
		list = (PersistentList)result.getItem().getValue("x");
		assertEquals(5, list.size());
		for (int i = 0; i < d1; i++) {
			assert(list.get(i) instanceof PersistentList);
			PersistentList list2 = (PersistentList)list.get(i);
			assert(list2.size() == d2);
			for (int j = 0; j < d2; j++) {
				assertEquals(0, list2.get(j));
			}
		}

		// 3D array of null
		result = assign("x", 
				new ArrayInitExpression(lit(d1), new ArrayInitExpression(lit(d2), new ArrayInitExpression(lit(d3), null))))
				.getIterator(new InitialVarStoreIterator(), ExecutionContext.createDefault());
		assert(result.next());
		assert(result.getItem().getValue("x") instanceof PersistentList);
		list = (PersistentList)result.getItem().getValue("x");
		assertEquals(5, list.size());
		for (int i = 0; i < d1; i++) {
			assert(list.get(i) instanceof PersistentList);
			PersistentList list2 = (PersistentList)list.get(i);
			assert(list2.size() == d2);
			for (int j = 0; j < d2; j++) {
				assert(list2.get(j) instanceof PersistentList);
				PersistentList list3 = (PersistentList)list2.get(j);
				assert(list3.size() == d3);
				for (int k = 0; k < d3; k++) {
					assertEquals(null, list3.get(k));
				}
			}
		}

		// 3D array of int
		result = assign("x", 
				new ArrayInitExpression(lit(d1), new ArrayInitExpression(lit(d2), new ArrayInitExpression(lit(d3), lit(0)))))
				.getIterator(new InitialVarStoreIterator(), ExecutionContext.createDefault());
		assert(result.next());
		assert(result.getItem().getValue("x") instanceof PersistentList);
		list = (PersistentList)result.getItem().getValue("x");
		assertEquals(5, list.size());
		for (int i = 0; i < d1; i++) {
			assert(list.get(i) instanceof PersistentList);
			PersistentList list2 = (PersistentList)list.get(i);
			assert(list2.size() == d2);
			for (int j = 0; j < d2; j++) {
				assert(list2.get(j) instanceof PersistentList);
				PersistentList list3 = (PersistentList)list2.get(j);
				assert(list3.size() == d3);
				for (int k = 0; k < d3; k++) {
					assertEquals(0, list3.get(k));
				}
			}
		}
}
	
	public void test1DArrayAssignment() throws RPLException {
		ProgramBuilder p = new ProgramBuilder()
				.add(assign("x", 
						new ArrayInitExpression(lit(3), null)))
				.add(assign(target("x", 0), 
						new Literal<Integer>(1)))
				.add(assign(target("x", 1), 
						new Literal<Integer>(2)))
				.add(assign(target("x", 2), 
						new Literal<Integer>(3)))
				.add(assign(target("x", 2), 
						new Literal<Integer>(100)));
		RankedIterator<VarStore> result = p.build().getIterator(new InitialVarStoreIterator(), ExecutionContext.createDefault());
		
		assert(result.next());
		VarStore vs = result.getItem();
		assert(!result.next());
		assertEquals(1, (int)(indexedExp(var("x"), 0)).getValue(vs, Type.INT));
		assertEquals(2, (int)(indexedExp(var("x"), 1)).getValue(vs, Type.INT));
		assertEquals(100, (int)(indexedExp(var("x"), 2)).getValue(vs, Type.INT));
	}
	
	public void test2DArrayAssignment() throws RPLException {
		ProgramBuilder p = new ProgramBuilder()
				.add(assign("x", 
						new ArrayInitExpression(lit(3), new ArrayInitExpression(lit(4), null))))
				.add(assign(target("x", 0, 1), 
						new Literal<Integer>(1)))
				.add(assign(target("x", 1, 2), 
						new Literal<Integer>(2)))
				.add(assign(target("x", 2, 3), 
						new Literal<Integer>(3)))
				.add(assign(target("x", 2, 3), 
						new Literal<Integer>(100)));
		RankedIterator<VarStore> result = p.build().getIterator(new InitialVarStoreIterator(), ExecutionContext.createDefault());
		
		assert(result.next());
		VarStore vs = result.getItem();
		assert(!result.next());
		assertEquals(1, (int)(indexedExp(var("x"), 0, 1)).getValue(vs, Type.INT));
		assertEquals(2, (int)(indexedExp(var("x"), 1, 2)).getValue(vs, Type.INT));
		assertEquals(100, (int)(indexedExp(var("x"), 2, 3)).getValue(vs, Type.INT));
	}
	
	public void testIndexed1DExpression() throws RPLException {
		ProgramBuilder p = new ProgramBuilder()
				.add(assign("x", 
						new ArrayInitExpression(lit(3), null)))
				.add(assign(target("x", 2), 
						new Literal<Integer>(100)))
				.add(assign("y", indexedExp(var("x"), 2)));
		RankedIterator<VarStore> result = p.build().getIterator(new InitialVarStoreIterator(), ExecutionContext.createDefault());
		
		assert(result.next());
		VarStore vs = result.getItem();
		assert(!result.next());
		assertEquals(100, (int)(indexedExp(var("x"), 2)).getValue(vs, Type.INT));
		assertEquals(100, (int)vs.getValue("y", Type.INT));
	}
	
	public void testIndexed2DExpression() throws RPLException {
		ProgramBuilder p = new ProgramBuilder()
				.add(assign("x", 
						new ArrayInitExpression(lit(3), new ArrayInitExpression(lit(4), null))))
				.add(assign(target("x", 2, 3), new Literal<Integer>(100)))
				.add(assign("y", indexedExp(var("x"), 2, 3)));
		RankedIterator<VarStore> result = p.build().getIterator(new InitialVarStoreIterator(), ExecutionContext.createDefault());
		
		assert(result.next());
		VarStore vs = result.getItem();
		assert(!result.next());
		assertEquals(100, (int)indexedExp(var("x"), 2, 3).getValue(vs, Type.INT));
		assertEquals(100, (int)vs.getValue("y", Type.INT));
	}

}
