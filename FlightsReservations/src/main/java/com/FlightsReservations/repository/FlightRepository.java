package com.FlightsReservations.repository;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.FlightsReservations.domain.Flight;
@Repository
public interface FlightRepository extends JpaRepository<Flight, Long>{
	@Query(value = "SELECT * FROM flight " + 
                   "WHERE to_char(takeoff_time, 'DD-MM-YYYY') = ?1 AND " + 
				   "start_id IN (SELECT id FROM airport WHERE city = ?2) AND " + 
				   "end_id IN (SELECT id FROM airport where city = ?3) AND " + 
				   "(?4 <= (SELECT COUNT(*) FROM seat WHERE seat.flight_id = flight.id AND (seat.available = true) AND (seat.type = ?5 OR ?5 = 0)) OR ?4 = 0)",
		   
			countQuery = "SELECT COUNT(*) FROM flight " + 
                   "WHERE to_char(takeoff_time, 'DD-MM-YYYY') = ?1 AND " + 
				   "start_id IN (SELECT id FROM airport WHERE city = ?2) AND " + 
				   "end_id IN (SELECT id FROM airport where city = ?3) AND " + 
				   "(?4 <= (SELECT COUNT(*) FROM seat WHERE seat.flight_id = flight.id AND (seat.available = true) AND (seat.type = ?5 OR ?5 = 0)) OR ?4 = 0)",
		   nativeQuery = true)
	Page<Flight> search(String takeoffDate, String startCity, String endCity, int numberOfPassengers, int type, Pageable pageable);

	@Query(value = "SELECT DISTINCT(passenger.passport) FROM passenger "
				 + "WHERE passenger.seat_id IN (SELECT seat.id FROM seat WHERE seat.flight_id = ?1)",
		   nativeQuery = true)
	Set<String> findPassportsInFlight(Long id);

}
