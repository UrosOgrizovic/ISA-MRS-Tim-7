package com.FlightsReservations.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.FlightsReservations.domain.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
	Company findByName(String name);
	
}
