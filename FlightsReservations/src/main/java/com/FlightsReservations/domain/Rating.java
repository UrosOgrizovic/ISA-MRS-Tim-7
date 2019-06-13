package com.FlightsReservations.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;


@Entity
public class Rating {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Reservation reservation;
	
	@Column
	@Min(value = 0)
	@Max(value = 5)
	private float flightRoomCarRating = 0;
	
	@Column
	@Min(value = 0)
	@Max(value = 5)
	private float companyRating = 0;
	
	@Column
	private Long companyBranchOfficeId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Reservation getReservation() {
		return reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

	public float getFlightRoomCarRating() {
		return flightRoomCarRating;
	}

	public void setFlightRoomCarRating(float flightRoomCarRating) {
		this.flightRoomCarRating = flightRoomCarRating;
	}

	public float getCompanyRating() {
		return companyRating;
	}

	public void setCompanyRating(float companyRating) {
		this.companyRating = companyRating;
	}

	public Long getCompanyBranchOfficeId() {
		return companyBranchOfficeId;
	}

	public void setCompanyBranchOfficeId(Long companyId) {
		this.companyBranchOfficeId = companyId;
	}

	public Rating(Long id, Reservation reservation, @Positive @Min(1) @Max(5) float flightRoomCarRating,
			@Positive @Min(1) @Max(5) float companyRating, Long companyId) {
		super();
		this.id = id;
		this.reservation = reservation;
		this.flightRoomCarRating = flightRoomCarRating;
		this.companyRating = companyRating;
		this.companyBranchOfficeId = companyId;
	}

	public Rating() {
		super();
	}
	
	
	
}
