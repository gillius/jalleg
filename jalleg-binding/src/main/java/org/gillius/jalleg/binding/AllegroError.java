package org.gillius.jalleg.binding;

public class AllegroError extends Error {
	public AllegroError() {
	}

	public AllegroError(String message) {
		super(message);
	}

	public AllegroError(String message, Throwable cause) {
		super(message, cause);
	}

	public AllegroError(Throwable cause) {
		super(cause);
	}

	public AllegroError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
