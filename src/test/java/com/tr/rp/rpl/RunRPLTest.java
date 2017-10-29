package com.tr.rp.rpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import com.tr.rp.RankPL;
import com.tr.rp.ast.AbstractExpression;
import com.tr.rp.ast.AbstractStatement;
import com.tr.rp.ast.statements.Program;
import com.tr.rp.base.ExecutionContext;
import com.tr.rp.base.RankedItem;
import com.tr.rp.exceptions.RPLException;
import com.tr.rp.executors.Guard;

import junit.framework.TestCase;

public class RunRPLTest extends TestCase {

	public RunRPLTest() {
		Guard.setEnabled(true);
	}
	
	public void testRPLTests() throws RPLException {
        try {
        	List<String> rplFiles = new ArrayList<String>();
        	Files.walk(Paths.get("./src/test/rpl"))
				.filter(p -> !Files.isDirectory(p))
				.filter(p -> p.toString().endsWith(".rpl"))
			    .forEach(e -> rplFiles.add(e.toString()));
        	for (String file: rplFiles) {
        		runTest(file);
        	}
		} catch (IOException e) {
			e.printStackTrace();
			assert(false);
		}
	}

	private void runTest(String file) {
		try { 
			System.out.println("Running test " + file + "... ");
			String source = RankPL.getFileContent(file);
			Program program = RankPL.parse(source);
			if (program == null) {
				fail("Parse error");
			}
			program.run(ExecutionContext.createDefault(), x -> true);
			System.out.println("OK");
		} catch (RPLException e) {
			fail("Failure: " + e.getDetailedDescription());
		} catch (IOException e) {
			e.printStackTrace();
			fail("Failure: " + e.toString());
		}
	}
	
}
