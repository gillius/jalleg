package org.gillius.jalleg.framework.audio;

public interface MonoWaveform {
	int getSamples();
	int getFrequency();
	float getSample(int sample, float period);
}
