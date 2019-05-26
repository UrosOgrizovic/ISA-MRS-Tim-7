package com.FlightsReservations.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.FlightsReservations.domain.SystemAdmin;

@Repository
public interface SystemAdminRepository extends JpaRepository<SystemAdmin, Long>
{
	SystemAdmin findByEmail(String email);
	Set<SystemAdmin> findByEmailIn(Set<String> systemAdmins);
}
