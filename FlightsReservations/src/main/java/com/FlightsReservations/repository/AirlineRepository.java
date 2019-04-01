package com.FlightsReservations.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.FlightsReservations.domain.Airline;

public interface AirlineRepository extends JpaRepository<Airline, Long>{
	Airline findByName(String name);
}
