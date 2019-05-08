package com.FlightsReservations.domain;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import com.FlightsReservations.domain.dto.RACSAdminDTO;

@Entity
public class RACSAdmin extends Admin
{
	@OneToOne
	private RACS racs;//TODO: check if concurrent, edit later
	
	public RACSAdmin() {
		super();
	}
	
	public RACSAdmin(String firstName, String lastName, String email, String phone, String address, String password,
			String picturePath, RACS racs) 
	{
		super(firstName, lastName, email, phone, address, password, picturePath);
		this.racs = racs;
	}
	
	public RACSAdmin(RACSAdminDTO dto)
	{ 
			this.setFirstName(dto.getFirstName());
			this.setLastName(dto.getLastName());
			this.setEmail(dto.getEmail()); 
			this.setPhone(dto.getPhone());
			this.setAddress(dto.getAddress());
			this.setPassword(dto.getPassword());
	}

	public RACS getRACS()
	{
		return racs;
	}

	public void setRACS(RACS racs)
	{
		this.racs = racs;
	}

	
}