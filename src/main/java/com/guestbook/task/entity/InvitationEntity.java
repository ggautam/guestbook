package com.guestbook.task.entity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "gb_invitation")
@JsonIgnoreProperties({ "updateDateTime" })
public class InvitationEntity implements Serializable {

	private static final long serialVersionUID = 5105864677083945560L;

	@Id
	@Column(name = "invite_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long inviteId;

	@ManyToOne
	@JoinColumn(name = "user_id")
	public UserEntity userEntity;

	@Column(name = "event_message", columnDefinition = "varchar(750) default NULL")
	public String message;

	@Lob
	@Column(name = "event_image", nullable = true, columnDefinition = "mediumblob")
	private byte[] eventImage;

	@Column(name = "is_active", columnDefinition = "tinyint(1) default 1")
	public boolean isActive = true;

	@Column(name = "is_approved", columnDefinition = "tinyint(1) default 0")
	public boolean isApproved = false;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "invite_create_dt")
	public Date createDateTime = new Date();

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "invite_update_dt", insertable = false)
	@LastModifiedDate
	public Date updateDateTime;

	public long getInviteId() {
		return inviteId;
	}

	public void setInviteId(long inviteId) {
		this.inviteId = inviteId;
	}

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public byte[] getEventImage() {
		return eventImage;
	}

	public void setEventImage(byte[] eventImage) {
		this.eventImage = eventImage;
	}

	public Date getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}

	public Date getUpdateDateTime() {
		return updateDateTime;
	}

	public void setUpdateDateTime(Date updateDateTime) {
		this.updateDateTime = updateDateTime;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public boolean isApproved() {
		return isApproved;
	}

	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}

	@Override
	public String toString() {
		return "InvitationEntity [inviteId=" + inviteId + ", userEntity=" + userEntity + ", message=" + message
				+ ", eventImage=" + Arrays.toString(eventImage) + ", isActive=" + isActive + ", isApproved="
				+ isApproved + ", createDateTime=" + createDateTime + ", updateDateTime=" + updateDateTime + "]";
	}

}
