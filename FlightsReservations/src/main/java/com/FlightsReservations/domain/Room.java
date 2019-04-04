package com.FlightsReservations.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Room
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@NotNull
	private double overallRating;
	@NotNull
	private double overNightStay;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	
		
	
}
