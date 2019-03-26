package com.FlightsReservations.repository;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import com.FlightsReservations.domain.User;

/*
 * This class will be used as dummy repository for purpose of first sprint. 
 * It will be replaced with repository that interacts with database later.
 */

@Repository
public class InMemoryUserRepository implements IRepository<User, String>{
	private ConcurrentHashMap<String, User> users = new ConcurrentHashMap<String, User>();
	
	@PostConstruct
	private void init_users() {
		User u1 = new User(123L, "pera", "peric", "pera@gmail.com", "123", "ul bb", "sifra", "path");
		User u2 = new User(456L, "mika", "mikic", "mika@gmail.com", "321", "ul bb2", "sifra2", "path2");
		create(u1);
		create(u2);
		System.out.println("USERS CREATED!");
	}
	
	@Override
	public User create(User t) {
		users.put(t.getEmail(), t);
		return t;
	}

	@Override
	public User update(User t) {
		users.put(t.getEmail(), t);
		return t;
	}

	@Override
	public User findOne(String email) {
		return users.get(email);
	}

	@Override
	public void delete(String email) {
		// TODO Auto-generated method stub
	}

	@Override
	public Collection<User> findAll() {
		return users.values();
	}

}
