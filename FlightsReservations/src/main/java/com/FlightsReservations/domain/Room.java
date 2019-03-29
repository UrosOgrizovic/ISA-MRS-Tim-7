package com.FlightsReservations.domain;

import java.util.Date;
import java.util.Set;

public class Room
{
	private int roomID;//TODO: room number - unique
	private double overallRating;//1-5
	private Set<Date> reservations;
	private Hotel hotel;
	private Set<String> imagePath;
	//TODO: add more attributes?
	
	public Room() {}

	public int getRoomID()
	{
		return roomID;
	}

	public void setRoomID(int roomID)
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
	
	
	
}
