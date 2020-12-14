package com.guestbook.task.exception;

public class ForbiddenException extends RuntimeException {

	private static final long serialVersionUID = 3095839541801982115L;

	public ForbiddenException(String message) {
		super(message);
	}

	public ForbiddenException(String message, Throwable cause) {
		super(message, cause);
	}

	public ForbiddenException(Throwable cause) {
		super(cause);
	}
}
