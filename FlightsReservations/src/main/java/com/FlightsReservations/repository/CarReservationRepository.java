package com.FlightsReservations.repository;


import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.FlightsReservations.domain.CarReservation;

public interface CarReservationRepository extends JpaRepository<CarReservation, Long> {
	@Query(value="SELECT * FROM reservation r WHERE r.reservation_type='CR' AND r.owner_id IN (SELECT u.id FROM users u WHERE u.user_type = 'U' AND ?1 = u.id)", nativeQuery = true)
	Collection<CarReservation> findCarReservationsOfUser(Long userId);
	
	@Query(value="SELECT * FROM reservation r WHERE r.reservation_type='CR' AND r.owner_id IN (SELECT u.id FROM users u WHERE u.user_type = 'A' AND ?1 = u.id)", nativeQuery = true)
	Collection<CarReservation> findCarReservationsOfAdmin(Long adminId);
	
	@Query(value="SELECT * FROM reservation r WHERE r.reservation_type='CR' AND r.car_id IN (SELECT c.id FROM car c WHERE r.car_id = c.id AND ?1 = c.racs_id)", nativeQuery = true)
	Collection<CarReservation> findCarReservationsOfRacs(Long racsId);
}
