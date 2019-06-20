package com.FlightsReservations.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

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
import org.springframework.web.bind.annotation.RestController;

import com.FlightsReservations.domain.dto.FlightReservationDTO;
import com.FlightsReservations.domain.dto.FlightsReservationRequestDTO;
import com.FlightsReservations.service.FlightReservationService;

@RestController
@RequestMapping("/flightReservations")
@CrossOrigin("*")
public class FlightReservationController {
	
	@Autowired
	private FlightReservationService service;
	
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> create(@Valid @RequestBody FlightsReservationRequestDTO dto) {
		FlightReservationDTO fr = service.create(dto);
		if (fr != null)
			return new ResponseEntity<>(fr, HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	@PutMapping(value = "/cancel/{id}")
	public ResponseEntity<?> cancel(@NotNull @Positive @PathVariable Long id) {
		if (service.cancel(id))
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	
	
	@PutMapping(value = "/confirmInvite/{inviteId}/{passport}")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<?> acceptInvite(@NotNull @Positive @PathVariable("inviteId") Long inviteId,
			@NotBlank @PathVariable("passport") String passport) {
		if (service.acceptInvite(inviteId, passport)) 
			return new ResponseEntity<>(HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping(value = "/declineInvite/{inviteId}")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<?> declineInvite(@NotNull @Positive @PathVariable Long inviteId) {
		if (service.declineInvite(inviteId)) 
			return new ResponseEntity<>(HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}	
	
	
	@GetMapping(value = "/getInvites")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<?> getInvites() {
		return new ResponseEntity<>(service.getInvites(), HttpStatus.OK);
	}
	
	
	@PostMapping(value = "/quickReservation/{flightId}/{seatNum}/{discount}")
	public ResponseEntity<?> createQuickReservation(@NotNull @PathVariable Long flightId, @NotNull @PathVariable Integer seatNum, @NotNull @PathVariable Float discount) {
		if (service.createQuickReservation(flightId, seatNum, discount)) 
			return new ResponseEntity<>(HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	
	@PutMapping(value = "/quickReservation/{reservationId}/{passport}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<?> takeQuickReservation(
			@NotNull @PathVariable Long reservationId, 
			@NotBlank @PathVariable String passport) {
		FlightReservationDTO r = service.takeQR(reservationId, passport);
		if (r != null)
			return new ResponseEntity<>(r, HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	
	@GetMapping(value = "/quickReservations/{airline}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getQuickReservations(@PathVariable @NotBlank String airline) {
		return new ResponseEntity<>(service.getQuickReservations(airline), HttpStatus.OK);
	}
	
}
