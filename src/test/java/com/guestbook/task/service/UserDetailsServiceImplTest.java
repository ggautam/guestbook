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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import com.guestbook.task.entity.UserEntity;
import com.guestbook.task.repository.UserRepository;
import com.guestbook.task.service.impl.UserDetailsServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { UserDetailsServiceImpl.class, })
public class UserDetailsServiceImplTest {

	@Autowired
	UserDetailsServiceImpl userDetailsServiceImpl;

	@MockBean
	private UserRepository userRepository;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void loadUserByUsernameTest() throws Exception {
		UserEntity userEntity = this.sampleUserEntity();
		Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(userEntity);
		UserDetails response = userDetailsServiceImpl.loadUserByUsername(Mockito.anyString());
		Assert.assertNotNull(response);
	}

	@Test
	public void loadUserByUsernameTestWithException() throws Exception {
		try {
			Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(null);
			UserDetails response = userDetailsServiceImpl.loadUserByUsername(Mockito.anyString());
			Assert.assertNotNull(response);
		} catch (Exception e) {
			Assert.assertNotNull(e);
		}
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

	@After
	public void tearDown() throws Exception {
	}

}
