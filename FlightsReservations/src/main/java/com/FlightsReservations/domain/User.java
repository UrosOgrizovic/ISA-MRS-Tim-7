package com.FlightsReservations.domain;

import javax.persistence.Entity;

@Entity
public class User extends AbstractUser {
	
	// friends attribute will be added
	
	public User() {
		super();
	}

	public User(String firstName, String lastName, String email, String phone, String address, String password, String picturePath) {
		super(firstName, lastName, email, phone, address, password, picturePath);
	}
}
