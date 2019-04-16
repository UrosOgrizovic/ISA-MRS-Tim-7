package com.FlightsReservations.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.FlightsReservations.domain.RACSAdmin;

@Repository
public interface RACSAdminRepository extends JpaRepository<RACSAdmin, Long>{
}
