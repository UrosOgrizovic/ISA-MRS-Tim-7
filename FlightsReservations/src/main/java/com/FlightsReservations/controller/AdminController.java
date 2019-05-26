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

import com.FlightsReservations.domain.dto.AirlineAdminDTO;
import com.FlightsReservations.domain.dto.HotelAdminDTO;
import com.FlightsReservations.domain.dto.RACSAdminDTO;
import com.FlightsReservations.domain.dto.SystemAdminDTO;
import com.FlightsReservations.service.AirlineAdminService;
import com.FlightsReservations.service.HotelAdminService;
import com.FlightsReservations.service.RACSAdminService;
import com.FlightsReservations.service.SystemAdminService;

@RestController
@RequestMapping("/Admin")
@CrossOrigin("*")
public class AdminController
{
    @Autowired
    HotelAdminService hotelAdminService;
    @Autowired
    AirlineAdminService airlineAdminService;
    @Autowired
    RACSAdminService racsAdminService;
    @Autowired
    SystemAdminService adminService;
    
    @RequestMapping(value = "/airline/create", method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<?> create(@RequestBody AirlineAdminDTO dto)
	{
		dto = airlineAdminService.create(dto);
		if (dto != null)
			return new ResponseEntity<>(dto, HttpStatus.CREATED);
		return new ResponseEntity<>("Admin already exists.", HttpStatus.BAD_REQUEST);
	}

    @RequestMapping(value = "/hotel/create", method = RequestMethod.POST,
    				consumes = MediaType.APPLICATION_JSON_VALUE,
    				produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<?> create(@RequestBody HotelAdminDTO dto)
    {
	    dto = hotelAdminService.create(dto);
		if (dto != null)
			return new ResponseEntity<>(dto, HttpStatus.CREATED);
		return new ResponseEntity<>("Admin already exists.", HttpStatus.BAD_REQUEST);
    }
    
    @RequestMapping(value = "/racs/create", method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<?> create(@RequestBody RACSAdminDTO dto)
	{
		dto = racsAdminService.create(dto);
		if (dto != null)
			return new ResponseEntity<>(dto, HttpStatus.CREATED);
		return new ResponseEntity<>("Admin already exists.", HttpStatus.BAD_REQUEST);
	}

    @RequestMapping(value = "/system/create", method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<?> create(@RequestBody SystemAdminDTO dto)
	{
		dto = adminService.create(dto);
		if (dto != null)
			return new ResponseEntity<>(dto, HttpStatus.CREATED);
		return new ResponseEntity<>("Admin already exists.", HttpStatus.BAD_REQUEST);
	}
}