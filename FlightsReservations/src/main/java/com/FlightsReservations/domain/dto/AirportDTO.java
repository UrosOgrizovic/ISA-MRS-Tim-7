package com.FlightsReservations.domain.dto;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.FlightsReservations.domain.Airport;

public class AirportDTO {
	@NotBlank
	private String name;

	@NotBlank
	private String city;

	@NotBlank
	private String state;

	@NotNull
	private float longitude;

	@NotNull
	private float latitude;

	private Set<AirlineDTO> airlines = new HashSet<>();
	private Set<FlightDTO> flights = new HashSet<>();

	public AirportDTO() {
		super();
	}

	public AirportDTO(String name, String city, String state, float longitude, float latitude) {
		super();
		this.name = name;
		this.city = city;
		this.state = state;
		this.longitude = longitude;
		this.latitude = latitude;
	}

	public AirportDTO(Airport a) {
		this(a.getName(), a.getCity(), a.getState(), a.getLongitude(), a.getLatitude());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public Set<AirlineDTO> getAirlines() {
		return airlines;
	}

	public void setAirlines(Set<AirlineDTO> airlines) {
		this.airlines = airlines;
	}

	public Set<FlightDTO> getFlights() {
		return flights;
	}

	public void setFlights(Set<FlightDTO> flights) {
		this.flights = flights;
	}
}
