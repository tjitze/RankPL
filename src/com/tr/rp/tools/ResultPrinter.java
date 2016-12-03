package com.tr.rp.tools;

import com.tr.rp.core.VarStore;
import com.tr.rp.core.rankediterators.RankedIterator;

public class ResultPrinter {

	public static void print(RankedIterator<VarStore> i) {

		try {
			int rank = i.getRank();
			VarStore v = i.getItem();
			System.out.println("Initial: " + rank + ": " + v);
		} catch (Exception e) {
			System.out.println("Initial: exception");
		}
		
		while (i.next() && i.getRank() < 2147483647) {
			int rank = i.getRank();
			VarStore v = i.getItem();
			System.out.println(rank + ": " + v);
		}
	}
}
