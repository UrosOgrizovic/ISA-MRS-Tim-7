package com.FlightsReservations.domain.dto;

import javax.persistence.Embeddable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

import org.springframework.lang.Nullable;

@Embeddable
public class RatingDTO {

	@Nullable
	@Positive
	@Min(value = 1)
	@Max(value = 5)
	private float companyRating;
	
	@Nullable
	@Positive
	@Min(value = 1)
	@Max(value = 5)
	private float flightRoomCarRating;

	@Nullable
	private String companyName;

	@Nullable
	private Long reservationId;
	
	public RatingDTO() {
		super();
	}

	public RatingDTO(String name, float companyRating, Long reservationId) {
		super();
		this.companyRating = companyRating;
		this.companyName = name;
		this.reservationId = reservationId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String name) {
		this.companyName = name;
	}

	public Long getReservationId() {
		return reservationId;
	}

	public void setReservationId(Long reservationId) {
		this.reservationId = reservationId;
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

	@Override
	public String toString() {
		return "CompanyRatingDTO [companyRating=" + companyRating + ", flightRoomCarRating=" + flightRoomCarRating
				+ ", name=" + companyName + ", reservationId=" + reservationId + "]";
	}

	

	

	
	
}
