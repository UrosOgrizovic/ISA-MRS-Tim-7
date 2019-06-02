package com.FlightsReservations.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.FlightsReservations.domain.enums.RoomType;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Room {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private int number;
	
	@NotNull
	private int numberOfGuests;
	
	@NotNull
	private int floor;
	
	@NotBlank
	private RoomType type;

	@Column(nullable = false)
	private double overallRating;
	
	@Column(nullable = false)
	private double overNightStay;
	
	//@JsonIgnore is used so as to avoid infinite recursion
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	private Hotel hotel;
	
	public Room() 
	{
		super();
	}
	
	public Room(int number, int numberOfGuests, RoomType type, double overallRating, double overNightStay, int floor, Hotel hotel) 
	{
		this.number = number;
		this.numberOfGuests = numberOfGuests;
		this.type = type;
		this.overallRating = overallRating;
		this.overNightStay = overNightStay;
		this.floor = floor;
		this.hotel = hotel;
	}
	
	
	
	public int getFloor()
	{
		return floor;
	}

	public void setFloor(int floor)
	{
		this.floor = floor;
	}

	public Hotel getHotel() 
	{
		return hotel;
	}
	public void setHotel(Hotel hotel) 
	{
		this.hotel = hotel;
	}
	
	public double getOverallRating() 
	{
		return this.overallRating;
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
	
	public Long getId() 
	{
		return id;
	}
	public void setId(Long id) 
	{
		this.id = id;
	}
	public int getNumber() 
	{
		return number;
	}
	public void setNumber(int number) 
	{
		this.number = number;
	}
	public RoomType getType() 
	{
		return type;
	}
	public void setType(RoomType type) 
	{
		this.type = type;
	}
	public int getNumberOfGuests()
	{
		return numberOfGuests;
	}
	public void setNumberOfGuests(int numberOfGuests)
	{
		this.numberOfGuests = numberOfGuests;
	}
	
	
	
	
}
