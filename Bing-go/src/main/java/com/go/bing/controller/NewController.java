package com.go.bing.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.go.bing.model.Event;
import com.go.bing.repository.EventRepository;

@EnableAutoConfiguration
@ComponentScan
@RestController("/newevent")
public class NewController {

	@Autowired
	private EventRepository eventRepo;
	
	@ResponseBody
	@RequestMapping("/getAllNewEvents")
	public List<Event> getAllNewEvents() {
		return eventRepo.findAll();
	}
}
