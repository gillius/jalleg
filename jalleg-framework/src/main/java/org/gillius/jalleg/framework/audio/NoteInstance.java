package org.gillius.jalleg.framework.audio;

import static org.gillius.jalleg.binding.AllegroLibrary.ALLEGRO_AUDIO_PAN_NONE;

/**
 * Represents a single played note. The note and octave set the note's frequency. The gain is volume from 0 to 1, and
 * pan is -1 (left) to 1 (right), 0 for center or {@link org.gillius.jalleg.binding.AllegroLibrary#ALLEGRO_AUDIO_PAN_NONE}
 * if panning will never be used and to play center at full volume.
 * <p>
 * The default gain is 1 and the default pan is {@link org.gillius.jalleg.binding.AllegroLibrary#ALLEGRO_AUDIO_PAN_NONE}.
 */
public class NoteInstance {
	public static final NoteInstance SILENCE = new NoteInstance(null, 0, 0f, ALLEGRO_AUDIO_PAN_NONE);

	private final Note note;
	private final int octave;

	private final float gain;
	private final float pan;

	public NoteInstance(Note note, int octave) {
		this(note, octave, 1, ALLEGRO_AUDIO_PAN_NONE);
	}

	public NoteInstance(Note note, int octave, float gain, float pan) {
		this.note = note;
		this.octave = octave;
		this.gain = gain;
		this.pan = pan;
	}

	public boolean isSilence() {
		return note == null;
	}

	public Note getNote() {
		return note;
	}

	public int getOctave() {
		return octave;
	}

	public float getSpeed() {
		return note.getFrequencyAtOctave(octave);
	}

	public float getGain() {
		return gain;
	}

	public float getPan() {
		return pan;
	}
}
