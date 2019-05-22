package com.FlightsReservations.domain.dto;

import java.util.Date;

import com.FlightsReservations.domain.Flight;
import com.FlightsReservations.domain.FlightReservation;
import com.FlightsReservations.domain.Passenger;
import com.fasterxml.jackson.annotation.JsonFormat;

public class QuickFlightReservationDTO {
	private Long id;

	@JsonFormat(pattern = "dd-MM-yyyy HH:mm", timezone = "Europe/Belgrade")
	private Date takeoffTime;

	@JsonFormat(pattern = "dd-MM-yyyy HH:mm", timezone = "Europe/Belgrade")
	private Date landingTime;

	private String startAirport;

	private String endAirport;

	private float discount;

	private int seatNumber;

	private float totalPrice;

	public QuickFlightReservationDTO() {
		super();
	}

	public QuickFlightReservationDTO(Long id, Date takeoffTime, Date landingTime, String startAirport,
			String endAirport, float discount, int seatNumber, float totalPrice) {
		super();
		this.id = id;
		this.takeoffTime = takeoffTime;
		this.landingTime = landingTime;
		this.startAirport = startAirport;
		this.endAirport = endAirport;
		this.discount = discount;
		this.seatNumber = seatNumber;
		this.totalPrice = totalPrice;
	}
	
	
	public QuickFlightReservationDTO(FlightReservation fr) {
		this.id = fr.getId();
		Flight f = (Flight) fr.getFlights().toArray()[0];
		this.takeoffTime = f.getTakeoffTime();
		this.landingTime = f.getLandingTime();
		this.startAirport = f.getStart().getName();
		this.endAirport = f.getEnd().getName();
		this.discount = fr.getDiscount();
		this.seatNumber = ((Passenger) fr.getPassengers().toArray()[0]).getSeat().getSeatNumber();
		this.totalPrice = fr.getPrice();
	} 

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getTakeoffTime() {
		return takeoffTime;
	}

	public void setTakeoffTime(Date takeoffTime) {
		this.takeoffTime = takeoffTime;
	}

	public Date getLandingTime() {
		return landingTime;
	}

	public void setLandingTime(Date landingTime) {
		this.landingTime = landingTime;
	}

	public String getStartAirport() {
		return startAirport;
	}

	public void setStartAirport(String startAirport) {
		this.startAirport = startAirport;
	}

	public String getEndAirport() {
		return endAirport;
	}

	public void setEndAirport(String endAirport) {
		this.endAirport = endAirport;
	}

	public float getDiscount() {
		return discount;
	}

	public void setDiscount(float discount) {
		this.discount = discount;
	}

	public int getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(int seatNumber) {
		this.seatNumber = seatNumber;
	}

	public float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}

}
