package com.FlightsReservations.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.FlightsReservations.domain.Airline;
import com.FlightsReservations.service.AirlineService;

@RestController
@RequestMapping("/airlines")
public class AirlineController {
	@Autowired
	private AirlineService service;
	
	@RequestMapping(value="/getAll", method = RequestMethod.GET) 
	public Collection<Airline> getAll() {
		return service.findAll();
		
	}
	
	@RequestMapping(
			value = "/update",
			method = RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Airline> update(@RequestBody Airline airline) {
		if (airline.getName().trim().isEmpty() ||
				airline.getLocation().trim().isEmpty() ||
				airline.getPromoDescription().trim().isEmpty())
			return new ResponseEntity<Airline>(HttpStatus.BAD_REQUEST);
		return new ResponseEntity<Airline>(service.update(airline), HttpStatus.OK);
	}
}
