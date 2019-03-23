package com.FlightsReservations.repository;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import com.FlightsReservations.domain.User;

/*
 * This class will be used as dummy repository for purpose of first sprint. 
 * It will be replaced with repository that interacts with database later.
 */

@Repository
public class InMemoryUserRepository implements IRepository<User>{
	
	private AtomicLong counter = new AtomicLong(); // thread safe while Long is not
	private ConcurrentHashMap<Long, User> users = new ConcurrentHashMap<Long, User>();
	
	@Override
	public User create(User t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User update(User t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findOne(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<User> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
