package com.FlightsReservations.domain.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class CarRatingDTO {
	@NotNull
	@Positive
	@Min(value = 1)
	@Max(value = 5)
	private float averageScore;
	
	@NotNull
	private Long carId;

	public float getAverageScore() {
		return averageScore;
	}

	public void setAverageScore(float averageScore) {
		this.averageScore = averageScore;
	}

	public Long getCarId() {
		return carId;
	}

	public void setCarId(Long carId) {
		this.carId = carId;
	}

	public CarRatingDTO(@NotNull @Positive @Min(1) @Max(5) float averageScore, @NotNull Long carId) {
		super();
		this.averageScore = averageScore;
		this.carId = carId;
	}

	public CarRatingDTO() {
		super();
	}
	
	
}
