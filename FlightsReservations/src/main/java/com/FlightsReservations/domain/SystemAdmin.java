package com.FlightsReservations.domain;

import javax.persistence.Entity;

import com.FlightsReservations.domain.dto.SystemAdminDTO;
import com.FlightsReservations.domain.enums.AdminType;

@Entity
public class SystemAdmin extends Admin
{
	private static final long serialVersionUID = 1L;

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
