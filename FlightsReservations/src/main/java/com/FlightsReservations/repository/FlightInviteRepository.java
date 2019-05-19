package com.FlightsReservations.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.FlightsReservations.domain.FlightInvite;

public interface FlightInviteRepository extends JpaRepository<FlightInvite, Long>{

}
