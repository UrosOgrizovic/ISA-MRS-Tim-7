package com.FlightsReservations.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FlightsReservations.domain.Flight;
import com.FlightsReservations.domain.FlightReservation;
import com.FlightsReservations.domain.Passenger;
import com.FlightsReservations.domain.Seat;
import com.FlightsReservations.domain.User;
import com.FlightsReservations.domain.dto.FlightReservationDTO;
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
	
	public FlightReservationDTO create(FlightsReservationRequestDTO dto) {
		if (!creatingSemanticValidation(dto))
			return null;

		User owner = userRepository.findByEmail(dto.getOwnerEmail());

		Float total = (float) 0;
		Float discount = (float) 0;
		FlightReservation reservation = new FlightReservation(
				new Date(), 
				discount, total, owner,true);

		Seat seat;
		Passenger passenger;
		Flight flight;
		for (FlightReservationDetailsDTO dtos : dto.getFlights()) {
			flight = findFlight(dtos.getFlightId());
			for (PassengerDTO pdto : dtos.getPassengers()) {
				// automatic detail entry for owner
				if (pdto.isOwner()) {
					pdto.setName(owner.getFirstName());
					pdto.setSurname(owner.getLastName());
				}
				
				seat = findSeat(flight, pdto.getSeatNumber());
				seat.setAvailable(false);
				// TODO : based on seat type add additional cost (pricelist service/controller needed first)
				// TODO : based on user travel points reduce cost 
				total += (float) flight.getPrice();
				
				passenger = new Passenger(pdto, seat);
				reservation.getPassengers().add(passenger);
				reservation.getFlights().add(flight);
			}
		}
		reservation.setPrice(total);
		reservation = repository.save(reservation);
		return new FlightReservationDTO(reservation);

	}

	private boolean creatingSemanticValidation(FlightsReservationRequestDTO dto) {
		// user with given email must exist
		if (userRepository.findByEmail(dto.getOwnerEmail()) == null)
			return false;
		
		// flight id's in reservation exists and they are unique
		HashMap<Long, Flight> flights = new HashMap<>();
		Flight flight = null;
		for (FlightReservationDetailsDTO detailDTO : dto.getFlights()) {
			Long id = detailDTO.getFlightId();
			if (flights.containsKey(id) || (flight = findFlight(id)) == null)
				return false;
			flights.put(id, flight);
		}

		// all requested seats must exist and must be available
		// number of automatic entry per flight must be 1 or multiple passengers with same details can exist
		for (FlightReservationDetailsDTO detailDTO : dto.getFlights()) {
			flight = flights.get(detailDTO.getFlightId());
			for (PassengerDTO passenger : detailDTO.getPassengers()) {
				Integer seatNum = passenger.getSeatNumber();
				Seat seat = findSeat(flight, seatNum);
				if (seat == null)
					return false;
				if (!seat.isAvailable())
					return false;
			}
			if (!checkAutoEntry(detailDTO.getPassengers()))
				return false;
		}
		// add validation for invited friends
		return true;
	}
	
	private boolean checkAutoEntry(List<PassengerDTO> passengers) {
		// check if number of automatic entry is valid
		int counter = 0;
		for (PassengerDTO p : passengers)
			if (p.isOwner())
				counter++;
		if (counter > 1)
			return false;
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
