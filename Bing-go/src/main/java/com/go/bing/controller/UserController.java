package com.go.bing.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.go.bing.model.USER_STATUS;
import com.go.bing.model.User;
import com.go.bing.repository.UserRepository;

@ComponentScan
@RestController("/user")
public class UserController {

	@Autowired
	UserRepository userRepository;

	/**
	 * A new user when accesses the application for first time,
	 * has to send the registration request for approval.
	 * This record is then saved in database for reviewing by Admin.
	 * Admin can later approve/archive this request after reviewing.
	 * @param user New user details
	 * @return success or failure
	 */
	@RequestMapping(path="/register", method=RequestMethod.PUT, consumes="application/json")
	public String registerUser(@Valid @RequestBody User user, BindingResult results) {
		if(results.hasErrors()) {
			return "FAILURE";
		} else {
			User temp = userRepository.findOne(user.getUserId());

			if(temp != null)
				return "User Id '" + user.getUserId() + "' is already registered";
			else {
				user.setStatus(USER_STATUS.REGISTERED);
				userRepository.save(user);
			}
		}
		return "SUCCESS";
	}

	/**
	 * User approval method used by admin only
	 * @param userId userId of the newly registered user
	 * @return success or failure
	 */
	@RequestMapping(path="/approve/{userId}", method=RequestMethod.PUT)
	public String approveUser(@PathVariable("userId") User user) {
		user.setStatus(USER_STATUS.ACTIVE);
		userRepository.save(user);
		return "SUCCESS";
	}

	/**
	 * User archival method used by admin only
	 * @param userId userId of user which is to be archived
	 * @return success or failure
	 */
	@RequestMapping(path="/archive/{userId}", method=RequestMethod.PUT)
	public String archiveUser(@PathVariable("userId") User user) {
		user.setStatus(USER_STATUS.ARCHIVED);
		userRepository.save(user);
		return "SUCCESS";
	}

	/**
	 * Method to fetch the list of users pending for approval (used by admin only)
	 * @return registered users list
	 */
	@RequestMapping(path="/registrationRequest", method=RequestMethod.GET)
	public List<User> getRegistrations() {
		return userRepository.findByStatus(USER_STATUS.REGISTERED);
	}

	/**
	 * Method to add new user (used by admin only) which gets approved instantly
	 * @param user new user details
	 * @return success or failure
	 */
	@RequestMapping(path="/adduser", method=RequestMethod.PUT)
	public String addNewUser(@RequestParam(name="user") User user) {
		userRepository.save(user);
		return "SUCCESS";
	}
}
