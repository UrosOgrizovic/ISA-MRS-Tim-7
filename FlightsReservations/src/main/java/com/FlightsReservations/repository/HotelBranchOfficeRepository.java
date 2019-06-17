package com.FlightsReservations.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import com.FlightsReservations.domain.HotelBranchOffice;

public interface HotelBranchOfficeRepository  extends JpaRepository<HotelBranchOffice, Long> {

	Collection<HotelBranchOffice> findByName(String name);

}
