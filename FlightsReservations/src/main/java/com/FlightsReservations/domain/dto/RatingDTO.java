package com.FlightsReservations.domain.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class RatingDTO {
	@NotNull
	@Positive
	private float score;

	@NotBlank
	private String name;

	public RatingDTO() {
		super();
	}

	public RatingDTO(@NotBlank String name, @NotNull @Positive float score) {
		super();
		this.score = score;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

}
