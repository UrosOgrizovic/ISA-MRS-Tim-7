package com.FlightsReservations.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.FlightsReservations.domain.CarReservation;

public interface CarReservationRepository extends JpaRepository<CarReservation, Long> {

}
