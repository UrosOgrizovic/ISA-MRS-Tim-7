package com.FlightsReservations.domain.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class CompanyRatingDTO {
	@NotNull
	@Positive
	@Min(value = 1)
	@Max(value = 5)
	private float averageScore;

	@NotBlank
	private String name;

	public CompanyRatingDTO() {
		super();
	}

	public CompanyRatingDTO(String name, @NotNull @Positive @Min(1) @Max(5) float score) {
		super();
		this.averageScore = score;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getaverageScore() {
		return averageScore;
	}

	public void setaverageScore(float averageScore) {
		this.averageScore = averageScore;
	}

}
