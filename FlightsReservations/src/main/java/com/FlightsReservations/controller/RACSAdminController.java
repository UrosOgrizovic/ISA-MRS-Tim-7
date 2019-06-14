package com.FlightsReservations.controller;

import javax.validation.constraints.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.FlightsReservations.domain.dto.RACSAdminDTO;
import com.FlightsReservations.service.RACSAdminService;

@RestController
@RequestMapping("/racsAdmin")
@CrossOrigin("*")

public class RACSAdminController
{
    @Autowired
    RACSAdminService service;

    @RequestMapping(value = "/create", method = RequestMethod.POST,
    				consumes = MediaType.APPLICATION_JSON_VALUE,
    				produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<?> create(@RequestBody RACSAdminDTO dto)
    {
	    dto = service.create(dto);
		if (dto != null)
			return new ResponseEntity<>(dto, HttpStatus.CREATED);
		return new ResponseEntity<>("Airline admin already exists.", HttpStatus.BAD_REQUEST);
    }    
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping(value = "getAverageRACSRating/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAverageRACSRating(@PathVariable @Email String email) {
		return new ResponseEntity<>(service.getAverageRACSRating(email), HttpStatus.OK);
	}
}