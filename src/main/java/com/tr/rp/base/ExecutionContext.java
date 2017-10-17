package com.tr.rp.base;

/**
 * An execution context holds settings that specify how a program 
 * is executed.
 */
public class ExecutionContext {

	public static ExecutionContext createDefault() {
		return new ExecutionContext();
	}
	
	/** Alternatives ranked higher than rankCutOff are discarded */
	private int rankCutOff = Rank.MAX;

	/** If true: observe-j blocks if condition has infinite rank */
	private boolean destructiveJConditioning = false;

	/** If true: observe-l blocks if condition has infinite rank */
	private boolean destructiveLConditioning = false;

	/** Max size of history used for de-duplication */
	private int maxDedupHistorySize = Integer.MAX_VALUE;

	/** Rank of maximum cut off event */
	private int maxCutOff = -1;

	/**
	 * Rank cut-off is the rank above which alternatives are discarded. Can
	 * be used to speed up execution or implement iterative deepening.
	 * 
	 * @param rank cut-off to use
	 */
	public final void setRankCutOff(int rankCutOff) {
		this.rankCutOff = rankCutOff;
	}

	public final int getRankCutOff() {
		return rankCutOff;
	}

	/**
	 * Concerns behavior of observe-j when the prior rank of the specified condition is
	 * infinity (i.e., if observed evidence is impossible).
	 * 
	 * If set to true, the observe-j statement will block if this happens.
	 * 
	 * If set to false, the observe-j statement returns the prior ranking unchanged, if this happens.
	 */
	public final void setDestructiveJConditioning(boolean destructiveJConditioning) {
		this.destructiveJConditioning = destructiveJConditioning;
	}

	public final boolean isDestructiveJConditioning() {
		return destructiveJConditioning;
	}

	/**
	 * Concerns behavior of observe-l when the prior rank of the specified condition is
	 * infinity (i.e., if observed evidence is impossible).
	 * 
	 * If set to true, the observe-l statement will block if this happens.
	 * 
	 * If set to false, the observe-l statement returns the prior ranking unchanged, if this happens.
	 */
	public final void setDestructiveLConditioning(boolean destructiveLConditioning) {
		this.destructiveLConditioning = destructiveLConditioning;
	}

	public final boolean isDestructiveLConditioning() {
		return destructiveLConditioning;
	}

	/**
	 * Duplicate variable stores are discarded during execution. However, this requires 
	 * storing variable stores to check for duplicates. This setting specifies the maximum 
	 * size of this list. Lower values potentially slow down execution. A higher value leads 
	 * to more heap memory consumption.
	 * 
	 * @param maxDedupHistorySize size to use for max dedup history
	 */
	public final void setMaxDedupHistorySize(int maxDedupHistorySize) {
		this.maxDedupHistorySize = maxDedupHistorySize;
	}

	public final int getMaxDedupHistorySize() {
		return maxDedupHistorySize;
	}

	public void registerCutOffEvent(int t) {
		maxCutOff = Integer.max(maxCutOff, t);
	}

	public int getMaxCutOff() {
		return maxCutOff;
	}

	/**
	 * Reset execution stats
	 */
	public void resetStats() {
		maxCutOff = -1;
	}

}
