package com.FlightsReservations.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.FlightsReservations.domain.RoomReservation;
@Repository
public interface RoomReservationRepository extends JpaRepository<RoomReservation, Long> {
	@Query(value="SELECT * FROM reservation r WHERE r.reservation_type='RR' AND r.owner_id in (SELECT u.id FROM users u WHERE ?1 = u.id)", nativeQuery = true)
	Collection<RoomReservation> findRoomReservationsOfUser(Long userId);

}
