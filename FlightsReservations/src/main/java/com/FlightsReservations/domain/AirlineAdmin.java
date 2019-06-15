package com.FlightsReservations.domain;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

import com.FlightsReservations.domain.dto.AirlineAdminDTO;
import com.FlightsReservations.domain.enums.AdminType;

@Entity
@DiscriminatorValue("AA")
public class AirlineAdmin extends Admin {
	private static final long serialVersionUID = 5498946054745184534L;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Airline airline;

	public AirlineAdmin() {
		super();
	}

	public AirlineAdmin(String firstName, String lastName, String email, String phone, String address, String password,
			String picturePath, Airline airline) 
	{
		super(firstName, lastName, email, phone, address, password, picturePath, "AIRLINE");
		this.airline = airline;
	}

	public AirlineAdmin(AirlineAdminDTO dto) {
		this.setFirstName(dto.getFirstName());
		this.setLastName(dto.getLastName());
		this.setEmail(dto.getEmail());
		this.setPassword(dto.getPassword());
		this.setPhone(dto.getPhone());
		this.setAddress(dto.getAddress());
		this.setType(AdminType.AIRLINE);
	}

	public Airline getAirline() {
		return airline;
	}

	public void setAirline(Airline airline) {
		this.airline = airline;
	}
}