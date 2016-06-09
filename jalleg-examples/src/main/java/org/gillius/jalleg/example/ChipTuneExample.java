package org.gillius.jalleg.example;

import org.gillius.jalleg.framework.AllegroAddon;
import org.gillius.jalleg.framework.Game;
import org.gillius.jalleg.framework.audio.*;

import static org.gillius.jalleg.binding.AllegroLibrary.al_clear_to_color;
import static org.gillius.jalleg.binding.AllegroLibrary.al_map_rgb_f;

public class ChipTuneExample extends Game {
	private ChipTuneRunner chipTuneRunner;
	private float start = 0f;

	@Override
	protected void onAllegroStarted() {
		initAddons(AllegroAddon.Audio);
		chipTuneRunner = new ChipTuneRunner(new ChipTuneSystem(1));

		addNote(Note.E);
		addNote(Note.D);
		addNote(Note.C);
		addNote(Note.D);
		addNote(Note.E);
		addNote(Note.E);
		addNote(Note.E);
		addNote(null);

		addNote(Note.D);
		addNote(Note.D);
		addNote(Note.D);
		addNote(null);

		addNote(Note.E);
		addNote(Note.F);
		addNote(Note.G);
		addNote(null);

		addNote(Note.E);
		addNote(Note.D);
		addNote(Note.C);
		addNote(Note.D);
		addNote(Note.E);
		addNote(Note.E);
		addNote(Note.E);
		addNote(Note.E);
		addNote(Note.D);
		addNote(Note.D);
		addNote(Note.E);
		addNote(Note.D);
		addNote(Note.C);
		addNote(null);

		chipTuneRunner.start();
	}

	private void addNote(Note note) {
		if (note != null) {
			chipTuneRunner.add(new ChipTuneNoteInstance(new NoteInstance(note, 4), 0, TriangleWave.INSTANCE), start);
			start += 0.48;
			chipTuneRunner.add(ChipTuneNoteInstance.silence(0), start);
			start += 0.02;
		} else {
			chipTuneRunner.add(ChipTuneNoteInstance.silence(0), start);
			start += 0.5;
		}
	}

	@Override
	protected void update() {
	}

	@Override
	protected void render() {
		al_clear_to_color(al_map_rgb_f(0f, 0f, 0f));
	}

	@Override
	protected void onStopped() {
		try {
			chipTuneRunner.stop();
		} catch (InterruptedException e) {
			//ignore
		}
	}

	public static void main(String[] args) {
		new ChipTuneExample().run();
	}
}
