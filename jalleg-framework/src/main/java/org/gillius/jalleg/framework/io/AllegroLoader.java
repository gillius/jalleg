package org.gillius.jalleg.framework.io;

import org.gillius.jalleg.binding.AllegroLibrary;
import org.gillius.jalleg.binding.AllegroLibrary.ALLEGRO_BITMAP;
import org.gillius.jalleg.framework.AllegroException;

import java.io.IOException;
import java.io.InputStream;

import static org.gillius.jalleg.binding.AllegroLibrary.al_load_bitmap_flags_f;

/**
 * Utility methods to load Allegro resources such as images from Java byte arrays, {@link InputStream}s, or classpath
 * resources.
 */
public class AllegroLoader {
	/**
	 * Pulls an Allegro "ident" file extension such as ".png" by looking at the end of the given path.
	 */
	public static String getIdentFromPath(String path) {
		int idx = path.lastIndexOf('.');
		if (idx >= 0) {
			return path.substring(idx);
		} else {
			return null;
		}
	}

	/**
	 * Uses {@link AllegroLibrary#al_load_bitmap_f} to load bitmap data. File type will be autodetected from the path's
	 * extension if possible (via {@link #getIdentFromPath(String)}), or from {@link AllegroLibrary#al_identify_bitmap_f}.
	 *
	 * @param path classpath available from the root (boot) classpath
	 *
	 * @throws AllegroException if al_load_bitmap_flags_f fails
	 */
	public static ALLEGRO_BITMAP loadBitmapFromClasspath(String path) throws IOException {
		return loadBitmapFromClasspath(path, null, 0);
	}

	/**
	 * Uses {@link AllegroLibrary#al_load_bitmap_f} to load bitmap data.
	 *
	 * @param path classpath available from the root (boot) classpath
	 * @param ident type of file such as ".png" or null to use {@link #getIdentFromPath(String)}. If the path does not
	 *              end with a file extension, then auto-detect with {@link AllegroLibrary#al_identify_bitmap_f}
	 * @param flags flags to pass such as {@link AllegroLibrary#ALLEGRO_NO_PREMULTIPLIED_ALPHA}
	 *
	 * @throws AllegroException if al_load_bitmap_flags_f fails
	 */
	public static ALLEGRO_BITMAP loadBitmapFromClasspath(String path, String ident, int flags) throws IOException {
		InputStream is = AllegroLoader.class.getResourceAsStream(path);
		if (is == null)
			throw new IOException("Unable to find classpath resource '" + path + "'");

		if (ident == null)
			ident = getIdentFromPath(path);

		return loadBitmap(is, ident, flags);
	}

	/**
	 * Uses {@link AllegroLibrary#al_load_bitmap_f} to load bitmap data.
	 *
	 * @param is image data; stream is closed by this function
	 * @param ident type of file such as ".png" or null to auto-detect with {@link AllegroLibrary#al_identify_bitmap_f}
	 *
	 * @throws AllegroException if al_load_bitmap_flags_f fails
	 */
	public static ALLEGRO_BITMAP loadBitmap(InputStream is, String ident) throws IOException {
		return loadBitmap(is, ident, 0);
	}

	/**
	 * Uses {@link AllegroLibrary#al_load_bitmap_f} to load bitmap data.
	 *
	 * @param is image data; stream is closed by this function
	 * @param ident type of file such as ".png" or null to auto-detect with {@link AllegroLibrary#al_identify_bitmap_f}
	 * @param flags flags to pass such as {@link AllegroLibrary#ALLEGRO_NO_PREMULTIPLIED_ALPHA}
	 *
	 * @throws AllegroException if al_load_bitmap_flags_f fails
	 */
	public static ALLEGRO_BITMAP loadBitmap(InputStream is, String ident, int flags) throws IOException {
		try (Memfile file = Memfile.from(is)) {
			ALLEGRO_BITMAP ret = al_load_bitmap_flags_f(file.getFile(), ident, flags);
			if (ret == null)
				throw new AllegroException("Failed to load bitmap with type " + ident);
			return ret;
		}
	}
}
