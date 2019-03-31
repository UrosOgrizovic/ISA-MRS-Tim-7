package com.FlightsReservations.domain;

import java.util.Date;
import java.util.Set;

public class Room
{
	private Long roomID;//TODO: room number - unique
	private int floorNumber;
	private String type;
	private String description;
	private double overallRating;//1-5
	private Set<Date> reservations;
	private Hotel hotel;
	private String imagePath;
	//TODO: add more attributes?
	
	public Room() {}
	
	

	public Room(Long roomID, int floorNumber, String type, String description, double overallRating, Set<Date> reservations, Hotel hotel, String imagePath)
	{
		this.roomID = roomID;
		this.floorNumber = floorNumber;
		this.type = type;
		this.description = description;
		this.overallRating = overallRating;
		this.reservations = reservations;
		this.hotel = hotel;
		this.imagePath = imagePath;
	}
	
	//TODO:The bottom constructor is used just for testing editHotel form
	public Room(int floorNumber, String type, String description)
	{
		this.floorNumber = floorNumber;
		this.type = type;
		this.description = description;
	}



	public Long getRoomID()
	{
		return roomID;
	}

	public void setRoomID(Long roomID)
	{
		this.roomID = roomID;
	}

	public double getOverallRating()
	{
		return overallRating;
	}

	public void setOverallRating(double overallRating)
	{
		this.overallRating = overallRating;
	}



	public Set<Date> getReservations()
	{
		return reservations;
	}



	public void setReservations(Set<Date> reservations)
	{
		this.reservations = reservations;
	}



	public Hotel getHotel()
	{
		return hotel;
	}



	public void setHotel(Hotel hotel)
	{
		this.hotel = hotel;
	}



	public String getImagePath()
	{
		return imagePath;
	}



	public void setImagePath(String imagePath)
	{
		this.imagePath = imagePath;
	}



	public int getFloorNumber()
	{
		return floorNumber;
	}



	public void setFloorNumber(int floorNumber)
	{
		this.floorNumber = floorNumber;
	}



	public String getType()
	{
		return type;
	}



	public void setType(String type)
	{
		this.type = type;
	}



	public String getDescription()
	{
		return description;
	}



	public void setDescription(String description)
	{
		this.description = description;
	}
	
	
	
}
