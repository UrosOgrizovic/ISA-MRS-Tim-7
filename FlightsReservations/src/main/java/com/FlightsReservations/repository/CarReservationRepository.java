package com.FlightsReservations.repository;


import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.FlightsReservations.domain.CarReservation;

@Repository
public interface CarReservationRepository extends JpaRepository<CarReservation, Long> {
	@Query(value="SELECT * FROM reservation r WHERE r.dtype='CarReservation' AND r.owner_id IN (SELECT u.id FROM users u WHERE u.dtype = 'User' AND ?1 = u.id)", nativeQuery = true)
	Collection<CarReservation> findCarReservationsOfUser(Long userId);
}
