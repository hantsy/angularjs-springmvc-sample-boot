package com.hantsylabs.restexample.springmvc.api;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hantsylabs.restexample.springmvc.Constants;
import com.hantsylabs.restexample.springmvc.api.ResponseMessage.Type;
import com.hantsylabs.restexample.springmvc.model.User;
import com.hantsylabs.restexample.springmvc.repository.UserRepository;
import com.hantsylabs.restexample.springmvc.security.SecurityUtil;

@RestController
@RequestMapping(value = Constants.URI_API)
public class UserController {
	private static final Logger log = LoggerFactory
			.getLogger(UserController.class);

	@Inject
	private PasswordEncoder passwordEncoder;

	@Inject
	private UserRepository userRepository;

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	@ResponseBody
	public User currentUser() {
		log.debug("get current user info");

		User user = SecurityUtil.currentUser();

		log.debug("current user value @" + user);

		return user;
	}

	@RequestMapping(value = "/user/changepw", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<ResponseMessage> changePassword(
			@RequestBody PasswordForm fm) {
		log.debug("change password of user@" + fm);
		User user = SecurityUtil.currentUser();

		if (!passwordEncoder.matches(fm.getOldPassword(), user.getPassword())) {
			return new ResponseEntity<ResponseMessage>(new ResponseMessage(
					Type.danger, "currentPasswordIsWrong"),
					HttpStatus.BAD_REQUEST);
		}

		user.setPassword(passwordEncoder.encode(fm.getNewPassword()));
		userRepository.save(user);

		return new ResponseEntity<ResponseMessage>(new ResponseMessage(
				Type.success, "passwordUpdated"), HttpStatus.OK);
	}

	@RequestMapping(value = "/user/updateProfile", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<ResponseMessage> updateProfile(@RequestBody User u) {
		log.debug("update user profile data @" + u);

		User user = SecurityUtil.currentUser();
		BeanUtils.copyProperties(u, user, "username", "password", "role");
		userRepository.save(user);

		return new ResponseEntity<ResponseMessage>(new ResponseMessage(
				Type.success, "profileUpdated"), HttpStatus.OK);
	}

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	@ResponseBody
	public List<User> allUsers() {
		log.debug("fetch all users...");

		List<User> users = userRepository.findAll();

		log.debug("count of users @" + users.size());

		return users;
	}
	
	@RequestMapping(value = "/users", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResponseMessage> saveUser(@RequestBody User u) {
		log.debug("save user data @" + u);
		u.setPassword(passwordEncoder.encode(u.getPassword()));
		userRepository.save(u);

		return new ResponseEntity<ResponseMessage>(new ResponseMessage(
				Type.success, "userSaved"), HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<ResponseMessage> deleteUser(@PathVariable("id") Long id) {
		log.debug("delete user data @" + id);
		userRepository.delete(id);
		
		return new ResponseEntity<ResponseMessage>(new ResponseMessage(
				Type.success, "userDeleted"), HttpStatus.OK);
	}

}
