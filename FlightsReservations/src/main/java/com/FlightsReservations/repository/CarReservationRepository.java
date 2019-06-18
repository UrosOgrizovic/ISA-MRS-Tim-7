package com.FlightsReservations.repository;


import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.FlightsReservations.domain.CarReservation;

@Repository
public interface CarReservationRepository extends JpaRepository<CarReservation, Long> {
	@Query(value="SELECT * FROM reservation r WHERE r.reservation_type='CR' AND r.owner_id IN (SELECT u.id FROM users u WHERE ?1 = u.id)", nativeQuery = true)
	Collection<CarReservation> findCarReservationsOfUser(Long userId);
	
	@Query(value="SELECT * FROM reservation r WHERE r.reservation_type='CR' AND ( (r.start_time >= ?1 AND r.start_time <= ?2) OR (r.end_time >= ?1 AND r.end_time <= ?2) "
																				+ "OR (?1 >= r.start_time AND ?1 <= r.end_time) OR (?2 >= r.start_time AND ?2 <= r.end_time) )", nativeQuery = true)
	Collection<CarReservation> findCarReservationsForPeriod(Date startTime, Date endTime);
	
	@Query(value="SELECT * FROM reservation r WHERE r.reservation_type='CR' AND r.car_id IN (SELECT c.id FROM car c WHERE r.car_id = c.id AND ?1 = c.racs_branch_office_id)", nativeQuery = true)
	Collection<CarReservation> findCarReservationsOfRacsBranchOffice(Long racsBranchOfficeId);
<<<<<<< HEAD
}
=======
}
>>>>>>> master
