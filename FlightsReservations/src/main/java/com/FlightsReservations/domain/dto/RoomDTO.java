package com.FlightsReservations.domain.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.FlightsReservations.domain.Hotel;

public class RoomDTO {
	@NotNull
	private float averageScore;
	@NotNull
	private double overNightStay;
	
	@NotNull
	private int number;
	
	@NotNull
	private int numberOfGuests;
	
	@NotBlank
	private String name;
	
	@NotBlank
	private String type;
	
	private Hotel hotel;
	
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
	
	public Hotel getHotel() {
		return hotel;
	}
	public void setHotel(Hotel hotel) {
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
	
	public RoomDTO(@NotNull float averageScore, @NotNull double overNightStay, @NotNull int number,
			@NotNull int numberOfGuests, @NotBlank String name, @NotBlank String type, Hotel hotel, int numberOfVotes) {
		super();
		this.averageScore = averageScore;
		this.overNightStay = overNightStay;
		this.number = number;
		this.numberOfGuests = numberOfGuests;
		this.name = name;
		this.type = type;
		this.hotel = hotel;
		this.numberOfVotes = numberOfVotes;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	
	
}
