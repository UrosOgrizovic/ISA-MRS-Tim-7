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
		for (Airline al : a.getAirlines())
			dto.getAirlines().add(al.getName());
		
//		for (Flight f : a.getStarts())
//			dto.getFlights().add(f.buduciId);
//		
//		for (Flight f : a.getEnds())
//			dto.getFlights().add(f.buduciId);
		
		return dto;
	}	
}