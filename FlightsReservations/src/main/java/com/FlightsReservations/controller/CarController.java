package com.FlightsReservations.controller;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.FlightsReservations.domain.Car;
import com.FlightsReservations.domain.dto.CreateCarDiscountDTO;
import com.FlightsReservations.domain.dto.DiscountCarDTO;
import com.FlightsReservations.service.CarService;

@RestController
@RequestMapping("/cars")
@CrossOrigin("*")
public class CarController {
	
	@Autowired
	private CarService service;
	
	//@PreAuthorize("hasRole('USER')")
	@GetMapping(value="/getAll", produces = MediaType.APPLICATION_JSON_VALUE) 
	public Collection<Car> getAll() {
		return service.findAll();
	}
	
	@DeleteMapping(value = "/removeCar/{id}")
	public Collection<Car> removeCar(@PathVariable Long id) {
		service.delete(id);
		return service.findAll();
	}
	
	@PutMapping(
			value = "/update",
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updateCar(@RequestBody @Valid Car car) {
		if (service.update(car))
			return new ResponseEntity<>("Update successful", HttpStatus.OK);
		return new ResponseEntity<>("Car with given id does not exist", HttpStatus.NOT_FOUND);
	}
	
	@PutMapping(value = "/addDiscountToCar", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addDiscountToCar(@Valid @RequestBody CreateCarDiscountDTO discount) {
		String success = service.addDiscountToCar(discount);
		if (success.equals("Success"))
			return new ResponseEntity<>("Discount succesffully added", HttpStatus.OK);
		return new ResponseEntity<>(success, HttpStatus.BAD_REQUEST);
	}

	@GetMapping(value = "/getAllDiscountCarsForPeriod/{startTime}/{endTime}/{city}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Collection<DiscountCarDTO> getAllDiscountCarsForPeriod(@PathVariable String startTime, @PathVariable String endTime, @PathVariable String city) {
		return service.getAllDiscountCarsForPeriod(startTime, endTime, city);
	}
	
}
