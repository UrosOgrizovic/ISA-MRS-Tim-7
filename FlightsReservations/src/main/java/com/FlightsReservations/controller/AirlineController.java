package com.FlightsReservations.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.FlightsReservations.domain.dto.AirlineDTO;
import com.FlightsReservations.service.AirlineService;

@RestController
@RequestMapping("/airlines")
@CrossOrigin("*")
public class AirlineController {
	
	@Autowired
	private AirlineService service;
	
	
	@GetMapping(
			value = "/{name}",
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<?> findOne(@PathVariable String name) {
		AirlineDTO a = service.findOne(name);
		if (a != null)
			return new ResponseEntity<>(a, HttpStatus.OK);
		return new ResponseEntity<>("Airport with given name doesnt exists!", HttpStatus.NOT_FOUND);
	}
	
	
	@GetMapping(
			produces = MediaType.APPLICATION_JSON_VALUE
			) 
	public List<AirlineDTO> findAll() {
		return service.findAll();
	}
	
	
	@PostMapping(
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<?> add(@RequestBody @Valid AirlineDTO airline) {
		AirlineDTO a = service.create(airline);
		if (a != null)
			return new ResponseEntity<>(a, HttpStatus.CREATED);
		return new ResponseEntity<>("Airline with given id already exists!", HttpStatus.CONFLICT);
	}
	
	
	@PutMapping(
			value = "/update",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<String> update(@RequestBody @Valid AirlineDTO airline) {
		if (service.update(airline))
			return new ResponseEntity<>("Update successfull.", HttpStatus.OK);
		return new ResponseEntity<>("Airline with given id does not exists.", HttpStatus.NOT_FOUND);
	}
	
	
	@PutMapping(
			value = "/addAirport/{airline}/{airport}",
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<?> update(@NotBlank @PathVariable String airline,
			@NotBlank @PathVariable String airport) {
		AirlineDTO dto = service.addAirport(airline, airport);
		if (dto != null) 
			return new ResponseEntity<>(dto, HttpStatus.OK);
		return new ResponseEntity<>("Bad input parameters!", HttpStatus.BAD_REQUEST);
	}
}
