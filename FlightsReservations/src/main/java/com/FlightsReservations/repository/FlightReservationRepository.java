package com.FlightsReservations.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.FlightsReservations.domain.FlightReservation;

public interface FlightReservationRepository extends JpaRepository<FlightReservation, Long>{
	@Query(value="SELECT * FROM flight_reservation fr WHERE fr.owner_id IN (SELECT u.id FROM user u WHERE ?1 = u.id)", nativeQuery = true)
	Collection<FlightReservation> findFlightReservationsOfUser(Long id);
}
