package com.FlightsReservations.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.FlightsReservations.domain.Car;


public interface CarRepository extends JpaRepository<Car, Long> {

}
