package com.FlightsReservations.controller;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.FlightsReservations.domain.dto.SeatDTO;
import com.FlightsReservations.domain.enums.SeatType;
import com.FlightsReservations.service.SeatService;

@RestController
@RequestMapping("/seats")
@CrossOrigin("*")
public class SeatController {
	
	@Autowired
	private SeatService service;
	
	
	@PostMapping(value = "/{flightID}/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> add(
			@NotNull @Positive @PathVariable Long flightID,
			@NotBlank @PathVariable SeatType type) {
		SeatDTO dto = service.add(flightID, type);
		if (dto != null)
			return new ResponseEntity<>(dto, HttpStatus.CREATED);
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	
	@PutMapping(value = "/{flightID}/{seatNum}/{type}")
	public ResponseEntity<?> edit(
			@NotNull @Positive @PathVariable Long flightID,
			@NotNull @Positive @PathVariable Long seatNum,
			@NotBlank @PathVariable SeatType type) {
		if (service.edit(flightID, seatNum, type)) 
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	
	@DeleteMapping(value = "/{flightID}/{seatNum}")
	public ResponseEntity<?> delete(
			@NotNull @Positive @PathVariable Long flightID,
			@NotNull @Positive @PathVariable Long seatNum) {
		if (service.delete(flightID, seatNum)) 
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
}	
