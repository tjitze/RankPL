package com.tr.rp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.BitSet;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.DefaultErrorStrategy;
import org.antlr.v4.runtime.InputMismatchException;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ProxyErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.ParseCancellationException;

import com.tr.rp.core.ConcreteParser;
import com.tr.rp.core.rankediterators.RankedIterator;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.parser.RankPLLexer;
import com.tr.rp.parser.RankPLParser;
import com.tr.rp.statement.Program;

public class RankPL {

	public static void main(String[] args) {
		
		// Process options
		int maxRank;
		if (args.length == 1) {
			maxRank = 0;
		} else if (args.length == 2) {
			maxRank = Integer.parseInt(args[1]);
		} else {
			printUsage();
			return;
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
			return;
        }
        RankPLLexer lexer = new RankPLLexer(input);
        TokenStream tokens = new CommonTokenStream(lexer);
        RankPLParser parser = new RankPLParser(tokens);
        parser.setErrorHandler(new BailErrorStrategy());
        ConcreteParser classVisitor = new ConcreteParser();
        
        try {
            Program program = (Program)classVisitor.visit(parser.program());
	        long time = System.currentTimeMillis();
	        RankedIterator<String> it = program.run();
	        // Print outcomes
			while (it.next() && it.getRank() <= maxRank) {
				System.out.println("Rank " + it.getRank() + ": " + it.getItem());
			}
			System.out.println("Took: " + (System.currentTimeMillis() - time) +" ms");
        } catch (RPLException e) {
			System.out.println("Exception: " + e);
        } catch (ParseCancellationException e) {
        	// Ugly hack to get parse exceptions: re-parse but now without the bail error strategy
			System.out.println("Syntax error");
            parser = new RankPLParser(tokens);
            classVisitor.visit(parser.program());
        } catch (Exception e) {
			e.printStackTrace();
        }
	}

	private static void printUsage() {
		System.out.println("Usage: java -jar RankPL.jar <source_file> [max_rank]");
		System.out.println();
		System.out.println("  where: <source_file>       is the RankPL source file to execute");
		System.out.println("         [max_rank]          is the maximum rank to show (default 0)");
		System.out.println();
	}	
}