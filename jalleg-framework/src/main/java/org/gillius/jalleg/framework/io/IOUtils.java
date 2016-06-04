package org.gillius.jalleg.framework.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * IO Utility methods that ought to be in the stock JDK but were never added so we have them in almost every Java
 * project. Yes, there are 3rd party libs to depend on for these but I want to limit jalleg's dependencies.
 */
public class IOUtils {
	/**
	 * Copy all bytes from the given {@link InputStream} to the given {@link OutputStream}, closing the input and output
	 * after finishing.
	 */
	public static void copy(InputStream i, OutputStream o) throws IOException {
		byte[] buf = new byte[4096];
		int read = i.read(buf);
		while (read > 0) {
			o.write(buf, 0, read);
			read = i.read(buf);
		}

		i.close();
		o.close();
	}

	/**
	 * Close the given {@link AutoCloseable}, ignoring nulls and exceptions.
	 */
	public static void safeClose(AutoCloseable closeable) {
		try {
			if (closeable != null)
				closeable.close();
		} catch (Exception e) {
			//ignore
		}
	}
}
