package com.FlightsReservations.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Room {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private String name;
	@NotNull
	private double overallRating;
	
	@NotNull
	private double overNightStay;
	
	//@JsonIgnore is used so as to avoid infinite recursion
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	private Hotel hotel;
	
	//TODO: add list of discounts (see Car class for inspiration)
	
	public Room() {
		super();
	}
	public Room(@NotNull String name, @NotNull double overallRating, @NotNull double overNightStay, Hotel hotel) {
		super();
		this.name = name;
		this.overallRating = overallRating;
		this.overNightStay = overNightStay;
		this.hotel = hotel;
	}
	public Hotel getHotel() {
		return hotel;
	}
	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public double getOverallRating() {
		return overallRating;
	}
	public void setOverallRating(double overallRating) {
		this.overallRating = overallRating;
	}
	public double getOverNightStay() {
		return overNightStay;
	}
	public void setOverNightStay(double overNightStay) {
		this.overNightStay = overNightStay;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
}
