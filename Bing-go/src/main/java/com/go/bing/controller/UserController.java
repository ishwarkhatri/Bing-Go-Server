package com.go.bing.controller;




import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.go.bing.model.User;

@ComponentScan
@RestController
public class Controller {

	@RequestMapping(path="/", method=RequestMethod.GET)
	public User getUser() {
		User user = new User();
		user.setFirstName("Ishwar");
		user.setLastName("Khatri");
		user.setEmailId("ikhatri1@binghamton.edu");
		user.setUserId("ikhatri1");
		user.setPassword("password");
		user.setCommunity("IGSO");
		return user;
	}
}
