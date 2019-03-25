package com.FlightsReservations.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FlightsReservations.domain.User;
import com.FlightsReservations.repository.InMemoryUserRepository;

@Service
public class UserService implements IService<User,String> {
	
	@Autowired
	private InMemoryUserRepository repository; // Type of repository will be changed in future
	
	@Override
	public User create(User t) {
		return repository.create(t);
	}

	@Override
	public User update(User t) {
		if (repository.findOne(t.getEmail()) == null)
			return null;
		return repository.update(t);
	}

	@Override
	public User findOne(String email) {
		return repository.findOne(email);
	}

	@Override
	public void delete(String email) {
		// TODO Auto-generated method stub
	}

	@Override
	public Collection<User> findAll() {
		return repository.findAll();
	}
}
