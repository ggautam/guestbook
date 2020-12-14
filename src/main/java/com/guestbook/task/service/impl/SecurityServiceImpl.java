package com.guestbook.task.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.guestbook.task.constants.GuestBookConstants;
import com.guestbook.task.service.SecurityService;

@Service
public class SecurityServiceImpl implements SecurityService {

	private static final Logger logger = LoggerFactory.getLogger(SecurityServiceImpl.class);

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	public String findLoggedInUsername() {
		logger.debug("SecurityServiceImpl|findLoggedInUsername|In");
		Object userDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();
		String user = GuestBookConstants.BLANK;
		if (userDetails instanceof UserDetails) {
			logger.debug("SecurityServiceImpl|findLoggedInUsername|Out");
			user = ((UserDetails) userDetails).getUsername();
		}
		logger.debug("SecurityServiceImpl|findLoggedInUsername|Out user: {}", user);
		return user;
	}

	@Override
	public void autoLogin(String username, String password) {
		logger.debug("SecurityServiceImpl|autoLogin|IN username: {}, password: {}", username, password);
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				userDetails, password, userDetails.getAuthorities());

		authenticationManager.authenticate(usernamePasswordAuthenticationToken);

		if (usernamePasswordAuthenticationToken.isAuthenticated()) {
			SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			logger.debug("Auto login {} successfully!", username);
		}
		logger.debug("SecurityServiceImpl|autoLogin|Out");
	}

}
