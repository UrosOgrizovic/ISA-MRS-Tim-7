package com.FlightsReservations.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.FlightsReservations.domain.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
	Authority findByName(String name);
}
