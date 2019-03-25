package com.FlightsReservations.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.FlightsReservations.domain.User;
import com.FlightsReservations.service.UserService;

@RestController
@RequestMapping("/users")
@CrossOrigin("*")
public class UserController {
	
	@Autowired 
	private UserService service;
	
	@RequestMapping(
			value = "/getAll", 
			method = RequestMethod.GET) 
	public Collection<User> getAll()  {
		return service.findAll();
	}
	
	@RequestMapping(
			value = "/update",
			method = RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> update(@RequestBody User user) {
		if (user.getFirstName().trim().isEmpty() ||
				user.getLastName().trim().isEmpty() || 
				user.getPhone().trim().isEmpty() || 
				user.getAddress().trim().isEmpty() )	
			return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
		return new ResponseEntity<User>(service.update(user), HttpStatus.OK);
	}
}            
