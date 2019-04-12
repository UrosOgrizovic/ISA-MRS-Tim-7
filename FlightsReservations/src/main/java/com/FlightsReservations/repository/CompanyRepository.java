package com.FlightsReservations.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.FlightsReservations.domain.Company;

public interface CompanyRepository extends JpaRepository<Company, Long>{
	
	Company findByName(String name);
	
}
