package org.gillius.jalleg.framework.audio;

public class SineWave implements MonoWaveform {
	public static final SineWave INSTANCE = new SineWave();

	@Override
	public int getSamples() {
		return 1000;
	}

	@Override
	public int getFrequency() {
		return 1000;
	}

	@Override
	public float getSample(int sample, float p) {
			return (float) Math.sin(p * 2.0 * Math.PI);
	}
}
