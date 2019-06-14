package com.FlightsReservations.domain;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

import com.FlightsReservations.domain.dto.RACSAdminDTO;
import com.FlightsReservations.domain.enums.AdminType;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@DiscriminatorValue("RA")
public class RACSAdmin extends Admin
{
	private static final long serialVersionUID = -5528506607893893333L;
	
	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE})
	private RACS racs;
	
	public RACSAdmin() {
		super();
	}
	
	public RACSAdmin(String firstName, String lastName, String email, String phone, String address, String password,
			String picturePath, RACS racs) 
	{
		super(firstName, lastName, email, phone, address, password, picturePath, "RENTACAR");
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
			this.setType(AdminType.RENTACAR);
			this.setRACS(dto.getRacs());
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