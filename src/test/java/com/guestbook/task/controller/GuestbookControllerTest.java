package com.guestbook.task.controller;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.guestbook.task.dto.GenericResponse;
import com.guestbook.task.service.InvitationService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { GuestbookController.class, })
public class GuestbookControllerTest {

	@MockBean
	private InvitationService invitationService;

	@Autowired
	private GuestbookController guestbookController;

	@Mock
	private GenericResponse genericResponse;

	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(guestbookController).build();
	}

	@Test
	public void saveConfirmInvitationTest() throws Exception {
		Mockito.when(
				invitationService.modifyInvitationStatus(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(genericResponse);

		GenericResponse response = guestbookController.saveConfirmInvitation(Mockito.anyString(), Mockito.anyString(),
				Mockito.anyString());

		assertNotNull(genericResponse);
		assertNotNull(response);
	}

	@After
	public void tearDown() throws Exception {
	}

}
