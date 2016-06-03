package org.gillius.jalleg.example;

import java.nio.ShortBuffer;

import static org.gillius.jalleg.binding.AllegroLibrary.ALLEGRO_AUDIO_DEPTH.ALLEGRO_AUDIO_DEPTH_INT16;
import static org.gillius.jalleg.binding.AllegroLibrary.*;
import static org.gillius.jalleg.binding.AllegroLibrary.ALLEGRO_CHANNEL_CONF.ALLEGRO_CHANNEL_CONF_1;
import static org.gillius.jalleg.binding.AllegroLibrary.ALLEGRO_MIXER_QUALITY.ALLEGRO_MIXER_QUALITY_POINT;
import static org.gillius.jalleg.binding.AllegroLibrary.ALLEGRO_PLAYMODE.ALLEGRO_PLAYMODE_LOOP;

/**
 * Plays a square wave at 200hz. Since a square wave is so simple, we can generate a square wave of any frequency
 * perfectly with only a 2 sample buffer, assuming the mixer is set to point interpolation (if it was default of linear,
 * we'd actually get triangle waves).
 */
public class AudioExample {
	public static void main(String[] args) throws Exception {
		al_install_system(ALLEGRO_VERSION_INT, null);
		al_install_audio();

		int sampleSize = al_get_channel_count(ALLEGRO_CHANNEL_CONF_1).intValue() *
		                 al_get_audio_depth_size(ALLEGRO_AUDIO_DEPTH_INT16).intValue();
		int bytes = sampleSize * 2;
		//Get the memory from Allegro itself, since it will manage and free it on its own heap.
		ShortBuffer buf = al_malloc(bytes).getByteBuffer(0, bytes).asShortBuffer();
		buf.put(0, Short.MAX_VALUE);
		buf.put(1, Short.MIN_VALUE);

		//Create the sample of 2 samples at 2 samples per second. That means the speed passed to al_play_sample will be the
		//frequency.
		ALLEGRO_SAMPLE s = al_create_sample(buf, 2, 2, ALLEGRO_AUDIO_DEPTH_INT16, ALLEGRO_CHANNEL_CONF_1, true);

		//Setting mixer quality works only before sample instances are attached to it (which happens with al_reserve_samples)
		//We need point sampling to produce a true square wave
		al_restore_default_mixer();
		al_set_mixer_quality(al_get_default_mixer(), ALLEGRO_MIXER_QUALITY_POINT);

		al_reserve_samples(1);
		al_play_sample(s, 1f, ALLEGRO_AUDIO_PAN_NONE, 200f, ALLEGRO_PLAYMODE_LOOP, null);
		Thread.sleep(1200);

		//Since we set free_buf parameter to true in al_create_sample, this will al_free the al_malloc'd buffer above.
		al_destroy_sample(s);
		al_uninstall_audio();
		al_uninstall_system();
	}
}
