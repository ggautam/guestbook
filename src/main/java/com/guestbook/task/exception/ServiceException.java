package com.guestbook.task.exception;

public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 702916170535218973L;

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}
}
