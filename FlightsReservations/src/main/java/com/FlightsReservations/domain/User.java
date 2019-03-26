package com.FlightsReservations.domain;

public class User extends AbstractUser {
	
	// Attributes and methods for manipulating friends
	
		/* Price discounts (if they are bound to our application)
		 * 
		 * If they are bound to particular hotel/airline-company/rent-a-car 
		 * where will we keep discount data? User or specific company?
		 * 
		 */
	
	public User() {
		super();
	}

	public User(Long id, String firstName, String lastName, String email, String phone, String address, String password, String picturePath) {
		super(id, firstName, lastName, email, phone, address, password, picturePath);
	}
	
}
