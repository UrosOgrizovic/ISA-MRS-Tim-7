package com.FlightsReservations.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.FlightsReservations.domain.Reservation;

@Repository
public interface ReservationRepository  extends JpaRepository<Reservation, Long> {

}
