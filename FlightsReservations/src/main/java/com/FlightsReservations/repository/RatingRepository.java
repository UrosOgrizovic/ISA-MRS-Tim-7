package com.FlightsReservations.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.FlightsReservations.domain.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
	@Query(value="SELECT * FROM rating r WHERE r.reservation_id=?1", nativeQuery = true)
	Rating findByReservationId(Long reservationId);
}
