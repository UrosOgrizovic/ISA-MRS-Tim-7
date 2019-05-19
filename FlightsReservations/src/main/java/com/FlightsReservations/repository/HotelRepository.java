package com.FlightsReservations.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.FlightsReservations.domain.Hotel;


public interface HotelRepository extends JpaRepository<Hotel, Long>{
	
	//@Query(value = "SELECT * FROM hotels WHERE hotel_name=")
	Hotel findByName(String name);
	
	@Query(
		value = "SELECT * FROM hotels WHERE hotel_id=?1 AND hotel_id=?2",
		nativeQuery = true
		)
	Object HaveRoom(Long hotelID, Long roomID);
	
	
	List<Hotel> findAll();
	
	
	Hotel save(Hotel hotel);
}
