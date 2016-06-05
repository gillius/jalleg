package org.gillius.jalleg.framework.stats;

/**
 * Manages the creation of {@link GameStats}.
 */
public class GameStatsRecorder {
	private final long duration;

	private long startTime;
	private long stateStart;
	private GameState state;
	private GameStats stats;

	public GameStatsRecorder(long duration) {
		this.duration = duration;
	}

	public void startLoop() {
		long now = System.nanoTime();
		if (stats == null) {
			stats = new GameStats();
			startTime = now;
		}
		stateStart = now;
		state = GameState.Idle;
	}

	public void transition(GameState newState) {
		long now = System.nanoTime();
		stats.add(state, now - stateStart);
		stateStart = now;
		state = newState;
	}

	public boolean endLoop() {
		transition(null);
		return stateStart - startTime >= duration;
	}

	public GameStats collectAndResetStats() {
		stats.setTotalTime(System.nanoTime() - startTime);
		GameStats ret = stats;
		stats = null;
		return ret;
	}
}
