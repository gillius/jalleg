package org.gillius.jalleg.framework;

import com.sun.jna.ptr.PointerByReference;
import org.gillius.jalleg.binding.AllegroLibrary;
import org.gillius.jalleg.binding.AllegroLibrary.ALLEGRO_CONFIG;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.gillius.jalleg.binding.AllegroLibrary.*;

/**
 * AllegroConfig implements the same sections model as an {@link ALLEGRO_CONFIG}, but completely within Java. There are
 * methods to convert to and from an {@link ALLEGRO_CONFIG}. The main reason to keep it within Java is for efficiency
 * (avoiding the penalty of Java to native), easy ability to provide Java structures (Map), and to not need to worry
 * about needing to free native resources with {@link AllegroLibrary#al_destroy_config}.
 */
public class AllegroConfig {
	private final Map<String, Map<String, String>> sections = new LinkedHashMap<>();

	public static AllegroConfig fromConfigAndDestroy(ALLEGRO_CONFIG config) {
		AllegroConfig ret = new AllegroConfig(config);
		al_destroy_config(config);
		return ret;
	}

	public AllegroConfig(ALLEGRO_CONFIG config) {
		PointerByReference sectionIter = new PointerByReference();
		PointerByReference valueIter = new PointerByReference();

		String section = al_get_first_config_section(config, sectionIter);
		while (section != null) {
			String value = al_get_first_config_entry(config, section, valueIter);
			while (value != null) {
				setConfigValue(section, value, al_get_config_value(config, section, value));
				value = al_get_next_config_entry(valueIter);
			}
			section = al_get_next_config_section(sectionIter);
		}
	}

	public String getConfigValue(String section, String key) {
		Map<String, String> s = getSection(section, false);
		return s == null ? null : s.get(key);
	}

	/**
	 * Returns the specified configuration value, or defaultValue if there is no entry.
	 */
	public String getConfigValue(String section, String key, String defaultValue) {
		String ret = getConfigValue(section, key);
		return ret == null ? defaultValue : ret;
	}

	/**
	 * Returns a mutable view into this AllegroConfig. The map is a multi-level map of section to values, and the values
	 * map is key to value.
	 */
	public Map<String, Map<String, String>> getSections() {
		return sections;
	}

	/**
	 * Returns a mutable view into a configuration section.
	 *
	 * @param section section to get
	 * @param create  true if to create a new section if not found
	 *
	 * @return section if found. If not found, returns new section if create is true, else null
	 */
	public Map<String, String> getSection(String section, boolean create) {
		Map<String, String> ret = sections.get(section);
		if (ret == null && create) {
			ret = new LinkedHashMap<>();
			sections.put(section, ret);
		}
		return ret;
	}

	/**
	 * Sets the given value in a section under the given key. The value is converted into a String.
	 */
	public void setConfigValue(String section, String key, Object value) {
		getSection(section, true).put(key, String.valueOf(value));
	}

	public void mergeInto(AllegroConfig config) {
		for (Map.Entry<String, Map<String, String>> section : sections.entrySet()) {
			for (Map.Entry<String, String> value : section.getValue().entrySet()) {
				config.setConfigValue(section.getKey(), value.getKey(), value.getValue());
			}
		}
	}

	public void mergeInto(ALLEGRO_CONFIG config) {
		for (Map.Entry<String, Map<String, String>> section : sections.entrySet()) {
			for (Map.Entry<String, String> value : section.getValue().entrySet()) {
				al_set_config_value(config, section.getKey(), value.getKey(), value.getValue());
			}
		}
	}

	/**
	 * Creates a new {@link ALLEGRO_CONFIG} with {@link AllegroLibrary#al_create_config()} and writes all current values
	 * into it. The returned config must be freed with {@link AllegroLibrary#al_destroy_config(ALLEGRO_CONFIG)}.
	 */
	public ALLEGRO_CONFIG createConfig() {
		ALLEGRO_CONFIG config = al_create_config();
		mergeInto(config);
		return config;
	}

	/**
	 * Saves the configuration to a file.
	 */
	public void saveConfig(String filename) {
		ALLEGRO_CONFIG config = createConfig();
		al_save_config_file(filename, config);
		al_destroy_config(config);
	}

	public void saveConfig(ALLEGRO_FILE file) {
		ALLEGRO_CONFIG config = createConfig();
		al_save_config_file_f(file, config);
		al_destroy_config(config);
	}
}
