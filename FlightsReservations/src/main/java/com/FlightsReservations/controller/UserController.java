package com.FlightsReservations.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.FlightsReservations.service.UserService;

@RestController
public class UserController {
	
	@Autowired 
	private UserService service;
	
	// method for getting user details
	// method for updating user details
	
}
