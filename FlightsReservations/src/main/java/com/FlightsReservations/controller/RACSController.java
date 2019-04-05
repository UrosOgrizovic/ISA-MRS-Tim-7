package com.FlightsReservations.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

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
	
	//@PreAuthorize("hasRole('USER')")
	@GetMapping(value="/getAll", produces = MediaType.APPLICATION_JSON_VALUE) 
	public Collection<RACS> getAll() {
		return service.findAll();
	}
	
	//@PreAuthorize("hasRole('ADMIN')")
	@PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RACS> add(@RequestBody @Valid RACS racs) {
		RACS r = service.create(racs);
		if (r != null) {
			return new ResponseEntity<>(r, HttpStatus.CREATED);
		}
		return new ResponseEntity<>(r, HttpStatus.CONFLICT);
	}
	
	@PutMapping(
			value = "/update",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> update(@RequestBody @Valid RACS racs) {
		if (service.update(racs))
			return new ResponseEntity<>("Update successful", HttpStatus.OK);
		return new ResponseEntity<>("Rent-a-car service with given id does not exist", HttpStatus.NOT_FOUND);
	}
	
	@GetMapping(value="/searchCars", produces = MediaType.APPLICATION_JSON_VALUE)
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
	
	@PutMapping(
			value = "/addCar",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Car> addCar(@RequestBody @Valid Car car) {
		System.out.println(car.getManufacturer() + " " + car.getName() + " " + car.getColor() + " " + car.getYearOfManufacture());
		RACS racs = service.findOne(car.getRacs().getId());
		Set<Car> cars = racs.getCars();
		cars.add(car);
		racs.setCars(cars);
		if (service.update(racs))
			return new ResponseEntity<>(car, HttpStatus.OK);
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}
}
