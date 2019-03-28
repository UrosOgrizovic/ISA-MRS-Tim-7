package com.FlightsReservations.controller;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	RACSService service;
	
	@RequestMapping(value="/getAll", method = RequestMethod.GET) 
	public Collection<RACS> getAll() {
		return service.findAll();
		
	}
	
	@RequestMapping(
			value = "/update",
			method = RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RACS> update(@RequestBody RACS racs) {
		if (racs.getName().trim().isEmpty() ||
				racs.getAddress().trim().isEmpty() ||
				racs.getDescription().trim().isEmpty())
			return new ResponseEntity<RACS>(HttpStatus.BAD_REQUEST);
		return new ResponseEntity<RACS>(service.update(racs), HttpStatus.OK);
	}
	
	@RequestMapping(value="/searchCars", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayList<Car>> searchCars(@RequestParam("name") String name, @RequestParam("manufacturer") String manufacturer,
			@RequestParam("yearOfManufacture") int yearOfManufacture) {
		if (name.trim().isEmpty() ||
				manufacturer.trim().isEmpty())
			return new ResponseEntity<ArrayList<Car>>(HttpStatus.BAD_REQUEST);
		Collection<RACS> racss = service.findAll();
		ArrayList<Car> matchingCars = new ArrayList<Car>();
		
		
		
		for (RACS r : racss) {
			for (Car c : r.getCars()) {
				if (name.equals(c.getName()) && yearOfManufacture == c.getYearOfManufacture() && 
						manufacturer.equals(c.getManufacturer())) {
					matchingCars.add(c);
				}
			}
		}
		
		return new ResponseEntity<ArrayList<Car>>(matchingCars, HttpStatus.OK);
		
	}
}
