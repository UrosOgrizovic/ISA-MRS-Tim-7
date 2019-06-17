package com.FlightsReservations.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.FlightsReservations.domain.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long>{
	
	List<Room> findByNumber(int number);
	List<Room> findByHotel_id(Long hotel_id);
	
	@Query(
		value = "SELECT * FROM rooms WHERE hotel_id=?1 AND hotel_id=?2",
		nativeQuery = true
		)
	Object HaveRoom(Long hotelID, Long roomID);//TODO: update later
}