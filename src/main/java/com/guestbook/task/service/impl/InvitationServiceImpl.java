package com.guestbook.task.service.impl;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guestbook.task.dto.GenericResponse;
import com.guestbook.task.dto.GenericResponse.Status;
import com.guestbook.task.entity.InvitationEntity;
import com.guestbook.task.repository.InvitationRepository;
import com.guestbook.task.service.InvitationService;

@Service
public class InvitationServiceImpl implements InvitationService {

	private static final Logger logger = LoggerFactory.getLogger(InvitationServiceImpl.class);

	@Autowired
	private InvitationRepository invitationRepository;

	/**
	 * Get User Invites list
	 * 
	 * @param inviteId inviteId to filter invitation list
	 * @param userId   userId to filter based on user
	 * @return Particular user invite
	 */
	@Override
	public InvitationEntity getInviteBasedOnUser(String inviteId, long userId) {
		InvitationEntity entry = invitationRepository.getInvitationByInviteIdAndUserId(Long.parseLong(inviteId),
				userId);
		return entry;
	}

	/**
	 * To approve and reject user invitation
	 * 
	 * @param inviteId inviteId to sort list invitation
	 * @param userId   userId to get specific
	 * @param status   approve / reject
	 * @return GenericResponse
	 */
	@Override
	public GenericResponse modifyInvitationStatus(String inviteId, String userId, String status) {
		logger.debug("InvitationServiceImpl|modifyInvitationStatus| inviteId: {}, userId: {} and status: {}", inviteId,
				userId, status);
		GenericResponse response = new GenericResponse();
		response.setStatus(Status.ERROR);
		if (StringUtils.isNotBlank(inviteId) && StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(status)) {
			InvitationEntity invitationEntity = invitationRepository
					.getInvitationByInviteIdAndUserId(Long.parseLong(inviteId), Long.parseLong(userId));
			if (invitationEntity != null) {
				String message = "";
				if(status.equalsIgnoreCase("delete")) {
					message = "Entry successfully deleted.";
					invitationRepository.updateDeactivateInvite(Long.parseLong(inviteId));
				} else {
					boolean inviteStatus = false;
					 message = "Entry successfully rejected.";
					if (status.equalsIgnoreCase("approve")) {
						inviteStatus = true;
						message = "Entry successfully approved.";
					}
					invitationRepository.updateInviteConfirmation(Long.parseLong(inviteId), inviteStatus);
				}
				response.setStatus(Status.SUCCESS);
				response.setMessage(message);
			} else {
				response.setMessage("Invalid Modification Request.");
			}
		} else {
			response.setMessage("Something wents wrong. please refresh and try again!");
		}
		return response;
	}

	@Override
	public InvitationEntity getInvite(long inviteId) {
		InvitationEntity response = invitationRepository.findByInviteId(inviteId);
		return response;
	}

}
