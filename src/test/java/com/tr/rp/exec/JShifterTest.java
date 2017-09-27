package com.tr.rp.exec;

import static com.tr.rp.ast.expressions.Expressions.and;
import static com.tr.rp.ast.expressions.Expressions.geq;
import static com.tr.rp.ast.expressions.Expressions.gt;
import static com.tr.rp.ast.expressions.Expressions.leq;
import static com.tr.rp.ast.expressions.Expressions.lit;
import static com.tr.rp.ast.expressions.Expressions.lt;
import static com.tr.rp.ast.expressions.Expressions.or;
import static com.tr.rp.ast.expressions.Expressions.var;

import java.util.LinkedList;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.base.State;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.executors.Executor;
import com.tr.rp.executors.JShifter;
import com.tr.rp.statement.RPLBaseTest;
import com.tr.rp.varstore.PMapVarStore;
import com.tr.rp.varstore.VarStore;
import com.tr.rp.varstore.VarStoreFactory;

public class JShifterTest extends RPLBaseTest {

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
		JShifter m = new JShifter(new Executor() {
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
		assertEquals(list(s1, 0), output);
		m.push(s3, 2);
		assertEquals(list(s1, 0), output);
		m.push(s4, 3);
		assertEquals(list(s1, 0, s4, 0), output);
		m.push(s5, 4);
		assertEquals(list(s1, 0, s4, 0, s5, 1, s2, 1), output);
		m.push(s6, 5);
		assertEquals(list(s1, 0, s4, 0, s5, 1, s2, 1, s6, 2, s3, 2), output);
		m.close();
		assertEquals(list(s1, 0, s4, 0, s5, 1, s2, 1, s6, 2, s3, 2), output);
		assertTrue(closed);
	}
	
	public void testShiftFT1() throws RPLException {
		
		AbstractExpression condition = geq(var("x"), lit(4));
		
		LinkedList<State> output = new LinkedList<State>();
		closed = false;
		JShifter m = new JShifter(new Executor() {
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

	public void testShiftFT10() throws RPLException {
		
		AbstractExpression condition = geq(var("x"), lit(4));
		
		LinkedList<State> output = new LinkedList<State>();
		closed = false;
		JShifter m = new JShifter(new Executor() {
			@Override
			public void close() throws RPLException {
				closed = true;
			}
			@Override
			public void push(State s) throws RPLException {
				output.addLast(s);
			}
		}, condition, 10);

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
		assertEquals(list(s4, 0, s5, 1, s6, 2, s1, 10, s2, 11, s3, 12), output);
		assertTrue(closed);
	}

	public void testShiftTF0() throws RPLException {
		
		AbstractExpression condition = lt(var("x"), lit(4));
		
		LinkedList<State> output = new LinkedList<State>();
		closed = false;
		JShifter m = new JShifter(new Executor() {
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
		assertEquals(output, list(s1, 0));
		m.push(s2, 1);
		assertEquals(output, list(s1, 0));
		m.push(s3, 2);
		assertEquals(output, list(s1, 0));
		m.push(s4, 3);
		assertEquals(output, list(s1, 0, s4, 0));
		m.push(s5, 4);
		assertEquals(output, list(s1, 0, s4, 0, s2, 1, s5, 1));
		m.push(s6, 5);
		assertEquals(output, list(s1, 0, s4, 0, s2, 1, s5, 1, s3, 2, s6, 2));
		m.close();
		assertEquals(output, list(s1, 0, s4, 0, s2, 1, s5, 1, s3, 2, s6, 2));
		assertTrue(closed);
	}

	public void testShiftTF1() throws RPLException {
		
		AbstractExpression condition = lt(var("x"), lit(4));
		
		LinkedList<State> output = new LinkedList<State>();
		closed = false;
		JShifter m = new JShifter(new Executor() {
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
		assertEquals(list(s1, 0, s2, 1), output);
		m.push(s4, 3);
		assertEquals(list(s1, 0, s2, 1, s4, 1), output);
		m.push(s5, 4);
		assertEquals(list(s1, 0, s2, 1, s4, 1, s3, 2, s5, 2), output);
		m.push(s6, 5);
		assertEquals(list(s1, 0, s2, 1, s4, 1, s3, 2, s5, 2, s6, 3), output);
		m.close();
		assertEquals(list(s1, 0, s2, 1, s4, 1, s3, 2, s5, 2, s6, 3), output);
		assertTrue(closed);
	}

	public void testShiftTF10() throws RPLException {
		
		AbstractExpression condition = lt(var("x"), lit(4));
		
		LinkedList<State> output = new LinkedList<State>();
		closed = false;
		JShifter m = new JShifter(new Executor() {
			@Override
			public void close() throws RPLException {
				closed = true;
			}
			@Override
			public void push(State s) throws RPLException {
				output.addLast(s);
			}
		}, condition, 10);

		m.push(s1, 0);
		assertEquals(list(s1, 0), output);
		m.push(s2, 1);
		assertEquals(list(s1, 0, s2, 1), output);
		m.push(s3, 2);
		assertEquals(list(s1, 0, s2, 1, s3, 2), output); 
		m.push(s4, 3);
		assertEquals(list(s1, 0, s2, 1, s3, 2), output);
		m.push(s5, 4);
		assertEquals(list(s1, 0, s2, 1, s3, 2), output);
		m.push(s6, 5);
		assertEquals(list(s1, 0, s2, 1, s3, 2), output);
		m.close();
		assertEquals(list(s1, 0, s2, 1, s3, 2, s4, 10, s5, 11, s6, 12), output);
		assertTrue(closed);
	}

	public void testShiftTFT0() throws RPLException {
		
		AbstractExpression condition = or(lt(var("x"), lit(4)), gt(var("x"), lit(6)));

		LinkedList<State> output = new LinkedList<State>();
		closed = false;
		JShifter m = new JShifter(new Executor() {
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
		assertEquals(list(s1, 0), output); // wait because there could be 0 ranked false items
		m.push(s3, 2); 
		assertEquals(list(s1, 0), output); 
		m.push(s4, 3); 
		assertEquals(list(s1, 0, s4, 0), output);
		m.push(s5, 4); 
		assertEquals(list(s1, 0, s4, 0, s2, 1, s5, 1), output);
		m.push(s6, 5); 
		assertEquals(list(s1, 0, s4, 0, s2, 1, s5, 1, s3, 2, s6, 2), output);
		m.push(s7, 6); 
		assertEquals(list(s1, 0, s4, 0, s2, 1, s5, 1, s3, 2, s6, 2), output); // wait because there could be lower ranked false items
		m.push(s8, 7); 
		assertEquals(list(s1, 0, s4, 0, s2, 1, s5, 1, s3, 2, s6, 2), output);
		m.push(s9, 8); 
		assertEquals(list(s1, 0, s4, 0, s2, 1, s5, 1, s3, 2, s6, 2), output);
		m.close();
		assertEquals(list(s1, 0, s4, 0, s2, 1, s5, 1, s3, 2, s6, 2, s7, 6, s8, 7, s9, 8), output);
		assertTrue(closed);
	}

	public void testShiftTFT1() throws RPLException {
		
		AbstractExpression condition = or(lt(var("x"), lit(4)), gt(var("x"), lit(6)));

		LinkedList<State> output = new LinkedList<State>();
		closed = false;
		JShifter m = new JShifter(new Executor() {
			@Override
			public void close() throws RPLException {
				closed = true;
			}
			@Override
			public void push(State s) throws RPLException {
				output.addLast(s);
			}
		}, condition, 1);

		m.push(s1, 0); // t
		assertEquals(list(s1, 0), output);
		m.push(s2, 1); // t 
		assertEquals(list(s1, 0, s2, 1), output);
		m.push(s3, 2); // t
		assertEquals(list(s1, 0, s2, 1), output); // wait because there can be 1-ranked false items
		m.push(s4, 3); // f
		assertEquals(list(s1, 0, s2, 1, s4, 1), output);
		m.push(s5, 4); // f
		assertEquals(list(s1, 0, s2, 1, s4, 1, s3, 2, s5, 2), output);
		m.push(s6, 5); // f
		assertEquals(list(s1, 0, s2, 1, s4, 1, s3, 2, s5, 2, s6, 3), output);
		m.push(s7, 6); 
		assertEquals(list(s1, 0, s2, 1, s4, 1, s3, 2, s5, 2, s6, 3), output); // could be lower ranked f items
		m.push(s8, 7); 
		assertEquals(list(s1, 0, s2, 1, s4, 1, s3, 2, s5, 2, s6, 3), output);
		m.push(s9, 8); 
		assertEquals(list(s1, 0, s2, 1, s4, 1, s3, 2, s5, 2, s6, 3, s7, 6), output);
		m.close();
		assertEquals(list(s1, 0, s2, 1, s4, 1, s3, 2, s5, 2, s6, 3, s7, 6, s8, 7, s9, 8), output);
		assertTrue(closed);
	}

	public void testShiftTFT10() throws RPLException {
		
		AbstractExpression condition = or(lt(var("x"), lit(4)), gt(var("x"), lit(6)));

		LinkedList<State> output = new LinkedList<State>();
		closed = false;
		JShifter m = new JShifter(new Executor() {
			@Override
			public void close() throws RPLException {
				closed = true;
			}
			@Override
			public void push(State s) throws RPLException {
				output.addLast(s);
			}
		}, condition, 10);
		
		m.push(s1, 0); 
		assertEquals(list(s1, 0), output);
		m.push(s2, 1); 
		assertEquals(list(s1, 0, s2, 1), output);
		m.push(s3, 2); 
		assertEquals(list(s1, 0, s2, 1, s3, 2), output); 
		m.push(s4, 3); 
		assertEquals(list(s1, 0, s2, 1, s3, 2), output); // wait
		m.push(s5, 4); 
		assertEquals(list(s1, 0, s2, 1, s3, 2), output); 
		m.push(s6, 5); 
		assertEquals(list(s1, 0, s2, 1, s3, 2), output); 
		m.push(s7, 6); 
		assertEquals(list(s1, 0, s2, 1, s3, 2, s7, 6), output); 
		m.push(s8, 7); 
		assertEquals(list(s1, 0, s2, 1, s3, 2, s7, 6, s8, 7), output); 
		m.push(s9, 8); 
		assertEquals(list(s1, 0, s2, 1, s3, 2, s7, 6, s8, 7, s9, 8), output); 
		m.close();
		assertEquals(list(s1, 0, s2, 1, s3, 2, s7, 6, s8, 7, s9, 8, s4, 10, s5, 11, s6, 12), output); // flush
		assertTrue(closed);
	}

	public void testShiftFTF0() throws RPLException {
		
		AbstractExpression condition = and(geq(var("x"), lit(4)), leq(var("x"), lit(6)));

		LinkedList<State> output = new LinkedList<State>();
		closed = false;
		JShifter m = new JShifter(new Executor() {
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
		assertEquals(list(s1, 0), output); 
		m.push(s3, 2); 
		assertEquals(list(s1, 0), output); 
		m.push(s4, 3); 
		assertEquals(list(s1, 0, s4, 0), output);
		m.push(s5, 4); 
		assertEquals(list(s1, 0, s4, 0, s5, 1, s2, 1), output);
		m.push(s6, 5); 
		assertEquals(list(s1, 0, s4, 0, s5, 1, s2, 1, s6, 2, s3, 2), output);
		m.push(s7, 6); 
		assertEquals(list(s1, 0, s4, 0, s5, 1, s2, 1, s6, 2, s3, 2), output);
		m.push(s8, 7); 
		assertEquals(list(s1, 0, s4, 0, s5, 1, s2, 1, s6, 2, s3, 2), output);
		m.push(s9, 8); 
		assertEquals(list(s1, 0, s4, 0, s5, 1, s2, 1, s6, 2, s3, 2), output);
		m.close();
		assertEquals(list(s1, 0, s4, 0, s5, 1, s2, 1, s6, 2, s3, 2, s7, 6, s8, 7, s9, 8), output);
		assertTrue(closed);
	}

	public void testShiftFTF1() throws RPLException {
		
		AbstractExpression condition = and(geq(var("x"), lit(4)), leq(var("x"), lit(6)));

		LinkedList<State> output = new LinkedList<State>();
		closed = false;
		JShifter m = new JShifter(new Executor() {
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
		assertEquals(list(), output); 
		m.push(s3, 2); 
		assertEquals(list(), output); 
		m.push(s4, 3); 
		assertEquals(list(s4, 0), output);
		m.push(s5, 4); 
		assertEquals(list(s4, 0, s5, 1, s1, 1), output);
		m.push(s6, 5); 
		assertEquals(list(s4, 0, s5, 1, s1, 1, s6, 2, s2, 2), output);
		m.push(s7, 6); 
		assertEquals(list(s4, 0, s5, 1, s1, 1, s6, 2, s2, 2, s3, 3), output); 
		m.push(s8, 7); 
		assertEquals(list(s4, 0, s5, 1, s1, 1, s6, 2, s2, 2, s3, 3), output); // wait for lower ranked true items
		m.push(s9, 8); 
		assertEquals(list(s4, 0, s5, 1, s1, 1, s6, 2, s2, 2, s3, 3), output);
		m.close();
		assertEquals(list(s4, 0, s5, 1, s1, 1, s6, 2, s2, 2, s3, 3, s7, 7, s8, 8, s9, 9), output);
		assertTrue(closed);
	}

	public void testShiftFTF10() throws RPLException {
		
		AbstractExpression condition = and(geq(var("x"), lit(4)), leq(var("x"), lit(6)));

		LinkedList<State> output = new LinkedList<State>();
		closed = false;
		JShifter m = new JShifter(new Executor() {
			@Override
			public void close() throws RPLException {
				closed = true;
			}
			@Override
			public void push(State s) throws RPLException {
				output.addLast(s);
			}
		}, condition, 10);

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
		m.push(s7, 6); 
		assertEquals(list(s4, 0, s5, 1, s6, 2), output);
		m.push(s8, 7); 
		assertEquals(list(s4, 0, s5, 1, s6, 2), output);
		m.push(s9, 8); 
		assertEquals(list(s4, 0, s5, 1, s6, 2), output);
		m.close();
		assertEquals(list(s4, 0, s5, 1, s6, 2, s1, 10, s2, 11, s3, 12, s7, 16, s8, 17, s9, 18), output);
		assertTrue(closed);
	}

}
