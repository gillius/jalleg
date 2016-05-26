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

import org.gillius.jalleg.binding.ALLEGRO_COLOR;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import static org.gillius.jalleg.binding.AllegroLibrary.*;

public class HelloJavaScript {
	public static void main(String[] args) throws Exception {
		al_install_system(ALLEGRO_VERSION_INT, null);

		ALLEGRO_COLOR color = al_map_rgb_f(1f, 0f, 0f);

		ALLEGRO_BITMAP display = al_create_display(640, 480);

		al_clear_to_color(color);
		al_flip_display();
		Thread.sleep(1000);

		//Just to prove we can use Allegro from any JVM language, such as JavaScript Nashorn (built into Java 8)
		ScriptEngineManager engineManager = new ScriptEngineManager();
		ScriptEngine engine = engineManager.getEngineByName("nashorn");
		if (engine != null) {
			//engine will be null only for JDK 7 or earlier, or if there's some Java 8 build without Nashorn
			//It should be possible to write a script to iterate all static methods and export them as globals to make it more C-like
			engine.eval("var al = Packages.org.gillius.jalleg.binding.AllegroLibrary;" +
					"var color = al.al_map_rgb_f(0, 1, 0);" +
					"al.al_clear_to_color(color);" +
					"al.al_flip_display();");
			Thread.sleep(1000);
		}

		al_destroy_display(display);

		al_uninstall_system();
	}
}
