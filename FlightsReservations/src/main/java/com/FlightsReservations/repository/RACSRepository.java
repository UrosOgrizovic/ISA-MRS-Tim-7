package com.FlightsReservations.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.FlightsReservations.domain.Car;
import com.FlightsReservations.domain.RACS;

@Repository
public interface RACSRepository extends JpaRepository<RACS, Long> {
	RACS findByName(String name);
}
