package org.gillius.jalleg.framework.audio;

public class TriangleWave implements MonoWaveform {
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
		if (p < 0.5f) {
			return p * 4f - 1f;
		} else {
			return (p-0.5f) * -4f + 1f;
		}
	}
}
