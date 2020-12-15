package com.guestbook.task.service.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.guestbook.task.dto.Invitation;
import com.guestbook.task.dto.User;
import com.guestbook.task.entity.InvitationEntity;
import com.guestbook.task.entity.UserEntity;
import com.guestbook.task.repository.InvitationRepository;
import com.guestbook.task.repository.UserRepository;
import com.guestbook.task.service.UserService;
import com.guestbook.task.dto.GenericResponse;
import com.guestbook.task.dto.GenericResponse.Status;

/**
 * Service to fetch user, create user
 * @author gaurav
 *
 */
@Service
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Value("${gb.invitation.card_path}")
	private String invitationCardPath;

	@Value("${gb.invitation.db_card_path}")
	private String invitationDbCardPath;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private InvitationRepository invitationRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	/**
	 * Save registered user
	 * @param user		User details to do register
	 */
	@Override
	public void save(User user) {
		logger.debug("UserServiceImpl|save| user: {}", user);
		UserEntity userEntity = new UserEntity();
		userEntity.setName(user.getName());
		userEntity.setEmail(user.getEmail());
		userEntity.setGsm(user.getGsm());
		userEntity.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userRepository.save(userEntity);
	}

	/**
	 * Get user info by email
	 * @param email		email address to get user info
	 * @return			user object
	 */
	@Override
	public UserEntity findByEmail(String email) {
		logger.debug("UserServiceImpl|findByEmail| email: {}", email);
		return userRepository.findByEmail(email);
	}

	/**
	 * Get admin user info by email
	 * @param email			email address to get user info
	 * @return				user object
	 */
	@Override
	public UserEntity getAdminUserByEmail(String email) {
		logger.debug("UserServiceImpl|getAdminUserByEmail| email: {}", email);
		return userRepository.getAdminUserByEmail(email);
	}

	/**
	 * Get user info by id
	 * @param userId		userId to fetch user details
	 * @return				user object
	 */
	@Override
	public UserEntity findById(long userId) {
		logger.debug("UserServiceImpl|findById| userId: {}", userId);
		return userRepository.findById(userId);
	}

	/**
	 * Get user info by id
	 * @param gsm			phone number to get user info
	 * @return				user object
	 */
	@Override
	public UserEntity findByGsm(String gsm) {
		logger.debug("UserServiceImpl|findByGsm| gsm: {}", gsm);
		return userRepository.findByGsm(gsm);
	}

	/**
	 * Get all invitation of specific user
	 * @param userId		userId to fetch all created invites
	 * @return				list of invitation object
	 */
	@Override
	public List<InvitationEntity> getInvitationByUserId(long userId) {
		logger.debug("UserServiceImpl|getInvitationByUserId| userId: {}", userId);
		List<InvitationEntity> invitationEntities = invitationRepository.getInvitationsByUserId(userId);
		if (!CollectionUtils.isEmpty(invitationEntities)) {
			logger.debug("UserServiceImpl|getInvitationByUserId|Total Invitations are {}", invitationEntities.size());
			return invitationEntities;
		} else {
			logger.debug("UserServiceImpl|getInvitationByUserId|No record found");
			return null;
		}
	}
 
	/**
	 * Create invite for an user
	 * @param invite		invites details to create an invitation	
	 * @return				GenericResponse			 
	 */
	@Override
	public GenericResponse saveInvite(Invitation invite) {
		logger.debug("UserServiceImpl|saveInvite| invite: {}", invite);
		boolean isValid = false;
		GenericResponse response = new GenericResponse();
		response.setStatus(Status.ERROR);
		InvitationEntity invitationEntity = new InvitationEntity();
		UserEntity userEntity = userRepository.findById(Long.parseLong(invite.getUserId()));
		if (userEntity != null) {
			invitationEntity.setUserEntity(userEntity);

			if (invite.getFile() != null && !invite.getFile().isEmpty()) {
				try {
					isValid = true;
					SimpleDateFormat dirDateTimeFormat = new SimpleDateFormat("yyyy-MM-dd");
					String directoryPath = invitationCardPath + File.separator + invite.getUserId() + File.separator
							+ dirDateTimeFormat.format(new Date());
					byte[] bytes = invite.getFile().getBytes();
					String filePath = this.createDirectory(directoryPath);
					SimpleDateFormat dateTimeFormat = new SimpleDateFormat("ddMMyyyy_HHmmss");
					String currentDate = dateTimeFormat.format(new Date());
					String fileExtension = FilenameUtils.getExtension(invite.getFile().getOriginalFilename());
					String filePathName = filePath + File.separator + invite.getUserId() + "-" + currentDate + "."
							+ fileExtension.toLowerCase();
					File serverFile = new File(filePathName);
					BufferedOutputStream mainStream = new BufferedOutputStream(new FileOutputStream(serverFile));
					mainStream.write(bytes);
					mainStream.close();
					String tempFilePathName = filePathName.replace(invitationCardPath, invitationDbCardPath);
					invitationEntity.setCard(tempFilePathName);
				} catch (Exception e) {
					logger.error("UserServiceImpl|saveInvite|File: {}|Exception: {}",
							invite.getFile().getOriginalFilename(), e);
				}
			}

			if (!isValid && StringUtils.isNotBlank(invite.getMessage())) {
				invitationEntity.setMessage(invite.getMessage());
				isValid = true;
			}

			if (isValid) {
				logger.debug("UserServiceImpl|saveInvite| invitationEntity: {}", invitationEntity);
				invitationRepository.save(invitationEntity);
				response.setStatus(Status.SUCCESS);
				response.setMessage("Yayy! your invitation created!");
			} else {
				logger.debug("UserServiceImpl|saveInvite| Invalid invitation");
				response.setMessage("Please provide valid Invitation Card or message");
			}
		} else {
			logger.error("UserServiceImpl|saveInvite| Invalid User");
			response.setMessage("Something wents wrong. please refresh and try again!");
		}
		return response;

	}

	/**
	 * Create a directory
	 * @param directoryPath			location to create
	 * @return						created location
	 * @throws Exception
	 */
	public String createDirectory(String directoryPath) throws Exception {
		logger.debug("UserServiceImpl|createDirectory|In| directoryPath: {}", directoryPath);
		File dir = new File(directoryPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		logger.debug("UserServiceImpl|createDirectory|In| directoryPath: {}", dir.getAbsolutePath());
		return dir.getAbsolutePath();
	}

}
