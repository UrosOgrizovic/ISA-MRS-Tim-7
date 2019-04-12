package com.FlightsReservations.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.FlightsReservations.domain.Flight;
import com.FlightsReservations.domain.enums.SeatType;

public interface FlightRepository extends JpaRepository<Flight, Long>{
	@Query(value = "SELECT * FROM flight WHERE DATE(takeoff_time) = ?1 and DATE(landing_time) = ?2 AND " + 
				   "start_id = (SELECT id FROM airport WHERE name = ?3) AND " + 
				   "end_id = (SELECT id FROM airport where name = ?4) AND " + 
				   "?5 <= (SELECT COUNT(*) FROM seat " + 
				   "WHERE seat.flight_id = flight.id " + 
				   "AND seat.available = 1 and seat.type = 1)",
				   countQuery = "SELECT * FROM flight WHERE DATE(takeoff_time) = ?1 and DATE(landing_time) = ?2 AND " + 
						   "start_id = (SELECT id FROM airport WHERE name = ?3) AND " + 
						   "end_id = (SELECT id FROM airport where name = ?4) AND " + 
						   "?5 <= (SELECT COUNT(*) FROM seat " + 
						   "WHERE seat.flight_id = flight.id " + 
						   "AND seat.available = 1 and seat.type = 1)",
				   nativeQuery = true)

	Page<Flight> search(String takeoffTime, String landingTime, String startAirport, String endAirport, Integer numberOfPassengers, SeatType type, Pageable pageable);
}
