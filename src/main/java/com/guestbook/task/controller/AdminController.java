package com.guestbook.task.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.guestbook.task.dto.UserInvitation;
import com.guestbook.task.entity.UserEntity;
import com.guestbook.task.service.AdminService;
import com.guestbook.task.service.UserService;

/**
 * Admin Role based journey
 * @author gaurav
 *
 */
@Controller
public class AdminController {

	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private AdminService adminService;

	/**
	 * View all user invitation to confirm
	 * @param userId	Filter based on user 
	 * @return			View for confirm invite
	 */
	@RequestMapping(value = { "/admin/review/invites" }, method = RequestMethod.GET)
	public ModelAndView confirmInvitation(@RequestParam(required = false) String userId) {
		logger.debug("AdminController|confirmInvitation|In");
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserEntity user = userService.getAdminUserByEmail(auth.getName());
		if (user == null) {
			logger.debug("AdminController|displayUserLists|Invalid Access. Redirected to Home Page");
			return new ModelAndView("redirect:/user/home");
		}
		List<UserInvitation> gbData = new ArrayList<UserInvitation>();
		if (StringUtils.isNotBlank(userId)) {
			UserEntity selectedUser = userService.findById(Long.parseLong(userId));
			gbData = adminService.getUserInvitationsByUserId(selectedUser.getId());
		} else {
			gbData = adminService.getUserInvitations();
		}
		modelAndView.addObject("userName", "Hello " + user.getName() + "!");
		modelAndView.addObject("userId", user.getId());
		modelAndView.addObject("isAdmin", user.isAdmin);
		modelAndView.addObject("gbData", gbData);
		modelAndView.setViewName("admin/validate-invites");
		logger.debug("AdminController|confirmInvitation|Out");
		return modelAndView;
	}

	/**
	 * Display all user registered to site
	 * @return		View for registered user
	 */
	@RequestMapping(value = { "/admin/users" }, method = RequestMethod.GET)
	public ModelAndView displayUserLists() {
		logger.debug("AdminController|displayUserLists|In");
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserEntity user = userService.getAdminUserByEmail(auth.getName());
		if (user == null) {
			logger.debug("AdminController|displayUserLists|Invalid User. Redirected to Home Page");
			return new ModelAndView("redirect:/user/home");
		}
		List<UserEntity> userData = adminService.getAllUsers();
		modelAndView.addObject("userName", "Hello " + user.getName() + "!");
		modelAndView.addObject("userId", user.getId());
		modelAndView.addObject("isAdmin", user.isAdmin);
		modelAndView.addObject("userData", userData);
		modelAndView.setViewName("admin/user-details");
		return modelAndView;
	}
}
