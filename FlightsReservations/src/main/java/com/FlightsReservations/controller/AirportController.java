package com.FlightsReservations.controller;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.FlightsReservations.domain.dto.AirportDTO;
import com.FlightsReservations.service.AirportService;

@RestController
@RequestMapping("/airports")
@CrossOrigin("*")
public class AirportController {
	
	@Autowired
	private AirportService service;
	
	
	@GetMapping(
			value = "/{name}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> findOne(@NotBlank @PathVariable String name) {
		System.out.println("Name:" + name);
		AirportDTO a = service.findOne(name);
		if (a != null)
			return new ResponseEntity<>(a, HttpStatus.OK);
		return new ResponseEntity<>("Airport with given name doesnt exists!", HttpStatus.NOT_FOUND);
	}
	
	
	@GetMapping(
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> findAll() {
		return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
	}
	
	
	@PostMapping(
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> add(@Valid @RequestBody AirportDTO dto) {
		dto = service.create(dto);
		if (dto != null)
			return new ResponseEntity<>(dto, HttpStatus.CREATED);
		return new ResponseEntity<>("Airport name already exists.", HttpStatus.BAD_REQUEST);
	} 
	
}
