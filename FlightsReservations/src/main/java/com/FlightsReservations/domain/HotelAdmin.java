package com.FlightsReservations.domain;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import com.FlightsReservations.domain.dto.HotelAdminDTO;
import com.FlightsReservations.domain.enums.AdminType;

@Entity
public class HotelAdmin extends Admin
{
	@OneToOne
	private Hotel hotel;//TODO: check if concurrent
	
	public HotelAdmin() {
		super();
	}
	
	public HotelAdmin(String firstName, String lastName, String email, String phone, String address, String password,
			String picturePath, Hotel hotel) 
	{
		super(firstName, lastName, email, phone, address, password, picturePath, "HOTEL");
		this.hotel = hotel;
	}

	public HotelAdmin(HotelAdminDTO dto) {
		// 
		this.setFirstName(dto.getFirstName());
		this.setLastName(dto.getLastName());
		this.setEmail(dto.getEmail()); 
		this.setPhone(dto.getPhone());
		this.setAddress(dto.getAddress());
		this.setPassword(dto.getPassword());
		this.setType(AdminType.HOTEL);
	}

	public Hotel getHotel()
	{
		return hotel;
	}

	public void setHotel(Hotel hotel)
	{
		this.hotel = hotel;
	}

	
}