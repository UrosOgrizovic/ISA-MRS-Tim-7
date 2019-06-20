package com.FlightsReservations.controller;

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

import com.FlightsReservations.domain.dto.UpdateRACSDTO;
import com.FlightsReservations.domain.dto.RACSAdminDTO;
import com.FlightsReservations.domain.dto.RACSDTO;
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
	public Collection<RACSDTO> getAll() {
		return service.findAll();
	}
	
	@GetMapping(value="/searchRACS/{value}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RACSDTO> searchRACS(@PathVariable String value) {
		RACSDTO rdto = service.findOne(value);
		if (rdto != null) {
			return new ResponseEntity<>(rdto, HttpStatus.OK);
		}
		return new ResponseEntity<>(rdto, HttpStatus.NOT_FOUND);
		
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RACSDTO> add(@RequestBody @Valid RACSDTO racsDTO) {
		RACSDTO rdto = service.create(racsDTO);
		if (rdto != null) {
			return new ResponseEntity<>(rdto, HttpStatus.CREATED);
		}
		return new ResponseEntity<>(rdto, HttpStatus.CONFLICT);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping(
			value = "/update",
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> update(@RequestBody @Valid UpdateRACSDTO racs) {
		if (service.update(racs))
			return new ResponseEntity<>("Update successful", HttpStatus.OK);
		return new ResponseEntity<>("Rent-a-car service with given id does not exist", HttpStatus.NOT_FOUND);
	}
	
	@GetMapping(value="/searchCars", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> searchAllCars(@RequestParam("racsName") String racsName, @RequestParam("name") String name, @RequestParam("manufacturer") String manufacturer,
			@RequestParam("yearOfManufacture") int yearOfManufacture) {
		
		return new ResponseEntity<>(service.searchAllCars(racsName, name, manufacturer, yearOfManufacture), HttpStatus.OK);

	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping(value = "getRevenueForPeriod/{email}/{startTime}/{endTime}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getRevenueForPeriod(@PathVariable String email, @PathVariable String startTime, @PathVariable String endTime) {
		RACSAdminDTO racsAdmin = racsAdminService.findOne(email);
		RACSDTO racsDTO = service.findOne(racsAdmin.getRacs().getName());
		if (racsDTO != null)
			return new ResponseEntity<>(service.getRevenueForPeriod(racsDTO.getName(), startTime, endTime), HttpStatus.OK);
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping(value = "getNumberOfCarReservationsOfRacs/{email}/{startTime}/{endTime}/{unit}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getNumberOfCarReservationsOfRacs(@PathVariable @Email String email, @PathVariable String startTime, @PathVariable String endTime, @PathVariable String unit) {
		RACSAdminDTO racsAdmin = racsAdminService.findOne(email);
		RACSDTO racsDTO = service.findOne(racsAdmin.getRacs().getName());
		if (racsDTO != null)
			return new ResponseEntity<>(service.getNumberOfCarReservationsOfRacs(racsDTO.getName(), startTime, endTime, unit), HttpStatus.OK);
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}
	
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping(value = "getAverageRatingForEachCarOfRacs/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAverageRatingForEachCarOfRacs(@PathVariable @Email String email) {
		RACSAdminDTO racsAdmin = racsAdminService.findOne(email);
		RACSDTO racsDTO = service.findOne(racsAdmin.getRacs().getName());
		if (racsDTO != null)
			return new ResponseEntity<>(service.getAverageRatingForEachCarOfRacs(racsDTO), HttpStatus.OK);
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
