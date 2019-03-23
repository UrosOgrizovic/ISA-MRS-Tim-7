package com.FlightsReservations.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FlightsReservations.domain.User;
import com.FlightsReservations.repository.InMemoryUserRepository;

@Service
public class UserService implements IService<User> {
	
	@Autowired
	private InMemoryUserRepository repository; // Type of repository will be changed in future
	
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
