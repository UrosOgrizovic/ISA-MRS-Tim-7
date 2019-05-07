package com.FlightsReservations.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("A")
public class Admin extends AbstractUser
{
	private AdminType type;

	public AdminType getType()
	{
		return type;
	}

	public void setType(AdminType type)
	{
		this.type = type;
	}
	
}
