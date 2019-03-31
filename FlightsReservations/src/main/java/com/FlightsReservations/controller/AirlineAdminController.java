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

import com.FlightsReservations.domain.AirlineAdmin;
import com.FlightsReservations.service.AirlineAdminService;

@RestController
@RequestMapping("/airlineAdmin")
@CrossOrigin("*")
public class AirlineAdminController
{
    @Autowired
    AirlineAdminService service;

    @RequestMapping(value = "/create", method = RequestMethod.POST,
    				consumes = MediaType.APPLICATION_JSON_VALUE,
    				produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<AirlineAdmin> create(@RequestBody AirlineAdmin airlineAdmin)
    {
    	//checking
        System.out.printf("\n\nAirlineAdmin: %s , %s, %s, %s, %s, %s\n\n", airlineAdmin.getFirstName(), airlineAdmin.getLastName(), airlineAdmin.getEmail(), airlineAdmin.getPhone(), airlineAdmin.getAddress(), airlineAdmin.getPassword() );
        return new ResponseEntity<AirlineAdmin>(service.create(airlineAdmin), HttpStatus.OK);
    }
            
}