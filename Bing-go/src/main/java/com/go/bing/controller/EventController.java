package com.go.bing.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.go.bing.model.Event;
import com.go.bing.repository.EventRepository;

@EnableAutoConfiguration
@ComponentScan
@RestController("/event")
public class EventController {

	@Autowired
	private EventRepository eventRepo;
	
	@RequestMapping(path="/getAllEvents", method=RequestMethod.GET, consumes="application/json")
	public List<Event> getAllEvents() {
		List<Event> events = eventRepo.findAll();
		
		if(events == null)
			events = new ArrayList<>();

		setEventDetails(events);
		
		return events;
	}
	
	private void setEventDetails(List<Event> events) {
		Date today = new Date();
		for(Event e : events) {
			//Check if ongoing
			int compareStart = e.getStartDate().compareTo(today);
			int compareEnd = e.getEndDate().compareTo(today);
			
			if(compareStart <= 0 && compareEnd >= 0) {
				e.setOngoing(true);
			}
			else if(compareEnd < 0) {
				e.setPast(true);
			}
			else if(compareStart > 0) {
				e.setUpcoming(true);
			}
		}
	}

	@RequestMapping(path="/addEvent", method=RequestMethod.POST, consumes="application/json")
	public List<Event> addEvent(@RequestBody Event event) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		Date sDate = null;
		Date eDate = null;
		try {
			sDate = dateFormat.parse(event.getStartDateString() + " " + event.getStartHour() + ":" + (event.getStartMin() == 0 ? "00" : event.getStartMin()));
			eDate = dateFormat.parse(event.getEndDateString() + " " + event.getEndHour() + ":" + (event.getEndMin() == 0 ? "00" : event.getEndMin()));
		} catch (ParseException e) {
			throw new RuntimeException("Could not parse date");
		}

		event.setStartDate(sDate);
		event.setEndDate(eDate);
		
		System.out.println(sDate + " " + eDate);
		eventRepo.save(event);

		List<Event> events = eventRepo.findAll();
		setEventDetails(events);
		
		return events;
	}

}
