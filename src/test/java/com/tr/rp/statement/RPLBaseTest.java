package com.tr.rp.statement;

import java.util.LinkedList;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;

import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.base.State;
import com.tr.rp.executors.Guard;
import com.tr.rp.parser.ConcreteParser;
import com.tr.rp.parser.RankPLLexer;
import com.tr.rp.parser.RankPLParser;
import com.tr.rp.parser.RankPLParser.ExpContext;
import com.tr.rp.varstore.VarStore;

import junit.framework.TestCase;

public abstract class RPLBaseTest extends TestCase {
	
	public RPLBaseTest() {
		Guard.setEnabled(true);
	}

	protected static Object evalProgram(String program) {
        CharStream charStream = new ANTLRInputStream(program);
        RankPLLexer lexer = new RankPLLexer(charStream);
        TokenStream tokens = new CommonTokenStream(lexer);
        RankPLParser parser = new RankPLParser(tokens);

        ConcreteParser classVisitor = new ConcreteParser();
        ExpContext ctx = parser.exp();
        AbstractExpression res = (AbstractExpression)classVisitor.visit(ctx);
        
        
        return res;
	}

	
	public static LinkedList<State> list() {
		LinkedList<State> list = new LinkedList<State>();
		return list;
	}

	public static LinkedList<State> list(VarStore s1, int rank1) {
		LinkedList<State> list = new LinkedList<State>();
		list.add(new State(s1, rank1));
		return list;
	}

	public static LinkedList<State> list(VarStore s1, int rank1, VarStore s2, int rank2) {
		LinkedList<State> list = new LinkedList<State>();
		list.add(new State(s1, rank1));
		list.add(new State(s2, rank2));
		return list;
	}
	
	public static LinkedList<State> list(VarStore s1, int rank1, VarStore s2, int rank2, VarStore s3, int rank3) {
		LinkedList<State> list = new LinkedList<State>();
		list.add(new State(s1, rank1));
		list.add(new State(s2, rank2));
		list.add(new State(s3, rank3));
		return list;
	}

	public static LinkedList<State> list(VarStore s1, int rank1, VarStore s2, int rank2, VarStore s3, int rank3,
			VarStore s4, int rank4) {
		LinkedList<State> list = new LinkedList<State>();
		list.add(new State(s1, rank1));
		list.add(new State(s2, rank2));
		list.add(new State(s3, rank3));
		list.add(new State(s4, rank4));
		return list;
	}

	public static LinkedList<State> list(VarStore s1, int rank1, VarStore s2, int rank2, VarStore s3, int rank3,
			VarStore s4, int rank4, VarStore s5, int rank5) {
		LinkedList<State> list = new LinkedList<State>();
		list.add(new State(s1, rank1));
		list.add(new State(s2, rank2));
		list.add(new State(s3, rank3));
		list.add(new State(s4, rank4));
		list.add(new State(s5, rank5));
		return list;
	}

	public static LinkedList<State> list(VarStore s1, int rank1, VarStore s2, int rank2, VarStore s3, int rank3,
			VarStore s4, int rank4, VarStore s5, int rank5, VarStore s6, int rank6) {
		LinkedList<State> list = new LinkedList<State>();
		list.add(new State(s1, rank1));
		list.add(new State(s2, rank2));
		list.add(new State(s3, rank3));
		list.add(new State(s4, rank4));
		list.add(new State(s5, rank5));
		list.add(new State(s6, rank6));
		return list;
	}
	
	public static LinkedList<State> list(VarStore s1, int rank1, VarStore s2, int rank2, VarStore s3, int rank3,
			VarStore s4, int rank4, VarStore s5, int rank5, VarStore s6, int rank6, VarStore s7, int rank7) {
		LinkedList<State> list = new LinkedList<State>();
		list.add(new State(s1, rank1));
		list.add(new State(s2, rank2));
		list.add(new State(s3, rank3));
		list.add(new State(s4, rank4));
		list.add(new State(s5, rank5));
		list.add(new State(s6, rank6));
		list.add(new State(s7, rank7));
		return list;
	}

	public static LinkedList<State> list(VarStore s1, int rank1, VarStore s2, int rank2, VarStore s3, int rank3,
			VarStore s4, int rank4, VarStore s5, int rank5, VarStore s6, int rank6, VarStore s7, int rank7,
			VarStore s8, int rank8) {
		LinkedList<State> list = new LinkedList<State>();
		list.add(new State(s1, rank1));
		list.add(new State(s2, rank2));
		list.add(new State(s3, rank3));
		list.add(new State(s4, rank4));
		list.add(new State(s5, rank5));
		list.add(new State(s6, rank6));
		list.add(new State(s7, rank7));
		list.add(new State(s8, rank8));
		return list;
	}

	public static LinkedList<State> list(VarStore s1, int rank1, VarStore s2, int rank2, VarStore s3, int rank3,
			VarStore s4, int rank4, VarStore s5, int rank5, VarStore s6, int rank6, VarStore s7, int rank7,
			VarStore s8, int rank8, VarStore s9, int rank9) {
		LinkedList<State> list = new LinkedList<State>();
		list.add(new State(s1, rank1));
		list.add(new State(s2, rank2));
		list.add(new State(s3, rank3));
		list.add(new State(s4, rank4));
		list.add(new State(s5, rank5));
		list.add(new State(s6, rank6));
		list.add(new State(s7, rank7));
		list.add(new State(s8, rank8));
		list.add(new State(s9, rank9));
		return list;
	}

}
