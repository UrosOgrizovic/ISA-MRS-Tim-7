package com.FlightsReservations.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.FlightsReservations.domain.Airline;

public interface AirlineBranchOfficeRepository  extends JpaRepository<Airline, Long> {

}
