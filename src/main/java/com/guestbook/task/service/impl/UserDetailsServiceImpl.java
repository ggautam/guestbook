package com.guestbook.task.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guestbook.task.constants.GuestBookConstants;
import com.guestbook.task.entity.UserEntity;
import com.guestbook.task.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

	/**
	 * Get UserDetails of loggedIn user by email with role access
	 * @param			user email to create UserDetails
	 * @return			UserDetails
	 */
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		logger.debug("UserDetailsServiceImpl|loadUserByUsername|In email: {}", email);
		UserEntity user = userRepository.findByEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid credentials");
		}
		String role = GuestBookConstants.NON_ADMIN_TEXT;
		if (user.isAdmin) {
			role = GuestBookConstants.ADMIN_TEXT;
		}
		Set<GrantedAuthority> grantedAuth = new HashSet<>();
		grantedAuth.add(new SimpleGrantedAuthority(role));
		logger.debug("UserDetailsServiceImpl|loadUserByUsername|Out");
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), grantedAuth);
	}

}
