package com.FlightsReservations.controller;

import java.util.Collection;

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

import com.FlightsReservations.domain.dto.HotelDTO;
import com.FlightsReservations.domain.dto.SearchHotelDTO;
import com.FlightsReservations.service.HotelService;
import com.FlightsReservations.service.RoomService;

@RestController
@RequestMapping("/hotels")
@CrossOrigin("*")
public class HotelController {
	@Autowired
	private HotelService service;
	@Autowired
	private RoomService roomService;
	
	//@PreAuthorize("hasRole('USER')")
	@GetMapping(value="/getAll", produces = MediaType.APPLICATION_JSON_VALUE) 
	public Collection<HotelDTO> getAll() {
		return service.findAll();
	}
	
	//@PreAuthorize("hasRole('ADMIN')")
	@PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HotelDTO> add(@RequestBody @Valid HotelDTO hotel) {
		HotelDTO h = service.create(hotel);
		if (h != null) {
			return new ResponseEntity<>(h, HttpStatus.CREATED);
		}
		return new ResponseEntity<>(h, HttpStatus.CONFLICT);//TODO: finish later - same id?
	}
	
	@GetMapping(value= "/searchHotels", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<HotelDTO> > searchHotels(@RequestBody SearchHotelDTO hotel)
	{
		System.out.println("Usao u kontroler1");
		Collection<HotelDTO> results = service.search(hotel);
		System.out.println("Usao u kontroler2");
		for(HotelDTO h : results)
		{
			System.out.println(h.getName() + "," + h.getCity() + "," + h.getState() + "," + Float.toString(h.getAverageScore() ) ); 
		}
		System.out.println("Usao u kontroler2");
		return new ResponseEntity<Collection<HotelDTO> >(results, HttpStatus.OK);
	}
	
	@PutMapping(
			value = "/update",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> update(@RequestBody @Valid HotelDTO hotel) {
		if (service.update(hotel))
			return new ResponseEntity<>("Update successful", HttpStatus.OK);
		return new ResponseEntity<>("Hotel with given id does not exist", HttpStatus.NOT_FOUND);
	}
	
	/*
	@PutMapping(value = "/addRoom", consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addRoom(@RequestBody @Valid RoomDTO room) {
		if (service.addRoom(room))
			roomService.save(room);
			return new ResponseEntity<>(room, HttpStatus.OK);
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}
	*/
}
