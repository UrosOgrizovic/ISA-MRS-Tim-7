package com.FlightsReservations.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.FlightsReservations.domain.dto.HotelAdminDTO;
import com.FlightsReservations.service.HotelAdminService;

@RestController
@RequestMapping("/hotelAdmin")
@CrossOrigin("*")
public class HotelAdminController
{
    @Autowired
    HotelAdminService service;

    @RequestMapping(value = "/create", method = RequestMethod.POST,
    				consumes = MediaType.APPLICATION_JSON_VALUE,
    				produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<?> create(@RequestBody HotelAdminDTO dto)
    {
	    dto = service.create(dto);
		if (dto != null)
			return new ResponseEntity<>(dto, HttpStatus.CREATED);
		return new ResponseEntity<>("Airline admin already exists.", HttpStatus.BAD_REQUEST);
    }    
}