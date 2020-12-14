package com.guestbook.task.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class User implements Serializable {

	private static final long serialVersionUID = 283871327656898777L;

	@NotEmpty(message = "* Please provide your name")
	private String name;

	@Email(message = "* Please provide a valid Email")
	@NotEmpty(message = "* Please provide an email")
	private String email;

	@NotEmpty(message = "* Please provide your phone number")
	private String gsm;

	@NotEmpty(message = "* Please provide your password")
	private String password;

	private boolean isAdmin;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGsm() {
		return gsm;
	}

	public void setGsm(String gsm) {
		this.gsm = gsm;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", email=" + email + ", gsm=" + gsm + ", password=" + password + ", isAdmin="
				+ isAdmin + "]";
	}

}
