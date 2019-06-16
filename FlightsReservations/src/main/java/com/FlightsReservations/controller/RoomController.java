package com.FlightsReservations.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.FlightsReservations.domain.Room;
import com.FlightsReservations.domain.dto.RoomDTO;
import com.FlightsReservations.service.RoomService;

@RestController
@RequestMapping("/rooms")
@CrossOrigin("*")
public class RoomController
{
	@Autowired
	private RoomService service;
	
	@GetMapping(value= "/getHotel", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getHotel(@RequestParam("email") String email)//, @RequestParam("createRoom") String cr)
	{
		String name = service.getHotelName(email);
		return new ResponseEntity<>(name, HttpStatus.FOUND);
		
	}
	
	@PostMapping(value= "/create", consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> create(@RequestBody RoomDTO dto) {
		 Room room = service.create(dto);
		if (room != null) 
			return new ResponseEntity<>(room, HttpStatus.CREATED);
		return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
	}
}