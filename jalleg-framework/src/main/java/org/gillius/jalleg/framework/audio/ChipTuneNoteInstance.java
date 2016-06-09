package org.gillius.jalleg.framework.audio;

public class ChipTuneNoteInstance {
	private final NoteInstance note;
	private final int channel;
	private final Object data;

	public static ChipTuneNoteInstance silence(int channel) {
		return new ChipTuneNoteInstance(null, channel, null);
	}

	public ChipTuneNoteInstance(NoteInstance note, int channel, Object data) {
		this.note = note;
		this.channel = channel;
		this.data = data;
	}

	public boolean isSilence() {
		return note == null || data == null || note.isSilence();
	}

	public NoteInstance getNote() {
		return note;
	}

	public int getChannel() {
		return channel;
	}

	public Object getData() {
		return data;
	}
}
