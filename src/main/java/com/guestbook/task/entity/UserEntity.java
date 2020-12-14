package com.guestbook.task.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "gb_user", uniqueConstraints = { @UniqueConstraint(columnNames = { "email", "gsm" }) })
@JsonIgnoreProperties({ "updateDateTime" })
public class UserEntity implements Serializable {

	private static final long serialVersionUID = -4302610581308896269L;

	@Id
	@Column(name = "uid")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long id;

	@Column(name = "name", columnDefinition = "varchar(48)")
	public String name;

	@Column(name = "email", nullable = false, columnDefinition = "varchar(48)")
	public String email;

	@Column(name = "gsm", nullable = false, columnDefinition = "varchar(10)")
	public String gsm;

	@Column(name = "password", nullable = false, columnDefinition = "varchar(255)")
	public String password;

	@Column(name = "is_admin", columnDefinition = "tinyint(1) default 0")
	public boolean isAdmin = false;

	@OneToMany(mappedBy = "userEntity", cascade = { CascadeType.ALL })
	public List<InvitationEntity> gbList;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_dt")
	public Date createDateTime = new Date();

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_dt", insertable = false)
	@LastModifiedDate
	public Date updateDateTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public List<InvitationEntity> getGbList() {
		return gbList;
	}

	public void setGbList(List<InvitationEntity> gbList) {
		this.gbList = gbList;
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

	@Override
	public String toString() {
		return "UserEntity [id=" + id + ", name=" + name + ", email=" + email + ", gsm=" + gsm + ", password="
				+ password + ", isAdmin=" + isAdmin + ", gbList=" + gbList + ", createDateTime=" + createDateTime
				+ ", updateDateTime=" + updateDateTime + "]";
	}

}
