package com.FlightsReservations.controller;

import java.util.List;

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

import com.FlightsReservations.domain.Airline;
import com.FlightsReservations.service.AirlineService;

@RestController
@RequestMapping("/airlines")
@CrossOrigin("*")
public class AirlineController {
	@Autowired
	private AirlineService service;
	
	@GetMapping(
			value="/getAll", 
			produces = MediaType.APPLICATION_JSON_VALUE
			) 
	public List<Airline> getAll() {
		return service.findAll();
	}
	
	
	
	@PostMapping(
			value = "/add",
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<Airline> add(@RequestBody @Valid Airline airline) {
		Airline a = service.create(airline);
		if (a != null)
			return new ResponseEntity<>(a, HttpStatus.CREATED);
		return new ResponseEntity<>(a, HttpStatus.CONFLICT);
	}
	
	
	
	@PutMapping(
			value = "/update",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<String> update(@RequestBody @Valid Airline airline) {
		if (service.update(airline))
			return new ResponseEntity<>("Update successfull.", HttpStatus.OK);
		return new ResponseEntity<>("Airline with given id does not exists.", HttpStatus.NOT_FOUND);
	}
}
