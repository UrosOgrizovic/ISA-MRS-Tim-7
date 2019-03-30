package com.FlightsReservations.controller;

import java.util.Collection;
import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.FlightsReservations.domain.User;
import com.FlightsReservations.domain.dto.RegistrationUserDTO;
import com.FlightsReservations.domain.dto.UserDTO;
import com.FlightsReservations.service.CustomUserDetailsService;

@RestController
@RequestMapping("/users")
@CrossOrigin("*")
public class UserController {
	
	@Autowired 
	private CustomUserDetailsService service;
	
	
	@GetMapping(
			value = "/getAll",
			produces = MediaType.APPLICATION_JSON_VALUE
			) 
	public List<UserDTO> getAll()  {
		return service.findAll();
	}
	
	@PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE) 
	public ResponseEntity<UserDTO> findOneUser(@RequestBody @Valid User user) {
		String email = user.getEmail();
		String password = user.getPassword();
		if (email.trim().isEmpty() ||
				password.trim().isEmpty())
			return new ResponseEntity<UserDTO>(HttpStatus.BAD_REQUEST);
		
		Collection<RegistrationUserDTO> users = service.findAllRegistrationUsers();
		
		for (RegistrationUserDTO u : users) {
			if (u.getEmail().equals(email) && u.getPassword().equals(password) && u.isEnabled()) {
				return new ResponseEntity<UserDTO>(u, HttpStatus.OK);
			}
		}
		return new ResponseEntity<UserDTO>(HttpStatus.NOT_FOUND);
	}
	
	
	@PostMapping(
			value = "/add",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<UserDTO> addUser(@RequestBody @Valid RegistrationUserDTO user) {
		UserDTO udto = service.create(user);
		if (udto != null) 
			return new ResponseEntity<>(udto, HttpStatus.CREATED);
		return new ResponseEntity<>(udto, HttpStatus.CONFLICT);
	}
	
	
	
	@PutMapping(
			value = "/update",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<String> update(@RequestBody @Valid UserDTO user) {
		if (service.update(user))
			return new ResponseEntity<>("Update successfull.", HttpStatus.OK);
		return new ResponseEntity<>("User with given email doesnt exists.", HttpStatus.NOT_FOUND);
	}
}            
