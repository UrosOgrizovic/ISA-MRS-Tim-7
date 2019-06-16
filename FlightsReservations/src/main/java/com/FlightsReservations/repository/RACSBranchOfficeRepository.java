package com.FlightsReservations.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.FlightsReservations.domain.RACSBranchOffice;

@Repository
public interface RACSBranchOfficeRepository  extends JpaRepository<RACSBranchOffice, Long>{
	RACSBranchOffice findByName(String name);
}