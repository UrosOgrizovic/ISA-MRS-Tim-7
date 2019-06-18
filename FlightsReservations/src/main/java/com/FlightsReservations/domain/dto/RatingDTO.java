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
<<<<<<< HEAD
	private Long companyBranchOfficeId;
=======
	private Long companyId;
	
	@Nullable
	private Long racsBranchOfficeId;
>>>>>>> master

	@Nullable
	private Long reservationId;
	
<<<<<<< HEAD
=======
	public Long getRacsBranchOfficeId() {
		return racsBranchOfficeId;
	}

	public void setRacsBranchOfficeId(Long racsBranchOfficeId) {
		this.racsBranchOfficeId = racsBranchOfficeId;
	}

>>>>>>> master
	public RatingDTO() {
		super();
	}

<<<<<<< HEAD
	public RatingDTO(Long companyBranchOfficeId, float companyRating, Long reservationId, float flightRoomCarRating) {
=======
	public RatingDTO(float companyRating, Long reservationId, float flightRoomCarRating) {
>>>>>>> master
		super();
		if (companyRating <= 0) {
			this.companyRating = 0;
		} else {
			this.companyRating = companyRating;
		}
		
<<<<<<< HEAD
		this.companyBranchOfficeId = companyBranchOfficeId;
=======
>>>>>>> master
		this.reservationId = reservationId;
		if (flightRoomCarRating <= 0) {
			this.flightRoomCarRating = 0;
		} else {
			this.flightRoomCarRating = flightRoomCarRating;
		}
		
	}

<<<<<<< HEAD
	public Long getCompanyBranchOfficeId() {
		return companyBranchOfficeId;
	}

	public void setCompanyBranchOfficeId(Long companyBranchOfficeId) {
		this.companyBranchOfficeId = companyBranchOfficeId;
=======
	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
>>>>>>> master
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
<<<<<<< HEAD
		return "CompanyRatingDTO [companyRating=" + companyRating + ", flightRoomCarRating=" + flightRoomCarRating
				+ ", name=" + companyBranchOfficeId + ", reservationId=" + reservationId + "]";
	}

	

	

	
	
}
=======
		return "RatingDTO [companyRating=" + companyRating + ", flightRoomCarRating=" + flightRoomCarRating
				+ ", companyId=" + companyId + ", racsBranchOfficeId=" + racsBranchOfficeId + ", reservationId="
				+ reservationId + "]";
	}

}
>>>>>>> master
