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

import org.springframework.lang.Nullable;


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
	private float flightRoomCarRating;
	
	@Column
	@Min(value = 0)
	@Max(value = 5)
	private float companyRating;
	
	@Nullable
	@Column
	private Long racsBranchOfficeId;
	
	@Nullable
	@Column
	private Long companyId;

	
	
	public Long getRacsBranchOfficeId() {
		return racsBranchOfficeId;
	}

	public void setRacsBranchOfficeId(Long racsBranchOfficeId) {
		this.racsBranchOfficeId = racsBranchOfficeId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

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

	public Rating(Reservation reservation, @Positive @Min(1) @Max(5) float flightRoomCarRating,
			@Positive @Min(1) @Max(5) float companyRating) {
		super();
		this.reservation = reservation;
		this.flightRoomCarRating = flightRoomCarRating;
		this.companyRating = companyRating;
	}

	public Rating() {
		super();
		this.companyRating = 0;
		this.flightRoomCarRating = 0;
	}
	
	
	
}
