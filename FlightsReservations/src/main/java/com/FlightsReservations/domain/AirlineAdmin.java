package com.FlightsReservations.domain;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import com.FlightsReservations.domain.dto.AirlineAdminDTO;

@Entity
public class AirlineAdmin extends Admin
{
	@OneToOne
	private Airline airline;//TODO: check if concurrent, edit later
	
	public AirlineAdmin() {
		super();
	}
	
	public AirlineAdmin(String firstName, String lastName, String email, String phone, String address, String password,
			String picturePath, Airline airline) 
	{
		super(firstName, lastName, email, phone, address, password, picturePath);
		this.airline = airline;
	}

	public AirlineAdmin(AirlineAdminDTO dto) {
		this.setFirstName(dto.getFirstName());
		this.setLastName(dto.getLastName());
		this.setEmail(dto.getEmail());
		this.setPassword(dto.getPassword());
		this.setPhone(dto.getPhone());
		this.setAddress(dto.getAddress());
	}

	public Airline getAirline()
	{
		return airline;
	}

	public void setAirline(Airline airline)
	{
		this.airline = airline;
	}

	
}