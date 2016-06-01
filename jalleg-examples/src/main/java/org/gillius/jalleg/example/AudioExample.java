package org.gillius.jalleg.example;

import java.nio.ShortBuffer;

import static org.gillius.jalleg.binding.AllegroLibrary.ALLEGRO_AUDIO_DEPTH.ALLEGRO_AUDIO_DEPTH_INT16;
import static org.gillius.jalleg.binding.AllegroLibrary.*;
import static org.gillius.jalleg.binding.AllegroLibrary.ALLEGRO_CHANNEL_CONF.ALLEGRO_CHANNEL_CONF_1;
import static org.gillius.jalleg.binding.AllegroLibrary.ALLEGRO_PLAYMODE.ALLEGRO_PLAYMODE_ONCE;

/**
 * Plays a square wave at 200hz
 */
public class AudioExample {
	public static void main(String[] args) throws Exception {
		al_install_system(ALLEGRO_VERSION_INT, null);
		al_install_audio();
		al_init_acodec_addon();

		al_create_display(640, 480);

		int sampleSize = al_get_channel_count(ALLEGRO_CHANNEL_CONF_1).intValue() *
		                 al_get_audio_depth_size(ALLEGRO_AUDIO_DEPTH_INT16).intValue();
		int bytes = sampleSize * 44100;
		ShortBuffer buf = al_malloc(bytes).getByteBuffer(0, bytes).asShortBuffer();

		int period = 44100 / 200;
		int zeroSize = period / 2;
		for (int i=0; i < 44100; ++i) {
			if (i % period <= zeroSize)
				buf.put(i, Short.MIN_VALUE);
			else
				buf.put(i, Short.MAX_VALUE);
		}

		ALLEGRO_SAMPLE s = al_create_sample(buf, 44100, 44100, ALLEGRO_AUDIO_DEPTH_INT16, ALLEGRO_CHANNEL_CONF_1, true);

		al_reserve_samples(1);
		System.out.println(al_play_sample(s, 0.01f, ALLEGRO_AUDIO_PAN_NONE, 1f, ALLEGRO_PLAYMODE_ONCE, null));
		Thread.sleep(2000);

		al_destroy_sample(s);
		al_uninstall_audio();
		al_uninstall_system();
	}
}
