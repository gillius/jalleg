package org.gillius.jalleg.framework.stats;

import java.util.concurrent.TimeUnit;

/**
 * Records duration and count of times the game spent performing certain actions. All times are in nanoseconds.
 */
public class GameStats {
	private static double nanosPerSecond = TimeUnit.SECONDS.toNanos(1);

	private long totalTime;

	private final long[] times;
	private final int[] counts;

	public GameStats() {
		int size = GameState.values().length;
		times = new long[size];
		counts = new int[size];
	}

	public void add(GameState state, long time) {
		times[state.ordinal()] += time;
		counts[state.ordinal()]++;
	}

	/**
	 * The duration of the measuring period in nanoseconds.
	 */
	public long getTotalTime() {
		return totalTime;
	}

	public double getTotalTimeSeconds() {
		return totalTime / nanosPerSecond;
	}

	public void setTotalTime(long totalTime) {
		this.totalTime = totalTime;
	}

	/**
	 * Returns the number of nanoseconds the game was in the given state in the measured period.
	 */
	public long getTimeInState(GameState state) {
		return times[state.ordinal()];
	}

	/**
	 * Returns the average number of nanoseconds the game was in the given state in the measured period.
	 */
	public long getAverageTimeInState(GameState state) {
		return getTimeInState(state) / getStateCount(state);
	}

	public double getPercentTimeInState(GameState state) {
		return getTimeInState(state) / (double)totalTime;
	}

	/**
	 * The number of times the game entered the given state in the measured period.
	 */
	public int getStateCount(GameState state) {
		return counts[state.ordinal()];
	}

	/**
	 * Returns the average render states (frames) per second in the measured period.
	 */
	public double getAverageFps() {
		return getStateCount(GameState.Render) / getTotalTimeSeconds();
	}

	/**
	 * Returns the average update states (logic executions) per second in the measured period.
	 */
	public double getAverageLps() {
		return getStateCount(GameState.Update) / getTotalTimeSeconds();
	}

	/**
	 * Returns a String with the most important statistics.
	 */
	public String getStatsString() {
		return "FPS: " + (int) getAverageFps() + ", LPS: " + (int)getAverageLps() +
		       ", flip: " + TimeUnit.NANOSECONDS.toMicros(getAverageTimeInState(GameState.Flip)) + " us" +
		       ", idle: " + TimeUnit.NANOSECONDS.toMicros(getAverageTimeInState(GameState.Idle)) + " us " +
		       "(" + (int)(getPercentTimeInState(GameState.Idle) * 100.0) + "%)";
	}
}
