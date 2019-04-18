package com.FlightsReservations.domain.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.FlightsReservations.domain.enums.TripType;

public class FlightsReservationRequestDTO {
	@NotBlank
	private String ownerUsername;

	@NotNull
	private TripType tripType;

	@NotEmpty
	private List<FlightReservationDetailsDTO> flights;

	public FlightsReservationRequestDTO() {
		super();
	}

	public String getOwnerUsername() {
		return ownerUsername;
	}

	public void setOwnerUsername(String ownerUsername) {
		this.ownerUsername = ownerUsername;
	}

	public TripType getTripType() {
		return tripType;
	}

	public void setTripType(TripType tripType) {
		this.tripType = tripType;
	}

	public List<FlightReservationDetailsDTO> getFlights() {
		return flights;
	}

	public void setFlights(List<FlightReservationDetailsDTO> flights) {
		this.flights = flights;
	}
}
