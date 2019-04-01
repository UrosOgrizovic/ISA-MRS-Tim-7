package com.FlightsReservations.domain.dto;

import javax.validation.constraints.NotBlank;

import com.FlightsReservations.domain.Airline;

public class AirlineDTO {
	@NotBlank private String name;
	@NotBlank private Float longitude;
	@NotBlank private Float latitude;
	@NotBlank private String promoDescription;
	private float avarageScore;
	
	public AirlineDTO() {
		super();
	}

	public AirlineDTO(String name, Float longitude, Float latitude, String promoDescription, float score) {
		super();
		this.name = name;
		this.longitude = longitude;
		this.latitude = latitude;
		this.promoDescription = promoDescription;
		this.avarageScore = score;
	}

	public AirlineDTO(Airline a) {
		this(a.getName(), a.getLongitude(), a.getLatitude(), a.getPromoDescription(), a.getAvarageScore());
	}

	public float getAvarageScore() {
		return avarageScore;
	}

	public void setAvarageScore(float avarageScore) {
		this.avarageScore = avarageScore;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float getLongitude() {
		return longitude;
	}

	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}

	public Float getLatitude() {
		return latitude;
	}

	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}

	public String getPromoDescription() {
		return promoDescription;
	}

	public void setPromoDescription(String promoDescription) {
		this.promoDescription = promoDescription;
	}

}
