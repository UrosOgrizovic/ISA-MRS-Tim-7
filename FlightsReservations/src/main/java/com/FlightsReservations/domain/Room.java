package com.FlightsReservations.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Room {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	private String name;
	
	@NotBlank
	private String type;

	@NotNull
	private double overallRating;
	
	@NotNull
	private double overNightStay;
	
	//@JsonIgnore is used so as to avoid infinite recursion
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	private Hotel hotel;
	
	public Room() 
	{
		super();
	}
	public Room(double overallRating, double overNightStay, Hotel hotel) 
	{
		super();
		this.overallRating = overallRating;
		this.overNightStay = overNightStay;
		this.hotel = hotel;
	}
	
	public Room(Long id, String name, String type, double overallRating,
			double overNightStay, Hotel hotel) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.overallRating = overallRating;
		this.overNightStay = overNightStay;
		this.hotel = hotel;
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
	
	public Long getId() 
	{
		return id;
	}
	public void setId(Long id) 
	{
		this.id = id;
	}
	public String getName() 
	{
		return name;
	}
	public void setName(String name) 
	{
		this.name = name;
	}
	public String getType() 
	{
		return type;
	}
	public void setType(String type) 
	{
		this.type = type;
	}
	
	
	
}
