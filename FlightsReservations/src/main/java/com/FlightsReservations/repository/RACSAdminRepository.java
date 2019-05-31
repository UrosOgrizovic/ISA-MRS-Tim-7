package com.FlightsReservations.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.FlightsReservations.domain.RACSAdmin;

@Repository
public interface RACSAdminRepository extends JpaRepository<RACSAdmin, Long>{
	RACSAdmin findByEmail(String email);
	Set<RACSAdmin> findByEmailIn(Set<String> racsAdmins);
	void deleteByEmail(String email);
}
