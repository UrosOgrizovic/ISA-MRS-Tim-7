package com.FlightsReservations.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.FlightsReservations.domain.Seat;

public interface SeatRepository extends JpaRepository<Seat, Long>{
	
	@Query(value = "SELECT * FROM seat WHERE flight_id = ?1 AND seat_number = ?2", nativeQuery = true)
	Seat findInFlight(Long flightID, Long seatNum);
	
	@Query(value = "SELECT MAX(seat_number) FROM seat WHERE flight_id = ?1", nativeQuery = true)
	Integer maxSeatNumber(Long flightID);
}
