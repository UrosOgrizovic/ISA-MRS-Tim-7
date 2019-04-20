package com.FlightsReservations.service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FlightsReservations.domain.Flight;
import com.FlightsReservations.domain.FlightReservation;
import com.FlightsReservations.domain.Passenger;
import com.FlightsReservations.domain.Seat;
import com.FlightsReservations.domain.User;
import com.FlightsReservations.domain.dto.FlightReservationDetailsDTO;
import com.FlightsReservations.domain.dto.FlightsReservationRequestDTO;
import com.FlightsReservations.domain.dto.PassengerDTO;
import com.FlightsReservations.repository.FlightRepository;
import com.FlightsReservations.repository.FlightReservationRepository;
import com.FlightsReservations.repository.UserRepository;

@Service
public class FlightReservationService {
	
	@Autowired
	private FlightReservationRepository repository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private FlightRepository flightRepository;
	
	public boolean create(FlightsReservationRequestDTO dto) {
		if(!creatingSemanticValidation(dto))
			return false;
		
		User owner = userRepository.findByEmail(dto.getOwnerEmail());
		
		Float total = (float) 0;
		Float discount = (float) 0;
		FlightReservation r = new FlightReservation(
				new Date(),
				dto.getTripType(),
				discount,
				total,
				owner, 
				true);
		
		Seat s;
		Passenger p;
		Flight f;
		for (FlightReservationDetailsDTO dtos : dto.getFlights()) {
			f = findFlight(dtos.getFlightId());
			for (PassengerDTO pdto : dtos.getPassengers()) {
				s = findSeat(f, pdto.getSeatNumber());
				s.setAvailable(false);
				p = new Passenger(pdto, s);
				r.getPassengers().add(p);
				r.getFlights().add(f);
				repository.save(r);
			}
		}
		return true;
		
	}
	
	
	public boolean creatingSemanticValidation(FlightsReservationRequestDTO dto) {
		// user with given email must exist
		if (userRepository.findByEmail(dto.getOwnerEmail()) == null)
			return false;
		
		// flight id's in reservation must be different from each other
		Set<Long> ids = new HashSet<>();
		for (FlightReservationDetailsDTO detailDTO : dto.getFlights()) {
			Long id = detailDTO.getFlightId();
			if (ids.contains(id))
				return false;
			ids.add(id);
		}
		
		// all flights from reservation exists
		List<Flight> flights = flightRepository.findAllById(ids);
		if (flights.size() != ids.size())
			return false;
		
		// all requested seats must exist and must be available
		for (FlightReservationDetailsDTO detailDTO : dto.getFlights()) {
			Flight f = findFlight(detailDTO.getFlightId());
			for(PassengerDTO passenger : detailDTO.getPassengers()) {
				Integer seatNum = passenger.getSeatNumber();
				Seat s = findSeat(f, seatNum);
				
				if (s == null)
					return false;
				
				if (!s.isAvailable())
					return false;
			}
		}	
		
		// add validation for invited friends
		
		return true;
	}
	
	private Flight findFlight(Long id) {
		Optional<Flight> f = flightRepository.findById(id);
		if (f.isPresent())
			return f.get();
		return null;
	}
	
	private Seat findSeat(Flight f, Integer seatNum) {
		for (Seat s : f.getSeats())
			if (s.getSeatNumber().equals(seatNum))
				return s;
		return null;
	}
}
