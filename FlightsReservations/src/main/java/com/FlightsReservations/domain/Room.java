package com.FlightsReservations.domain;

public class Room
{
	private int roomID;//TODO: room number - unique
	private double overallRating;//1-5
	private double overNightStay;
	//private Set<BufferedImage> images;  //TODO: ??
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

	public double getOverNightStay()
	{
		return overNightStay;
	}

	public void setOverNightStay(double overNightStay)
	{
		this.overNightStay = overNightStay;
	}
	
	
	
}
