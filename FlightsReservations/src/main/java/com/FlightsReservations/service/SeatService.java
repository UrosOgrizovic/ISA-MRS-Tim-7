package com.FlightsReservations.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FlightsReservations.domain.Flight;
import com.FlightsReservations.domain.Seat;
import com.FlightsReservations.domain.dto.SeatDTO;
import com.FlightsReservations.domain.enums.TypeClass;
import com.FlightsReservations.repository.FlightRepository;
import com.FlightsReservations.repository.SeatRepository;

@Service
public class SeatService {
	@Autowired
	private SeatRepository repository;

	@Autowired
	private FlightRepository flightRepository;

	
	public SeatDTO add(Long flightId, TypeClass type) {
		Optional<Flight> of = flightRepository.findById(flightId);
		if (of.isPresent()) {
			Integer nextSeatNum = repository.maxSeatNumber(flightId) + 1;
			Seat s = new Seat(nextSeatNum, true, type, of.get());
			repository.save(s);
			return new SeatDTO(s);
		}
		return null;
	}

	
	public boolean edit(Long flightID, Long seatNum, TypeClass type) {
		Seat s = repository.findInFlight(flightID, seatNum);
		if (s != null)
			if (s.isAvailable()) {
				s.setType(type);
				return true;
			}
		return false;
	}

	
	public boolean delete(Long flightID, Long seatNum) {
		Seat s = repository.findInFlight(flightID, seatNum);
		if (s != null) 
			if (s.isAvailable()) {
				repository.delete(s);
				return true;
			}
		return false;
	}
}