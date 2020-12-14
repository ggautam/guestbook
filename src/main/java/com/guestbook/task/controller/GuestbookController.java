package com.guestbook.task.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guestbook.task.dto.GenericResponse;
import com.guestbook.task.service.InvitationService;

@RestController
@RequestMapping(value = "/api/gb/")
public class GuestbookController {

	private static final Logger logger = LoggerFactory.getLogger(GuestbookController.class);

	@Autowired
	private InvitationService invitationService;

	@GetMapping("/confirm/invite/{inviteId}/{userId}/{status}")
	public GenericResponse saveConfirmInvitation(@PathVariable("inviteId") String inviteId,
			@PathVariable("userId") String userId, @PathVariable("status") String status) {
		logger.debug("GuestbookController|saveConfirmInvitation|In inviteId:{}, userId: {}, status: {}", inviteId,
				userId, status);
		return invitationService.modifyInvitationStatus(inviteId, userId, status);
	}

}
