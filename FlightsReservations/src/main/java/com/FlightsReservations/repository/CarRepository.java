package com.FlightsReservations.repository;


import java.util.Optional;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.FlightsReservations.domain.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

	Optional<Car> findById(Long id);

	// reading not allowed (so that no one else can use the same car to make a car reservation), writing not allowed
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query(value="SELECT c FROM Car c WHERE c.id = :id")
	Optional<Car> reservationFindById(@Param("id") Long id);
	
	
}
