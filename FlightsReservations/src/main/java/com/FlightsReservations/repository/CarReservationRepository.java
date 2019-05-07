package com.FlightsReservations.repository;


import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.FlightsReservations.domain.CarReservation;

public interface CarReservationRepository extends JpaRepository<CarReservation, Long> {
	@Query(value="SELECT * FROM car_reservation cr WHERE cr.owner_id IN (SELECT u.id FROM user u WHERE ?1 = u.id)", nativeQuery = true)
	Collection<CarReservation> findCarReservationsOfUser(Long userId);
}
