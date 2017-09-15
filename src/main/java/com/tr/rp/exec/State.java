package com.tr.rp.exec;

import com.tr.rp.varstore.VarStore;

public class State {
	
	private final VarStore varStore;
	private final int rank;
	
	public State(VarStore varStore, int rank) {
		this.varStore = varStore;
		this.rank = rank;
	}
	
	public VarStore getVarStore() {
		return varStore;
	}
	
	public int getRank() {
		return rank;
	}
	
	public State shiftDown(int shift) {
		if (shift == 0) {
			return this;
		} else {
			return new State(varStore, Rank.sub(rank, shift));
		}
	}
	
	public State shiftUp(int shift) {
		if (shift == 0) {
			return this;
		} else {
			return new State(varStore, Rank.add(rank, shift));
		}
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

