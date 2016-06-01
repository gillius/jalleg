package org.gillius.jalleg.example;

import java.nio.ShortBuffer;

import static org.gillius.jalleg.binding.AllegroLibrary.ALLEGRO_AUDIO_DEPTH.ALLEGRO_AUDIO_DEPTH_INT16;
import static org.gillius.jalleg.binding.AllegroLibrary.*;
import static org.gillius.jalleg.binding.AllegroLibrary.ALLEGRO_CHANNEL_CONF.ALLEGRO_CHANNEL_CONF_1;
import static org.gillius.jalleg.binding.AllegroLibrary.ALLEGRO_PLAYMODE.ALLEGRO_PLAYMODE_LOOP;

/**
 * Plays a square wave at 200hz
 */
public class AudioExample {
	public static void main(String[] args) throws Exception {
		al_install_system(ALLEGRO_VERSION_INT, null);
		al_install_audio();

		int sampleSize = al_get_channel_count(ALLEGRO_CHANNEL_CONF_1).intValue() *
		                 al_get_audio_depth_size(ALLEGRO_AUDIO_DEPTH_INT16).intValue();
		int bytes = sampleSize * 220;
		ShortBuffer buf = al_malloc(bytes).getByteBuffer(0, bytes).asShortBuffer();

		for (int i=0; i < 220; ++i) {
			if (i <= 110)
				buf.put(i, Short.MIN_VALUE);
			else
				buf.put(i, Short.MAX_VALUE);
		}

		ALLEGRO_SAMPLE s = al_create_sample(buf, 220, 44100, ALLEGRO_AUDIO_DEPTH_INT16, ALLEGRO_CHANNEL_CONF_1, true);

		al_reserve_samples(1);
		System.out.println(al_play_sample(s, 0.05f, ALLEGRO_AUDIO_PAN_NONE, 1f, ALLEGRO_PLAYMODE_LOOP, null));
		Thread.sleep(2000);

		al_destroy_sample(s);
		al_uninstall_audio();
		al_uninstall_system();
	}
}
