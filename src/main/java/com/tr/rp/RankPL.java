package com.tr.rp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.tr.rp.ast.statements.Program;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.iterators.ranked.ExecutionContext;
import com.tr.rp.iterators.ranked.RankedIterator;
import com.tr.rp.parser.ConcreteParser;
import com.tr.rp.parser.RankPLLexer;
import com.tr.rp.parser.RankPLParser;
import com.tr.rp.ranks.Rank;

public class RankPL {

	private static int maxRank = 0;
	private static int rankCutOff = Rank.MAX;
	private static boolean iterativeDeepening = false;
	private static int timeOut = Integer.MAX_VALUE;
	private static boolean noExecStats = false;
	private static boolean noRanks = false;
	private static boolean terminateAfterFirst = false;
	private static String fileName;

	public static void main(String[] args) {

		// Handle options
		if (!parseOptions(args)) {
			printUsage();
			return;
		}

		// Parse input
		String source = "";
		try {
			source = getFileContent(fileName);
		} catch (IOException e) {
			System.err.println("I/O Exception while reading source file: " + e.getMessage());
			return;
		}

		// Parse
		Program program = null;
		try {
			program = parse(source);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		if (program == null) {
			System.exit(-1);
		}

		// Execute
		try {
			execute(program, rankCutOff, maxRank, iterativeDeepening, noRanks, terminateAfterFirst);

		} catch (RPLException e) {
			// e.printStackTrace(); // use this for debugging
			System.out.println("Exception: " + e.getDescription());
			String info = "";
			if (e.getExpression() != null) {
				info += "In expression " + e.getExpression();
			}
			if (e.getStatement() != null) {
				info += (info.equals("") ? "In" : ", in") + " statement " + e.getStatement();
				if (e.getStatement().getLineNumber() != -1) {
					info += ", on line " + e.getStatement().getLineNumber();
				}
			}
			info += ".";
			System.out.println(info);
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	public static Program parse(String source) {
		RankPLLexer lexer = new RankPLLexer(new ANTLRInputStream(source));
		TokenStream tokens = new CommonTokenStream(lexer);
		RankPLParser parser = new RankPLParser(tokens);
		parser.setErrorHandler(new BailErrorStrategy());
		ConcreteParser classVisitor = new ConcreteParser();

		// Parse
		Program program = null;
		try {
			program = (Program) classVisitor.visit(parser.program());
		} catch (ParseCancellationException e) {
			System.out.println("Syntax error");
			lexer = new RankPLLexer(new ANTLRInputStream(source));
			tokens = new CommonTokenStream(lexer);
			parser = new RankPLParser(tokens);
			classVisitor = new ConcreteParser();
			try {
				program = (Program) classVisitor.visit(parser.program());
			} catch (Exception ex) {
				// Ignore
			}
			return null;
		}
		return program;
	}

	/**
	 * Execute program, keeping track of timeout, and print results to console.
	 * 
	 * @param program Program to execute
	 * @throws RPLException Exception occurring during execution of program
	 */
	public static Map<Integer, Set<String>> execute(Program program, int rankCutOff, int maxRank, boolean iterativeDeepening, boolean noRanks, boolean terminateAfterFirst) throws RPLException {
		
		final Map<Integer, Set<String>> resultMap = new LinkedHashMap<Integer, Set<String>>();

		ExecutionContext c = new ExecutionContext();

		long startTime = System.currentTimeMillis();
		
		final Runnable executeTask = new Thread() {
			@Override
			public void run() {
				try {
					// Run
					RankedIterator<String> it;
					if (iterativeDeepening) {
						it = program.runWithIterativeDeepening(c, rankCutOff);
					} else {
						c.setRankCutOff(rankCutOff);
						;
						it = program.run(c);
					}
					if (!noRanks) {
						System.out.println("Rank    Outcome");
					}
					// Print outcomes
					while (it.next() && it.getRank() <= maxRank) {
						// Print outcome
						if (noRanks) {
							System.out.println(it.getItem());
						} else {
							System.out.println(String.format(" %3d    ", it.getRank()) + it.getItem());
						}
						// Store outcome in map
						Set<String> rankResults = resultMap.get(it.getRank());
						if (rankResults == null) {
							rankResults = new LinkedHashSet<String>();
							resultMap.put(it.getRank(), rankResults);
						}
						rankResults.add(it.getItem());
						if (terminateAfterFirst) {
							return;
						}
					}
				} catch (RPLException e) {
					throw new RuntimeException(e);
				}
			}
		};
		final ExecutorService executor = Executors.newSingleThreadExecutor();
		final Future<?> future = executor.submit(executeTask);
		executor.shutdown(); // This does not cancel the already-scheduled task.

		try { 
		  future.get(timeOut, TimeUnit.MILLISECONDS); 
		} catch (InterruptedException ie) { 
			ie.printStackTrace();
		} catch (ExecutionException ee) { 
			// Re-throw the RPL exception thrown inside the thread
			c.setInterruptRequested();
			ee.printStackTrace();
			//if (ee.getCause() instanceof RuntimeException && ee.getCause().getCause() instanceof RPLException) {
			throw (RPLException)ee.getCause().getCause();
			//} else {
			//}
		} catch (TimeoutException te) { 
			c.setInterruptRequested();
			System.out.println("Remaining results omitted due to timeout.");
		}
		if (!executor.isTerminated()) {
		    executor.shutdownNow();
		}
		// Print exec stats
		if (!noExecStats) {
			System.out.println("Took: " + (System.currentTimeMillis() - startTime) + " ms");
		}
		return resultMap;
	}

	/**
	 * @return Options object
	 */
	private static Options createOptions() {
		Options options = new Options();
		options.addOption(Option.builder("source").hasArg().required().argName("source_file")
				.desc("source file to execute").build());
		options.addOption(Option.builder("rank").hasArg().type(Number.class).argName("max_rank")
				.desc("generate outcomes with ranks up to max_rank (defaults to 0)").build());
		options.addOption(Option.builder("all")
				.desc("generate all outcomes").build());
		options.addOption(Option.builder("c").hasArg().type(Number.class).argName("rank_cutoff")
				.desc("discard computations above this rank (default ∞)").build());
		options.addOption(Option.builder("d")
				.desc("enable iterative deepening (run repeatedly with increasing rank_cutoff values)").build());
		options.addOption(Option.builder("f")
				.desc("terminate after first answer").build());
		options.addOption(Option.builder("t").hasArg().type(Number.class).argName("timeout")
				.desc("execution time-out (in milliseconds)").build());
		options.addOption(Option.builder("ns").desc("don't print execution stats").build());
		options.addOption(Option.builder("nr").desc("don't print ranks").build());
		options.addOption(Option.builder("help").desc("show help message").build());
		return options;
	}

	/**
	 * Parse options and set static fields. Returns true if successful.
     *
	 * @param args Options to parse
	 * @return True if successful
	 */
	private static boolean parseOptions(String[] args) {
		try {
			CommandLineParser parser = new DefaultParser();
			CommandLine cmd = parser.parse(createOptions(), args);
			if (cmd.hasOption("source")) {
				fileName = cmd.getOptionValue("source");
			}
			if (cmd.hasOption("rank")) {
				try {
					maxRank = ((Number) cmd.getParsedOptionValue("rank")).intValue();
				} catch (Exception e) {
					System.err.println("Illegal value provided for -rank option.");
					return false;
				}
			}
			if (cmd.hasOption("all")) {
				if (cmd.hasOption("rank")) {
					System.err.println("Cannot use both -rank and -all options.");
					return false;
				}
				maxRank = Rank.MAX;
			}
			if (cmd.hasOption("t")) {
				try {
					timeOut = ((Number) cmd.getParsedOptionValue("t")).intValue();
				} catch (Exception e) {
					System.err.println("Illegal value provided for -t option.");
					return false;
				}
			}
			if (cmd.hasOption("c")) {
				try {
					rankCutOff = ((Number) cmd.getParsedOptionValue("c")).intValue();
				} catch (Exception e) {
					System.err.println("Illegal value provided for -c option.");
					return false;
				}
			}
			if (cmd.hasOption("d")) {
				iterativeDeepening = true;
				if (maxRank > 0) {
					System.out
							.println("Warning: ignoring max_rank setting (must be 0 when iterative deepening is enabled).");
					maxRank = 0;
				}
			}
			if (cmd.hasOption("f")) {
				terminateAfterFirst = true;
			}
			if (cmd.hasOption("ns")) {
				noExecStats = true;
			}
			if (cmd.hasOption("nr")) {
				noRanks = true;
			}
			if (cmd.hasOption("help")) {
				printUsage();
				return false;
			}
		} catch (ParseException pe) {
			System.out.println(pe.getMessage());
			return false;
		}
		return true;
	}

	private static void printUsage() {
		HelpFormatter formatter = new HelpFormatter();
		formatter.setWidth(160);
		List<String> order = Arrays.asList("source", "r", "t", "c", "d", "f", "ns", "nr", "help");
		formatter.setOptionComparator(new Comparator<Option>() {
			@Override
			public int compare(Option o1, Option o2) {
				String s1 = ((Option) o1).getOpt();
				String s2 = ((Option) o2).getOpt();
				return order.indexOf(s1) - order.indexOf(s2);
			}
		});
		formatter.printHelp("java -jar RankPL.jar", createOptions(), true);
	}

	public static String getFileContent(String sourceFile) throws IOException {
		File file = new File(sourceFile);
		FileInputStream fis = new FileInputStream(file);
		StringBuilder sb = new StringBuilder();
		Reader r = new InputStreamReader(fis, "UTF-8"); // or whatever encoding
		int ch = r.read();
		while (ch >= 0) {
			sb.append((char) ch);
			ch = r.read();
		}
		r.close();
		return sb.toString();
	}

}
