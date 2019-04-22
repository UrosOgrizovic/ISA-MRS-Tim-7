package com.FlightsReservations.domain.dto;

import java.util.Date;

import com.FlightsReservations.domain.CarReservation;
import com.fasterxml.jackson.annotation.JsonFormat;

public class CarReservationDTO {
	private Long id;
	private Float price;
	private String ownerEmail;
	private Boolean confirmed;
	private int reservationDurationHours;
	
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm")
	private Date dateOfReservation;

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

	public Boolean getConfirmed() {
		return confirmed;
	}

	public void setConfirmed(Boolean confirmed) {
		this.confirmed = confirmed;
	}

	public Date getDateOfReservation() {
		return dateOfReservation;
	}

	public void setDateOfReservation(Date dateOfReservation) {
		this.dateOfReservation = dateOfReservation;
	}

	public int getReservationDurationHours() {
		return reservationDurationHours;
	}

	public void setReservationDurationHours(int reservationDurationHours) {
		this.reservationDurationHours = reservationDurationHours;
	}
	
	public CarReservationDTO() {
		super();
	}

	public CarReservationDTO(Long id, Float price, String ownerEmail, Boolean confirmed, Date dateOfReservation, int reservationDurationHours) {
		super();
		this.id = id;
		this.price = price;
		this.ownerEmail = ownerEmail;
		this.confirmed = confirmed;
		this.dateOfReservation = dateOfReservation;
		this.reservationDurationHours = reservationDurationHours;
	}
	
	public CarReservationDTO(CarReservation reservation) {
		this(reservation.getId(), reservation.getPrice(), reservation.getOwner().getEmail(), reservation.getConfirmed(), reservation.getDateOfReservation(), reservation.getReservationDurationHours());
	}
}
