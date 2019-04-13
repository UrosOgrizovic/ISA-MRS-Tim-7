package com.FlightsReservations.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.FlightsReservations.domain.dto.RatingDTO;
import com.FlightsReservations.service.CompanyService;

@RestController
@RequestMapping(value = "/companies")
@CrossOrigin("*")
public class CompanyController {
	
	@Autowired
	private CompanyService service;
	
	@PutMapping(
			value = "/rate",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> rate(@Valid @RequestBody RatingDTO dto) {
		dto = service.rate(dto);
		if (dto != null)
			return new ResponseEntity<>(dto, HttpStatus.OK);
		return new ResponseEntity<>("Bad request.", HttpStatus.BAD_REQUEST);
	}
}
