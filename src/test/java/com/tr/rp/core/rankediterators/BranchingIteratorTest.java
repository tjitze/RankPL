package com.tr.rp.core.rankediterators;

import static com.tr.rp.ast.expressions.Expressions.*;
import static com.tr.rp.ast.statements.Statements.*;

import java.util.function.Function;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.AbstractStatement;
import com.tr.rp.ast.expressions.CustomFunction;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.exceptions.RPLTypeError;
import com.tr.rp.exceptions.RPLUndefinedException;
import com.tr.rp.iterators.ranked.BranchingIterator;
import com.tr.rp.iterators.ranked.BranchingIteratorErrorHandler;
import com.tr.rp.iterators.ranked.ExecutionContext;
import com.tr.rp.iterators.ranked.RankedIterator;
import com.tr.rp.statement.RPLBaseTest;
import com.tr.rp.varstore.PMapVarStore;
import com.tr.rp.varstore.VarStore;
import com.tr.rp.varstore.types.Type;

public class BranchingIteratorTest extends RPLBaseTest implements BranchingIteratorErrorHandler {
	
	public void testSimple1() throws RPLException {
		AbstractExpression exp = eq(mod(var("x"), lit(2)), lit(0));
		AbstractStatement s1 = assign("p", 0);
		AbstractStatement s2 = assign("p", 1);
		RankedIterator<VarStore> in = getIterator("x", 5);
		BranchingIterator it = new BranchingIterator(in, exp, s1, s2, ExecutionContext.createDefault(), this);
		
		assertTrue(it.next());
		VarStore v = it.getItem();
		int rank = it.getRank();
		assertEquals(rank, 0);
		assertEquals(v.getValue("x"), 0);
		assertEquals(v.getValue("p"), 0);
		
		assertTrue(it.next());
		v = it.getItem();
		rank = it.getRank();
		assertEquals(rank, 1);
		assertEquals(v.getValue("x"), 1);
		assertEquals(v.getValue("p"), 1);

		assertTrue(it.next());
		v = it.getItem();
		rank = it.getRank();
		assertEquals(rank, 2);
		assertEquals(v.getValue("x"), 2);
		assertEquals(v.getValue("p"), 0);

		assertTrue(it.next());
		v = it.getItem();
		rank = it.getRank();
		assertEquals(rank, 3);
		assertEquals(v.getValue("x"), 3);
		assertEquals(v.getValue("p"), 1);

		assertTrue(it.next());
		v = it.getItem();
		rank = it.getRank();
		assertEquals(rank, 4);
		assertEquals(v.getValue("x"), 4);
		assertEquals(v.getValue("p"), 0);

		assertFalse(it.next());
	}

	public void testSimple2() throws RPLException {
		AbstractExpression exp = not(eq(mod(var("x"), lit(2)), lit(0)));
		AbstractStatement s1 = assign("p", 0);
		AbstractStatement s2 = assign("p", 1);
		RankedIterator<VarStore> in = getIterator("x", 5);
		BranchingIterator it = new BranchingIterator(in, exp, s1, s2, ExecutionContext.createDefault(), this);
		
		assertTrue(it.next());
		VarStore v = it.getItem();
		int rank = it.getRank();
		assertEquals(rank, 0);
		assertEquals(v.getValue("x"), 0);
		assertEquals(v.getValue("p"), 1);
		
		assertTrue(it.next());
		v = it.getItem();
		rank = it.getRank();
		assertEquals(rank, 1);
		assertEquals(v.getValue("x"), 1);
		assertEquals(v.getValue("p"), 0);

		assertTrue(it.next());
		v = it.getItem();
		rank = it.getRank();
		assertEquals(rank, 2);
		assertEquals(v.getValue("x"), 2);
		assertEquals(v.getValue("p"), 1);

		assertTrue(it.next());
		v = it.getItem();
		rank = it.getRank();
		assertEquals(rank, 3);
		assertEquals(v.getValue("x"), 3);
		assertEquals(v.getValue("p"), 0);

		assertTrue(it.next());
		v = it.getItem();
		rank = it.getRank();
		assertEquals(rank, 4);
		assertEquals(v.getValue("x"), 4);
		assertEquals(v.getValue("p"), 1);

		assertFalse(it.next());
	}
	
	public void testSimple3() throws RPLException {
		AbstractExpression exp = or(eq(var("x"),lit(0)),eq(var("x"),lit(1)));
		AbstractStatement s1 = assign("p", 0);
		AbstractStatement s2 = assign("p", 1);
		RankedIterator<VarStore> in = getIterator("x", 5);
		BranchingIterator it = new BranchingIterator(in, exp, s1, s2, ExecutionContext.createDefault(), this);
		
		assertTrue(it.next());
		VarStore v = it.getItem();
		int rank = it.getRank();
		assertEquals(rank, 0);
		assertEquals(v.getValue("x"), 0);
		assertEquals(v.getValue("p"), 0);
		
		assertTrue(it.next());
		v = it.getItem();
		rank = it.getRank();
		assertEquals(rank, 1);
		assertEquals(v.getValue("x"), 1);
		assertEquals(v.getValue("p"), 0);

		assertTrue(it.next());
		v = it.getItem();
		rank = it.getRank();
		assertEquals(rank, 2);
		assertEquals(v.getValue("x"), 2);
		assertEquals(v.getValue("p"), 1);

		assertTrue(it.next());
		v = it.getItem();
		rank = it.getRank();
		assertEquals(rank, 3);
		assertEquals(v.getValue("x"), 3);
		assertEquals(v.getValue("p"), 1);

		assertTrue(it.next());
		v = it.getItem();
		rank = it.getRank();
		assertEquals(rank, 4);
		assertEquals(v.getValue("x"), 4);
		assertEquals(v.getValue("p"), 1);


		assertFalse(it.next());
	}

	public void testSimple4() throws RPLException {
		AbstractExpression exp = or(or(eq(var("x"),lit(2)),eq(var("x"),lit(3))),eq(var("x"),lit(4)));
		AbstractStatement s1 = assign("p", 0);
		AbstractStatement s2 = assign("p", 1);
		RankedIterator<VarStore> in = getIterator("x", 5);
		BranchingIterator it = new BranchingIterator(in, exp, s1, s2, ExecutionContext.createDefault(), this);
		
		assertTrue(it.next());
		VarStore v = it.getItem();
		int rank = it.getRank();
		assertEquals(rank, 0);
		assertEquals(v.getValue("x"), 0);
		assertEquals(v.getValue("p"), 1);
		
		assertTrue(it.next());
		v = it.getItem();
		rank = it.getRank();
		assertEquals(rank, 1);
		assertEquals(v.getValue("x"), 1);
		assertEquals(v.getValue("p"), 1);

		assertTrue(it.next());
		v = it.getItem();
		rank = it.getRank();
		assertEquals(rank, 2);
		assertEquals(v.getValue("x"), 2);
		assertEquals(v.getValue("p"), 0);

		assertTrue(it.next());
		v = it.getItem();
		rank = it.getRank();
		assertEquals(rank, 3);
		assertEquals(v.getValue("x"), 3);
		assertEquals(v.getValue("p"), 0);

		assertTrue(it.next());
		v = it.getItem();
		rank = it.getRank();
		assertEquals(rank, 4);
		assertEquals(v.getValue("x"), 4);
		assertEquals(v.getValue("p"), 0);

		assertFalse(it.next());
	}
	
	public void testSimple5() throws RPLException {
		AbstractExpression exp = lit(false);
		AbstractStatement s1 = assign("p", 0);
		AbstractStatement s2 = assign("p", 1);
		RankedIterator<VarStore> in = getIterator("x", 5);
		BranchingIterator it = new BranchingIterator(in, exp, s1, s2, ExecutionContext.createDefault(), this);
		
		assertTrue(it.next());
		VarStore v = it.getItem();
		int rank = it.getRank();
		assertEquals(rank, 0);
		assertEquals(v.getValue("x"), 0);
		assertEquals(v.getValue("p"), 1);
		
		assertTrue(it.next());
		v = it.getItem();
		rank = it.getRank();
		assertEquals(rank, 1);
		assertEquals(v.getValue("x"), 1);
		assertEquals(v.getValue("p"), 1);

		assertTrue(it.next());
		v = it.getItem();
		rank = it.getRank();
		assertEquals(rank, 2);
		assertEquals(v.getValue("x"), 2);
		assertEquals(v.getValue("p"), 1);

		assertTrue(it.next());
		v = it.getItem();
		rank = it.getRank();
		assertEquals(rank, 3);
		assertEquals(v.getValue("x"), 3);
		assertEquals(v.getValue("p"), 1);

		assertTrue(it.next());
		v = it.getItem();
		rank = it.getRank();
		assertEquals(rank, 4);
		assertEquals(v.getValue("x"), 4);
		assertEquals(v.getValue("p"), 1);

		assertFalse(it.next());
	}	
	
	public void testSimple6() throws RPLException {
		AbstractExpression exp = lit(true);
		AbstractStatement s1 = assign("p", 0);
		AbstractStatement s2 = assign("p", 1);
		RankedIterator<VarStore> in = getIterator("x", 5);
		BranchingIterator it = new BranchingIterator(in, exp, s1, s2, ExecutionContext.createDefault(), this);
		
		assertTrue(it.next());
		VarStore v = it.getItem();
		int rank = it.getRank();
		assertEquals(rank, 0);
		assertEquals(v.getValue("x"), 0);
		assertEquals(v.getValue("p"), 0);
		
		assertTrue(it.next());
		v = it.getItem();
		rank = it.getRank();
		assertEquals(rank, 1);
		assertEquals(v.getValue("x"), 1);
		assertEquals(v.getValue("p"), 0);

		assertTrue(it.next());
		v = it.getItem();
		rank = it.getRank();
		assertEquals(rank, 2);
		assertEquals(v.getValue("x"), 2);
		assertEquals(v.getValue("p"), 0);

		assertTrue(it.next());
		v = it.getItem();
		rank = it.getRank();
		assertEquals(rank, 3);
		assertEquals(v.getValue("x"), 3);
		assertEquals(v.getValue("p"), 0);

		assertTrue(it.next());
		v = it.getItem();
		rank = it.getRank();
		assertEquals(rank, 4);
		assertEquals(v.getValue("x"), 4);
		assertEquals(v.getValue("p"), 0);

		assertFalse(it.next());
	}
	
//	public void testBug() throws RPLException {
//		AbstractExpression exp = lit(false);
//		AbstractStatement s1 = comp(assign("p", 0));
//		AbstractStatement s2 = comp(assign("p", 1));
//		RankedIterator<VarStore> in = getIterator("x", 1);
//		
//		BranchingIterator it = new BranchingIterator(in, exp, s1, s2, ExecutionContext.createDefault(), this);
//
//		while(it.next()) {
//			VarStore v = it.getItem();
//			int rank = it.getRank();
//			System.out.println("rank " + rank + ": " + v);
//		}
//
//		for (int i = 0; i < 1; i++) {
//			assertTrue(it.next());
//			VarStore v = it.getItem();
//			int rank = it.getRank();
//			assertEquals(i, rank);
//			assertEquals(1, v.getValue("p"));
//		}
//		assertFalse(it.next());
//	}
//

	public void testExhaustive() throws RPLException {
		for (int k = 0; k < 5; k++) {
			for (int i = 0; i < (int)Math.pow(2, k); i++) {
				performTest(i, k);
			}
		}
	}
	
	public void performTest(int mask, int len) throws RPLException {
		System.out.println("Perform test mask = " + mask + " len = " + len);
		AbstractExpression exp = CustomFunction.create(new Function<VarStore, Boolean>() {
			@Override
			public Boolean apply(VarStore t) {
				int value = 0;
				try {
					value = t.getValue("x", Type.INT);
				} catch (RPLUndefinedException e) {
					fail();
				} catch (RPLTypeError e) {
					fail();
				}
				return (mask & (1 << value)) != 0;
			}
		});
		AbstractStatement s1 = comp(assign("p", 0), assign("q", 10));
		AbstractStatement s2 = comp(assign("p", 5), assign("q", 15));
		RankedIterator<VarStore> in = getIterator("x", len);
		
		BranchingIterator it = new BranchingIterator(in, exp, s1, s2, ExecutionContext.createDefault(), this);

		for (int i = 0; i < len; i++) {
			assertTrue(it.next());
			VarStore v = it.getItem();
			int rank = it.getRank();
			assertEquals(i, rank);
			int value = 0;
			try {
				value = v.getValue("x", Type.INT);
			} catch (RPLUndefinedException e) {
				fail();
			} catch (RPLTypeError e) {
				fail();
			}
			if ((mask & (1 << value)) != 0) {
				assertEquals(0, v.getValue("p"));
				assertEquals(10, v.getValue("q"));
			} else {
				assertEquals(5, v.getValue("p"));
				assertEquals(15, v.getValue("q"));
			}
		}
		assertFalse(it.next());
	}
	
	public void testBlockedThen() throws RPLException {
		AbstractExpression exp = eq(mod(var("x"), lit(2)), lit(0));
		AbstractStatement s1 = observe(lit(false));
		AbstractStatement s2 = assign("p", 5);
		RankedIterator<VarStore> in = getIterator("x", 5);
		BranchingIterator it = new BranchingIterator(in, exp, s1, s2, ExecutionContext.createDefault(), this);

//		System.out.println("blocked then");
//		while(it.next()) {
//			VarStore v = it.getItem();
//			int rank = it.getRank();
//			System.out.println("rank " + rank + ": " + v);
//		}

//		assertTrue(it.next());
//		VarStore v = it.getItem();
//		int rank = it.getRank();
//		assertEquals(rank, 0);
//		assertEquals(v.getValue("x"), 0);
//		assertEquals(v.getValue("p"), 5);
		
		assertTrue(it.next());
		VarStore v = it.getItem();
		int rank = it.getRank();
		assertEquals(rank, 0);
		assertEquals(v.getValue("x"), 1);
		assertEquals(v.getValue("p"), 5);

//		assertTrue(it.next());
//		v = it.getItem();
//		rank = it.getRank();
//		assertEquals(rank, 2);
//		assertEquals(v.getValue("x"), 2);
//		assertEquals(v.getValue("p"), 5);

		assertTrue(it.next());
		v = it.getItem();
		rank = it.getRank();
		assertEquals(rank, 2);
		assertEquals(v.getValue("x"), 3);
		assertEquals(v.getValue("p"), 5);

//		assertTrue(it.next());
//		v = it.getItem();
//		rank = it.getRank();
//		assertEquals(rank, 4);
//		assertEquals(v.getValue("x"), 4);
//		assertEquals(v.getValue("p"), 5);

		assertFalse(it.next());
	}

	public void testBlockedElse() throws RPLException {
		AbstractExpression exp = eq(mod(var("x"), lit(2)), lit(0));
		AbstractStatement s1 = assign("p", 5);
		AbstractStatement s2 = observe(lit(false));
		RankedIterator<VarStore> in = getIterator("x", 5);
		BranchingIterator it = new BranchingIterator(in, exp, s1, s2, ExecutionContext.createDefault(), this);

//		System.out.println("blocked else");
//		while(it.next()) {
//			VarStore v = it.getItem();
//			int rank = it.getRank();
//			System.out.println("rank " + rank + ": " + v);
//		}
		
		assertTrue(it.next());
		VarStore v = it.getItem();
		int rank = it.getRank();
		assertEquals(rank, 0);
		assertEquals(v.getValue("x"), 0);
		assertEquals(v.getValue("p"), 5);
		
//		assertTrue(it.next());
//		v = it.getItem();
//		rank = it.getRank();
//		assertEquals(rank, 1);
//		assertEquals(v.getValue("x"), 1);
//		assertEquals(v.getValue("p"), 5);

		assertTrue(it.next());
		v = it.getItem();
		rank = it.getRank();
		assertEquals(rank, 2);
		assertEquals(v.getValue("x"), 2);
		assertEquals(v.getValue("p"), 5);

//		assertTrue(it.next());
//		v = it.getItem();
//		rank = it.getRank();
//		assertEquals(rank, 3);
//		assertEquals(v.getValue("x"), 3);
//		assertEquals(v.getValue("p"), 5);

		assertTrue(it.next());
		v = it.getItem();
		rank = it.getRank();
		assertEquals(rank, 4);
		assertEquals(v.getValue("x"), 4);
		assertEquals(v.getValue("p"), 5);

		assertFalse(it.next());
	}

	private RankedIterator<VarStore> getIterator(String var, int b) {
		
		return new RankedIterator<VarStore>() {
			private int i = -1;

			@Override
			public boolean next() throws RPLException {
				i++;
				return i < b;
			}

			@Override
			public VarStore getItem() throws RPLException {
				VarStore v = new PMapVarStore();
				v = v.create(var, i);
				return v;
			}

			@Override
			public int getRank() {
				return i;
			}
			
		};
	}

	@Override
	public void handlExpError(RPLException exception) throws RPLException {
		throw exception;
	}

}
