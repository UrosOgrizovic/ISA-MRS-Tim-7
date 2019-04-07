package com.FlightsReservations.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FlightsReservations.domain.Airline;
import com.FlightsReservations.domain.Airport;
import com.FlightsReservations.domain.Flight;
import com.FlightsReservations.domain.dto.AirlineDTO;
import com.FlightsReservations.domain.dto.AirportDTO;
import com.FlightsReservations.domain.dto.FlightDTO;
import com.FlightsReservations.repository.AirportRepository;

@Service
public class AirportService {
	
	@Autowired
	private AirportRepository repository;
	
	
	public AirportDTO findOne(String name) {
		Airport a = repository.findByName(name);

		if (a != null) 
			return createDTO(a);
		return null;
	}
	
	
	public Set<AirportDTO> findAll() {
		List<Airport> airports = repository.findAll();
		Set<AirportDTO> dtos = new HashSet<>();

		for (Airport a : airports)
			dtos.add(createDTO(a));
		return dtos;
 	}
	
	
	public AirportDTO create(AirportDTO dto) {
		if (repository.findByName(dto.getName()) == null) {
			Airport a = new Airport(dto);
			repository.save(a);
			return createDTO(a);
		}
		return null;
	}
	
	
	public boolean update(AirportDTO dto) {
		Airport a = repository.findByName(dto.getName());
		if (a != null) {
			a.setCity(dto.getCity());
			a.setState(dto.getState());
			a.setLongitude(dto.getLongitude());
			a.setLatitude(dto.getLatitude());
			repository.save(a);
			return true;
		}
		return false;
	}
	
	
	
	private AirportDTO createDTO(Airport a) {
		AirportDTO dto = new AirportDTO(a);
		dto.setFlights(getFlights(a));
		dto.setAirlines(getAirlines(a));
		return dto;
	}
	
	
	private Set<AirlineDTO> getAirlines(Airport a) {
		if (a != null) {
			Set<AirlineDTO> airlines = new HashSet<>();
			for (Airline al : a.getAirlines())
				airlines.add(new AirlineDTO(al));
			return airlines;
		}
		return null;
	}
	
	private Set<FlightDTO> getFlights(Airport a) {
		if (a != null) {
			Set<FlightDTO> flights = new HashSet<>();
			for (Flight f : a.getStarts())
				flights.add(new FlightDTO(f));
			
			for (Flight f : a.getStops())
				flights.add(new FlightDTO(f));
			
			return flights;
		}
		return null;
	}
}
