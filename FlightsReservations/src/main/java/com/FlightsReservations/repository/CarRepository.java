package com.FlightsReservations.repository;


import java.util.Optional;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import com.FlightsReservations.domain.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
	// reading not allowed (so that no one else can use the same car to make a car reservation), writing not allowed
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	Optional<Car> findById(Long id);
}
