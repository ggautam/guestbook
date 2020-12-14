package com.guestbook.task.dto;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

public class Invitation implements Serializable {

	private static final long serialVersionUID = -2610372240397967866L;

	private String userId;

	private String message;

	private MultipartFile file;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	@Override
	public String toString() {
		return "Invitation [userId=" + userId + ", message=" + message + ", file=" + file + "]";
	}

}
