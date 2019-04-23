package com.FlightsReservations.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Room
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@NotNull
	private String name;
	@NotNull
	private double overallRating;
	@NotNull
	private double overNightStay;
	@ManyToOne(fetch = FetchType.LAZY)
	private Hotel hotel;
	
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hotel == null) ? 0 : hotel.hashCode());
		result = prime * result + id;
		long temp;
		temp = Double.doubleToLongBits(overNightStay);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(overallRating);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Room other = (Room) obj;
		if (hotel == null) {
			if (other.hotel != null)
				return false;
		} else if (!hotel.equals(other.hotel))
			return false;
		if (id != other.id)
			return false;
		if (Double.doubleToLongBits(overNightStay) != Double.doubleToLongBits(other.overNightStay))
			return false;
		if (Double.doubleToLongBits(overallRating) != Double.doubleToLongBits(other.overallRating))
			return false;
		return true;
	}
	
		
	
}
