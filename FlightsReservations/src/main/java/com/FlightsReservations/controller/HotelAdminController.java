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

import com.FlightsReservations.domain.HotelAdmin;
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
    public ResponseEntity<HotelAdmin> create(@RequestBody HotelAdmin hotelAdmin)
    {
    	//checking
        System.out.printf("\n\nHotelAdmin: %s , %s, %s, %s, %s, %s\n\n", hotelAdmin.getFirstName(), hotelAdmin.getLastName(), hotelAdmin.getEmail(), hotelAdmin.getPhone(), hotelAdmin.getAddress(), hotelAdmin.getPassword() );
        return new ResponseEntity<HotelAdmin>(service.create(hotelAdmin), HttpStatus.OK);
    }
            
}