/*
 * Copyright 2016 Jason Winnebeck
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gillius.jalleg.example;

import static org.gillius.jalleg.binding.AllegroLibrary.*;

/**
 * Displays a 640x480 red window for 1 second.
 */
public class HelloAllegro {
	public static void main(String[] args) throws Exception {
		al_install_system(ALLEGRO_VERSION_INT, null);

		int version = al_get_allegro_version();
		int major = version >>> 24;
		int minor = (version >>> 16) & 255;
		int revision = (version >>> 8) & 255;
		int release = version & 255;
		System.out.println("Loaded Allegro version " + major + "." + minor + "." + revision + " release " + release);

		ALLEGRO_DISPLAY display = al_create_display(640, 480);

		al_clear_to_color(al_map_rgb_f(1f, 0f, 0f));
		al_flip_display();
		Thread.sleep(1000);

		al_destroy_display(display);

		al_uninstall_system();
	}
}
