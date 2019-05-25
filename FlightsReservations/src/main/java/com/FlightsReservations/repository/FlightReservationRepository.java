package com.FlightsReservations.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.FlightsReservations.domain.FlightReservation;

public interface FlightReservationRepository extends JpaRepository<FlightReservation, Long>{
	@Query(value="SELECT * FROM reservation r WHERE r.dtype='FlightReservation' AND r.owner_id IN (SELECT u.id FROM users u WHERE u.dtype = 'User' AND ?1 = u.id)", nativeQuery = true)
	Collection<FlightReservation> findFlightReservationsOfUser(Long id);
	
	@Query(value="SELECT r FROM FlightReservation r WHERE r.discount > 0 AND r.owner IS NULL")
	List<FlightReservation> findQuickReservations();	
}
