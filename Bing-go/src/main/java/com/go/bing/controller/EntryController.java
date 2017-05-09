package com.go.bing.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.go.bing.model.Community;
import com.go.bing.repository.CommunityRepository;

@EnableAutoConfiguration
@ComponentScan
@RestController("/entry")
public class EntryController {

	@Autowired
	private CommunityRepository communityRepo;
	
	@RequestMapping(path="/bingEntry", method=RequestMethod.GET, produces="application/json")
	public List<Community> getCommunities() {
		List<Community> allCom = communityRepo.findAll();
		return allCom;
	}
	
	@RequestMapping(path="/addNewCommunity", method=RequestMethod.POST, produces="application/json")
	public void addNewCommunity(@RequestBody Community community) {
		communityRepo.save(new Community(community.getCommunityName(), community.getDescription()));
	}
}
