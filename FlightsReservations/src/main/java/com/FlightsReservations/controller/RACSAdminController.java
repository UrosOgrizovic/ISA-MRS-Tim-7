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

import com.FlightsReservations.domain.RACSAdmin;
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
    public ResponseEntity<RACSAdmin> create(@RequestBody RACSAdmin racsAdmin)
    {
    	//checking
        System.out.printf("\n\nRACSAdmin: %s , %s, %s, %s, %s, %s\n\n", racsAdmin.getFirstName(), racsAdmin.getLastName(), racsAdmin.getEmail(), racsAdmin.getPhone(), racsAdmin.getAddress(), racsAdmin.getPassword() );
        return new ResponseEntity<RACSAdmin>(service.create(racsAdmin), HttpStatus.OK);
    }
            
}