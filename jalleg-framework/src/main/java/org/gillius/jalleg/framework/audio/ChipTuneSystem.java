package org.gillius.jalleg.framework.audio;

import org.gillius.jalleg.binding.AllegroLibrary.*;
import org.gillius.jalleg.framework.AllegroException;

import java.util.HashMap;
import java.util.Map;

import static org.gillius.jalleg.binding.AllegroLibrary.ALLEGRO_MIXER_QUALITY.ALLEGRO_MIXER_QUALITY_POINT;
import static org.gillius.jalleg.binding.AllegroLibrary.ALLEGRO_PLAYMODE.ALLEGRO_PLAYMODE_LOOP;
import static org.gillius.jalleg.binding.AllegroLibrary.*;

public class ChipTuneSystem implements AutoCloseable {
	private ALLEGRO_MIXER mixer;
	private Channel[] channels;

	private Map<Object, SampleData> samples = new HashMap<>();

	public ChipTuneSystem(int numChannels) {
		if (!al_is_audio_installed())
			throw new AllegroException("Audio is not installed");

		al_restore_default_mixer();
		mixer = al_get_default_mixer();
		if (mixer == null)
			throw new AllegroException("Error creating mixer");

		//We need to disable linear/cubic interpolation to get true waves like square wave
		if (!al_set_mixer_quality(mixer, ALLEGRO_MIXER_QUALITY_POINT))
			throw new AllegroException("Error setting mixer quality");

		channels = new Channel[numChannels];
		for (int i = 0; i < numChannels; i++) {
			channels[i] = new Channel();

			if (!al_attach_sample_instance_to_mixer(channels[i].sampleInstance, mixer))
				throw new AllegroException("Unable to attach sample instance to mixer");
		}
	}

	@Override
	public void close() throws Exception {
		if (mixer != null) {
			al_destroy_mixer(mixer);
			mixer = null;
		}

		if (channels != null) {
			for (Channel channel : channels) {
				al_destroy_sample_instance(channel.sampleInstance);
			}
			channels = null;
		}

		if (samples != null) {
			for (SampleData sample : samples.values()) {
				sample.close();
			}
			samples = null;
		}
	}

	public void play(ChipTuneNoteInstance note) {
		if (note.isSilence()) {
			stop(note.getChannel());
		} else {

			Object data = note.getData();
			if (data instanceof SampleData) {
				play((SampleData) data, note.getChannel(), note.getNote());

			} else if (data instanceof MonoWaveform) {
				play((MonoWaveform) data, note.getChannel(), note.getNote());
			}
		}
	}

	public void play(SampleData data, int channel, NoteInstance note) {
		if (!playExisting(data, channel, note)) {
			samples.put(data, data);
			playExisting(data, channel, note);
		}
	}

	public void play(MonoWaveform waveform, int channel, NoteInstance note) {
		if (!playExisting(waveform, channel, note)) {
			samples.put(waveform, new WaveformSample(waveform));
			playExisting(waveform, channel, note);
		}
	}

	private boolean playExisting(Object obj, int channel, NoteInstance note) {
		SampleData sampleData = samples.get(obj);
		if (sampleData == null)
			return false;

		channels[channel].playUntil(sampleData.getSample(), note);

		return true;
	}

	public void stop(int channel) {
		channels[channel].stop();
	}

	@Override
	protected void finalize() throws Throwable {
		close();
		super.finalize();
	}

	private static class Channel {
		public final ALLEGRO_SAMPLE_INSTANCE sampleInstance;

		public Channel() {
			sampleInstance = al_create_sample_instance(null);
			if (sampleInstance == null)
				throw new AllegroException("Error creating sample instance");
		}

		void playUntil(ALLEGRO_SAMPLE data, NoteInstance note) {
			al_set_sample(sampleInstance, data);
			al_set_sample_instance_speed(sampleInstance, note.getSpeed());
			al_set_sample_instance_gain(sampleInstance, note.getGain());
			al_set_sample_instance_playmode(sampleInstance, ALLEGRO_PLAYMODE_LOOP);
			al_set_sample_instance_pan(sampleInstance, note.getPan());
			al_play_sample_instance(sampleInstance);
		}

		void stop() {
			al_stop_sample_instance(sampleInstance);
		}
	}
}
