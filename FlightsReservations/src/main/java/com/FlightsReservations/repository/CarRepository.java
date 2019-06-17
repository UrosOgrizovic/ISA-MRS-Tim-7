package com.FlightsReservations.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.FlightsReservations.domain.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
}
