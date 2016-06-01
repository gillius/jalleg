package org.gillius.jalleg.framework;

public class AllegroException extends RuntimeException {
	public AllegroException() {
	}

	public AllegroException(String message) {
		super(message);
	}

	public AllegroException(String message, Throwable cause) {
		super(message, cause);
	}

	public AllegroException(Throwable cause) {
		super(cause);
	}

	public AllegroException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
