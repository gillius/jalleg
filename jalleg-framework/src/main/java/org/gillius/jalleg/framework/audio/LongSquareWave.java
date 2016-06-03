package org.gillius.jalleg.framework.audio;

/**
 * The long square wave is a square wave that has extra samples so that even when used with default linear mixer,
 * it still preserves its square shape.
 */
public class LongSquareWave implements MonoWaveform {
	public static LongSquareWave INSTANCE = new LongSquareWave();

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
