package com.FlightsReservations.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.FlightsReservations.domain.dto.CreateFlightDTO;
import com.FlightsReservations.domain.dto.FlightDTO;
import com.FlightsReservations.domain.dto.FlightRatingDTO;
import com.FlightsReservations.service.FlightService;

@RestController
@RequestMapping("/flights")
@CrossOrigin("*")
public class FlightController {
	
	/*
	 * addFlight
	 * editFlight
	 * getFlights - pageable
	 * getAllFlights - for testing
	 */
	
	@Autowired
	private FlightService service;
	
	@GetMapping(
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<?> findAll() {
		return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
	}
	
	@PostMapping(
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> add(@Valid @RequestBody CreateFlightDTO dto) {
		FlightDTO flight= service.add(dto);
		if (flight != null)
			return new ResponseEntity<>(flight, HttpStatus.CREATED);
		return new ResponseEntity<>("Bad input parameters.", HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping(value ="/rate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> rate(@Valid @RequestBody FlightRatingDTO dto) {

		FlightRatingDTO flight = service.rate(dto);
		if (flight != null)
			return new ResponseEntity<>(flight, HttpStatus.OK);
		return new ResponseEntity<>(flight, HttpStatus.NOT_FOUND);
	}
}
