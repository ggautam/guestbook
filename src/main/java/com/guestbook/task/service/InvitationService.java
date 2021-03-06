package com.guestbook.task.service;

import com.guestbook.task.dto.GenericResponse;
import com.guestbook.task.entity.InvitationEntity;

public interface InvitationService {

	InvitationEntity getInvite(long inviteId);

	InvitationEntity getInviteBasedOnUser(String inviteId, long userId);

	GenericResponse modifyInvitationStatus(String inviteId, String userId, String status);
}
