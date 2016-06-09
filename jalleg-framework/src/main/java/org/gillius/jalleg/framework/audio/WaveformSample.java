package org.gillius.jalleg.framework.audio;

import org.gillius.jalleg.framework.AllegroException;

import java.nio.FloatBuffer;

import static org.gillius.jalleg.binding.AllegroLibrary.ALLEGRO_AUDIO_DEPTH.ALLEGRO_AUDIO_DEPTH_FLOAT32;
import static org.gillius.jalleg.binding.AllegroLibrary.ALLEGRO_CHANNEL_CONF.ALLEGRO_CHANNEL_CONF_1;
import static org.gillius.jalleg.binding.AllegroLibrary.*;

/**
 * WaveformSample generates an Allegro sample from a {@link MonoWaveform} and exposes it as {@link SampleData}, managing
 * the memory while open.
 */
public class WaveformSample implements SampleData, AutoCloseable {
	private ALLEGRO_SAMPLE sample;

	public WaveformSample(MonoWaveform waveform) {
		if (!al_is_audio_installed())
			throw new AllegroException("Audio is not installed");

		int numSamples = waveform.getSamples();
		int sampleSize = al_get_channel_count(ALLEGRO_CHANNEL_CONF_1).intValue() *
		                 al_get_audio_depth_size(ALLEGRO_AUDIO_DEPTH_FLOAT32).intValue();
		int bytes = sampleSize * numSamples;
		FloatBuffer buf = al_malloc(bytes).getByteBuffer(0, bytes).asFloatBuffer();

		for (int i=0; i < numSamples; ++i) {
			buf.put(i, waveform.getSample(i, (float)i / numSamples));
		}

		sample = al_create_sample(buf, numSamples, waveform.getFrequency(),
		                          ALLEGRO_AUDIO_DEPTH_FLOAT32, ALLEGRO_CHANNEL_CONF_1, true);
	}

	public ALLEGRO_SAMPLE getSample() {
		return sample;
	}

	@Override
	public void close() {
		if (sample != null)
			al_destroy_sample(sample);
	}

	@Override
	protected void finalize() throws Throwable {
		close();
		super.finalize();
	}
}
