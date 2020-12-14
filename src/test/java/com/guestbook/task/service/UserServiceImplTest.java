package com.guestbook.task.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import com.guestbook.task.dto.GenericResponse;
import com.guestbook.task.dto.Invitation;
import com.guestbook.task.dto.User;
import com.guestbook.task.entity.InvitationEntity;
import com.guestbook.task.entity.UserEntity;
import com.guestbook.task.repository.InvitationRepository;
import com.guestbook.task.repository.UserRepository;
import com.guestbook.task.service.impl.UserServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { UserServiceImpl.class, })
public class UserServiceImplTest {
	
	@Autowired
	UserServiceImpl userServiceImpl;

	@MockBean
	private UserRepository userRepository;

	@MockBean
	private InvitationRepository invitationRepository;
	
	@MockBean
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void saveTest() throws Exception {
		User user = this.sampleUser();
		UserEntity userEntity = this.sampleUserEntity();
		Mockito.when(userRepository.save(Mockito.any(UserEntity.class))).thenReturn(userEntity);
		userServiceImpl.save(user);
	}

	@Test
	public void findByEmailTest() throws Exception {
		UserEntity userEntity = this.sampleUserEntity();
		Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(userEntity);
		UserEntity response = userServiceImpl.findByEmail(Mockito.anyString());
		Assert.assertNotNull(response);
	}

	@Test
	public void getAdminUserByEmailTest() throws Exception {
		UserEntity userEntity = this.sampleUserEntity();
		Mockito.when(userRepository.getAdminUserByEmail(Mockito.anyString())).thenReturn(userEntity);
		UserEntity response = userServiceImpl.getAdminUserByEmail(Mockito.anyString());
		Assert.assertNotNull(response);
	}

	@Test
	public void findByIdTest() throws Exception {
		UserEntity userEntity = this.sampleUserEntity();
		Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(userEntity);
		UserEntity response = userServiceImpl.findById(Mockito.anyLong());
		Assert.assertNotNull(response);
	}

	@Test
	public void findByGsmTest() throws Exception {
		UserEntity userEntity = this.sampleUserEntity();
		Mockito.when(userRepository.findByGsm(Mockito.anyString())).thenReturn(userEntity);
		UserEntity response = userServiceImpl.findByGsm(Mockito.anyString());
		Assert.assertNotNull(response);
	}

	@Test
	public void getInvitationByUserIdTest() throws Exception {
		List<InvitationEntity> inviteList = this.sampleInvitationEntity();
		Mockito.when(invitationRepository.getInvitationsByUserId(Mockito.anyLong())).thenReturn(inviteList);
		List<InvitationEntity> response = userServiceImpl.getInvitationByUserId(Mockito.anyLong());
		Assert.assertNotNull(response);
	}

	@Test
	public void saveInviteByMessageTest() throws Exception {
		Invitation invitation = this.sampleInvitation();
		UserEntity userEntity = this.sampleUserEntity();
		InvitationEntity invitationEntity = this.sampleInvitationEntityData();
		Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(userEntity);
		Mockito.when(invitationRepository.save(Mockito.any(InvitationEntity.class))).thenReturn(invitationEntity);
		GenericResponse response = userServiceImpl.saveInvite(invitation);
		Assert.assertNotNull(response);
	}

	@Test
	public void saveInviteByFileTest() throws Exception {
		Invitation invitation = this.sampleInvitationByFile();
		UserEntity userEntity = this.sampleUserEntity();
		InvitationEntity invitationEntity = this.sampleInvitationEntityData();
		Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(userEntity);
		Mockito.when(invitationRepository.save(Mockito.any(InvitationEntity.class))).thenReturn(invitationEntity);
		GenericResponse response = userServiceImpl.saveInvite(invitation);
		Assert.assertNotNull(response);
	}

	public User sampleUser() {
		User user = new User();
		user.setName("Gaurav");
		user.setEmail("gg00483532@techmahindra.com");
		user.setAdmin(false);
		user.setGsm("9916386581");
		user.setPassword("Test@1234");
		return user;
	}

	public UserEntity sampleUserEntity() {
		UserEntity user = new UserEntity();
		user.setId(1);
		user.setName("Gaurav");
		user.setEmail("gg00483532@techmahindra.com");
		user.setAdmin(true);
		user.setGsm("9916386581");
		user.setPassword("Test@1234");
		user.setCreateDateTime(new Date());

		return user;
	}

	public List<InvitationEntity> sampleInvitationEntity() {
		List<InvitationEntity> inviteList = new ArrayList<InvitationEntity>();
		UserEntity user = new UserEntity();
		user.setId(1);
		user.setName("Gaurav");
		user.setEmail("gg00483532@techmahindra.com");
		user.setAdmin(false);
		user.setGsm("9916386581");
		user.setPassword("Test@1234");
		user.setCreateDateTime(new Date());

		InvitationEntity invitation = new InvitationEntity();
		invitation.setInviteId(1);
		invitation.setUserEntity(user);
		invitation.setMessage("This is test. Please ignore");
		invitation.setCreateDateTime(new Date());
		inviteList.add(invitation);
		return inviteList;
	}

	public Invitation sampleInvitation() {
		Invitation invitation = new Invitation();
		invitation.setMessage("Test message");
		invitation.setUserId("1");
		return invitation;
	}

	public Invitation sampleInvitationByFile() {
		Invitation invitation = new Invitation();
		MockMultipartFile file = new MockMultipartFile("file", "test.jpg", "text/xml", "hello".getBytes());
		invitation.setFile(file);
		invitation.setUserId("1");
		return invitation;
	}

	public InvitationEntity sampleInvitationEntityData() {
		UserEntity user = new UserEntity();
		user.setId(1);
		user.setName("Gaurav");
		user.setEmail("gg00483532@techmahindra.com");
		user.setAdmin(false);
		user.setGsm("9916386581");
		user.setPassword("Test@1234");
		user.setCreateDateTime(new Date());

		InvitationEntity invitation = new InvitationEntity();
		invitation.setInviteId(1);
		invitation.setUserEntity(user);
		invitation.setMessage("This is test. Please ignore");
		invitation.setCreateDateTime(new Date());
		return invitation;
	}

	@After
	public void tearDown() throws Exception {
	}

}
