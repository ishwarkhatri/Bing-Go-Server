package com.go.bing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.go.bing.model.Event;
import com.go.bing.repository.EventRepository;

@ComponentScan
@RestController("/event")
public class EventController {

	@Autowired
	private EventRepository eventRepo;
	
	@RequestMapping(path="/addEvent", method=RequestMethod.POST, consumes="application/json")
	public String addEvent(@RequestBody Event event) {
		eventRepo.save(event);

		return "SUCCESS";
	}

}
