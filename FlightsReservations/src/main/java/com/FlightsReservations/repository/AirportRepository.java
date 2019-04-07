package com.FlightsReservations.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.FlightsReservations.domain.Airport;

public interface AirportRepository extends JpaRepository<Airport, Long>{
	Airport findByName(String name);
}
