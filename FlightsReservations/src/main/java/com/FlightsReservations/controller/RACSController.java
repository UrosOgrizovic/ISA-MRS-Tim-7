package com.
FlightsReservations.controller;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.FlightsReservations.domain.Car;
import com.FlightsReservations.domain.RACS;
import com.FlightsReservations.service.RACSService;

@RestController
@RequestMapping("/racss")
@CrossOrigin("*")
public class RACSController {
	
	@Autowired
	private RACSService service;
	
	
	@GetMapping(value="/getAll", produces = MediaType.APPLICATION_JSON_VALUE) 
	public Collection<RACS> getAll() {
		return service.findAll();
	}
	
	@GetMapping(value="/searchRACS/{value}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Collection<RACS> searchRACS(@PathVariable String value) {
		return service.findByName(value);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RACS> add(@RequestBody @Valid RACS racs) {
		RACS r = service.create(racs);
		if (r != null) {
			return new ResponseEntity<>(r, HttpStatus.CREATED);
		}
		return new ResponseEntity<>(r, HttpStatus.CONFLICT);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping(
			value = "/update",
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> update(@RequestBody @Valid RACS racs) {
		if (service.update(racs))
			return new ResponseEntity<>("Update successful", HttpStatus.OK);
		return new ResponseEntity<>("Rent-a-car service with given id does not exist", HttpStatus.NOT_FOUND);
	}
	
	@GetMapping(value="/searchCars", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayList<Car>> searchAllCars(@RequestParam("name") String name, @RequestParam("manufacturer") String manufacturer,
			@RequestParam("yearOfManufacture") int yearOfManufacture) {
		
		Collection<RACS> racss = service.findAll();
		
		return new ResponseEntity<ArrayList<Car>>(service.searchAllCars(racss, name, manufacturer, yearOfManufacture), HttpStatus.OK);

	}
}
