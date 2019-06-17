package com.FlightsReservations.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.FlightsReservations.domain.RACS;

@Repository
public interface RACSRepository extends JpaRepository<RACS, Long> {
	RACS findByName(String name);
}
