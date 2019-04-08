package com.FlightsReservations.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.FlightsReservations.domain.Airline;

@Repository
public interface AirlineRepository extends JpaRepository<Airline, Long>{
	Airline findByName(String name);
}
