package com.FlightsReservations.domain.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

public class FlightRatingDTO {
	@NotNull
	@PositiveOrZero
	@Min(value = 1)
	@Max(value = 5)
	private float averageScore;
	
	@NotNull
	@Positive
	private long id;

	public FlightRatingDTO() {
		super();
	}

	public FlightRatingDTO(long id,float averageScore) {
		super();
		this.averageScore = averageScore;
		this.id = id;
	}

	public float getAverageScore() {
		return averageScore;
	}

	public void setAverageScore(float averageScore) {
		this.averageScore = averageScore;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
