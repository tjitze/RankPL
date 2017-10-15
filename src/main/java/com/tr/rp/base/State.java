package com.tr.rp.base;

import com.tr.rp.varstore.VarStore;

public final class State {
	
	private final VarStore varStore;
	private final int rank;
	
	public State(VarStore varStore, int rank) {
		this.varStore = varStore;
		this.rank = rank;
	}
	
	public final VarStore getVarStore() {
		return varStore;
	}
	
	public final int getRank() {
		return rank;
	}
	
	public final State shiftDown(int shift) {
		return shift == 0? this: new State(varStore, Rank.sub(rank, shift));
	}
	
	public final State shiftUp(int shift) {
		return shift == 0? this: new State(varStore, Rank.add(rank, shift));
	}
	
	public boolean equals(Object o) {
		return o instanceof State &&
				((State)o).getVarStore().equals(getVarStore()) &&
				((State)o).getRank() == getRank();
	}
	
	public int hashCode() {
		return getVarStore().hashCode() * getRank();
	}

	public String toString() {
		return getVarStore() + ": " + getRank();
	}
}

