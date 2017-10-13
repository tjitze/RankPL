package com.tr.rp.varstore;

import com.tr.rp.ast.AbstractStatement;

public class VarStoreFactory {

	public static VarStore getInitialvarStore() {
		return new TrieMapVarStore();
	}
	
	public static VarStore getInitialVarStoreForProram(AbstractStatement s) {
		return new TrieMapVarStore();
	}
}
