package org.gillius.jalleg.framework.audio;

import org.gillius.jalleg.binding.AllegroLibrary.ALLEGRO_SAMPLE;

public interface SampleData extends AutoCloseable {
	ALLEGRO_SAMPLE getSample();
}
