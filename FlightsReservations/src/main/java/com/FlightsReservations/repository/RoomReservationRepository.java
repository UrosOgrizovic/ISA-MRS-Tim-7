package com.FlightsReservations.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.FlightsReservations.domain.RoomReservation;

public interface RoomReservationRepository extends JpaRepository<RoomReservation, Long> {
	@Query(value="SELECT * FROM room_reservation rr WHERE rr.owner_id in (SELECT u.id FROM user u WHERE ?1 = u.id)", nativeQuery = true)
	Collection<RoomReservation> findRoomReservationsOfUser(Long userId);

}
