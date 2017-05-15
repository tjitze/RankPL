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
import com.tr.rp.statement.Program;

public class RankPL {

	public static void main(String[] args) {
		
		// Process options
		if (args.length != 2) {
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
        Program program = (Program)classVisitor.visit(parser.program());
        try {
	        long time = System.currentTimeMillis();
	        RankedIterator<String> it = program.run();
	        // Print outcomes
			while (it.next() && it.getRank() <= maxRank) {
				System.out.println("Rank " + it.getRank() + ": " + it.getItem());
			}
			System.out.println("Took: " + (System.currentTimeMillis() - time) +" ms");
        } catch (Exception e) {
			System.out.println("Exception: " + e);
			e.printStackTrace();
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
