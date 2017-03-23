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
		if (args.length < 2) {
			System.err.println("Usage: java -jar RPL.jar source_file <max_rank> [var1] [var2] ...");
			System.exit(-1);
		}
		int maxRank = 0;
		try {
			maxRank = Integer.parseInt(args[1]);
		} catch (Exception e) {
			System.err.println("Usage: java -jar RPL.jar <source_file> <max_rank> <var1> <var2> ...");
		}
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
		RankedIterator it = program.getIterator(new InitialVarStoreIterator());
		if (args.length > 2) {
			it = new MarginalizingIterator(it, Arrays.copyOfRange(args, 2, args.length));
		}
		while (it.next() && it.getRank() <= maxRank) {
			VarStore v = it.getVarStore();
			int rank = it.getRank();
			System.out.println("Rank " + rank + ": " + v);
		}
	}
}
