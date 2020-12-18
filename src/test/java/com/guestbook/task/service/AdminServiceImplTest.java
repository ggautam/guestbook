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
import org.springframework.test.context.junit4.SpringRunner;

import com.guestbook.task.dto.UserInvitation;
import com.guestbook.task.entity.UserEntity;
import com.guestbook.task.repository.InvitationRepository;
import com.guestbook.task.repository.UserRepository;
import com.guestbook.task.service.impl.AdminServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { AdminServiceImpl.class, })
public class AdminServiceImplTest {

	@Autowired
	AdminServiceImpl adminServiceImpl;

	@MockBean
	private UserRepository userRepository;

	@MockBean
	private InvitationRepository invitationRepository;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void getUserInvitationsTest() throws Exception {
		List<Object[]> objList = new ArrayList<>();
		Date currDate = new Date();
		Object[] objArr = { 1, 1, "test", "tester@test.com", "9876543210", "", "test message", true, currDate };
		objList.add(objArr);
		Mockito.when(invitationRepository.getUserInvitations()).thenReturn(objList);
		List<UserInvitation> response = adminServiceImpl.getUserInvitations();
		Assert.assertNotNull(response);
	}

	@Test
	public void getUserInvitationsByUserIdTest() throws Exception {
		List<Object[]> objList = new ArrayList<>();
		Date currDate = new Date();
		Object[] objArr = { 1, 1, "test", "tester@test.com", "9876543210", "", "test message", true, currDate };
		objList.add(objArr);
		Mockito.when(invitationRepository.getUserInvitationsByUserId(Mockito.anyLong())).thenReturn(objList);
		List<UserInvitation> response = adminServiceImpl.getUserInvitationsByUserId(Mockito.anyLong());
		Assert.assertNotNull(response);
	}

	@Test
	public void getAllUsersTest() throws Exception {
		List<UserEntity> userList = this.sampleUserEntityList();
		Mockito.when(userRepository.findAll()).thenReturn(userList);
		List<UserEntity> response = adminServiceImpl.getAllUsers();
		Assert.assertNotNull(response);
	}

	public List<UserEntity> sampleUserEntityList() {
		List<UserEntity> userList = new ArrayList<UserEntity>();
		UserEntity user = new UserEntity();
		user.setId(1);
		user.setName("Gaurav");
		user.setEmail("gautam.gaurav@hotmail.com");
		user.setAdmin(true);
		user.setGsm("9916386581");
		user.setPassword("Test@1234");
		user.setCreateDateTime(new Date());
		userList.add(user);
		return userList;
	}

	@After
	public void tearDown() throws Exception {
	}
}
