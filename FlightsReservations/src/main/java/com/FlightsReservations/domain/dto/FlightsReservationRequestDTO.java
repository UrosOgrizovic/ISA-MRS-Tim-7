package com.FlightsReservations.domain.dto;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.FlightsReservations.domain.enums.TripType;

public class FlightsReservationRequestDTO {
	@NotBlank
	private String ownerEmail;

	@NotNull
	private TripType tripType;

	@NotEmpty
	@Valid
	private List<FlightReservationDetailsDTO> flights;

	public FlightsReservationRequestDTO() {
		super();
	}

	public String getOwnerEmail() {
		return ownerEmail;
	}

	public void setOwnerEmail(String ownerEmail) {
		this.ownerEmail = ownerEmail;
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
