package com.tr.rp.tools;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tr.rp.core.VarStore;
import com.tr.rp.core.rankediterators.RankedIterator;
import com.tr.rp.expressions.bool.BoolExpression;

public class ResultPrinter {

	public static void print(RankedIterator<VarStore> i) {
		print(i, 5);
	}
	public static void print(RankedIterator<VarStore> i, int maxRank) {
		try {
			int rank = i.getRank();
			VarStore v = i.getVarStore();
			System.out.println("Initial: " + rank + ": " + v);
		} catch (Exception e) {
			System.out.println("Initial: exception");
		}
		
		while (i.next() && i.getRank() < maxRank) {
			int rank = i.getRank();
			VarStore v = i.getVarStore();
			System.out.println(rank + ": " + v);
		}
	}
	public static void printRanks(RankedIterator<VarStore> it, BoolExpression...bs) {
		Set<Integer> found = new HashSet<Integer>();
		while (it.next() && found.size() < bs.length) {
			for (int i = 0; i< bs.length; i++) {
				if (!found.contains(i)) {
					if (bs[i].isTrue(it.getVarStore())) {
						System.out.println("Rank of " + bs[i] + " is " + it.getRank());
						found.add(i);
					}
				}
			}
		}
		for (int i = 0; i< bs.length; i++) {
			if (!found.contains(i)) {
				System.out.println("Rank of " + bs[i] + " is infinity");
			}
		}
	}
}
