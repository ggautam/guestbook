package com.guestbook.task.dto;

import java.io.Serializable;
import java.util.Date;

public class UserInvitation implements Serializable {

	private static final long serialVersionUID = 562152107867224482L;

	private long invite_id;

	private long uid;

	private String name;

	private String email;

	private String gsm;

	private String card;

	private String message;

	private boolean is_approved;

	private Date invite_create_dt;

	public long getInvite_id() {
		return invite_id;
	}

	public void setInvite_id(long invite_id) {
		this.invite_id = invite_id;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

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

	public String getCard() {
		return card;
	}

	public void setCard(String card) {
		this.card = card;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getInvite_create_dt() {
		return invite_create_dt;
	}

	public void setInvite_create_dt(Date invite_create_dt) {
		this.invite_create_dt = invite_create_dt;
	}

	public boolean isIs_approved() {
		return is_approved;
	}

	public void setIs_approved(boolean is_approved) {
		this.is_approved = is_approved;
	}

	@Override
	public String toString() {
		return "UserInvitation [invite_id=" + invite_id + ", uid=" + uid + ", name=" + name + ", email=" + email
				+ ", gsm=" + gsm + ", card=" + card + ", message=" + message + ", is_approved=" + is_approved
				+ ", invite_create_dt=" + invite_create_dt + "]";
	}

}
