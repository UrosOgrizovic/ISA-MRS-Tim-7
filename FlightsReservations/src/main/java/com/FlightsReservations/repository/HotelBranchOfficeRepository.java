package com.FlightsReservations.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.FlightsReservations.domain.Hotel;

public interface HotelBranchOfficeRepository  extends JpaRepository<Hotel, Long> {

}
