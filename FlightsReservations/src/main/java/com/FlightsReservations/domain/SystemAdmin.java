package com.FlightsReservations.domain;

import javax.persistence.Entity;

import com.FlightsReservations.domain.dto.SystemAdminDTO;

@Entity
public class SystemAdmin extends Admin
{
	public SystemAdmin() { this.setType(AdminType.SYSTEM);}
	
	
	public SystemAdmin(String firstName, String lastName, String email, String phone, String address, String password,
			String picturePath) 
	{
		super(firstName, lastName, email, phone, address, password, picturePath, "SYSTEM");
	}

	public SystemAdmin(SystemAdminDTO dto) {
		// 
		this.setFirstName(dto.getFirstName());
		this.setLastName(dto.getLastName());
		this.setEmail(dto.getEmail()); 
		this.setPhone(dto.getPhone());
		this.setAddress(dto.getAddress());
		this.setPassword(dto.getPassword());
		this.setType(AdminType.SYSTEM);
	}
}
