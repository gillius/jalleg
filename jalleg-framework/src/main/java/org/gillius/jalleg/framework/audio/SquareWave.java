package org.gillius.jalleg.framework.audio;

/**
 * Generates a square wave that has extra samples so that even when used with default linear mixer, it still preserves
 * its square shape.
 */
public class SquareWave implements MonoWaveform {
	public static final SquareWave INSTANCE = new SquareWave();

	@Override
	public int getSamples() {
		return 1000;
	}

	@Override
	public int getFrequency() {
		return 1000;
	}

	@Override
	public float getSample(int sample, float period) {
		return (sample < 500) ? 1f : -1f;
	}
}
