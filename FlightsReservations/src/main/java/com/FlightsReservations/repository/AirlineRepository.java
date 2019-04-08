package com.FlightsReservations.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.FlightsReservations.domain.Airline;

@Repository
public interface AirlineRepository extends JpaRepository<Airline, Long>{
	Airline findByName(String name);
	
	@Query(
		value = "SELECT * FROM airline_airports WHERE airline_id=?1 AND airport_id=?2",
		nativeQuery = true
		)
	Object airlineHaveAirport(Long airlineID, Long airportID);
}
