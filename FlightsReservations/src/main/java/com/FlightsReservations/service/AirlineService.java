package com.FlightsReservations.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FlightsReservations.domain.Airline;
import com.FlightsReservations.domain.AirlinePriceList;
import com.FlightsReservations.domain.Airport;
import com.FlightsReservations.domain.Flight;
import com.FlightsReservations.domain.dto.AirlineDTO;
import com.FlightsReservations.domain.dto.AirportDTO;
import com.FlightsReservations.domain.dto.FlightDTO;
import com.FlightsReservations.domain.dto.PricelistDTO;
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
					t.getAverageScore(), t.getNumberOfVotes());
			repository.save(a);
			return createDTO(a);
		}
		return null;
	}

	public boolean update(String airline, String promo) {
		Airline a = repository.findByName(airline);
		if (a != null) {
			a.setPromoDescription(promo);
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
	
	
	public AirportDTO addAirport(String airlineName, String airportName) {
		Airline airline = repository.findByName(airlineName);
		Airport airport = airportRepository.findByName(airportName);
			
		if (airline != null && airport != null) {
			for (Airport a : airline.getAirports())
				if (a.getName().equals(airport.getName()))
					return null;
			
			airline.getAirports().add(airport);
			repository.save(airline);
			return new AirportDTO(airport);
		}
		return null;
	}
	
	
	public List<AirportDTO> getAirports(String airlineName) {
		Airline airline = repository.findByName(airlineName);
		List<AirportDTO> dtos = new ArrayList<AirportDTO>();
		if (airline != null) {
			for (Airport a : airline.getAirports())
				dtos.add(new AirportDTO(a));
		}
		return dtos;
	}
	
	
	public PricelistDTO getPricelist(String airline) {
		Airline a = repository.findByName(airline);
		if (a != null && a.getPricelist() != null)
			return new PricelistDTO(a.getPricelist());
		return null;
	}
	
	
	
	public boolean setPricelist(PricelistDTO dto) {
		Airline a = repository.findByName(dto.getAirline());
		if (a != null) {
			AirlinePriceList p = new AirlinePriceList(a, dto.getBusiness(), dto.getEconomic(), dto.getFirst());
			a.setPricelist(p);
			repository.save(a);
			return true;
		}
		return false;
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
