package com.FlightsReservations.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.FlightsReservations.domain.dto.CarReservationDTO;
import com.FlightsReservations.domain.dto.CarReservationRequestDTO;
import com.FlightsReservations.service.CarReservationService;

@RestController
@RequestMapping("/carReservations")
@CrossOrigin("*")
public class CarReservationController {

	@Autowired
	private CarReservationService service;
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> create(@Valid @RequestBody CarReservationRequestDTO dto) {
		CarReservationDTO cdto = service.create(dto);
		if (cdto != null)
			return new ResponseEntity<>(cdto, HttpStatus.CREATED);
		return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@PutMapping(value = "/cancel/{id}")
	public ResponseEntity<?> cancel(@NotNull @Positive @PathVariable Long id) {
		if (service.cancel(id)) 
			return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
		return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
	}
}
