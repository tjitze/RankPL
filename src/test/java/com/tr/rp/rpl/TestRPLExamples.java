package com.tr.rp.rpl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.tr.rp.RankPL;
import com.tr.rp.ast.statements.Program;
import com.tr.rp.ranks.Rank;

import junit.framework.TestCase;

public class TestRPLExamples extends TestCase {

	public void testBoolCircuitExample() {
		
		Map<Integer, Set<String>> expectedResultMap = new HashMap<Integer, Set<String>>();
		expectedResultMap.put(0, new HashSet<String>(Arrays.asList(
				"X1 broken: true, X2 broken: false, A1 broken: false, a2 broken: false, O1 broken: false"
		)));
		expectedResultMap.put(1, new HashSet<String>(Arrays.asList(
				"X1 broken: false, X2 broken: true, A1 broken: false, a2 broken: true, O1 broken: false",
				"X1 broken: false, X2 broken: true, A1 broken: false, a2 broken: false, O1 broken: true",
				"X1 broken: true, X2 broken: true, A1 broken: false, a2 broken: false, O1 broken: false",
				"X1 broken: false, X2 broken: true, A1 broken: true, a2 broken: false, O1 broken: false",
				"X1 broken: true, X2 broken: false, A1 broken: true, a2 broken: false, O1 broken: false",
				"X1 broken: true, X2 broken: false, A1 broken: false, a2 broken: false, O1 broken: true",
				"X1 broken: true, X2 broken: false, A1 broken: false, a2 broken: true, O1 broken: false"
		)));
		expectedResultMap.put(2, new HashSet<String>(Arrays.asList(
				"X1 broken: true, X2 broken: true, A1 broken: true, a2 broken: false, O1 broken: false",
				"X1 broken: true, X2 broken: false, A1 broken: true, a2 broken: true, O1 broken: false",
				"X1 broken: false, X2 broken: true, A1 broken: true, a2 broken: true, O1 broken: false",
				"X1 broken: true, X2 broken: true, A1 broken: false, a2 broken: true, O1 broken: false",
				"X1 broken: true, X2 broken: false, A1 broken: false, a2 broken: true, O1 broken: true",
				"X1 broken: false, X2 broken: true, A1 broken: false, a2 broken: true, O1 broken: true",
				"X1 broken: false, X2 broken: true, A1 broken: true, a2 broken: false, O1 broken: true",
				"X1 broken: true, X2 broken: true, A1 broken: false, a2 broken: false, O1 broken: true",
				"X1 broken: true, X2 broken: false, A1 broken: true, a2 broken: false, O1 broken: true"
		)));
		expectedResultMap.put(3, new HashSet<String>(Arrays.asList(
				"X1 broken: true, X2 broken: true, A1 broken: true, a2 broken: true, O1 broken: false",
				"X1 broken: true, X2 broken: true, A1 broken: true, a2 broken: false, O1 broken: true",
				"X1 broken: true, X2 broken: false, A1 broken: true, a2 broken: true, O1 broken: true",
				"X1 broken: true, X2 broken: true, A1 broken: false, a2 broken: true, O1 broken: true",
				"X1 broken: false, X2 broken: true, A1 broken: true, a2 broken: true, O1 broken: true"
		)));
		expectedResultMap.put(4, new HashSet<String>(Arrays.asList(
				"X1 broken: true, X2 broken: true, A1 broken: true, a2 broken: true, O1 broken: true"
		)));
		
		String file = "./examples/boolcircuit.rpl";
		try { 
			System.out.print("Running test " + file + "... ");
			String source = RankPL.getFileContent(file);
			Program program = RankPL.parse(source);
			if (program == null) {
				fail("Parse error");
			}
			Map<Integer, Set<String>> resultMap = RankPL.execute(program, Rank.MAX, Rank.MAX, false, false, false);
			assertEquals(resultMap, expectedResultMap);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Failure: " + e.toString());
		}
	}
	
	public void testSpellingCorrectorExample() {
		
		// We test a couple of different inputs and compare the first match
		String actualExample = "inkorrect";
		Map<String, String> expectedResults = new LinkedHashMap<String, String>();
		expectedResults.put("inkorrect", "incorrect");
		expectedResults.put("svvitserland", "switzerland");
		expectedResults.put("castlx", "castle");
		expectedResults.put("xastle", "castle");
		expectedResults.put("casle", "castle");
		expectedResults.put("casxtle", "castle");
		
		for (Entry<String, String> entry: expectedResults.entrySet()) {
			String file = "./examples/spellingcorrector.rpl";
			try {
				System.out.println("Running test " + file + " with input " + entry.getKey());
				String source = RankPL.getFileContent(file);
				source = source.replaceFirst(actualExample, entry.getKey());
				Program program = RankPL.parse(source);
				if (program == null) {
					fail("Parse error");
				}
				Map<Integer, Set<String>> resultMap = RankPL.execute(program, Rank.MAX, Rank.MAX, true, false, true);
				assertTrue(resultMap.size() == 1);
				assertTrue(resultMap.get(0).size() == 1);
				assertEquals(resultMap.get(0).iterator().next(), entry.getValue());
			} catch (Exception e) {
				e.printStackTrace();
				fail("Failure: " + e.toString());
			}
		}
	}
}
