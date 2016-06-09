package org.gillius.jalleg.framework.audio;

/**
 * A note based on "standard concert pitch". See https://en.wikipedia.org/wiki/A440_%28pitch_standard%29. The A Note
 * at octave 4 has a frequency of 440hz.
 */
public enum Note {
	C(16.352f),
	D(18.354f),
	E(20.602f),
	F(21.827f),
	G(24.5f),
	A(27.5f),
	B(30.868f);

	private final float baseFrequency;

	Note(float baseFrequency) {
		this.baseFrequency = baseFrequency;
	}

	/**
	 * Returns the note's base frequency (frequency at octave 4).
	 */
	public float getBaseFrequency() {
		return baseFrequency;
	}

	public float getFrequencyAtOctave(int octave) {
		return baseFrequency * (1 << octave);
	}
}
