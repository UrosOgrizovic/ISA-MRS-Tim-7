package com.FlightsReservations.domain.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.FlightsReservations.domain.Hotel;

public class RoomDTO {

	private Long id;
	
	@NotNull
	private float averageScore;
	@NotNull
	private double overNightStay;
	
	private int number;
	private int numberOfGuests;	
	private int floor;
	private String type;
	
	private String hotelName;
	
	private int numberOfVotes;
	
	public int getNumberOfVotes() {
		return numberOfVotes;
	}
	public void setNumberOfVotes(int numberOfVotes) {
		this.numberOfVotes = numberOfVotes;
	}
	public void setAverageScore(float averageScore) {
		this.averageScore = averageScore;
	}
	public float getAverageScore() 
	{
		return averageScore;
	}
	
	public double getOverNightStay() 
	{
		return overNightStay;
	}
	public void setOverNightStay(double overNightStay) 
	{
		this.overNightStay = overNightStay;
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
	public String getHotelName()
	{
		return hotelName;
	}
	public void setHotelName(String hotelName)
	{
		this.hotelName = hotelName;
	}
	public int getFloor()
	{
		return floor;
	}
	public void setFloor(int floor)
	{
		this.floor = floor;
	}
	
	
	
	public Long getId()
	{
		return id;
	}
	public void setId(Long id)
	{
		this.id = id;
	}
	public RoomDTO(@NotNull float averageScore, @NotNull double overNightStay, @NotNull int number,
			@NotNull int numberOfGuests, @NotBlank String name, @NotBlank String type, Hotel hotel, int numberOfVotes) {
		super();
		this.averageScore = averageScore;
		this.overNightStay = overNightStay;
		this.number = number;
		this.numberOfGuests = numberOfGuests;
		if(hotel!=null)this.hotelName = hotel.getName();
		else this.hotelName = "";
		this.type = type;
		this.numberOfVotes = numberOfVotes;
	}

	public RoomDTO(int number, int floor, int numberOfGuests, String type, float averageScore, double overNightStay, String hotelName, int numberOfVotes)
	{
		super();
		this.averageScore = averageScore;
		this.overNightStay = overNightStay;
		this.number = number;
		this.floor = floor;
		this.numberOfGuests = numberOfGuests;
		this.type = type;
		this.hotelName = hotelName;
		this.numberOfVotes = numberOfVotes;
	}
	public RoomDTO(Long id, @NotNull float averageScore, @NotNull double overNightStay, int number, int numberOfGuests,
			int floor, String type, String hotelName, int numberOfVotes)
	{
		super();
		this.id = id;
		this.averageScore = averageScore;
		this.overNightStay = overNightStay;
		this.number = number;
		this.numberOfGuests = numberOfGuests;
		this.floor = floor;
		this.type = type;
		this.hotelName = hotelName;
		this.numberOfVotes = numberOfVotes;
	}
	
	
}