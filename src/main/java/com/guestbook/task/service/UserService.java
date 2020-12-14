package com.guestbook.task.service;

import java.util.List;

import com.guestbook.task.dto.GenericResponse;
import com.guestbook.task.dto.Invitation;
import com.guestbook.task.dto.User;
import com.guestbook.task.entity.InvitationEntity;
import com.guestbook.task.entity.UserEntity;

public interface UserService {
	void save(User user);

	GenericResponse saveInvite(Invitation invite);

	UserEntity findByEmail(String email);

	UserEntity getAdminUserByEmail(String email);

	UserEntity findById(long userId);

	UserEntity findByGsm(String gsm);

	List<InvitationEntity> getInvitationByUserId(long userId);
}
