package com.FlightsReservations.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.FlightsReservations.domain.dto.AirlineDTO;
import com.FlightsReservations.domain.dto.AirportDTO;
import com.FlightsReservations.domain.dto.PricelistDTO;
import com.FlightsReservations.service.AirlineService;
import com.fasterxml.jackson.annotation.JsonFormat;

@RestController
@RequestMapping("/airlines")
@CrossOrigin("*")
public class AirlineController {

	@Autowired
	private AirlineService service;

	@GetMapping(value = "/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> findOne(@NotBlank @PathVariable String name) {
		AirlineDTO a = service.findOne(name);
		if (a != null)
			return new ResponseEntity<>(a, HttpStatus.OK);
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<AirlineDTO> findAll() {
		return service.findAll();
	}

	@PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> add(@RequestBody @Valid AirlineDTO airline) {
		AirlineDTO a = service.create(airline);
		if (a != null)
			return new ResponseEntity<>(a, HttpStatus.CREATED);
		return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
	}

	@PutMapping(value = "/update/{airline}/{promo}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@NotBlank @PathVariable String airline, @NotBlank @PathVariable String promo) {
		if (service.update(airline, promo))
			return new ResponseEntity<>("OK", HttpStatus.OK);
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}

	@PutMapping(value = "/addAirport/{airline}/{airport}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addAirport(@NotBlank @PathVariable String airline,
			@NotBlank @PathVariable String airport) {
		AirportDTO dto = service.addAirport(airline, airport);
		if (dto != null)
			return new ResponseEntity<>(dto, HttpStatus.OK);
		return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
	}

	@GetMapping(value = "/airports/{airline}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAirports(@NotBlank @PathVariable String airline) {
		List<AirportDTO> dtos = service.getAirports(airline);
		return new ResponseEntity<>(dtos, HttpStatus.OK);
	}

	@GetMapping(value = "/flights/{airline}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getFlights(@NotBlank @PathVariable String airline) {
		return new ResponseEntity<>(service.getFlights(airline), HttpStatus.OK);
	}

	@GetMapping(value = "/pricelist/{airline}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getPricelist(@NotBlank @PathVariable String airline) {
		PricelistDTO dto = service.getPricelist(airline);
		if (dto != null)
			return new ResponseEntity<>(dto, HttpStatus.OK);
		return new ResponseEntity<>(null, HttpStatus.OK);
	}

	@PostMapping(value = "/pricelist", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> setPricelist(@Valid @RequestBody PricelistDTO dto) {
		if (service.setPricelist(dto))
			return new ResponseEntity<>("", HttpStatus.OK);
		return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
	}

	@GetMapping(value = "/reports/day", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getDayReport() {
		return new ResponseEntity<>(service.getCountReport("dd-MM-yyyy"), HttpStatus.OK);
	}

	@GetMapping(value = "/reports/month", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getMonthReport() {
		return new ResponseEntity<>(service.getCountReport("MM-yyyy"), HttpStatus.OK);
	}

	@GetMapping(value = "/reports/week", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getWeekReport() {
		return new ResponseEntity<>(service.getCountReport("W-MM-yyyy"), HttpStatus.OK);
	}

	@GetMapping(value = "/reports/{start}/{end}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getPeriodReport(
			@PathVariable @NotBlank @JsonFormat(pattern = "dd-MM-yyyy", timezone = "Belgrade/Europe") Date start,
			@PathVariable @NotBlank @JsonFormat(pattern = "dd-MM-yyyy", timezone = "Belgrade/Europe") Date end) {
		return new ResponseEntity<>(service.getIncomeForPeriod(start, end), HttpStatus.OK);
	}

}
