package com.tr.rp.rpl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Ignore;

import com.tr.rp.RankPL;
import com.tr.rp.ast.statements.Program;
import com.tr.rp.exec.Rank;

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
			Map<Integer, Set<String>> resultMap = RankPL.execute(program, Rank.MAX, Rank.MAX, 0, false, false);
			assertEquals(resultMap, expectedResultMap);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Failure: " + e.toString());
		}
	}
	
	public void testRankingNetworkExample() {
		
		Map<Integer, Set<String>> expectedResultMap = new HashMap<Integer, Set<String>>();
		expectedResultMap.put(0, new HashSet<String>(Arrays.asList(
				"rain: false	sprinkler: true	grass_wet: true"
		)));
		expectedResultMap.put(2, new HashSet<String>(Arrays.asList(
				"rain: true	sprinkler: true	grass_wet: true",
				"rain: false	sprinkler: true	grass_wet: false"
		)));
		expectedResultMap.put(7, new HashSet<String>(Arrays.asList(
				"rain: true	sprinkler: true	grass_wet: false"
		)));
		
		String file = "./examples/rankingnetwork.rpl";
		try { 
			System.out.print("Running test " + file + "... ");
			String source = RankPL.getFileContent(file);
			Program program = RankPL.parse(source);
			if (program == null) {
				fail("Parse error");
			}
			Map<Integer, Set<String>> resultMap = RankPL.execute(program, Rank.MAX, Rank.MAX, 0, false, false);
			assertEquals(resultMap, expectedResultMap);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Failure: " + e.toString());
		}
	}

	public void testSpellingCorrectorExample() {
		Map<Integer, Set<String>> expectedResultMap = new HashMap<Integer, Set<String>>();
		expectedResultMap.put(0, new HashSet<String>(Arrays.asList(
				"twelve", "eleven"
		)));
		expectedResultMap.put(2, new HashSet<String>(Arrays.asList(
				"seven"
		)));
		expectedResultMap.put(3, new HashSet<String>(Arrays.asList(
				"thirteen", "fifteen", "sixteen", "ten", "twenty", "three", "nineteen"
		)));
		expectedResultMap.put(4, new HashSet<String>(Arrays.asList(
				"eighteen", "fourteen", "two", "five", "seventeen"
		)));
		expectedResultMap.put(5, new HashSet<String>(Arrays.asList(
				"eight", "one", "nine"
		)));
		expectedResultMap.put(6, new HashSet<String>(Arrays.asList(
				"four", "six"
		)));
		
		String file = "./examples/spellingcorrector.rpl";
		try { 
			System.out.print("Running test " + file + "... ");
			String source = RankPL.getFileContent(file);
			Program program = RankPL.parse(source);
			if (program == null) {
				fail("Parse error");
			}
			Map<Integer, Set<String>> resultMap = RankPL.execute(program, Rank.MAX, Rank.MAX, 0, false, false);
			assertEquals(resultMap, expectedResultMap);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Failure: " + e.toString());
		}
	}

	public void testSpellingCorrectorExample10K() {
		
		// We test a couple of different inputs and compare the first match
		String actualExample = "inkorrect";
		Map<String, String> expectedResults = new LinkedHashMap<String, String>();
		expectedResults.put("inkorrect", "incorrect");
		expectedResults.put("switzerland", "switzerland");
		expectedResults.put("swizerland", "switzerland");
		expectedResults.put("switxzerland", "switzerland");
		expectedResults.put("svitzerland", "switzerland");
		expectedResults.put("svvitzerland", "switzerland");
		expectedResults.put("serbia", "serbia");
		expectedResults.put("srbia", "serbia");
		expectedResults.put("serrbia", "serbia");
		expectedResults.put("serbja", "serbia");
		
		for (Entry<String, String> entry: expectedResults.entrySet()) {
			String file = "./examples/spellingcorrector-10K-words.rpl";
			try {
				System.out.println("Running test " + file + " with input " + entry.getKey());
				String source = RankPL.getFileContent(file);
				source = source.replaceFirst(actualExample, entry.getKey());
				Program program = RankPL.parse(source);
				if (program == null) {
					fail("Parse error");
				}
				Map<Integer, Set<String>> resultMap = RankPL.execute(program, Rank.MAX, Rank.MAX, RankPL.DEFAULT_MIN_CUTOFF, false, true);
				assertTrue(resultMap.size() == 1);
				assertTrue(resultMap.get(0).size() == 1);
				assertEquals(resultMap.get(0).iterator().next(), entry.getValue());
			} catch (Exception e) {
				e.printStackTrace();
				fail("Failure: " + e.toString());
			}
		}
	}
	
	public void ignore_testLocalizerExample() {
		
		Map<Integer, Set<String>> expectedResultMapK1 = new HashMap<Integer, Set<String>>();
		expectedResultMapK1.put(0, new HashSet<String>(Arrays.asList(
				"Inferred location (1, 5)",
				"Inferred location (2, 5)",
				"Inferred location (3, 5)",
				"Inferred location (5, 4)"
		)));
		expectedResultMapK1.put(1, new HashSet<String>(Arrays.asList(
				"Inferred location (10, 4)",
				"Inferred location (9, 4)",
				"Inferred location (8, 4)",
				"Inferred location (4, 5)",
				"Inferred location (6, 4)",
				"Inferred location (7, 4)"
		)));
		
		Map<Integer, Set<String>> expectedResultMapK2 = new HashMap<Integer, Set<String>>();
		expectedResultMapK2.put(0, new HashSet<String>(Arrays.asList(
				"Inferred location (6, 4)"
		)));
		expectedResultMapK2.put(1, new HashSet<String>(Arrays.asList(
				"Inferred location (2, 5)",
				"Inferred location (4, 5)",
				"Inferred location (3, 5)"
		)));

		Map<Integer, Set<String>> expectedResultMapK3 = new HashMap<Integer, Set<String>>();
		expectedResultMapK3.put(0, new HashSet<String>(Arrays.asList(
				"Inferred location (7, 4)"
		)));

		Map<Integer, Set<String>> expectedResultMapK4 = new HashMap<Integer, Set<String>>();
		expectedResultMapK4.put(0, new HashSet<String>(Arrays.asList(
				"Inferred location (8, 4)"
		)));

		String file = "./examples/localizer.rpl";
		try { 
			System.out.print("Running test " + file + "... ");
			// K1
			String source = RankPL.getFileContent(file);
			source = source.replaceFirst("k := 1", "k := 1");
			Program program = RankPL.parse(source);
			if (program == null) {
				fail("Parse error");
			}
			Map<Integer, Set<String>> resultMap = RankPL.execute(program, Rank.MAX, 1, 0, false, false);
			assertEquals(resultMap, expectedResultMapK1);
			// K2
			source = RankPL.getFileContent(file);
			source = source.replaceFirst("k := 1", "k := 2");
			program = RankPL.parse(source);
			if (program == null) {
				fail("Parse error");
			}
			resultMap = RankPL.execute(program, Rank.MAX, 1, 0, false, false);
			assertEquals(resultMap, expectedResultMapK2);
			// K3
			source = RankPL.getFileContent(file);
			source = source.replaceFirst("k := 1", "k := 3");
			program = RankPL.parse(source);
			if (program == null) {
				fail("Parse error");
			}
			resultMap = RankPL.execute(program, Rank.MAX, 1, 0, false, false);
			assertEquals(resultMap, expectedResultMapK3);
			// K4
			source = RankPL.getFileContent(file);
			source = source.replaceFirst("k := 1", "k := 4");
			program = RankPL.parse(source);
			if (program == null) {
				fail("Parse error");
			}
			resultMap = RankPL.execute(program, Rank.MAX, 1, 0, false, false);
			assertEquals(resultMap, expectedResultMapK4);

		} catch (Exception e) {
			e.printStackTrace();
			fail("Failure: " + e.toString());
		}
	}
}
