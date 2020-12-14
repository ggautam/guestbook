package com.guestbook.task.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.guestbook.task.dto.UserInvitation;
import com.guestbook.task.entity.InvitationEntity;
import com.guestbook.task.entity.UserEntity;
import com.guestbook.task.service.AdminService;
import com.guestbook.task.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { AdminController.class, })
public class AdminControllerTest {

	@MockBean
	private UserService userService;

	@MockBean
	private AdminService adminService;

	@Autowired
	private AdminController adminController;

	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/templates/");
		viewResolver.setSuffix(".html");
		mockMvc = MockMvcBuilders.standaloneSetup(adminController).setViewResolvers(viewResolver).build();
	}

	@Test
	@WithMockUser("gg00483532@techmahindra.com")
	public void confirmInvitationTest() throws Exception {
		UserEntity UserEntity = this.sampleUserEntity();
		List<UserInvitation> gbData = this.sampleUserInvitation();
		Mockito.when(userService.getAdminUserByEmail(Mockito.anyString())).thenReturn(UserEntity);
		Mockito.when(adminService.getUserInvitations()).thenReturn(gbData);
		MvcResult result = mockMvc.perform(get("/admin/review/invites")).andDo(print())
				.andExpect(view().name("admin/validate-invites")).andReturn();
		Assert.assertNotNull(result);
	}

	@Test
	@WithMockUser("gg00483532@techmahindra.com")
	public void confirmInvitationTestWithUserId() throws Exception {
		UserEntity UserEntity = this.sampleUserEntity();
		List<UserInvitation> gbData = this.sampleUserInvitation();
		Mockito.when(userService.getAdminUserByEmail(Mockito.anyString())).thenReturn(UserEntity);
		Mockito.when(userService.findById(Mockito.anyLong())).thenReturn(UserEntity);
		Mockito.when(adminService.getUserInvitationsByUserId(Mockito.anyLong())).thenReturn(gbData);
		MvcResult result = mockMvc.perform(get("/admin/review/invites").param("userId", "1")).andDo(print())
				.andExpect(view().name("admin/validate-invites")).andReturn();
		Assert.assertNotNull(result);
	}

	@Test
	@WithMockUser("gg00483532@techmahindra.com")
	public void displayUserListsTest() throws Exception {
		UserEntity UserEntity = this.sampleUserEntity();
		List<UserEntity> userList = this.sampleUserEntityList();
		Mockito.when(userService.getAdminUserByEmail(Mockito.anyString())).thenReturn(UserEntity);
		Mockito.when(adminService.getAllUsers()).thenReturn(userList);
		MvcResult result = mockMvc.perform(get("/admin/users")).andDo(print())
				.andExpect(view().name("admin/user-details")).andReturn();
		Assert.assertNotNull(result);
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

	public List<UserEntity> sampleUserEntityList() {
		List<UserEntity> userList = new ArrayList<UserEntity>();
		UserEntity user = new UserEntity();
		user.setId(1);
		user.setName("Gaurav");
		user.setEmail("gg00483532@techmahindra.com");
		user.setAdmin(true);
		user.setGsm("9916386581");
		user.setPassword("Test@1234");
		user.setCreateDateTime(new Date());
		userList.add(user);
		return userList;
	}

	public List<UserInvitation> sampleUserInvitation() {
		List<UserInvitation> userInviteList = new ArrayList<UserInvitation>();
		UserInvitation userInvite = new UserInvitation();
		userInvite.setInvite_id(1);
		userInvite.setMessage("This is test. Please ignore");
		userInvite.setEmail("gg00483532@techmahindra.com");
		userInvite.setName("Gaurav");
		userInvite.setUid(1);
		userInvite.setGsm("9876543210");
		userInvite.setIs_approved(true);
		userInvite.setInvite_create_dt(new Date());
		userInviteList.add(userInvite);
		return userInviteList;
	}

	public InvitationEntity sampleInvitationEntity() {
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
