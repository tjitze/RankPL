package com.tr.rp.rpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.tr.rp.RankPL;
import com.tr.rp.ast.statements.Program;
import com.tr.rp.exceptions.RPLException;

import junit.framework.TestCase;

public class RunRPLTest extends TestCase {

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
			System.out.print("Running " + file + "... ");
			String source = RankPL.getFileContent(file);
			Program program = RankPL.parse(source);
			if (program == null) {
				fail("Parse error");
			}
			RankPL.execute(program);
			System.out.println("OK");
		} catch (Exception e) {
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
