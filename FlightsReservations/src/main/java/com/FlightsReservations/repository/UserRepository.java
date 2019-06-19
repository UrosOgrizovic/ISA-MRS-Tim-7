package com.FlightsReservations.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.FlightsReservations.domain.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	User findByEmail(String email);

	@Query(value="SELECT COUNT(*) FROM user_friends WHERE user_id = ?1 AND friend_id = ?2", nativeQuery = true)
	Integer areFriends(Long userId, Long friendId);
}
