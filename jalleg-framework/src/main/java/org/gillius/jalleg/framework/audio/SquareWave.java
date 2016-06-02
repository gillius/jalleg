package org.gillius.jalleg.framework.audio;

import org.gillius.jalleg.binding.ALLEGRO_SAMPLE_ID;
import org.gillius.jalleg.binding.AllegroLibrary.*;

import java.io.Closeable;
import java.io.IOException;
import java.nio.ShortBuffer;

import static org.gillius.jalleg.binding.AllegroLibrary.*;
import static org.gillius.jalleg.binding.AllegroLibrary.ALLEGRO_AUDIO_DEPTH.ALLEGRO_AUDIO_DEPTH_INT16;
import static org.gillius.jalleg.binding.AllegroLibrary.ALLEGRO_CHANNEL_CONF.ALLEGRO_CHANNEL_CONF_1;
import static org.gillius.jalleg.binding.AllegroLibrary.ALLEGRO_PLAYMODE.ALLEGRO_PLAYMODE_LOOP;

public class SquareWave implements Closeable {
	private ALLEGRO_SAMPLE samp;
	private ALLEGRO_SAMPLE_ID id;
	private boolean playing;
	private double endTime;

	public SquareWave() {
		int sampleSize = al_get_channel_count(ALLEGRO_CHANNEL_CONF_1).intValue() *
		                 al_get_audio_depth_size(ALLEGRO_AUDIO_DEPTH_INT16).intValue();
		int bytes = sampleSize * 220;
		ShortBuffer buf = al_malloc(bytes).getByteBuffer(0, bytes).asShortBuffer();

		for (int i=0; i < 220; ++i) {
			buf.put(i, squareWave(i / 220f));
		}

		samp = al_create_sample(buf, 220, 44100, ALLEGRO_AUDIO_DEPTH_INT16, ALLEGRO_CHANNEL_CONF_1, true);
		id = new ALLEGRO_SAMPLE_ID();
	}

	@Override
	public void close() throws IOException {
		stop();
		al_destroy_sample(samp);
		samp = null;
	}

	@Override
	protected void finalize() throws Throwable {
		if (samp != null)
			al_destroy_sample(samp);
		super.finalize();
	}

	public void update(double t) {
		if (playing && t >= endTime) {
			stop();
		}
	}

	public void play(float gain, float freq, double endTime) {
		stop();

		playing = al_play_sample(samp, gain, ALLEGRO_AUDIO_PAN_NONE, freq / 200f, ALLEGRO_PLAYMODE_LOOP, id);
		this.endTime = endTime;
	}

	public void stop() {
		if (playing) {
			al_stop_sample(id);
			playing = false;
		}
	}

	public static short squareWave(float p) {
		return p < 0.5f ? Short.MAX_VALUE : Short.MIN_VALUE;
	}
}
