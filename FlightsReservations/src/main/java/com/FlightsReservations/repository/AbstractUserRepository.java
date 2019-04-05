package com.FlightsReservations.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.FlightsReservations.domain.AbstractUser;

public interface AbstractUserRepository extends JpaRepository<AbstractUser, Long>{
	AbstractUser findByEmail(String email);
}
