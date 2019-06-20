package com.FlightsReservations.domain.dto;

import java.util.Date;

import com.FlightsReservations.domain.Rating;
import com.FlightsReservations.domain.RoomReservation;
import com.fasterxml.jackson.annotation.JsonFormat;

public class RoomReservationDTO {
	private Long roomId;
	private Float price;
	private String ownerEmail;
	private Boolean confirmed;
	
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm")
	private Date dateOfReservation;
	
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm")
	private Date startTime;
	
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm")
	private Date endTime;
	
	private Long hotelId;
	private RatingDTO rating;
	

	public Long getHotelId() {
		return hotelId;
	}

	public void setHotelId(Long hotelId) {
		this.hotelId = hotelId;
	}

	public RatingDTO getRating() {
		return rating;
	}

	public void setRating(RatingDTO rating) {
		this.rating = rating;
	}

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

	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long id) {
		this.roomId = id;
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

	public RoomReservationDTO(Long roomId, Float price, String ownerEmail, Boolean confirmed, Date dateOfReservation,
			Date startTime, Date endTime, Long hotelId, RatingDTO rating) {
		super();
		this.roomId = roomId;
		this.price = price;
		this.ownerEmail = ownerEmail;
		this.confirmed = confirmed;
		this.dateOfReservation = dateOfReservation;
		this.startTime = startTime;
		this.endTime = endTime;
		this.hotelId = hotelId;
		this.rating = rating;
	}
	
	public RoomReservationDTO(Long roomId, Float price, String ownerEmail, Boolean confirmed, Date dateOfReservation,
			Date startTime, Date endTime, Long hotelId, Rating rating) {
		super();
		this.roomId = roomId;
		this.price = price;
		this.ownerEmail = ownerEmail;
		this.confirmed = confirmed;
		this.dateOfReservation = dateOfReservation;
		this.startTime = startTime;
		this.endTime = endTime;
		this.hotelId = hotelId;
		RatingDTO r = new RatingDTO();
		r.setCompanyId(rating.getCompanyId());
		r.setCompanyRating(rating.getCompanyRating());
		r.setFlightRoomCarRating(rating.getFlightRoomCarRating());
		r.setReservationId(rating.getId());

		this.rating = r;
	}

	public RoomReservationDTO() {
		super();
	}
	
	public RoomReservationDTO(RoomReservation reservation) {
		this(reservation.getId(), reservation.getPrice(), reservation.getOwner().getEmail(), reservation.getConfirmed(), 
				reservation.getDateOfReservation(), reservation.getStartTime(), reservation.getEndTime(), reservation.getHotelId(), reservation.getRating());
	}
}
