package com.FlightsReservations.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.FlightsReservations.domain.dto.FlightReservationDTO;
import com.FlightsReservations.domain.dto.FlightsReservationRequestDTO;
import com.FlightsReservations.service.FlightReservationService;

@RestController
@RequestMapping("/flightReservations")
@CrossOrigin("*")
public class FlightReservationController {
	
	@Autowired
	private FlightReservationService service;
	
	@PostMapping(
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> create(@Valid @RequestBody FlightsReservationRequestDTO dto) {
		FlightReservationDTO fdto = service.create(dto);
		if (fdto != null)
			return new ResponseEntity<>(fdto, HttpStatus.CREATED);
		return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
	}

	@PutMapping(value = "/cancel/{id}")
	public ResponseEntity<?> cancel(@NotNull @Positive @PathVariable Long id) {
		// To be implemented
		return null;
	}

}
