package com.FlightsReservations.repository;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.FlightsReservations.domain.Flight;

public interface FlightRepository extends JpaRepository<Flight, Long>{
	@Query(value = "SELECT * FROM flight " + 
                   "WHERE DATE(takeoff_time) = ?1 AND " + 
				   "start_id IN (SELECT id FROM airport WHERE city = ?2) AND " + 
				   "end_id IN (SELECT id FROM airport where city = ?3) AND " + 
				   "(?4 <= (SELECT COUNT(*) FROM seat WHERE seat.flight_id = flight.id AND seat.available = 1 AND (seat.type = ?5 OR ?5 IS NULL)) OR ?4 IS NULL)",
		   countQuery = "SELECT COUNT(*) FROM flight " + 
                   "WHERE DATE(takeoff_time) = ?1 AND " + 
				   "start_id IN (SELECT id FROM airport WHERE city = ?2) AND " + 
				   "end_id IN (SELECT id FROM airport where city = ?3) AND " + 
				   "(?4 <= (SELECT COUNT(*) FROM seat WHERE seat.flight_id = flight.id AND seat.available = 1 AND (seat.type = ?5 OR ?5 IS NULL)) OR ?4 IS NULL)",
		   nativeQuery = true)
	Page<Flight> search(String takeoffDate, String startCity, String endCity, Integer numberOfPassengers, Long type, Pageable pageable);

	@Query(value = "SELECT DISTINCT(passenger.passport) FROM passenger "
				 + "WHERE passenger.seat_id IN (SELECT seat.id FROM seat WHERE seat.flight_id = ?1)",
		   nativeQuery = true)
	Set<String> findPassportsInFlight(Long id);

}
