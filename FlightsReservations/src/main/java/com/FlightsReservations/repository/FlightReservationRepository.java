package com.FlightsReservations.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.FlightsReservations.domain.FlightReservation;

public interface FlightReservationRepository extends JpaRepository<FlightReservation, Long>{

}
