package com.guestbook.task.service;

import java.util.Date;

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

import com.guestbook.task.dto.GenericResponse;
import com.guestbook.task.entity.InvitationEntity;
import com.guestbook.task.entity.UserEntity;
import com.guestbook.task.repository.InvitationRepository;
import com.guestbook.task.service.impl.InvitationServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { InvitationServiceImpl.class, })
public class InvitationServiceImplTest {

	@Autowired
	InvitationServiceImpl invitationServiceImpl;

	@MockBean
	private InvitationRepository invitationRepository;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void modifyInvitationStatusTest() throws Exception {
		InvitationEntity invitation = this.sampleInvitationEntity();
		Mockito.when(invitationRepository.getInvitationByInviteIdAndUserId(Mockito.anyLong(), Mockito.anyLong()))
				.thenReturn(invitation);
		GenericResponse response = invitationServiceImpl.modifyInvitationStatus("1", "1", "approve");
		Assert.assertNotNull(response);
	}

	public InvitationEntity sampleInvitationEntity() {
		UserEntity user = new UserEntity();
		user.setId(1);
		user.setName("Gaurav");
		user.setEmail("gautam.gaurav@hotmail.com");
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
