package com.guestbook.task.dto;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

public class UserInvitation implements Serializable {

	private static final long serialVersionUID = 562152107867224482L;

	private long invite_id;

	private long uid;

	private String name;

	private String email;

	private String gsm;

	private byte[] event_image;

	private String event_message;

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

	public byte[] getEvent_image() {
		return event_image;
	}

	public void setEvent_image(byte[] event_image) {
		this.event_image = event_image;
	}

	public String getEvent_message() {
		return event_message;
	}

	public void setEvent_message(String event_message) {
		this.event_message = event_message;
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
				+ ", gsm=" + gsm + ", event_image=" + Arrays.toString(event_image) + ", event_message=" + event_message
				+ ", is_approved=" + is_approved + ", invite_create_dt=" + invite_create_dt + "]";
	}

}
