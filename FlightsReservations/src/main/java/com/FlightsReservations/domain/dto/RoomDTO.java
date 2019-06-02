package com.FlightsReservations.domain.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.FlightsReservations.domain.Hotel;
import com.FlightsReservations.domain.enums.RoomType;

public class RoomDTO {
	@NotNull
	private double overallRating;
	@NotNull
	private double overNightStay;
	
	@NotNull
	private int number;
	
	@NotNull
	private int floor;
	
	@NotNull
	private int numberOfGuests;
	
	@NotBlank
	private RoomType type;
	
	private Hotel hotel;
	
	public RoomDTO() 
	{
		super();
	}
	
	public RoomDTO(double overallRating, double overNightStay, int number,
			int floor, int numberOfGuests, RoomType type)
	{
		this.overallRating = overallRating;
		this.overNightStay = overNightStay;
		this.number = number;
		this.floor = floor;
		this.numberOfGuests = numberOfGuests;
		this.type = type;
		this.hotel = null;
	}
	
		
	public RoomDTO(double overallRating, double overNightStay, int number,
			int floor, int numberOfGuests, String name, RoomType type, Hotel hotel)
	{
		this.overallRating = overallRating;
		this.overNightStay = overNightStay;
		this.number = number;
		this.floor = floor;
		this.numberOfGuests = numberOfGuests;
		this.type = type;
		this.hotel = hotel;
	}
	public double getOverallRating() 
	{
		return overallRating;
	}
	public void setOverallRating(double overallRating) 
	{
		this.overallRating = overallRating;
	}
	public double getOverNightStay() 
	{
		return overNightStay;
	}
	public void setOverNightStay(double overNightStay) 
	{
		this.overNightStay = overNightStay;
	}
	public Hotel getHotel() 
	{
		return hotel;
	}
	public void setHotel(Hotel hotel) 
	{
		this.hotel = hotel;
	}
	
	public int getNumber()
	{
		return number;
	}
	public void setNumber(int number)
	{
		this.number = number;
	}
	public int getNumberOfGuests()
	{
		return numberOfGuests;
	}
	public void setNumberOfGuests(int numberOfGuests)
	{
		this.numberOfGuests = numberOfGuests;
	}
	public RoomType getType()
	{
		return type;
	}
	public void setType(RoomType type)
	{
		this.type = type;
	}

	public int getFloor()
	{
		return floor;
	}


	public void setFloor(int floor)
	{
		this.floor = floor;
	}
	
	
}
