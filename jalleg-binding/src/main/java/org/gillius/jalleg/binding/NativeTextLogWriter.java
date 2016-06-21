package org.gillius.jalleg.binding;

import org.gillius.jalleg.binding.AllegroLibrary.ALLEGRO_TEXTLOG;

import java.io.IOException;
import java.io.Writer;

/**
 * A Java io implementation of {@link Writer} that outputs to an ALLEGRO_TEXTLOG. You can wrap the writer in a
 * {@link java.io.PrintWriter}, which also allows output from multiple threads.
 */
public class NativeTextLogWriter extends Writer {
	private final ALLEGRO_TEXTLOG textlog;

	public NativeTextLogWriter(ALLEGRO_TEXTLOG textlog) {
		this.textlog = textlog;
	}

	@Override
	public void write(char[] cbuf, int off, int len) throws IOException {
		AllegroLibrary.jalleg_append_native_text_log(textlog, new String(cbuf, off, len));
	}

	@Override
	public void flush() throws IOException {
	}

	@Override
	public void close() throws IOException {
	}
}
