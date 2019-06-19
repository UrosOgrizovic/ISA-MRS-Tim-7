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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.FlightsReservations.domain.dto.HotelDTO;
import com.FlightsReservations.domain.dto.RoomDTO;
import com.FlightsReservations.domain.dto.SearchHotelDTO;
import com.FlightsReservations.domain.dto.SearchRoomDTO;
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
		System.out.println("Usao u kontroler");
		HotelDTO h = service.create(hotel);
		if (h != null) {
			System.out.println("Sve u redu");
			return new ResponseEntity<>(h, HttpStatus.CREATED);
		}
		return new ResponseEntity<>(h, HttpStatus.CONFLICT);//TODO: finish later - same id?
	}
	
	@GetMapping(value= "/searchHotels", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<HotelDTO> > searchHotels(@RequestParam("name") String name, @RequestParam("city") String city, @RequestParam("state") String state, @RequestParam("averageScore") String averageScore, @RequestParam("searchHotel") String sh)
	{
		SearchHotelDTO hotel = new SearchHotelDTO(name, city, state, averageScore);
		
		Collection<HotelDTO> results = service.search(hotel);
		for(HotelDTO h : results)
		{
			System.out.println(h.getName() + "," + h.getCity() + "," + h.getState() + "," + Float.toString(h.getAverageScore() ) ); 
		}
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
	
	@GetMapping( value = "/searchRooms",
				 consumes = MediaType.APPLICATION_JSON_VALUE,
				 produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<RoomDTO> > searchRooms(@RequestParam("type") String type, @RequestParam("floor") String floor, @RequestParam("numberOfGuests") String numberOfGuests, @RequestParam("overallRating") String overallRating, @RequestParam("overnightStay") String overnightStay, @RequestParam("hotelName") String hotelName, @RequestParam("searchRoom") String sr)
	{
		
		SearchRoomDTO room = new SearchRoomDTO(type, floor, numberOfGuests, overallRating, overnightStay, hotelName);
		
		Collection<RoomDTO> results = roomService.search(room);
		for(RoomDTO r : results)
		{
			//System.out.println(r.getName() + "," + r.getCity() + "," + r.getState() + "," + Float.toString(r.getAverageScore() ) ); 
		}
		return new ResponseEntity<Collection<RoomDTO> >(results, HttpStatus.OK);
	}
}
