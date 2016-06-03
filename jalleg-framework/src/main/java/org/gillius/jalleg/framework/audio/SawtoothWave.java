package org.gillius.jalleg.framework.audio;

public class SawtoothWave implements MonoWaveform {
	public static final SawtoothWave INSTANCE = new SawtoothWave();

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
			return p * 2f - 1f;
	}
}
