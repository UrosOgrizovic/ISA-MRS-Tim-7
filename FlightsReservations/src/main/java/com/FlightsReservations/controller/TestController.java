package com.FlightsReservations.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
	
	
	@PreAuthorize("hasRole('USER')")
	@GetMapping(value = "/user")
	public String user() {
		return "User level auth is correct!";
	}
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping(value = "/admin")
	public String admin() {
		return "Admin level auth is correct!";
	}
 	
}
