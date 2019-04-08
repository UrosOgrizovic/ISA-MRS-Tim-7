package com.FlightsReservations.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.FlightsReservations.domain.dto.CreateFlightDTO;
import com.FlightsReservations.domain.dto.FlightDTO;
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
	
	@PostMapping(
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> add(@Valid @RequestBody CreateFlightDTO dto) {
		FlightDTO flight= service.add(dto);
		if (flight != null)
			return new ResponseEntity<>(flight, HttpStatus.CREATED);
		return new ResponseEntity<>("Bad input parameters.", HttpStatus.BAD_REQUEST);
	}
}
