package org.gillius.jalleg.framework.io;

import com.sun.jna.Pointer;
import org.gillius.jalleg.binding.AllegroLibrary;
import org.gillius.jalleg.binding.AllegroLibrary.ALLEGRO_FILE;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.gillius.jalleg.binding.AllegroLibrary.*;

/**
 * Wraps the Allegro memfile interface around an Allegro memfile by holding memory references while the file is open.
 * This prevents the underlying memory from being garbage collected while the file is open. To close the Allegro file,
 * use the {@link #close()} method instead of {@link AllegroLibrary#al_fclose}.
 * <p>
 * The file memory is copied into a buffer allocated and freed by al_malloc and al_free.
 */
public class Memfile implements AutoCloseable {
	private ALLEGRO_FILE file;
	private Pointer memory;
	private int length;

	/**
	 * Creates a Memfile by reading all bytes of the given stream (then closing the stream).
	 */
	public static Memfile from(InputStream stream) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(4096);
		IOUtils.copy(stream, baos);
		return new Memfile(baos.toByteArray());
	}

	/**
	 * Creates a Memfile by copying the given data.
	 */
	public Memfile(byte[] data) {
		this(data, 0, data.length);
	}

	/**
	 * Creates a Memfile by copying the given data.
	 */
	public Memfile(byte[] data, int offset, int length) {
		this.length = length;
		memory = al_malloc(length);
		memory.write(0, data, offset, length);
		file = al_open_memfile(memory, length, "rw");
	}

	/**
	 * Creates a Memfile of a fixed size for writing.
	 */
	public Memfile(int size) {
		length = size;
		memory = al_malloc(length);
		file = al_open_memfile(memory, length, "w");
	}

	public ALLEGRO_FILE getFile() {
		return file;
	}

	/**
	 * Returns a copied snapshot of the file data.
	 *
	 * @param length amount of data to get, equal to or less than the allocated size.
	 */
	public byte[] getData(int length) {
		if (length > this.length)
			throw new IllegalArgumentException("Asked for " + length + " bytes, but only " + this.length + " allocated");

		byte[] ret = new byte[length];
		if (memory != null) {
			memory.read(0, ret, 0, length);
		}
		return ret;
	}

	@Override
	public void close() {
		if (file != null) {
			al_fclose(file);
			file = null;
		}
		if (memory != null) {
			al_free(memory);
			memory = null;
		}
	}

	@Override
	protected void finalize() throws Throwable {
		close();
		super.finalize();
	}
}
