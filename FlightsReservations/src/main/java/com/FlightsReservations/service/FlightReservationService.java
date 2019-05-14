package com.FlightsReservations.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;
import java.util.Set;

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
		
		//TODO: check if flight is on discount. If yes, then total = total - total*discount/100
		
		FlightReservation reservation = new FlightReservation(new Date(), total, owner, true);

		Seat seat;
		Passenger passenger;
		Flight flight;
		for (FlightReservationDetailsDTO dtos : dto.getFlights()) {
			flight = findFlight(dtos.getFlightId());
			for (PassengerDTO pdto : dtos.getPassengers()) {
				// seat doesn't exists or if seat is already taken
				seat = findAvailableSeat(flight, pdto.getSeatNumber());
				if (seat != null) {
					// TODO : based on seat type add additional cost (pricelist service/controller needed first)
					// TODO : based on user travel points reduce cost
					seat.setAvailable(false);
					total += (float) flight.getPrice();
					passenger = new Passenger(pdto, seat);
					reservation.getPassengers().add(passenger);
					reservation.getFlights().add(flight);
				} else {
					return null;
				}
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
		
		// flight id's in reservation exists and they are unique inside reservation
		HashMap<Long, Flight> flights = new HashMap<>();
		Flight flight = null;
		for (FlightReservationDetailsDTO detailDTO : dto.getFlights()) {
			Long id = detailDTO.getFlightId();
			if (flights.containsKey(id) || (flight = findFlight(id)) == null)
				return false;
			flights.put(id, flight);
		}

		// every passport must be unique for that flight
		Set<String> flightPassports;
		for (FlightReservationDetailsDTO detailDTO : dto.getFlights()) {
			flight = flights.get(detailDTO.getFlightId());
			flightPassports = flightRepository.findPassportsInFlight(flight.getId());
			for (PassengerDTO passenger : detailDTO.getPassengers()) {
				if (flightPassports.contains(passenger.getPassport()))
					return false;
				flightPassports.add(passenger.getPassport());
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

	private Seat findAvailableSeat(Flight f, Integer seatNum) {
		for (Seat s : f.getSeats())
			if (s.getSeatNumber().equals(seatNum) && s.isAvailable())
				return s;
		return null;
	}
	
	public boolean cancel(Long id) {
		FlightReservation fr = repository.findById(id).get();
		
		if (fr != null) {
			Date now = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date minDate = new Date();
			try {
				minDate = sdf.parse("2050-05-05");
			} catch (ParseException e) {
				e.printStackTrace();
			}
			for (Flight f : fr.getFlights()) {
				Date takeoffTime = f.getTakeoffTime();
				if (takeoffTime.before(minDate)) {
					minDate = takeoffTime;
				}
			}
			// difference between now (cancellation time) and reservation start time in hours
			long diff = (minDate.getTime() - now.getTime()) / (60 * 60 * 1000);
			System.out.println(diff);
			// reservation cannot be cancelled less than two days before the reservation starts
			if (diff < 3) return false;
			
			fr.setConfirmed(false);
			repository.save(fr);
			return true;
		}
		return false;
	}
}
