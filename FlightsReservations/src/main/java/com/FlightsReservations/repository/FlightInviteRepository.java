package com.FlightsReservations.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.FlightsReservations.domain.FlightInvite;
@Repository
public interface FlightInviteRepository extends JpaRepository<FlightInvite, Long>{

}
