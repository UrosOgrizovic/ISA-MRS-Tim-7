package com.FlightsReservations.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.FlightsReservations.domain.HotelPricelistItem;

@Repository
public interface HotelPricelistItemRepository extends JpaRepository<HotelPricelistItem, Long>{
	HotelPricelistItem findByName(String name);

}
