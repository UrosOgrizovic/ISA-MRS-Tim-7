package com.FlightsReservations.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FlightsReservations.domain.Airline;
import com.FlightsReservations.domain.Airport;
import com.FlightsReservations.domain.Flight;
import com.FlightsReservations.domain.dto.AirlineDTO;
import com.FlightsReservations.domain.dto.FlightDTO;
import com.FlightsReservations.repository.AirlineRepository;
import com.FlightsReservations.repository.AirportRepository;

@Service
public class AirlineService {

	@Autowired
	private AirlineRepository repository;
	
	@Autowired
	private AirportRepository airportRepository;

	public AirlineDTO create(AirlineDTO t) {
		Airline a = repository.findByName(t.getName());
		if (a == null) {
			a = new Airline(
					t.getName(), 
					t.getLongitude(), 
					t.getLatitude(), 
					t.getPromoDescription(), 
					0, 0);
			repository.save(a);
			return createDTO(a);
		}
		return null;
	}

	public boolean update(AirlineDTO t) {
		Airline a = repository.findByName(t.getName());
		if (a != null) {
			a.setName(t.getName());
			a.setLongitude(t.getLongitude());
			a.setLatitude(t.getLatitude());
			a.setPromoDescription(t.getPromoDescription());
			repository.save(a);
			return true;
		}
		return false;
	}

	public AirlineDTO findOne(String name) {
		Airline a = repository.findByName(name);
		if (a != null)
			return createDTO(a);
		return null;
	}
	
	public List<AirlineDTO> findAll() {
		List<AirlineDTO> dtos = new ArrayList<>();
		for (Airline a : repository.findAll()) 
			dtos.add(createDTO(a));
		return dtos;
	}	
	
	
	public AirlineDTO addAirport(String airlineName, String airportName) {
		Airline airline = repository.findByName(airlineName);
		Airport airport = airportRepository.findByName(airportName);
		
		if (airline != null && airport != null) {
			airline.getAirports().add(airport);
			repository.save(airline);
			return createDTO(airline);
		}
		return null;
	}
	
	
	private AirlineDTO createDTO(Airline airline) {
		AirlineDTO dto = new AirlineDTO(airline);
		for (Airport a : airline.getAirports()) 
			dto.getAirports().add(a.getName());
		for (Flight f : airline.getFlights())
			dto.getFlights().add(new FlightDTO(f));
		
		return dto;
	}
}
