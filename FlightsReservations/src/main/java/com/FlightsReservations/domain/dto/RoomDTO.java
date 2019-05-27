package com.FlightsReservations.domain.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.FlightsReservations.domain.Hotel;

public class RoomDTO {
	@NotNull
	private double overallRating;
	@NotNull
	private double overNightStay;
	
	@NotNull
	private int number;
	
	@NotNull
	private int floor;
	
	@NotBlank
	private String type;
	
	private Hotel hotel;
	
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
	public int getFloor()
	{
		return floor;
	}
	public void setFloor(int floor)
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
	public RoomDTO() 
	{
		super();
	}
}
