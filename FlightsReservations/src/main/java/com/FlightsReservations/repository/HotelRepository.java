package com.FlightsReservations.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.FlightsReservations.domain.Hotel;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long>{
	
	Hotel findByName(String name);
	
	@Query(
		value = "SELECT * FROM hotels WHERE hotel_id=?1 AND hotel_id=?2",
		nativeQuery = true
		)
	Object HaveRoom(Long hotelID, Long roomID);
}
