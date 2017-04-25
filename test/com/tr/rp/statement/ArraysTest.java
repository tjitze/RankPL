package com.tr.rp.statement;

import com.tr.rp.core.ProgramBuilder;
import com.tr.rp.core.VarStore;
import com.tr.rp.core.rankediterators.InitialVarStoreIterator;
import com.tr.rp.core.rankediterators.RankedIterator;
import com.tr.rp.expressions.num.IntLiteral;
import com.tr.rp.expressions.num.NumExpression;
import com.tr.rp.expressions.num.Var;

public class ArraysTest extends RPLBaseTest {

	public void test1DArrayAssignment() {
		ProgramBuilder p = new ProgramBuilder()
				.add(new Assign("x", 
						new NumExpression[] { new IntLiteral(0) }, 
						new IntLiteral(1)))
				.add(new Assign("x", 
						new NumExpression[] { new IntLiteral(1) }, 
						new IntLiteral(2)))
				.add(new Assign("x", 
						new NumExpression[] { new IntLiteral(2) }, 
						new IntLiteral(3)))
				.add(new Assign("x", 
						new NumExpression[] { new IntLiteral(2) }, 
						new IntLiteral(100)));
		RankedIterator<VarStore> result = p.build().getIterator(new InitialVarStoreIterator());
		
		assert(result.next());
		VarStore vs = result.getVarStore();
		assert(!result.next());
		assert(vs.getValue("x[0]") == 1);
		assert(vs.getValue("x[1]") == 2);
		assert(vs.getValue("x[2]") == 100);

	}
	
	public void test2DArrayAssignment() {
		ProgramBuilder p = new ProgramBuilder()
				.add(new Assign("x", 
						new NumExpression[] { new IntLiteral(0), new IntLiteral(1) }, 
						new IntLiteral(1)))
				.add(new Assign("x", 
						new NumExpression[] { new IntLiteral(1), new IntLiteral(2) }, 
						new IntLiteral(2)))
				.add(new Assign("x", 
						new NumExpression[] { new IntLiteral(2), new IntLiteral(3) }, 
						new IntLiteral(3)))
				.add(new Assign("x", 
						new NumExpression[] { new IntLiteral(2), new IntLiteral(3) }, 
						new IntLiteral(100)));
		RankedIterator<VarStore> result = p.build().getIterator(new InitialVarStoreIterator());
		
		assert(result.next());
		VarStore vs = result.getVarStore();
		assert(!result.next());
		assert(vs.getValue("x[0][1]") == 1);
		assert(vs.getValue("x[1][2]") == 2);
		assert(vs.getValue("x[2][3]") == 100);

	}
	
	public void testIndexed1DExpression() {
		ProgramBuilder p = new ProgramBuilder()
				.add(new Assign("x", 
						new NumExpression[] { new IntLiteral(2) }, 
						new IntLiteral(100)))
				.add(new Assign("y", new Var("x", 
						new NumExpression[] { new IntLiteral(2) })));
		RankedIterator<VarStore> result = p.build().getIterator(new InitialVarStoreIterator());
		
		assert(result.next());
		VarStore vs = result.getVarStore();
		assert(!result.next());
		assert(vs.getValue("x[2]") == 100);
		assert(vs.getValue("y") == 100);
	}
	
	public void testIndexed2DExpression() {
		ProgramBuilder p = new ProgramBuilder()
				.add(new Assign("x", 
						new NumExpression[] { new IntLiteral(2), new IntLiteral(3) }, 
						new IntLiteral(100)))
				.add(new Assign("x", new Var("x", 
						new NumExpression[] { new IntLiteral(2), new IntLiteral(3) })));
		RankedIterator<VarStore> result = p.build().getIterator(new InitialVarStoreIterator());
		
		assert(result.next());
		VarStore vs = result.getVarStore();
		assert(!result.next());
		assert(vs.getValue("x[2][3]") == 100);
		assert(vs.getValue("x") == 100);
	}

}
