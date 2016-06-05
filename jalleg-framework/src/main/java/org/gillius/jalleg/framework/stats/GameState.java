package org.gillius.jalleg.framework.stats;

public enum GameState {
	/**
	 * Waiting for events.
	 */
	Idle,
	/**
	 * In logic update code (or handling logic timer).
	 */
	Update,
	/**
	 * Handling Allegro events, other than logic timer.
	 */
	Event,
	/**
	 * Rendering.
	 */
	Render,
	/**
	 * Running al_flip_display.
	 */
	Flip
}
