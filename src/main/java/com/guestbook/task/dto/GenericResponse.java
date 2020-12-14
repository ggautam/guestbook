package com.guestbook.task.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenericResponse implements Serializable {
	
	private static final long serialVersionUID = -1008891742095310522L;

	public GenericResponse(Status status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

	public GenericResponse() {
		super();
	}

	private Status status;
	private String message;

	public enum Status {
		SUCCESS, ERROR
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "GenericResponse [status=" + status + ", message=" + message + "]";
	}
}
