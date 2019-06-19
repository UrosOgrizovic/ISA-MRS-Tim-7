package com.FlightsReservations.domain.dto;

import com.FlightsReservations.domain.enums.RoomType;

public class SearchRoomDTO
{	
	private String numberOfGuests;
	
	private String floor;
	
	private String type;

	private String overallRating;
	
	private String overNightStay;
	
	private String hotelName;

	public SearchRoomDTO(String type, String floor, String numberOfGuests, String overallRating, String overNightStay, String hotelName)
	{
		this.numberOfGuests = numberOfGuests;
		this.floor = floor;
		this.type = type;
		this.overallRating = overallRating;
		this.overNightStay = overNightStay;
		this.hotelName = hotelName;
	}

	
	public String getHotelName()
	{
		return hotelName;
	}


	public void setHotelName(String hotelName)
	{
		this.hotelName = hotelName;
	}


	public String getNumberOfGuests()
	{
		return numberOfGuests;
	}

	public void setNumberOfGuests(String numberOfGuests)
	{
		this.numberOfGuests = numberOfGuests;
	}

	public String getFloor()
	{
		return floor;
	}

	public void setFloor(String floor)
	{
		this.floor = floor;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getOverallRating()
	{
		return overallRating;
	}

	public void setOverallRating(String overallRating)
	{
		this.overallRating = overallRating;
	}

	public String getOverNightStay()
	{
		return overNightStay;
	}

	public void setOverNightStay(String overNightStay)
	{
		this.overNightStay = overNightStay;
	}
	
	
}
