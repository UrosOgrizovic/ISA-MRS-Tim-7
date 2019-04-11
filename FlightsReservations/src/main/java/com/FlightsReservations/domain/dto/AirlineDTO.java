package com.FlightsReservations.domain.dto;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.FlightsReservations.domain.Airline;

public class AirlineDTO {
	@NotBlank
	private String name;
	@NotNull
	private Float longitude;
	@NotNull
	private Float latitude;
	@NotBlank
	private String promoDescription;
	private float avarageScore;

	private Set<String> airports = new HashSet<>();
	private Set<FlightDTO> flights = new HashSet<>();

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

	public Set<String> getAirports() {
		return airports;
	}

	public void setAirports(Set<String> airports) {
		this.airports = airports;
	}

	public Set<FlightDTO> getFlights() {
		return flights;
	}

	public void setFlights(Set<FlightDTO> flights) {
		this.flights = flights;
	}

}
