package com.FlightsReservations.controller;

import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.FlightsReservations.domain.dto.AirlineAdminDTO;
import com.FlightsReservations.domain.dto.AirlineDTO;
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
    public ResponseEntity<?> create(@RequestBody AirlineAdminDTO dto)
    {
	    dto = service.create(dto);
		if (dto != null)
			return new ResponseEntity<>(dto, HttpStatus.CREATED);
		return new ResponseEntity<>("Airline admin already exists.", HttpStatus.BAD_REQUEST);
    }    
    
    
    @GetMapping(value = "/getAirline", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAirline(@PathVariable @NotBlank String adminEmail) {
    	AirlineDTO dto = service.getAirline(adminEmail);
    	return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    
}