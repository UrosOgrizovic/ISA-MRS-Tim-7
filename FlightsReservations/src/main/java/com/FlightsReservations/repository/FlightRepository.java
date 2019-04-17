package com.FlightsReservations.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.FlightsReservations.domain.Flight;

public interface FlightRepository extends JpaRepository<Flight, Long>{
	@Query(value = "SELECT * FROM flight " + 
                   "WHERE DATE(takeoff_time) = ?1 AND DATE(landing_time) = ?2 AND " + 
				   "start_id = (SELECT id FROM airport WHERE name = ?3) AND " + 
				   "end_id = (SELECT id FROM airport where name = ?4) AND " + 
				   "(?5 <= (SELECT COUNT(*) FROM seat WHERE seat.flight_id = flight.id AND seat.available = 1 AND (seat.type = ?6 OR ?6 IS NULL)) OR ?5 IS NULL)",
		   countQuery = "SELECT COUNT(*) FROM flight " + 
		                "WHERE DATE(takeoff_time) = ?1 AND DATE(landing_time) = ?2 AND " + 
						"start_id = (SELECT id FROM airport WHERE name = ?3) AND " + 
						"end_id = (SELECT id FROM airport where name = ?4) AND " + 
						"(?5 <= (SELECT COUNT(*) FROM seat WHERE seat.flight_id = flight.id AND seat.available = 1 AND (seat.type = ?6 OR ?6 IS NULL)) OR ?5 IS NULL)",
		   nativeQuery = true)
	Page<Flight> search(String takeoffTime, String landingTime, String startAirport, String endAirport, Integer numberOfPassengers, Long type, Pageable pageable);
}
