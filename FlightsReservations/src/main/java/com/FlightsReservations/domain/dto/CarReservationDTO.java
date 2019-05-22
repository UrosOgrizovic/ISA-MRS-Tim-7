package com.FlightsReservations.domain.dto;

import java.util.Date;

import com.FlightsReservations.domain.CarReservation;
import com.fasterxml.jackson.annotation.JsonFormat;

public class CarReservationDTO {
	private Long id;
	private Float price;
	private String ownerEmail;
	
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm")
	private Date dateOfReservation;
	
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm")
	private Date startTime;
	
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm")
	private Date endTime;

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float totalPrice) {
		this.price = totalPrice;
	}

	public String getOwnerEmail() {
		return ownerEmail;
	}

	public void setOwnerEmail(String ownerEmail) {
		this.ownerEmail = ownerEmail;
	}

	public Date getDateOfReservation() {
		return dateOfReservation;
	}

	public void setDateOfReservation(Date dateOfReservation) {
		this.dateOfReservation = dateOfReservation;
	}

	public CarReservationDTO() {}

	public CarReservationDTO(Long id, Float price, String ownerEmail, Date dateOfReservation, Date startTime, Date endTime) {
		super();
		this.id = id;
		this.price = price;
		this.ownerEmail = ownerEmail;
		this.dateOfReservation = dateOfReservation;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	public CarReservationDTO(CarReservation reservation) {
		this(reservation.getId(), reservation.getPrice(), reservation.getOwner().getEmail(), 
				reservation.getDateOfReservation(), reservation.getStartTime(), reservation.getEndTime());
	}
}
