package com.FlightsReservations.repository;
import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.FlightsReservations.domain.RACS;

@Repository
public interface RACSRepository extends JpaRepository<RACS, Long> {
	Collection<RACS> findByName(String name);
}
