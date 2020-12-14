package com.guestbook.task.service;

import java.util.List;

import com.guestbook.task.dto.UserInvitation;
import com.guestbook.task.entity.UserEntity;

public interface AdminService {

	List<UserInvitation> getUserInvitationsByUserId(long userId);

	List<UserInvitation> getUserInvitations();

	List<UserEntity> getAllUsers();

}
