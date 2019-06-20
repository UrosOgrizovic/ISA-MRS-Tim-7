package com.FlightsReservations.domain;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.FlightsReservations.domain.enums.AdminType;

@Entity
@DiscriminatorValue("A")
public class Admin extends AbstractUser 
{
	private static final long serialVersionUID = -6075524980472282776L;
	private AdminType type;
	
	public Admin() {
	}
	
	public Admin(String firstName, String lastName, String email, String phone, String address, String password,
			String picturePath, String type) 
	{
		super(firstName, lastName, email, phone, address, password, true);
		this.type = AdminType.valueOf(type);
	}
	
	public AdminType getType()
	{
		return type;
	}

	public void setType(AdminType type)
	{
		this.type = type;
	}

}