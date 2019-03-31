package com.FlightsReservations.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.FlightsReservations.domain.Hotel;
import com.FlightsReservations.domain.RACS;
import com.FlightsReservations.service.HotelService;

@RestController
@RequestMapping("/hotel")
@CrossOrigin("*")
public class HotelController
{
    @Autowired
    HotelService service;
    
	@RequestMapping(value="/getAll", method = RequestMethod.GET) 
	public Collection<Hotel> getAll() {
		return service.findAll();
		
	}

    @RequestMapping(value = "/create", method = RequestMethod.POST,
    				consumes = MediaType.APPLICATION_JSON_VALUE,
    				produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<Hotel> create(@RequestBody Hotel hotel)
    {
        System.out.printf("\n\nHotel: %s , %s, %s\n\n", hotel.getName(), hotel.getAddress(), hotel.getPromoDescription() );
        return new ResponseEntity<Hotel>(service.create(hotel), HttpStatus.OK);
    }
            
}