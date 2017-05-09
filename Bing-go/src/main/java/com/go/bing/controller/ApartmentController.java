package com.go.bing.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.go.bing.model.Apartment;
import com.go.bing.model.User;
import com.go.bing.repository.ApartmentRepository;

@EnableAutoConfiguration
@RestController("/apartments")
public class ApartmentController {

	@Autowired
	private ApartmentRepository apartmentRepo;

	@ResponseBody
    @RequestMapping("/listings")
    public List<Apartment> listUploadedFiles() {
    	List<Apartment> listings = apartmentRepo.findAll();
        return listings;
    }
	
	@RequestMapping(path="/addnewlisting/{userId}", method=RequestMethod.POST, produces="application/json")
	public List<Apartment> addNewListing(@RequestBody Apartment apartment, @PathVariable("userId") User user) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date postedOn = new Date();
		
		apartment.setPostedOn(postedOn);
		apartment.setPostedOnString(dateFormat.format(postedOn));
		apartment.setPostedBy(user.getFirstName() + " " + user.getLastName());

		apartmentRepo.save(apartment);
		
		List<Apartment> listings = apartmentRepo.findAll();
        return listings;
	}
}
