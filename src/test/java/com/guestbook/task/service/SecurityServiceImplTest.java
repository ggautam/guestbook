package com.guestbook.task.service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import com.guestbook.task.service.impl.SecurityServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { SecurityServiceImpl.class, })
public class SecurityServiceImplTest {

	@Autowired
	SecurityServiceImpl securityServiceImpl;

	@MockBean
	AuthenticationManager authenticationManager;

	@MockBean
	UserDetailsService userDetailsService;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	@WithMockUser("gautam.gaurav@hotmail.com")
	public void findLoggedInUsernameTest() throws Exception {
		String response = securityServiceImpl.findLoggedInUsername();
		Assert.assertNotNull(response);
	}

	@Test
	@Ignore
	public void autoLoginTest() throws Exception {
		securityServiceImpl.autoLogin("gautam.gaurav@hotmail.com", "Test@1234");
	}

	@After
	public void tearDown() throws Exception {
	}

}
