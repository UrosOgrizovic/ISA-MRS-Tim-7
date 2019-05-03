package com.FlightsReservations.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.FlightsReservations.domain.RoomReservation;

public interface RoomReservationRepository extends JpaRepository<RoomReservation, Long> {

}
