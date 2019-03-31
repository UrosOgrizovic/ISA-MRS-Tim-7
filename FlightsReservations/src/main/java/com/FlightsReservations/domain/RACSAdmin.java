package com.FlightsReservations.domain;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class RACSAdmin extends Admin
{
	private Set<RACS> racsSet;//TODO: check if concurrent, edit later
	
	public RACSAdmin() {
		super();
	}
	
	public RACSAdmin(Long id, String firstName, String lastName, String email, String phone, String address, String password,
			String picturePath, Set<RACS> racsSet) 
	{
		super(id,  firstName, lastName, email, phone, address, password, picturePath);
		this.racsSet = racsSet;
	}

	public Set<RACS> getHotelSet()
	{
		return racsSet;
	}

	public void setHotelSet(Set<RACS> racsSet)
	{
		this.racsSet = racsSet;
	}

	
}