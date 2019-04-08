package com.FlightsReservations.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.FlightsReservations.service.FlightService;

@RestController
@RequestMapping("/flights")
@CrossOrigin("*")
public class FlightsController {
	
	/*
	 * addFlight
	 * editFlight
	 * getFlight
	 */
	
	@Autowired
	private FlightService service;
	
	@GetMapping(
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<?> find() {
		return null;
	}
	
	
	
	
	
}
