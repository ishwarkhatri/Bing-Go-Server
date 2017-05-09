package com.go.bing.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.go.bing.common.Utility;
import com.go.bing.model.Community;
import com.go.bing.model.USER_STATUS;
import com.go.bing.model.User;
import com.go.bing.repository.CommunityRepository;
import com.go.bing.repository.UserRepository;

@EnableAutoConfiguration
@ComponentScan
@RestController("/user")
public class UserController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	CommunityRepository communityRepository;
	
	/**
	 * A new user when accesses the application for first time,
	 * has to send the registration request for approval.
	 * This record is then saved in database for reviewing by Admin.
	 * Admin can later approve/archive this request after reviewing.
	 * @param user New user details
	 * @return success or failure
	 */
	@ResponseBody
	@RequestMapping(path="/register", method=RequestMethod.POST, consumes="application/json", produces="text/plain")
	public String registerUser(@RequestBody User user) {

		String response = checkForValidation(user);

		if(response.equalsIgnoreCase("SUCCESS")) {
			//Save entry in databse as REGISTERED user
			user.setStatus(USER_STATUS.REGISTERED);
			userRepository.save(user);
			
			//Send activation link in email
			Utility utility = new Utility();
			utility.sendMail(user);
			
		}

		return response;
	}

	@CrossOrigin
	@ResponseBody
	@RequestMapping(path="/loginuser", method=RequestMethod.POST, consumes="application/json", produces="text/plain")
	public String loginUser(@RequestBody User user) {
		String response = "Invalid login credentials";
		if(validateLogin(user)) {
			user = userRepository.findByUserId(user.getUserId()).get(0);
			response = "SUCCESS";

			if(USER_STATUS.REGISTERED.equals(user.getStatus())) {
				response = "The account is not verified yet.\r\nPlease activate your account by following the link sent to you";
			}
			else if(USER_STATUS.ARCHIVED.equals(user.getStatus())){
				response = "You account is deactivated. Please contact the admin.";
			}
		}

		return response;
	}
	
	private boolean validateLogin(User user) {
		List<User> users = userRepository.findByUserId(user.getUserId());
		if(!users.isEmpty() && users.get(0).getPassword().equals(user.getPassword())) {
			return true;
		}
		return false;
	}

	private String checkForValidation(User user) {
		if(!isAllFieldsPresent(user)) {
			return "Mandatory fields missing";
		}
		
		List<User> temp = userRepository.findByUserId(user.getUserId());
		String returnString = "SUCCESS";

		//Check for User Id existence
		if(temp != null && !temp.isEmpty()) {
			returnString = "User Id '" + user.getUserId() + "' is already registered";
		}
		else { //Check for email id existence
			List<User> temp1 = userRepository.findByEmailId(user.getEmailId());
			
			if(temp1 != null && !temp1.isEmpty()) {
				returnString = "Email Id '" + user.getEmailId() + "' is already in use";
			}
			else { //Check if new community entered
				if(user.getCommunity().equalsIgnoreCase("Other")) {
					List<Community> community = communityRepository.findByCommunityName(user.getNewCommunity());
					
					//Check if community exists
					if(community != null && !community.isEmpty()) {
						returnString = "The entered community '" + user.getNewCommunity() + "' already exists";
					}
					else { //Create new community
						Community newCommunity = new Community(user.getNewCommunity(), user.getNewCommunityDescription());
						newCommunity.getCommunityUsers().add(user);
						communityRepository.save(newCommunity);
					}
				}
				else { //Add the user to community
					List<Community> community = communityRepository.findByCommunityName(user.getCommunity());
					community.get(0).getCommunityUsers().add(user);
					communityRepository.save(community.get(0));
				}
			}
		}
		return returnString;
	}

	private boolean isAllFieldsPresent(User user) {
		if("".equals(user.getFirstName()) || "".equals(user.getLastName()) || "".equals(user.getEmailId())
				|| "".equals(user.getUserId()) || "".equals(user.getPassword()) || "".equals(user.getCommunity())
				|| ("Other".equalsIgnoreCase(user.getCommunity()) && ("".equals(user.getNewCommunity()) || "".equals(user.getNewCommunityDescription()))))
			return false;
				
		return true;
	}

	/**
	 * User approval method used by admin only
	 * @param userId userId of the newly registered user
	 * @return success or failure
	 */
	@RequestMapping(path="/approve/{userId}", method=RequestMethod.GET)
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
