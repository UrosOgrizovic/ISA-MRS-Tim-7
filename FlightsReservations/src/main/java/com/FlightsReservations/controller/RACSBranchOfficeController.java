package com.FlightsReservations.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.FlightsReservations.domain.dto.CarDTO;
import com.FlightsReservations.domain.dto.RACSBranchOfficeDTO;
import com.FlightsReservations.service.RACSBranchOfficeService;

@RestController
@RequestMapping("/racssBranchOffices")
@CrossOrigin("*")
public class RACSBranchOfficeController {
	
	@Autowired
	private RACSBranchOfficeService service;
	
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
	
	@GetMapping(value = "/findByName/{branchOfficeName}", consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RACSBranchOfficeDTO> findByName(@PathVariable String branchOfficeName) {
		RACSBranchOfficeDTO rbodto = service.findByName(branchOfficeName);
		if (rbodto != null)
			return new ResponseEntity<>(rbodto, HttpStatus.OK);
		return new ResponseEntity<>(rbodto, HttpStatus.NOT_FOUND);
	}
	
	//TODO: Add search cars of RACS branch office method
}
