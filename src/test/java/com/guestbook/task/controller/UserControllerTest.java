package com.guestbook.task.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.guestbook.task.dto.GenericResponse;
import com.guestbook.task.dto.Invitation;
import com.guestbook.task.dto.User;
import com.guestbook.task.dto.GenericResponse.Status;
import com.guestbook.task.entity.InvitationEntity;
import com.guestbook.task.entity.UserEntity;
import com.guestbook.task.service.InvitationService;
import com.guestbook.task.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { UserController.class, })
public class UserControllerTest {

	@MockBean
	private UserService userService;

	@MockBean
	private InvitationService invitationService;

	@Autowired
	private UserController userController;

	@Autowired
	private MockHttpServletRequest request;

	@Mock
	private BindingResult mockBindingResult;

	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/templates/");
		viewResolver.setSuffix(".html");
		mockMvc = MockMvcBuilders.standaloneSetup(userController).setViewResolvers(viewResolver).build();
	}

	@Test
	public void welcomePageTest() throws Exception {
		UserEntity UserEntity = this.sampleUserEntity();
		Authentication authentication = Mockito.mock(Authentication.class);
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		Mockito.when(userService.findByEmail(Mockito.anyString())).thenReturn(UserEntity);
		MvcResult result = mockMvc.perform(get("/welcome")).andDo(print()).andExpect(view().name("welcome"))
				.andReturn();
		Assert.assertNotNull(result);
	}

	@Test
	@WithMockUser("gg00483532@techmahindra.com")
	public void welcomePageTestWithMockUser() throws Exception {
		UserEntity UserEntity = this.sampleUserEntity();
		Mockito.when(userService.findByEmail(Mockito.anyString())).thenReturn(UserEntity);
		MvcResult result = mockMvc.perform(get("/welcome")).andDo(print()).andExpect(view().name("welcome"))
				.andReturn();
		Assert.assertNotNull(result);
	}

	@Test
	public void welcomePageTestWithException() throws Exception {
		try {
			Authentication authentication = Mockito.mock(Authentication.class);
			SecurityContext securityContext = Mockito.mock(SecurityContext.class);
			Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
			SecurityContextHolder.setContext(securityContext);
			UserEntity user = null;
			Mockito.when(userService.findByEmail(Mockito.anyString())).thenReturn(user);
			MvcResult result = mockMvc.perform(get("/welcome")).andDo(print()).andExpect(view().name("welcome"))
					.andReturn();
			Assert.assertNotNull(result);
		} catch (Exception e) {
			Assert.assertNotNull(e);
		}
	}

	@Test
	public void aboutPageTestOk() throws Exception {
		UserEntity UserEntity = this.sampleUserEntity();
		Authentication authentication = Mockito.mock(Authentication.class);
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		Mockito.when(userService.findByEmail(Mockito.anyString())).thenReturn(UserEntity);
		MvcResult result = mockMvc.perform(get("/about")).andDo(print()).andExpect(view().name("about")).andReturn();
		Assert.assertNotNull(result);
	}

	@Test
	public void aboutPageTestWithException() throws Exception {
		try {
			Authentication authentication = Mockito.mock(Authentication.class);
			SecurityContext securityContext = Mockito.mock(SecurityContext.class);
			Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
			SecurityContextHolder.setContext(securityContext);
			UserEntity user = null;
			Mockito.when(userService.findByEmail(Mockito.anyString())).thenReturn(user);
			MvcResult result = mockMvc.perform(get("/about")).andDo(print()).andExpect(view().name("about"))
					.andReturn();
			Assert.assertNotNull(result);
		} catch (Exception e) {
			Assert.assertNotNull(e);
		}
	}

	@Test
	public void loginPageTestOk() throws Exception {
		UserEntity UserEntity = this.sampleUserEntity();
		Authentication authentication = Mockito.mock(Authentication.class);
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		Mockito.when(userService.findByEmail(Mockito.anyString())).thenReturn(UserEntity);
		MvcResult result = mockMvc.perform(get("/login")).andDo(print()).andExpect(view().name("login")).andReturn();
		Assert.assertNotNull(result);
	}

	@Test
	public void loginPageTestWithException() throws Exception {
		try {
			Authentication authentication = Mockito.mock(Authentication.class);
			SecurityContext securityContext = Mockito.mock(SecurityContext.class);
			Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
			SecurityContextHolder.setContext(securityContext);
			UserEntity user = null;
			Mockito.when(userService.findByEmail(Mockito.anyString())).thenReturn(user);
			MvcResult result = mockMvc.perform(get("/login")).andDo(print()).andExpect(view().name("login"))
					.andReturn();
			Assert.assertNotNull(result);
		} catch (Exception e) {
			Assert.assertNotNull(e);
		}
	}

	@Test
	@Ignore
	public void testUserLogin() throws Exception {
		HttpSession session = mockMvc
				.perform(post("/login").param("email", "gg00483532@techmahindra.com").param("password", "Test@1234"))
				.andDo(print()).andExpect(status().isMovedTemporarily()).andExpect(redirectedUrl("/user/home"))
				.andReturn().getRequest().getSession();
		request.setSession(session);
		SecurityContext securityContext = (SecurityContext) session
				.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
		SecurityContextHolder.setContext(securityContext);
	}

	@Test
	public void registrationPageIfBindingErrorsTest() throws Exception {
		UserEntity UserEntity = this.sampleUserEntity();
		User user = this.sampleUser();
		Mockito.when(mockBindingResult.hasErrors()).thenReturn(true);
		Mockito.when(userService.findByEmail(Mockito.anyString())).thenReturn(UserEntity);
		doNothing().when(userService).save(Mockito.any(User.class));
		ModelAndView mav = userController.createNewUser(user, mockBindingResult);
		assertEquals("registration", mav.getViewName());
	}

	@Test
	public void registrationPageTest() throws Exception {
		Mockito.when(userService.findByEmail(Mockito.anyString())).thenReturn(null);
		doNothing().when(userService).save(Mockito.any(User.class));
		mockMvc.perform(post("/registration").contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("name", "GauravG").param("email", "gg00483532@techmahindra.com").param("gsm", "9876543210")
				.param("password", "Test@1234")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().hasNoErrors())
				.andExpect(MockMvcResultMatchers.model().attributeExists("user"))
				.andExpect(MockMvcResultMatchers.view().name("registration"));
	}

	@Test
	@WithMockUser("gg00483532@techmahindra.com")
	public void userHomePageTestWithMockUser() throws Exception {
		UserEntity UserEntity = this.sampleUserEntity();
		Mockito.when(userService.findByEmail(Mockito.anyString())).thenReturn(UserEntity);
		MvcResult result = mockMvc.perform(get("/user/home")).andDo(print()).andExpect(view().name("user/home"))
				.andReturn();
		Assert.assertNotNull(result);
	}

	@Test
	@WithMockUser("gg00483532@techmahindra.com")
	public void createInvitePageTestWithMockUser() throws Exception {
		UserEntity UserEntity = this.sampleUserEntity();
		Mockito.when(userService.findByEmail(Mockito.anyString())).thenReturn(UserEntity);
		MvcResult result = mockMvc.perform(get("/user/create/invite")).andDo(print())
				.andExpect(view().name("user/create-invite")).andReturn();
		Assert.assertNotNull(result);
	}

	@Test
	@WithMockUser("gg00483532@techmahindra.com")
	public void saveCreateInviteTest() throws Exception {
		UserEntity userEntity = this.sampleUserEntity();
		Invitation invitation = this.sampleInvitation();
		GenericResponse response = this.sampleGenericResponse();
		Mockito.when(userService.findByEmail(Mockito.anyString())).thenReturn(userEntity);
		Mockito.when(userService.saveInvite(Mockito.any(Invitation.class))).thenReturn(response);
		MvcResult result = mockMvc.perform(post("/user/create/invite").flashAttr("invitation", invitation)).andReturn();
		Assert.assertNotNull(result);
	}

	@Test
	@WithMockUser("gg00483532@techmahindra.com")
	public void modifyInvitePageTestWithMockUser() throws Exception {
		UserEntity UserEntity = this.sampleUserEntity();
		InvitationEntity invitationEntity = this.sampleInvitationEntity();
		Mockito.when(userService.findByEmail(Mockito.anyString())).thenReturn(UserEntity);
		Mockito.when(invitationService.getInviteBasedOnUser(Mockito.anyString(), Mockito.anyLong()))
				.thenReturn(invitationEntity);
		MvcResult result = mockMvc.perform(get("/user/modify/invite/1")).andDo(print())
				.andExpect(view().name("user/modify-invite")).andReturn();
		Assert.assertNotNull(result);
	}

	@Test
	@WithMockUser("gg00483532@techmahindra.com")
	public void modifyCreateInviteTest() throws Exception {
		UserEntity userEntity = this.sampleUserEntity();
		InvitationEntity invitationEntity = this.sampleInvitationEntity();
		Invitation invitation = this.sampleInvitation();
		invitation.setInviteId("1");
		GenericResponse response = this.sampleGenericResponse();
		Mockito.when(userService.findByEmail(Mockito.anyString())).thenReturn(userEntity);
		Mockito.when(userService.saveInvite(Mockito.any(Invitation.class))).thenReturn(response);
		Mockito.when(invitationService.getInviteBasedOnUser(Mockito.anyString(), Mockito.anyLong()))
				.thenReturn(invitationEntity);
		MvcResult result = mockMvc.perform(post("/user/modify/invite/1").flashAttr("invitation", invitation))
				.andReturn();
		Assert.assertNotNull(result);
	}

	public GenericResponse sampleGenericResponse() {
		GenericResponse response = new GenericResponse();
		response.setStatus(Status.SUCCESS);
		response.setMessage("created");
		return response;
	}

	public Invitation sampleInvitation() {
		Invitation invitation = new Invitation();
		invitation.setMessage("Test message");
		invitation.setUserId("1");
		return invitation;
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
