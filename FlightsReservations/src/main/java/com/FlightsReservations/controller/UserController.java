package com.FlightsReservations.controller;

import java.util.List;
import java.util.Optional;

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
	public List<AbstractUserDTO> getAll()  {
		return service.findAll();
	}
		
	@GetMapping(value = "/getUser/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_USER')")
	public Optional<AbstractUser> loadUserById(@PathVariable Long id) {
		return service.findById(id);
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
	
	
	@PutMapping(
			value = "/addFriend/{userId}/{friendId}",
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	@PreAuthorize("hasRole('ROLE_USER')")
	public void addFriend(@PathVariable Long userId, @PathVariable Long friendId) {
		service.addFriend(userId, friendId);
	}

	
	@GetMapping(value = "/getFriends/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<?> getFriends(@NotBlank @Email @PathVariable String email) {
		return new ResponseEntity<>(service.getFriends(email), HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/getSentFriendRequests/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<?> getSentFriendRequests(@NotBlank @Email @PathVariable String email) {
		return new ResponseEntity<>(service.getSentFriendRequests(email), HttpStatus.OK);
	} 
	
	
	
	@GetMapping(value = "/getRecievedFriendRequests/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<?> getRecievedFriendRequests(@NotBlank @Email @PathVariable String email) {
		return new ResponseEntity<>(service.getRecievedFriendRequests(email), HttpStatus.OK);
	} 
	
	
	@PutMapping(value="/friendRequest/send/{sender}/{reciever}")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<?> sendFriendRequest(@NotBlank @Email @PathVariable String sender, @NotBlank @Email @PathVariable String reciever) {
		if (service.sendFriendRequest(sender, reciever)) 
			return new ResponseEntity<>(HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	
	@PutMapping(value="/friendRequest/approve/{sender}/{reciever}")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<?> approveFriendRequest(@NotBlank @Email @PathVariable String sender, @NotBlank @Email @PathVariable String reciever) {
		if (service.approveFriendRequest(sender, reciever)) 
			return new ResponseEntity<>(HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	} 
	
	
	@PutMapping(value="/friendRequest/decline/{sender}/{reciever}")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<?> declineFriendRequest(@NotBlank @Email @PathVariable String sender, @NotBlank @Email @PathVariable String reciever) {
		if (service.declineFriendRequest(sender, reciever)) 
			return new ResponseEntity<>(HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	} 
	
	
	@PutMapping(value="/removeFriend/{sender}/{reciever}")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<?> removeFriend(@NotBlank @Email @PathVariable String sender, @NotBlank @Email @PathVariable String reciever) {
		if (service.removeFriend(sender, reciever)) 
			return new ResponseEntity<>(HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	} 


	@PostMapping(value = "/add",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
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
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	
}            
