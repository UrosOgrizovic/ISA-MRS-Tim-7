package com.FlightsReservations.controller;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.Valid;
import javax.validation.constraints.Email;

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
import com.FlightsReservations.domain.dto.CarDTO;
import com.FlightsReservations.domain.dto.RACSAdminDTO;
import com.FlightsReservations.service.RACSAdminService;
import com.FlightsReservations.service.RACSService;

@RestController
@RequestMapping("/racss")
@CrossOrigin("*")
public class RACSController {
	
	@Autowired
	private RACSService service;
	
	@Autowired
	private RACSAdminService racsAdminService;
	
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
	public ResponseEntity<ArrayList<Car>> searchCars(@RequestParam("name") String name, @RequestParam("manufacturer") String manufacturer,
			@RequestParam("yearOfManufacture") int yearOfManufacture) {
		
		Collection<RACS> racss = service.findAll();
		ArrayList<Car> matchingCars = new ArrayList<Car>();
		
		if (yearOfManufacture != 0) {
			if (name.trim().isEmpty() && manufacturer.trim().isEmpty()) {
				for (RACS r : racss) {
					for (Car c : r.getCars()) {
						if (yearOfManufacture == c.getYearOfManufacture()) {
							matchingCars.add(c);
						}
					}
				}
			} else if (name.trim().isEmpty() && !manufacturer.trim().isEmpty()) {
				for (RACS r : racss) {
					for (Car c : r.getCars()) {
						if (yearOfManufacture == c.getYearOfManufacture() && manufacturer.equals(c.getManufacturer())) {
							matchingCars.add(c);
						}
					}
				}
			} else if (!name.trim().isEmpty() && manufacturer.trim().isEmpty()) {
				for (RACS r : racss) {
					for (Car c : r.getCars()) {
						if (name.equals(c.getName()) && yearOfManufacture == c.getYearOfManufacture()) {
							matchingCars.add(c);
						}
					}
				}
			} else {
				for (RACS r : racss) {
					for (Car c : r.getCars()) {
						if (name.equals(c.getName()) && yearOfManufacture == c.getYearOfManufacture() && manufacturer.equals(c.getManufacturer())) {
							matchingCars.add(c);
						}
					}
				}
			}
		} else { // yearOfManufacture == 0
			if (name.trim().isEmpty() && manufacturer.trim().isEmpty()) {
				for (RACS r : racss) {
					for (Car c : r.getCars()) {
						matchingCars.add(c);
					}
				}
			} else if (name.trim().isEmpty() && !manufacturer.trim().isEmpty()) {
				for (RACS r : racss) {
					for (Car c : r.getCars()) {
						if (manufacturer.equals(c.getManufacturer())) {
							matchingCars.add(c);
						}
					}
				}
			} else if (!name.trim().isEmpty() && manufacturer.trim().isEmpty()) {
				for (RACS r : racss) {
					for (Car c : r.getCars()) {
						if (name.equals(c.getName())) {
							matchingCars.add(c);
						}
					}
				}
			} else {
				for (RACS r : racss) {
					for (Car c : r.getCars()) {
						if (name.equals(c.getName()) && manufacturer.equals(c.getManufacturer())) {
							matchingCars.add(c);
						}
					}
				}
			}
		}
		
		
		return new ResponseEntity<ArrayList<Car>>(matchingCars, HttpStatus.OK);

	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping(
			value = "/addCar",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addCar(@RequestBody @Valid CarDTO car) {
		if (service.addCar(car))
			return new ResponseEntity<>(car, HttpStatus.OK);
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping(value = "getRevenueForPeriod/{email}/{startTime}/{endTime}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getRevenueForPeriod(@PathVariable String email, @PathVariable String startTime, @PathVariable String endTime) {
		RACSAdminDTO racsAdmin = racsAdminService.findOne(email);
		RACS racs = service.findOne(racsAdmin.getRacs().getId());
		if (racs != null)
			return new ResponseEntity<>(service.getRevenueForPeriod(racs.getId(), startTime, endTime), HttpStatus.OK);
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping(value = "getNumberOfCarReservationsOfRacsDaily/{email}/{startTime}/{endTime}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getNumberOfCarReservationsOfRacsDaily(@PathVariable @Email String email, @PathVariable String startTime, @PathVariable String endTime) {
		RACSAdminDTO racsAdmin = racsAdminService.findOne(email);
		RACS racs = service.findOne(racsAdmin.getRacs().getId());
		if (racs != null)
			return new ResponseEntity<>(service.getNumberOfCarReservationsOfRacsDaily(racs.getId(), startTime, endTime), HttpStatus.OK);
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping(value = "getAverageRatingForEachCarOfRacs/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAverageRatingForEachCarOfRacs(@PathVariable @Email String email) {
		RACSAdminDTO racsAdmin = racsAdminService.findOne(email);
		RACS racs = service.findOne(racsAdmin.getRacs().getId());
		if (racs != null)
			return new ResponseEntity<>(service.getAverageRatingForEachCarOfRacs(racs), HttpStatus.OK);
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping(value = "addAdmin/{racsId}/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addAdmin(@PathVariable Long racsId, @PathVariable @Email String email) {
		RACSAdminDTO dto = service.addAdmin(racsId, email);
		if (dto != null)
			return new ResponseEntity<>(dto, HttpStatus.OK);
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}
	
	
	
	
}
