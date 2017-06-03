package com.tr.rp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Comparator;
import java.util.List;

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
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.tr.rp.core.ConcreteParser;
import com.tr.rp.core.rankediterators.ExecutionContext;
import com.tr.rp.core.rankediterators.RankedIterator;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.parser.RankPLLexer;
import com.tr.rp.parser.RankPLParser;
import com.tr.rp.statement.Program;

public class RankPL {

	private static int maxRank = 0;
	private static boolean iterativeDeepening = false;
	private static int timeOut = Integer.MAX_VALUE;
	private static boolean noExecStats = false;
	private static boolean noRanks = false;
	private static String fileName;
	
	public static void main(String[] args) {
		
		// Handle options
		try {
			parseOptions(args);
		} catch (ParseException e1) {
			System.out.println(e1.getMessage());
			printUsage();
			return;
		}
		
		// Parse input
        File file = new File(fileName);
        FileInputStream fis = null;
        ANTLRInputStream input = null;
        try {
            fis = new FileInputStream(file);
            input = new ANTLRInputStream(fis);
        } catch (IOException e) {
			System.err.println("I/O Exception while reading source file: " + e.getMessage());
			return;
        }
        RankPLLexer lexer = new RankPLLexer(input);
        TokenStream tokens = new CommonTokenStream(lexer);
        RankPLParser parser = new RankPLParser(tokens);
        parser.setErrorHandler(new BailErrorStrategy());
        ConcreteParser classVisitor = new ConcreteParser();
        
        // Execute
        Program program = (Program)classVisitor.visit(parser.program());
        try {
	        execute(program);
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

	private static void execute(Program program) throws RPLException {
        // Build execution context
        ExecutionContext c = new ExecutionContext();

        // Start time
        long startTime = System.currentTimeMillis();

        // Run
        RankedIterator<String> it = iterativeDeepening? program.runWithIterativeDeepening(c, Integer.MAX_VALUE): program.run(c);

        // Print outcomes
		while (it.next() && it.getRank() <= maxRank) {
			if (noRanks) {
				System.out.println(it.getItem());
			} else {
				System.out.println("Rank " + it.getRank() + ": " + it.getItem());
			}
			if (System.currentTimeMillis() - startTime >= timeOut) {
				break;
			}
		}

		// Print exec stats
		if (!noExecStats) {
			System.out.println("Took: " + (System.currentTimeMillis() - startTime) +" ms");
		}

	}
	
	private static Options createOptions() {
		Options options = new Options();
		options.addOption(Option.builder("source")
				.hasArg()
				.required()
				.argName("source_file")
				.desc("source file to execute")
				.build());
		options.addOption(Option.builder("r")
				.hasArg()
				.type(Number.class)
				.argName("max_rank")
				.desc("maximum rank to generate (default 0)")
				.build());
		options.addOption(Option.builder("d")
				.argName("max_rank")
				.desc("apply iterative deepening (faster but experimental)")
				.build());
		options.addOption(Option.builder("t")
				.hasArg()
				.type(Number.class)
				.argName("timeout")
				.desc("execution time-out (in milliseconds)")
				.build());
		options.addOption(Option.builder("ns")
				.argName("max_rank")
				.desc("don't print execution stats")
				.build());
		options.addOption(Option.builder("nr")
				.argName("max_rank")
				.desc("don't print ranks")
				.build());
		return options;
	}
	
	private static void parseOptions(String[] args) throws ParseException {
		CommandLineParser parser = new DefaultParser();
		CommandLine cmd = parser.parse(createOptions(), args);
		if (cmd.hasOption("source")) {
			fileName = cmd.getOptionValue("source");
		}
		if (cmd.hasOption("r")) {
			maxRank = ((Number)cmd.getParsedOptionValue("r")).intValue();
		}
		if (cmd.hasOption("t")) {
			timeOut = ((Number)cmd.getParsedOptionValue("timeOut")).intValue();
		}
		if (cmd.hasOption("d")) {
			iterativeDeepening = true;
			if (maxRank > 0) {
				System.out.println("Warning: ignoring max_rank setting (must be 0 when iterative deepening is enabled).");
				maxRank = 0;
			}
		}
		if (cmd.hasOption("ns")) {
			noExecStats = true;
		}
		if (cmd.hasOption("nr")) {
			noRanks = true;
		}
	}
	
	private static void printUsage() {
		HelpFormatter formatter = new HelpFormatter();
		formatter.setWidth(160);
		List<String> order = Arrays.asList("source", "r", "t", "d", "ns", "nr");
		formatter.setOptionComparator(new Comparator<Option>() {
			@Override
			public int compare(Option o1, Option o2) {
				String s1 = ((Option)o1).getOpt();
				String s2 = ((Option)o2).getOpt();
				return order.indexOf(s1) - order.indexOf(s2);
			}
		});
		formatter.printHelp("java -jar RankPL.jar", createOptions(), true);

	}
}
