package com.FlightsReservations.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.FlightsReservations.domain.Flight;

public interface FlightRepository extends JpaRepository<Flight, Long>{

}
