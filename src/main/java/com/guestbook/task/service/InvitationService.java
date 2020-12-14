package com.guestbook.task.service;

import com.guestbook.task.dto.GenericResponse;

public interface InvitationService {
	GenericResponse modifyInvitationStatus(String inviteId, String userId, String status);
}
