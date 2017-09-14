package com.tr.rp.rpl;

import java.io.IOException;
import java.util.function.Consumer;

import com.tr.rp.RankPL;
import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.AbstractStatement;
import com.tr.rp.ast.statements.Program;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.exec.ExecutionContext;
import com.tr.rp.exec.RankedItem;

import junit.framework.TestCase;

public class RunRPLTest extends TestCase {

	public void testRPLTests() throws RPLException {
//        try {
//        	List<String> rplFiles = new ArrayList<String>();
//        	Files.walk(Paths.get("./src/test/rpl"))
//				.filter(p -> !Files.isDirectory(p))
//				.filter(p -> p.toString().endsWith(".rpl"))
//			    .forEach(e -> rplFiles.add(e.toString()));
//        	for (String file: rplFiles) {
//        		runTest(file);
//        	}
//		} catch (IOException e) {
//			e.printStackTrace();
//			assert(false);
//		}
//		runTest("./src/test/rpl/debug_rankedchoice.rpl");
		runTest("./src/test/rpl/test_ifelse.rpl");				//OK
		runTest("./src/test/rpl/test_expressions.rpl");			//OK
		runTest("./src/test/rpl/test_list_operations.rpl");		//OK
		runTest("./src/test/rpl/test_map_operations.rpl");		//OK
		runTest("./src/test/rpl/test_multi_assignment.rpl");	//OK
		runTest("./src/test/rpl/test_observe-j.rpl");			//OK
		runTest("./src/test/rpl/test_observe-l.rpl");			//OK
		runTest("./src/test/rpl/test_observe.rpl");				//OK
		runTest("./src/test/rpl/test_rankedchoice.rpl");		//OK
		runTest("./src/test/rpl/test_rankexpression.rpl");		//OK
		runTest("./src/test/rpl/test_set_operations.rpl");		//OK
		runTest("./src/test/rpl/test_stack_operations.rpl");	//OK
		runTest("./src/test/rpl/test_while.rpl");				//to implement
	}

	private void runTest(String file) {
		try { 
			System.out.println("Running test " + file + "... ");
			String source = RankPL.getFileContent(file);
			Program program = RankPL.parse(source);
			if (program == null) {
				fail("Parse error");
			}
			program.run(ExecutionContext.createDefault(), new Consumer<RankedItem<String>>() {

				@Override
				public void accept(RankedItem<String> t) {
					System.out.println(t);
				}
				
			});
			System.out.println("OK");
		} catch (RPLException e) {
			AbstractStatement st = e.getStatement();
			AbstractExpression exp = e.getExpression();
			if (st != null) {
				System.err.println("Failure in " + file + " (line " + st.getLineNumber() + ")");
				System.err.println("Statement: " + st);
				System.err.println(e.toString());
			} else if (exp != null) {
				System.err.println("Failure in " + file);
				System.err.println("Expression: " + exp);
				System.err.println(e.toString());
			}
			e.printStackTrace();
			fail("Failure: " + e.toString());
		} catch (IOException e) {
			e.printStackTrace();
			fail("Failure: " + e.toString());
		}
	}
	
	
//	private void getRPLTestFileNames(Path path) {
//	    try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
//	        for (Path entry : stream) {
//	            if (Files.isDirectory(entry)) {
//	            	getRPLTestFileNames(entry);
//	            }
//	            files.add(entry);
//	        }
//	    }
//
//	}
}
