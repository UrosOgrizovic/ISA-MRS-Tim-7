package com.FlightsReservations.domain;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class HotelAdmin extends Admin
{
	private Set<Hotel> hotelSet;//TODO: check if concurrent
	
	public HotelAdmin() {
		super();
	}
	
	public HotelAdmin(Long id, String firstName, String lastName, String email, String phone, String address, String password,
			String picturePath, Set<Hotel> hotelSet) 
	{
		super(id,  firstName, lastName, email, phone, address, password, picturePath);
		this.hotelSet = hotelSet;
	}

	public Set<Hotel> getHotelSet()
	{
		return hotelSet;
	}

	public void setHotelSet(Set<Hotel> hotelSet)
	{
		this.hotelSet = hotelSet;
	}

	
}