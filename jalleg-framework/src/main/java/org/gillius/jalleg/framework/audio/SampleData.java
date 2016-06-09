package org.gillius.jalleg.framework.audio;

import org.gillius.jalleg.binding.AllegroLibrary.ALLEGRO_SAMPLE;

/**
 * Represents a closeable source of Allegro sample data. When closed, the sample is destroyed, typically with
 * {@link org.gillius.jalleg.binding.AllegroLibrary#al_destroy_sample(ALLEGRO_SAMPLE)}.
 */
public interface SampleData extends AutoCloseable {
	ALLEGRO_SAMPLE getSample();
}
