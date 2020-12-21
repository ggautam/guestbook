package com.guestbook.task.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.guestbook.task.dto.GenericResponse;
import com.guestbook.task.dto.Invitation;
import com.guestbook.task.dto.User;
import com.guestbook.task.entity.InvitationEntity;
import com.guestbook.task.entity.UserEntity;
import com.guestbook.task.service.InvitationService;
import com.guestbook.task.service.UserService;
import com.guestbook.task.dto.GenericResponse.Status;

/**
 * User and Guest journey. 
 * @author gaurav
 *
 */
@Controller
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private InvitationService invitationService;

	/**
	 * Guest or logged-in user landing Page
	 * 
	 * @param errmsg 	Page access warning
	 * @return 			View for welcome
	 */
	@RequestMapping(value = { "/", "/welcome" }, method = RequestMethod.GET)
	public ModelAndView welcomePage(@RequestParam(required = false) String errmsg) {
		logger.debug("UserController|welcomePage|In");
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserEntity user = userService.findByEmail(auth.getName());
		boolean isUserLoggedIn = false;
		boolean isAdmin = false;
		String userName = "";
		String homeUrl = "/";
		if (user != null) {
			isUserLoggedIn = true;
			isAdmin = user.isAdmin();
			userName = "Hello " + user.getName() + "!";
			homeUrl = "/user/home";
		}
		if (StringUtils.isNotBlank(errmsg)) {
			logger.debug("UserController|welcomePage|errmsg: {}", errmsg);
			modelAndView.addObject("errmsg", true);
		} else {
			modelAndView.addObject("errmsg", false);
		}
		modelAndView.addObject("userName", userName);
		modelAndView.addObject("isUserLoggedIn", isUserLoggedIn);
		modelAndView.addObject("isAdmin", isAdmin);
		modelAndView.addObject("homeUrl", homeUrl);
		modelAndView.addObject("currPage", "welcome");
		modelAndView.setViewName("welcome");
		logger.debug("UserController|welcomePage|Out");
		return modelAndView;
	}

	/**
	 * Site about us page
	 * 
	 * @return View for about
	 */
	@RequestMapping(value = "/about", method = RequestMethod.GET)
	public ModelAndView aboutUsPage() {
		logger.debug("UserController|aboutUsPage|In");
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		boolean isAdmin = false;
		boolean isUserLoggedIn = false;
		String userName = "";
		String homeUrl = "/";
		if(auth != null && StringUtils.isNotBlank(auth.getName())) {
			UserEntity user = userService.findByEmail(auth.getName());
			if (user != null) {
				isUserLoggedIn = true;
				isAdmin = user.isAdmin();
				userName = "Hello " + user.getName() + "!";
				homeUrl = "/user/home";
			}
		}
		modelAndView.addObject("userName", userName);
		modelAndView.addObject("isUserLoggedIn", isUserLoggedIn);
		modelAndView.addObject("isAdmin", isAdmin);
		modelAndView.addObject("homeUrl", homeUrl);
		modelAndView.addObject("currPage", "about");
		modelAndView.setViewName("about");
		logger.debug("UserController|aboutUsPage|Out");
		return modelAndView;
	}

	/**
	 * Site error page
	 * 
	 * @return View for error
	 */
	@RequestMapping(value = "/error", method = RequestMethod.GET)
	public ModelAndView errorPage() {
		logger.debug("UserController|errorPage|In");
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserEntity user = userService.findByEmail(auth.getName());
		boolean isAdmin = false;
		boolean isUserLoggedIn = false;
		String userName = "";
		String homeUrl = "/";
		if (user != null) {
			isUserLoggedIn = true;
			isAdmin = user.isAdmin();
			userName = "Hello " + user.getName() + "!";
			homeUrl = "/user/home";
		}
		modelAndView.addObject("userName", userName);
		modelAndView.addObject("isUserLoggedIn", isUserLoggedIn);
		modelAndView.addObject("isAdmin", isAdmin);
		modelAndView.addObject("homeUrl", homeUrl);
		modelAndView.addObject("currPage", "error");
		modelAndView.setViewName("error");
		logger.debug("UserController|aboutUsPage|Out");
		return modelAndView;
	}

	/**
	 * Login page
	 * 
	 * @return 	View for login
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView loginPage() {
		logger.debug("UserController|loginPage|In");
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserEntity user = userService.findByEmail(auth.getName());
		if (user != null) {
			return new ModelAndView("redirect:/user/home");
		}
		modelAndView.addObject("userName", "");
		modelAndView.addObject("isUserLoggedIn", false);
		modelAndView.addObject("isAdmin", false);
		modelAndView.addObject("homeUrl", "/");
		modelAndView.addObject("currPage", "login");
		modelAndView.setViewName("login");
		logger.debug("UserController|loginPage|Out");
		return modelAndView;
	}

	/**
	 * Registration Page
	 * 
	 * @return 	View for registration
	 */
	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public ModelAndView registrationPage() {
		logger.debug("UserController|registrationPage|In");
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserEntity checkUser = userService.findByEmail(auth.getName());
		if (checkUser != null) {
			return new ModelAndView("redirect:/user/home");
		}
		UserEntity user = new UserEntity();
		modelAndView.addObject("user", user);
		modelAndView.addObject("userName", "");
		modelAndView.addObject("isUserLoggedIn", false);
		modelAndView.addObject("isAdmin", false);
		modelAndView.addObject("homeUrl", "/");
		modelAndView.addObject("currPage", "registration");
		modelAndView.setViewName("registration");
		logger.debug("UserController|registrationPage|Out");
		return modelAndView;
	}

	/**
	 * Register a user to site
	 * 
	 * @param user          	User details to do registration
	 * @param bindingResult 	Holds the result of a validation and binding and contains errors that may have occurred
	 * @return 					Registration form for submission
	 */
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
		logger.debug("UserController|createNewUser|In user: {}", user);
		ModelAndView modelAndView = new ModelAndView();
		UserEntity userEmailExists = userService.findByEmail(user.getEmail());
		if (userEmailExists != null) {
			bindingResult.rejectValue("email", "error.user",
					"There is already a user registered with the email provided");
		}
		UserEntity userGsmExists = userService.findByGsm(user.getGsm());
		if (userGsmExists != null) {
			bindingResult.rejectValue("gsm", "error.user",
					"There is already a user registered with the phone number provided");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("registration");
		} else {
			userService.save(user);
			modelAndView.addObject("successMessage", "User has been registered successfully");
			modelAndView.addObject("user", new User());
			modelAndView.setViewName("registration");
		}
		modelAndView.addObject("userName", "");
		modelAndView.addObject("isUserLoggedIn", false);
		modelAndView.addObject("isAdmin", false);
		modelAndView.addObject("homeUrl", "/");
		modelAndView.addObject("currPage", "registration");
		logger.debug("UserController|createNewUser|Out");
		return modelAndView;
	}
	
	/**
	 * Logged-in user home page
	 * @return	View for home
	 */
	@RequestMapping(value = "/user/home", method = RequestMethod.GET)
	public ModelAndView userHomePage(@RequestParam(required = false) String errmsg) {
		logger.debug("UserController|userHomePage|In");
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserEntity user = userService.findByEmail(auth.getName());
		List<InvitationEntity> invitationLists = new ArrayList<InvitationEntity>();
		boolean isUserLoggedIn = false;
		String homeUrl = "/";
		if (user == null) {
			return new ModelAndView("redirect:/welcome?errmsg=1");
		} else {
			isUserLoggedIn = true;
			homeUrl = "/user/home";
		}
		invitationLists = userService.getInvitationByUserId(user.getId());
		int invitationCnt = 0;
		if (!CollectionUtils.isEmpty(invitationLists)) {
			invitationCnt = invitationLists.size();
		}
		if (StringUtils.isNotBlank(errmsg)) {
			logger.debug("UserController|welcomePage|errmsg: {}", errmsg);
			modelAndView.addObject("errmsg", true);
		} else {
			modelAndView.addObject("errmsg", false);
		}
		modelAndView.addObject("userName", "Hello " + user.getName() + "!");
		modelAndView.addObject("userId", user.getId());
		modelAndView.addObject("isAdmin", user.isAdmin);
		modelAndView.addObject("isUserLoggedIn", isUserLoggedIn);
		modelAndView.addObject("homeUrl", homeUrl);
		modelAndView.addObject("invitationCount", invitationCnt);
		modelAndView.addObject("invitationLists", invitationLists);
		modelAndView.addObject("currPage", "welcome");
		modelAndView.setViewName("user/home");
		logger.debug("UserController|userHomePage|Out");
		return modelAndView;
	}

	/**
	 * Page to create an invitation
	 * @return	View for Create an invitation
	 */
	@RequestMapping(value = "/user/create/invite", method = RequestMethod.GET)
	public ModelAndView createInvitePage() {
		logger.debug("UserController|createInvitePage|In");
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserEntity user = userService.findByEmail(auth.getName());
		boolean isUserLoggedIn = false;
		String homeUrl = "/";
		if (user == null) {
			return new ModelAndView("redirect:/welcome?errmsg=1");
		} else {
			isUserLoggedIn = true;
			homeUrl = "/user/home";
		}
		modelAndView.addObject("isUserLoggedIn", isUserLoggedIn);
		modelAndView.addObject("homeUrl", homeUrl);
		modelAndView.addObject("userName", "Hello " + user.getName() + "!");
		modelAndView.addObject("userId", user.getId());
		modelAndView.addObject("isAdmin", user.isAdmin);
		modelAndView.addObject("currPage", "createInvite");
		modelAndView.setViewName("user/create-invite");
		logger.debug("UserController|createInvitePage|Out");
		return modelAndView;
	}

	/**
	 * Create an entry for user
	 * @param invitation	Invite details to create
	 * @return				Invitation form for submission
	 */
	@RequestMapping(value = "/user/create/invite", method = RequestMethod.POST)
	public ModelAndView saveCreateInvite(Invitation invitation) {
		logger.debug("UserController|saveCreateInvite|In");
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserEntity user = userService.findByEmail(auth.getName());
		modelAndView.addObject("isUserLoggedIn", true);
		modelAndView.addObject("homeUrl", "/user/home");
		modelAndView.addObject("userName", "Hello " + user.getName() + "!");
		modelAndView.addObject("userId", user.getId());
		modelAndView.addObject("isAdmin", user.isAdmin);
		modelAndView.setViewName("user/create-invite");
		boolean isValidInvite = false;
		if (invitation.getFile() != null && !invitation.getFile().isEmpty()) {
			isValidInvite = true;
		}

		if (!isValidInvite && StringUtils.isNotBlank(invitation.getMessage())) {
			isValidInvite = true;
		}

		if (!isValidInvite) {
			logger.error("UserController|saveCreateInvite|Invalid input");
			modelAndView.addObject("errorMessage", "Please provide valid Invitation Card or message");
			modelAndView.addObject("isError", true);
		} else {
			GenericResponse inviteResponse = userService.saveInvite(invitation);
			if (inviteResponse.getStatus() == Status.ERROR) {
				modelAndView.addObject("errorMessage", inviteResponse.getMessage());
			} else {
				modelAndView.addObject("successMessage", inviteResponse.getMessage());
			}
			modelAndView.addObject("invitation", new Invitation());
			logger.debug("UserController|saveCreateInvite|inviteResponse: {}", inviteResponse);
		}
		modelAndView.addObject("currPage", "createInvite");
		modelAndView.setViewName("user/create-invite");
		logger.debug("UserController|saveCreateInvite|Out");
		return modelAndView;
	}

	/**
	 * Page to modify user invitation
	 * @return	View for update an entry
	 */
	@RequestMapping(value = "/user/modify/invite/{inviteId}", method = RequestMethod.GET)
	public ModelAndView modifyInvitePage(@PathVariable("inviteId") String inviteId) {
		logger.debug("UserController|modifyInvitePage|In");
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserEntity user = userService.findByEmail(auth.getName());
		boolean isUserLoggedIn = false;
		String homeUrl = "/";
		if (user == null || StringUtils.isBlank(inviteId)) {
			return new ModelAndView("redirect:/welcome?errmsg=1");
		} else {
			isUserLoggedIn = true;
			homeUrl = "/user/home";
		}
		InvitationEntity entry = invitationService.getInviteBasedOnUser(inviteId, user.getId());
		Invitation invitation = new Invitation();
		if (entry == null) {
			return new ModelAndView("redirect:/user/home?errmsg=2");
		} else {
			invitation.setInviteId(Long.toString(entry.getInviteId()));
			invitation.setUserId(Long.toString(entry.getUserEntity().getId()));
			invitation.setMessage(entry.getMessage());			
		}
		modelAndView.addObject("isUserLoggedIn", isUserLoggedIn);
		modelAndView.addObject("homeUrl", homeUrl);
		modelAndView.addObject("userName", "Hello " + user.getName() + "!");
		modelAndView.addObject("userId", user.getId());
		modelAndView.addObject("isAdmin", user.isAdmin);
		modelAndView.addObject("invitation", invitation);
		modelAndView.addObject("invite", entry);
		modelAndView.addObject("currPage", "modifyInvite");
		modelAndView.setViewName("user/modify-invite");
		return modelAndView;
	}

	/**
	 * To modify user invitation
	 * @param invitation	Invite details to update
	 * @return				Invitation form for submission
	 */
	@RequestMapping(value = "/user/modify/invite/{inviteId}", method = RequestMethod.POST)
	public ModelAndView updateInvite(@PathVariable("inviteId") String inviteId, Invitation invitation) {
		logger.debug("UserController|updateInvite|In");
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserEntity user = userService.findByEmail(auth.getName());
		modelAndView.addObject("isUserLoggedIn", true);
		modelAndView.addObject("homeUrl", "/user/home");
		modelAndView.addObject("userName", "Hello " + user.getName() + "!");
		modelAndView.addObject("userId", user.getId());
		modelAndView.addObject("isAdmin", user.isAdmin);
		boolean isValidInvite = false;
		if (invitation.getFile() != null && !invitation.getFile().isEmpty()) {
			isValidInvite = true;
		}

		if (!isValidInvite && StringUtils.isNotBlank(invitation.getMessage())) {
			isValidInvite = true;
		}

		if (!isValidInvite) {
			logger.error("UserController|updateInvite|Invalid input");
			modelAndView.addObject("errorMessage", "Please provide valid Invitation Card or message");
			modelAndView.addObject("isError", true);
		} else {
			GenericResponse inviteResponse = userService.saveInvite(invitation);
			if (inviteResponse.getStatus() == Status.ERROR) {
				modelAndView.addObject("errorMessage", inviteResponse.getMessage());
			} else {
				modelAndView.addObject("successMessage", inviteResponse.getMessage());
			}
			InvitationEntity entry = invitationService.getInviteBasedOnUser(inviteId, user.getId());
			modelAndView.addObject("invitation", invitation);
			modelAndView.addObject("invite", entry);
			logger.debug("UserController|updateInvite|inviteResponse: {}", inviteResponse);
		}
		modelAndView.addObject("currPage", "modifyInvite");
		modelAndView.setViewName("user/modify-invite");
		logger.debug("UserController|updateInvite|Out");
		return modelAndView;
	}

}
