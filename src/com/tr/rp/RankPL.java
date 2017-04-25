package com.tr.rp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;

import com.tr.rp.core.ConcreteParser;
import com.tr.rp.core.DStatement;
import com.tr.rp.core.VarStore;
import com.tr.rp.core.rankediterators.InitialVarStoreIterator;
import com.tr.rp.core.rankediterators.MarginalizingIterator;
import com.tr.rp.core.rankediterators.RankedIterator;
import com.tr.rp.parser.DefProgLexer;
import com.tr.rp.parser.DefProgParser;

public class RankPL {

	public static void main(String[] args) {
		
		// Process options
		if (args.length < 2) {
			printUsage();
			System.exit(-1);
		}
		int maxRank = 0;
		try {
			maxRank = Integer.parseInt(args[1]);
		} catch (Exception e) {
			printUsage();
			System.exit(-1);
		}
		String[] variables = Arrays.copyOfRange(args, 2, args.length);
		for (String var: variables) {
			if (var.length() == 0 || !Character.isAlphabetic(var.charAt(0))) {
				System.err.println("Invalid variable name: " + var);
				System.exit(-1);
			}
			for (char c: var.toCharArray()) {
				if (!Character.isAlphabetic(c) && !Character.isDigit(c)) {
					System.err.println("Invalid variable name: " + var);
					System.exit(-1);
				}
			}
		}

		// Parse input
        File file = new File(args[0]);
        FileInputStream fis = null;
        ANTLRInputStream input = null;
        try {
            fis = new FileInputStream(file);
            input = new ANTLRInputStream(fis);
        } catch (IOException e) {
			System.err.println("I/O Exception while reading file: " + e.getMessage());
        }
        DefProgLexer lexer = new DefProgLexer(input);
        TokenStream tokens = new CommonTokenStream(lexer);
        DefProgParser parser = new DefProgParser(tokens);
        ConcreteParser classVisitor = new ConcreteParser();
        DStatement program = (DStatement)classVisitor.visit(parser.program());
		RankedIterator<VarStore> it = program.getIterator(new InitialVarStoreIterator());

		// Apply variable list
		if (variables.length > 0) {
			it = new MarginalizingIterator(it, variables);
		}
		
		// Print outcomes
		boolean emptyResult = false;
		while (it.next() && it.getRank() <= maxRank) {
			VarStore v = it.getItem();
			if (v.toString().equals("[]")) {
				emptyResult = true;
			}
			int rank = it.getRank();
			System.out.println("Rank " + rank + ": " + v);
		}
		if (emptyResult) {
			System.out.println("Warning: one or more outcomes did not assign a value to any of the variables " + Arrays.toString(variables) + ".");
		}
	}

	private static void printUsage() {
		System.out.println("Usage: java -jar RankPL.jar <source_file> <max_rank> [var1] [var2] ...");
		System.out.println();
		System.out.println("  where: <source_file>       is the RankPL source file to execute");
		System.out.println("         <max_rank>          is the maximum rank to show (i.e. only outcomes ranked at most <max_rank> are shown)");
		System.out.println("         [var1] [var2] ...   are the variables to show (if none are specified then all variables are shown)");
		System.out.println();
	}	
}
