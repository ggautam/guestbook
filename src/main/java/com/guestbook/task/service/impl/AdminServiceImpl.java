package com.guestbook.task.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.guestbook.task.dto.UserInvitation;
import com.guestbook.task.entity.UserEntity;
import com.guestbook.task.repository.InvitationRepository;
import com.guestbook.task.repository.UserRepository;
import com.guestbook.task.service.AdminService;

/**
 * Service to fetch all user, invitation info for admin user
 * @author gaurav
 *
 */
@Service
public class AdminServiceImpl implements AdminService {

	private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private InvitationRepository invitationRepository;

	/**
	 * Get all user created invitation
	 * @return			List of user and invites info
	 */
	@Override
	public List<UserInvitation> getUserInvitations() {
		logger.debug("AdminServiceImpl|getUserInvitations|In");
		List<Object[]> invitationResult = invitationRepository.getUserInvitations();
		List<UserInvitation> userInvitation = new ArrayList<UserInvitation>();
		if (!CollectionUtils.isEmpty(invitationResult)) {
			logger.debug("AdminServiceImpl|getUserInvitations|Total Invitations are {}", invitationResult.size());
			userInvitation = this.mapObjectToUserInvitationList(invitationResult);
		} else {
			logger.debug("AdminServiceImpl|getUserInvitations|No record found");
		}
		return userInvitation;
	}

	/**
	 * Get specific user created invitation
	 * @param			userId to get user and invites info 
	 * @return			List of user and invites info
	 */
	@Override
	public List<UserInvitation> getUserInvitationsByUserId(long userId) {
		logger.debug("AdminServiceImpl|getUserInvitationsByUserId|In");
		List<Object[]> invitationResult = invitationRepository.getUserInvitationsByUserId(userId);
		List<UserInvitation> userInvitation = new ArrayList<UserInvitation>();
		if (!CollectionUtils.isEmpty(invitationResult)) {
			logger.debug("AdminServiceImpl|getUserInvitationsByUserId|Total Invitations are {}",
					invitationResult.size());
			userInvitation = this.mapObjectToUserInvitationList(invitationResult);
		} else {
			logger.debug("AdminServiceImpl|getUserInvitationsByUserId|No record found");
		}
		return userInvitation;
	}

	/**
	 * Mapped object entries to userinvitation
	 * @param obj		Object array
	 * @return			List of user and invites info
	 */
	private List<UserInvitation> mapObjectToUserInvitationList(List<Object[]> obj) {
		logger.debug("AdminServiceImpl|mapObjectToUserInvitationList|In obj: {}", obj.size());
		List<UserInvitation> userInvitation = new ArrayList<UserInvitation>();
		if (!CollectionUtils.isEmpty(obj)) {
			for (Object[] invitation : obj) {
				UserInvitation ui = new UserInvitation();
				ui.setInvite_id(Long.parseLong(String.valueOf(invitation[0])));
				ui.setUid(Long.parseLong(String.valueOf(invitation[1])));
				ui.setName(String.valueOf(invitation[2]));
				ui.setEmail(String.valueOf(invitation[3]));
				ui.setGsm(String.valueOf(invitation[4]));
				if (invitation[5] != null) {
					ui.setEvent_image((byte[])invitation[5]);
				}
				if (invitation[6] != null && StringUtils.isNotBlank(String.valueOf(invitation[6]))) {
					ui.setEvent_message(String.valueOf(invitation[6]));
				}
				ui.setIs_approved(((Boolean) invitation[7]).booleanValue());
				ui.setInvite_create_dt((Date) invitation[8]);
				userInvitation.add(ui);
			}
			logger.debug("AdminServiceImpl|mapObjectToUserInvitationList|Total userInvitation are {}",
					userInvitation.size());
		} else {
			logger.debug("AdminServiceImpl|mapObjectToUserInvitationList|Empty Object");
		}
		return userInvitation;
	}

	/**
	 * Get all user info
	 * @return			List of user info
	 */
	@Override
	public List<UserEntity> getAllUsers() {
		return userRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
	}

}
