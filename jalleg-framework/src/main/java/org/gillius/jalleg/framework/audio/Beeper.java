package org.gillius.jalleg.framework.audio;

/**
 * A common specialization of the {@link LoopingSingleInstanceSample} using a square wave.
 */
public class Beeper extends LoopingSingleInstanceSample {
	public Beeper() {
		super(new WaveformSample(SquareWave.INSTANCE));
	}

	public void beep(float frequency, double endTime) {
		setSpeed(frequency);
		play(endTime);
	}
}
