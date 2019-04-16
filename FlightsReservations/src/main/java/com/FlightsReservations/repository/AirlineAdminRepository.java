package com.FlightsReservations.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.FlightsReservations.domain.AirlineAdmin;

@Repository
public interface AirlineAdminRepository extends JpaRepository<AirlineAdmin, Long>{
}
