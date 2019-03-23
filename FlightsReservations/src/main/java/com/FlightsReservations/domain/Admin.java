package com.FlightsReservations.domain;

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
