package com.FlightsReservations.domain;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class AirlineAdmin extends Admin
{
	private Set<Airline> airlineSet;//TODO: check if concurrent, edit later
	
	public AirlineAdmin() {
		super();
	}
	
	public AirlineAdmin(Long id, String firstName, String lastName, String email, String phone, String address, String password,
			String picturePath, Set<Airline> airlineSet) 
	{
		super(id,  firstName, lastName, email, phone, address, password, picturePath);
		this.airlineSet = airlineSet;
	}

	public Set<Airline> getAirlinelSet()
	{
		return airlineSet;
	}

	public void setAirlinelSet(Set<Airline> airlineSet)
	{
		this.airlineSet = airlineSet;
	}

	
}