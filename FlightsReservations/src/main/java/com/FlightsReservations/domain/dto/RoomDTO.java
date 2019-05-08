package com.FlightsReservations.domain.dto;

public class RoomDTO {
	private double overallRating;
	private double overNightStay;
	private Long hotel_id;
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
	public Long getHotel_id() {
		return hotel_id;
	}
	public void setHotel_id(Long hotel_id) {
		this.hotel_id = hotel_id;
	}
	
	public RoomDTO() {
		super();
	}
}
