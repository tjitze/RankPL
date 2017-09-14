package com.tr.rp.exec;

import java.util.LinkedList;

import com.tr.rp.exceptions.RPLException;
import com.tr.rp.statement.RPLBaseTest;
import com.tr.rp.varstore.PMapVarStore;
import com.tr.rp.varstore.VarStore;

public class MergerTest extends RPLBaseTest {

	public static VarStore s1 = new PMapVarStore().create("x", 1);
	public static VarStore s2 = new PMapVarStore().create("x", 2);
	public static VarStore s3 = new PMapVarStore().create("x", 3);
	public static VarStore s4 = new PMapVarStore().create("x", 4);
	public static VarStore s5 = new PMapVarStore().create("x", 5);
	public static VarStore s6 = new PMapVarStore().create("x", 6);
	
	private boolean closed;

	public void testMerge() throws RPLException {
		
		LinkedList<State> output = new LinkedList<State>();
		closed = false;
		Merger m = new Merger(new Executor() {
			@Override
			public void close() throws RPLException {
				closed = true;
			}
			@Override
			public void push(State s) throws RPLException {
				output.addLast(s);
			}
		});
		Executor in1 = m.getIn1();
		Executor in2 = m.getIn2();

		in1.push(s1, 0);
		assertEquals(output, list(s1, 0));
		in1.push(s2, 1);
		assertEquals(output, list(s1, 0));
		in1.push(s3, 1);
		assertEquals(output, list(s1, 0));
		in2.push(s4, 0);
		assertEquals(output, list(s1, 0, s4, 0));
		in2.push(s5, 2);
		assertEquals(output, list(s1, 0, s4, 0, s2, 1, s3, 1));
		in1.close();
		assertEquals(output, list(s1, 0, s4, 0, s2, 1, s3, 1, s5, 2));
		in2.close();
		assertEquals(output, list(s1, 0, s4, 0, s2, 1, s3, 1, s5, 2));
		
	}
	

}
