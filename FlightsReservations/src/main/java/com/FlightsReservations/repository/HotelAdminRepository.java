package com.FlightsReservations.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.FlightsReservations.domain.AirlineAdmin;
import com.FlightsReservations.domain.HotelAdmin;

@Repository
public interface HotelAdminRepository extends JpaRepository<HotelAdmin, Long>{
	HotelAdmin findByEmail(String email);
	Set<HotelAdmin> findByEmailIn(Set<String> hotelAdmins);
}
