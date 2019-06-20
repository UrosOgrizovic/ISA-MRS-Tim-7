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

import com.FlightsReservations.domain.dto.RoomReservationDTO;
import com.FlightsReservations.domain.dto.RoomReservationRequestDTO;
import com.FlightsReservations.service.RoomReservationService;

@RestController
@RequestMapping("/roomReservations")
@CrossOrigin("*")
public class RoomReservationController {
	
	@Autowired
	private RoomReservationService service;
	
	@PostMapping(value= "/create", consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> create(@Valid @RequestBody RoomReservationRequestDTO dto) {
		RoomReservationDTO rdto = service.create(dto);
		if (rdto != null) 
			return new ResponseEntity<>(rdto, HttpStatus.CREATED);
		return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping(value = "/cancel/{id}")
	public ResponseEntity<?> cancel(@NotNull @Positive @PathVariable Long id) {
		if (service.cancel(id)) 
			return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
		return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
	}

}
