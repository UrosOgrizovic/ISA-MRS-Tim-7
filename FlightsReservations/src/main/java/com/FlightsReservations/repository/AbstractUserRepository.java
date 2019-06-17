package com.FlightsReservations.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.FlightsReservations.domain.AbstractUser;

public interface AbstractUserRepository extends JpaRepository<AbstractUser, Long>{
	AbstractUser findByEmail(String email);
	
	@Query(value="SELECT * FROM users u WHERE u.id IN (SELECT uf.friend_id FROM user_friends uf WHERE ?1 = uf.user_id)", nativeQuery = true)
	List<AbstractUser> findFriends(Long id);
	
	@Transactional
	@Modifying
	@Query(value="INSERT INTO user_friends VALUES (?1, ?2)", nativeQuery = true)
	void addFriend(Long userId, Long friendId);	
	
	@Query(value="SELECT COUNT(*) FROM user_friends WHERE user_id = ?1 AND friend_id = ?2", nativeQuery = true)
	Integer areFriends(Long userId, Long friendId);
}
