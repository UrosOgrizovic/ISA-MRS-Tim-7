package com.FlightsReservations.domain.dto;

import java.util.Date;

import com.FlightsReservations.domain.RoomReservation;
import com.fasterxml.jackson.annotation.JsonFormat;

public class RoomReservationDTO {
	private Long id;
	private Float price;
	private String ownerEmail;
	private Boolean confirmed;
	
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

	public RoomReservationDTO(Long id, Float price, String ownerEmail, Boolean confirmed, Date dateOfReservation,
			Date startTime, Date endTime) {
		super();
		this.id = id;
		this.price = price;
		this.ownerEmail = ownerEmail;
		this.confirmed = confirmed;
		this.dateOfReservation = dateOfReservation;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public RoomReservationDTO() {
		super();
	}
	
	public RoomReservationDTO(RoomReservation reservation) {
		this(reservation.getId(), reservation.getPrice(), reservation.getOwner().getEmail(), reservation.getConfirmed(), 
				reservation.getDateOfReservation(), reservation.getStartTime(), reservation.getEndTime());
	}
}
