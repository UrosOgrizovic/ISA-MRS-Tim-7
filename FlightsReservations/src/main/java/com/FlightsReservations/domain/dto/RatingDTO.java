package com.FlightsReservations.domain.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.lang.Nullable;

public class RatingDTO {

	@Nullable
	@Min(value = 0)
	@Max(value = 5)
	private float companyRating;
	
	@Nullable
	@Min(value = 0)
	@Max(value = 5)
	private float flightRoomCarRating;

	@Nullable
	private Long companyBranchOfficeId;

	@Nullable
	private Long reservationId;
	
	public RatingDTO() {
		super();
	}

	public RatingDTO(Long companyBranchOfficeId, float companyRating, Long reservationId, float flightRoomCarRating) {
		super();
		if (companyRating <= 0) {
			this.companyRating = 0;
		} else {
			this.companyRating = companyRating;
		}
		
		this.companyBranchOfficeId = companyBranchOfficeId;
		this.reservationId = reservationId;
		if (flightRoomCarRating <= 0) {
			this.flightRoomCarRating = 0;
		} else {
			this.flightRoomCarRating = flightRoomCarRating;
		}
		
	}

	public Long getCompanyBranchOfficeId() {
		return companyBranchOfficeId;
	}

	public void setCompanyBranchOfficeId(Long companyBranchOfficeId) {
		this.companyBranchOfficeId = companyBranchOfficeId;
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
				+ ", name=" + companyBranchOfficeId + ", reservationId=" + reservationId + "]";
	}

	

	

	
	
}
