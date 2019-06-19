package com.FlightsReservations.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

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

import com.FlightsReservations.domain.AbstractUser;
import com.FlightsReservations.domain.dto.AbstractUserDTO;
import com.FlightsReservations.domain.dto.CarReservationDTO;
import com.FlightsReservations.domain.dto.FlightReservationDTO;
import com.FlightsReservations.domain.dto.RegistrationUserDTO;
import com.FlightsReservations.domain.dto.RoomReservationDTO;
import com.FlightsReservations.service.UserService;

@RestController
@RequestMapping("/users")
@CrossOrigin("*")
public class UserController {

	@Autowired
	private UserService service;

	@GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public List<AbstractUserDTO> getAll() {
		return service.findAll();
	}

	@GetMapping(value = "/getUser/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_USER')")
	public AbstractUser loadUserById(@PathVariable Long id) {
		return service.findById(id);
	}
	
	@GetMapping(value = "/myDetails", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_USER')")
	public AbstractUserDTO myDetails() {
		return service.myDetails();
	} 

	@GetMapping(value = "/getCarReservations/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_USER')")
	public List<CarReservationDTO> getCarReservations(@PathVariable String email) {
		email = email.trim();
		return this.service.getCarReservations(email);
	}

	@GetMapping(value = "/getFlightReservations/{email}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_USER')")
	public List<FlightReservationDTO> getFlightReservations(@PathVariable String email) {
		email = email.trim();
		return this.service.getFlightReservations(email);
	}

	@GetMapping(value = "/getRoomReservations/{email}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_USER')")
	public List<RoomReservationDTO> getRoomReservations(@PathVariable String email) {
		email = email.trim();
		return this.service.getRoomReservations(email);
	}
	
	@GetMapping(value = "/searchUsers/{query}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<?> searchUsers(@NotBlank @PathVariable("query") String query) {
		return new ResponseEntity<>(service.searchUsers(query), HttpStatus.OK);
	}

	@GetMapping(value = "/getFriends", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<?> getFriends() {
		return new ResponseEntity<>(service.getFriends(), HttpStatus.OK);
	}

	@GetMapping(value = "/getSentFriendRequests", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<?> getSentFriendRequests() {
		return new ResponseEntity<>(service.getSentFriendRequests(), HttpStatus.OK);
	}

	@GetMapping(value = "/getRecievedFriendRequests", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<?> getRecievedFriendRequests() {
		return new ResponseEntity<>(service.getRecievedFriendRequests(), HttpStatus.OK);
	}

	@PutMapping(value = "/friendRequest/send/{reciever}")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<?> sendFriendRequest(@NotBlank @Email @PathVariable String reciever) {
		if (service.sendFriendRequest(reciever))
			return new ResponseEntity<>(HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@PutMapping(value = "/friendRequest/approve/{sender}")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<?> approveFriendRequest(@NotBlank @Email @PathVariable String sender) {
		if (service.approveFriendRequest(sender))
			return new ResponseEntity<>(HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@PutMapping(value = "/friendRequest/decline/{sender}")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<?> declineFriendRequest(@NotBlank @Email @PathVariable String sender) {
		if (service.declineFriendRequest(sender))
			return new ResponseEntity<>(HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@PutMapping(value = "/removeFriend/{friend}")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<?> removeFriend(@NotBlank @Email @PathVariable String friend) {
		if (service.removeFriend(friend))
			return new ResponseEntity<>(HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<?> addUser(@RequestBody @Valid RegistrationUserDTO user) {
		AbstractUserDTO udto = service.create(user);
		if (udto != null)
			return new ResponseEntity<>(udto, HttpStatus.CREATED);
		return new ResponseEntity<>(HttpStatus.CONFLICT);
	}

	@PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<?> update(@RequestBody @Valid AbstractUserDTO user) {
		if (service.update(user))
			return new ResponseEntity<>(HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

}
