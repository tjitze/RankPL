package com.tr.rp.exec;

import static com.tr.rp.ast.expressions.Expressions.geq;
import static com.tr.rp.ast.expressions.Expressions.lit;
import static com.tr.rp.ast.expressions.Expressions.lt;
import static com.tr.rp.ast.expressions.Expressions.var;

import java.util.LinkedList;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.statement.RPLBaseTest;
import com.tr.rp.varstore.PMapVarStore;
import com.tr.rp.varstore.VarStore;
import com.tr.rp.varstore.VarStoreFactory;

public class LShifterTest extends RPLBaseTest {

	public static VarStore s1 = VarStoreFactory.getInitialvarStore().create("x", 1);
	public static VarStore s2 = VarStoreFactory.getInitialvarStore().create("x", 2);
	public static VarStore s3 = VarStoreFactory.getInitialvarStore().create("x", 3);
	public static VarStore s4 = VarStoreFactory.getInitialvarStore().create("x", 4);
	public static VarStore s5 = VarStoreFactory.getInitialvarStore().create("x", 5);
	public static VarStore s6 = VarStoreFactory.getInitialvarStore().create("x", 6);
	public static VarStore s7 = VarStoreFactory.getInitialvarStore().create("x", 7);
	public static VarStore s8 = VarStoreFactory.getInitialvarStore().create("x", 8);
	public static VarStore s9 = VarStoreFactory.getInitialvarStore().create("x", 9);
	
	private boolean closed;

	public void testShiftFT0() throws RPLException {
		
		AbstractExpression condition = geq(var("x"), lit(4));
		
		LinkedList<State> output = new LinkedList<State>();
		closed = false;
		LShifter m = new LShifter(new Executor() {
			@Override
			public void close() throws RPLException {
				closed = true;
			}
			@Override
			public void push(State s) throws RPLException {
				output.addLast(s);
			}
		}, condition, 0);

		m.push(s1, 0);
		assertEquals(list(s1, 0), output);
		m.push(s2, 1);
		assertEquals(list(s1, 0, s2, 1), output);
		m.push(s3, 2);
		assertEquals(list(s1, 0, s2, 1, s3, 2), output);
		m.push(s4, 3);
		assertEquals(list(s1, 0, s2, 1, s3, 2, s4, 3), output);
		m.push(s5, 4);
		assertEquals(list(s1, 0, s2, 1, s3, 2, s4, 3, s5, 4), output);
		m.push(s6, 5);
		assertEquals(list(s1, 0, s2, 1, s3, 2, s4, 3, s5, 4, s6, 5), output);
		m.close();
		assertEquals(list(s1, 0, s2, 1, s3, 2, s4, 3, s5, 4, s6, 5), output);
		assertTrue(closed);
	}
	
	public void testShiftFT1() throws RPLException {

		AbstractExpression condition = geq(var("x"), lit(4));
		
		LinkedList<State> output = new LinkedList<State>();
		closed = false;
		LShifter m = new LShifter(new Executor() {
			@Override
			public void close() throws RPLException {
				closed = true;
			}
			@Override
			public void push(State s) throws RPLException {
				output.addLast(s);
			}
		}, condition, 1);

		m.push(s1, 0);
		assertEquals(list(), output);
		m.push(s2, 1);
		assertEquals(list(s1, 0), output);
		m.push(s3, 2);
		assertEquals(list(s1, 0, s2, 1), output);
		m.push(s4, 3);
		assertEquals(list(s1, 0, s2, 1, s4, 2, s3, 2), output);
		m.push(s5, 4);
		assertEquals(list(s1, 0, s2, 1, s4, 2, s3, 2, s5, 3), output);
		m.push(s6, 5);
		assertEquals(list(s1, 0, s2, 1, s4, 2, s3, 2, s5, 3, s6, 4), output);
		m.close();
		assertEquals(list(s1, 0, s2, 1, s4, 2, s3, 2, s5, 3, s6, 4), output);
		assertTrue(closed);
	}

	public void testShiftFT2() throws RPLException {

		AbstractExpression condition = geq(var("x"), lit(4));
		
		LinkedList<State> output = new LinkedList<State>();
		closed = false;
		LShifter m = new LShifter(new Executor() {
			@Override
			public void close() throws RPLException {
				closed = true;
			}
			@Override
			public void push(State s) throws RPLException {
				output.addLast(s);
			}
		}, condition, 2);

		m.push(s1, 0);
		assertEquals(list(), output);
		m.push(s2, 1);
		assertEquals(list(), output);
		m.push(s3, 2);
		assertEquals(list(s1, 0), output);
		m.push(s4, 3);
		assertEquals(list(s1, 0, s4, 1, s2, 1), output);
		m.push(s5, 4);
		assertEquals(list(s1, 0, s4, 1, s2, 1, s5, 2, s3, 2), output);
		m.push(s6, 5);
		assertEquals(list(s1, 0, s4, 1, s2, 1, s5, 2, s3, 2, s6, 3), output);
		m.close();
		assertEquals(list(s1, 0, s4, 1, s2, 1, s5, 2, s3, 2, s6, 3), output);
		assertTrue(closed);
	}


	public void testShiftFT3() throws RPLException {

		AbstractExpression condition = geq(var("x"), lit(4));
		
		LinkedList<State> output = new LinkedList<State>();
		closed = false;
		LShifter m = new LShifter(new Executor() {
			@Override
			public void close() throws RPLException {
				closed = true;
			}
			@Override
			public void push(State s) throws RPLException {
				output.addLast(s);
			}
		}, condition, 3);

		m.push(s1, 0);
		assertEquals(list(), output);
		m.push(s2, 1);
		assertEquals(list(), output);
		m.push(s3, 2);
		assertEquals(list(), output);
		m.push(s4, 3);
		assertEquals(list(s4, 0, s1, 0), output);
		m.push(s5, 4);
		assertEquals(list(s4, 0, s1, 0, s5, 1, s2, 1), output);
		m.push(s6, 5);
		assertEquals(list(s4, 0, s1, 0, s5, 1, s2, 1, s6, 2, s3, 2), output);
		m.close();
		assertEquals(list(s4, 0, s1, 0, s5, 1, s2, 1, s6, 2, s3, 2), output);
		assertTrue(closed);
	}

	public void testShiftFT4() throws RPLException {

		AbstractExpression condition = geq(var("x"), lit(4));
		
		LinkedList<State> output = new LinkedList<State>();
		closed = false;
		LShifter m = new LShifter(new Executor() {
			@Override
			public void close() throws RPLException {
				closed = true;
			}
			@Override
			public void push(State s) throws RPLException {
				output.addLast(s);
			}
		}, condition, 4);

		m.push(s1, 0);
		assertEquals(list(), output);
		m.push(s2, 1);
		assertEquals(list(), output);
		m.push(s3, 2);
		assertEquals(list(), output);
		m.push(s4, 3);
		assertEquals(list(s4, 0), output);
		m.push(s5, 4);
		assertEquals(list(s4, 0, s5, 1, s1, 1), output);
		m.push(s6, 5);
		assertEquals(list(s4, 0, s5, 1, s1, 1, s6, 2, s2, 2), output);
		m.close();
		assertEquals(list(s4, 0, s5, 1, s1, 1, s6, 2, s2, 2, s3, 3), output);
		assertTrue(closed);
	}

	public void testShiftFT5() throws RPLException {

		AbstractExpression condition = geq(var("x"), lit(4));
		
		LinkedList<State> output = new LinkedList<State>();
		closed = false;
		LShifter m = new LShifter(new Executor() {
			@Override
			public void close() throws RPLException {
				closed = true;
			}
			@Override
			public void push(State s) throws RPLException {
				output.addLast(s);
			}
		}, condition, 5);

		m.push(s1, 0);
		assertEquals(list(), output);
		m.push(s2, 1);
		assertEquals(list(), output);
		m.push(s3, 2);
		assertEquals(list(), output);
		m.push(s4, 3);
		assertEquals(list(s4, 0), output);
		m.push(s5, 4);
		assertEquals(list(s4, 0, s5, 1), output);
		m.push(s6, 5);
		assertEquals(list(s4, 0, s5, 1, s6, 2, s1, 2), output);
		m.close();
		assertEquals(list(s4, 0, s5, 1, s6, 2, s1, 2, s2, 3, s3, 4), output);
		assertTrue(closed);
	}

	public void testShiftFT6() throws RPLException {

		AbstractExpression condition = geq(var("x"), lit(4));
		
		LinkedList<State> output = new LinkedList<State>();
		closed = false;
		LShifter m = new LShifter(new Executor() {
			@Override
			public void close() throws RPLException {
				closed = true;
			}
			@Override
			public void push(State s) throws RPLException {
				output.addLast(s);
			}
		}, condition, 6);

		m.push(s1, 0);
		assertEquals(list(), output);
		m.push(s2, 1);
		assertEquals(list(), output);
		m.push(s3, 2);
		assertEquals(list(), output);
		m.push(s4, 3);
		assertEquals(list(s4, 0), output);
		m.push(s5, 4);
		assertEquals(list(s4, 0, s5, 1), output);
		m.push(s6, 5);
		assertEquals(list(s4, 0, s5, 1, s6, 2), output);
		m.close();
		assertEquals(list(s4, 0, s5, 1, s6, 2, s1, 3, s2, 4, s3, 5), output);
		assertTrue(closed);
	}

	public void testShiftTF0() throws RPLException {
		
		AbstractExpression condition = lt(var("x"), lit(4));
		
		LinkedList<State> output = new LinkedList<State>();
		closed = false;
		LShifter m = new LShifter(new Executor() {
			@Override
			public void close() throws RPLException {
				closed = true;
			}
			@Override
			public void push(State s) throws RPLException {
				output.addLast(s);
			}
		}, condition, 0);

		m.push(s1, 0);
		assertEquals(list(s1, 0), output);
		m.push(s2, 1);
		assertEquals(list(s1, 0, s2, 1), output);
		m.push(s3, 2);
		assertEquals(list(s1, 0, s2, 1, s3, 2), output);
		m.push(s4, 3);
		assertEquals(list(s1, 0, s2, 1, s3, 2, s4, 3), output);
		m.push(s5, 4);
		assertEquals(list(s1, 0, s2, 1, s3, 2, s4, 3, s5, 4), output);
		m.push(s6, 5);
		assertEquals(list(s1, 0, s2, 1, s3, 2, s4, 3, s5, 4, s6, 5), output);
		m.close();
		assertEquals(list(s1, 0, s2, 1, s3, 2, s4, 3, s5, 4, s6, 5), output);
		assertTrue(closed);
	}

	public void testShiftTF1() throws RPLException {
		
		AbstractExpression condition = lt(var("x"), lit(4));
		
		LinkedList<State> output = new LinkedList<State>();
		closed = false;
		LShifter m = new LShifter(new Executor() {
			@Override
			public void close() throws RPLException {
				closed = true;
			}
			@Override
			public void push(State s) throws RPLException {
				output.addLast(s);
			}
		}, condition, 1);

		m.push(s1, 0);
		assertEquals(list(s1, 0), output);
		m.push(s2, 1);
		assertEquals(list(s1, 0, s2, 1), output);
		m.push(s3, 2);
		assertEquals(list(s1, 0, s2, 1, s3, 2), output);
		m.push(s4, 3);
		assertEquals(list(s1, 0, s2, 1, s3, 2), output);
		m.push(s5, 4);
		assertEquals(list(s1, 0, s2, 1, s3, 2, s4, 4), output);
		m.push(s6, 5);
		assertEquals(list(s1, 0, s2, 1, s3, 2, s4, 4, s5, 5), output);
		m.close();
		assertEquals(list(s1, 0, s2, 1, s3, 2, s4, 4, s5, 5, s6, 6), output);
		assertTrue(closed);
	}


	public void testShiftTF2() throws RPLException {
		
		AbstractExpression condition = lt(var("x"), lit(4));
		
		LinkedList<State> output = new LinkedList<State>();
		closed = false;
		LShifter m = new LShifter(new Executor() {
			@Override
			public void close() throws RPLException {
				closed = true;
			}
			@Override
			public void push(State s) throws RPLException {
				output.addLast(s);
			}
		}, condition, 2);

		m.push(s1, 0);
		assertEquals(list(s1, 0), output);
		m.push(s2, 1);
		assertEquals(list(s1, 0, s2, 1), output);
		m.push(s3, 2);
		assertEquals(list(s1, 0, s2, 1, s3, 2), output);
		m.push(s4, 3);
		assertEquals(list(s1, 0, s2, 1, s3, 2), output); // (3,4 can still be pushed)
		m.push(s5, 4);
		assertEquals(list(s1, 0, s2, 1, s3, 2), output); // (4, 5 can still be pushed)
		m.push(s6, 5);
		assertEquals(list(s1, 0, s2, 1, s3, 2, s4, 5), output); // (5, 6 can still be pushed)
		m.close();
		assertEquals(list(s1, 0, s2, 1, s3, 2, s4, 5, s5, 6, s6, 7), output);
		assertTrue(closed);
	}

	public void testShiftTF3() throws RPLException {
		
		AbstractExpression condition = lt(var("x"), lit(4));
		
		LinkedList<State> output = new LinkedList<State>();
		closed = false;
		LShifter m = new LShifter(new Executor() {
			@Override
			public void close() throws RPLException {
				closed = true;
			}
			@Override
			public void push(State s) throws RPLException {
				output.addLast(s);
			}
		}, condition, 3);

		m.push(s1, 0);
		assertEquals(list(s1, 0), output);
		m.push(s2, 1);
		assertEquals(list(s1, 0, s2, 1), output);
		m.push(s3, 2);
		assertEquals(list(s1, 0, s2, 1, s3, 2), output);
		m.push(s4, 3);
		assertEquals(list(s1, 0, s2, 1, s3, 2), output); // (3,4,5 can still be pushed)
		m.push(s5, 4);
		assertEquals(list(s1, 0, s2, 1, s3, 2), output); // (4,5,6 can still be pushed)
		m.push(s6, 5);
		assertEquals(list(s1, 0, s2, 1, s3, 2), output); // (5,6,7 can still be pushed)
		m.close();
		assertEquals(list(s1, 0, s2, 1, s3, 2, s4, 6, s5, 7, s6, 8), output);
		assertTrue(closed);
	}

	public void testAlwaysFalse() throws RPLException {
		
		AbstractExpression condition = lit(false);
		
		LinkedList<State> output = new LinkedList<State>();
		closed = false;
		LShifter m = new LShifter(new Executor() {
			@Override
			public void close() throws RPLException {
				closed = true;
			}
			@Override
			public void push(State s) throws RPLException {
				output.addLast(s);
			}
		}, condition, 1);

		m.push(s1, 0);
		assertEquals(list(), output);
		m.push(s2, 1);
		assertEquals(list(s1, 0), output);
		m.push(s3, 2);
		assertEquals(list(s1, 0, s2, 1), output);
		m.push(s4, 3);
		assertEquals(list(s1, 0, s2, 1, s3, 2), output);
		m.push(s5, 4);
		assertEquals(list(s1, 0, s2, 1, s3, 2, s4, 3), output);
		m.push(s6, 5);
		assertEquals(list(s1, 0, s2, 1, s3, 2, s4, 3, s5, 4), output);
		m.close();
		assertEquals(list(s1, 0, s2, 1, s3, 2, s4, 3, s5, 4, s6, 5), output);
		assertTrue(closed);
	}
	
	public void testAlwaysTrue() throws RPLException {
		
		AbstractExpression condition = lit(true);
		
		LinkedList<State> output = new LinkedList<State>();
		closed = false;
		LShifter m = new LShifter(new Executor() {
			@Override
			public void close() throws RPLException {
				closed = true;
			}
			@Override
			public void push(State s) throws RPLException {
				output.addLast(s);
			}
		}, condition, 1);

		m.push(s1, 0);
		assertEquals(list(s1, 0), output);
		m.push(s2, 1);
		assertEquals(list(s1, 0, s2, 1), output);
		m.push(s3, 2);
		assertEquals(list(s1, 0, s2, 1, s3, 2), output);
		m.push(s4, 3);
		assertEquals(list(s1, 0, s2, 1, s3, 2, s4, 3), output);
		m.push(s5, 4);
		assertEquals(list(s1, 0, s2, 1, s3, 2, s4, 3, s5, 4), output);
		m.push(s6, 5);
		assertEquals(list(s1, 0, s2, 1, s3, 2, s4, 3, s5, 4, s6, 5), output);
		m.close();
		assertEquals(list(s1, 0, s2, 1, s3, 2, s4, 3, s5, 4, s6, 5), output);
		assertTrue(closed);
	}

}
