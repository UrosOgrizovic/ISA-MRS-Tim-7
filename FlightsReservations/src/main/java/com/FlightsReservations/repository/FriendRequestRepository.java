package com.FlightsReservations.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.FlightsReservations.domain.FriendRequest;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
	@Query(value="SELECT * FROM friend_request WHERE (sender_id = ?1 AND reciever_id = ?2) OR (sender_id = ?2 AND reciever_id = ?1)", nativeQuery = true)
	FriendRequest findFriendRequest(Long senderId, Long recieverId);
}
