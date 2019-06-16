package com.FlightsReservations.domain.dto;

import java.util.Date;

import com.FlightsReservations.domain.CarReservation;
import com.FlightsReservations.domain.Rating;
import com.fasterxml.jackson.annotation.JsonFormat;

public class CarReservationDTO {
	private Long id;
	private Float price;
	private String ownerEmail;
	private Boolean confirmed;
	private Long racsBranchOfficeId;
	private RatingDTO rating;
	
	public Long getRacsBranchOfficeId() {
		return racsBranchOfficeId;
	}

	public void setRacsBranchOfficeId(Long racsBranchOfficeId) {
		this.racsBranchOfficeId = racsBranchOfficeId;
	}

	public RatingDTO getRating() {
		return rating;
	}

	public void setRating(RatingDTO rating) {
		this.rating = rating;
	}

	@JsonFormat(pattern = "dd-MM-yyyy HH:mm")
	private Date dateOfReservation;
	
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm")
	private Date startTime;
	
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm")
	private Date endTime;
	
	public Long getRACSBranchOfficeId() {
		return racsBranchOfficeId;
	}

	public void setRACSBranchOfficeId(Long racsBranchOfficeId) {
		this.racsBranchOfficeId = racsBranchOfficeId;
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

	public CarReservationDTO() {
		super();
	}

	public CarReservationDTO(Long id, Float price, String ownerEmail, Boolean confirmed, Date dateOfReservation, Date startTime, Date endTime, Long racsBranchOfficeId, RatingDTO rating) {
		super();
		this.id = id;
		this.price = price;
		this.ownerEmail = ownerEmail;
		this.confirmed = confirmed;
		this.dateOfReservation = dateOfReservation;
		this.startTime = startTime;
		this.endTime = endTime;
		this.racsBranchOfficeId = racsBranchOfficeId;
		this.rating = rating;
	}
	
	public CarReservationDTO(Long id, Float price, String ownerEmail, Boolean confirmed, Date dateOfReservation, Date startTime, Date endTime, Long racsBranchOfficeId, Rating rating) {
		super();
		this.id = id;
		this.price = price;
		this.ownerEmail = ownerEmail;
		this.confirmed = confirmed;
		this.dateOfReservation = dateOfReservation;
		this.startTime = startTime;
		this.endTime = endTime;
		this.racsBranchOfficeId = racsBranchOfficeId;
		RatingDTO r = new RatingDTO();
		r.setCompanyBranchOfficeId(rating.getCompanyBranchOfficeId());
		r.setCompanyRating(rating.getCompanyRating());
		r.setFlightRoomCarRating(rating.getFlightRoomCarRating());
		r.setReservationId(rating.getId());

		this.rating = r;
	}
	
	public CarReservationDTO(CarReservation reservation) {
		this(reservation.getId(), reservation.getPrice(), reservation.getOwner().getEmail(), reservation.getConfirmed(), 
				reservation.getDateOfReservation(), reservation.getStartTime(), reservation.getEndTime(), reservation.getRACSBranchOfficeId(), reservation.getRating());
	}
}
