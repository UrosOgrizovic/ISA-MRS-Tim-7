package com.FlightsReservations.domain.dto;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class FlightsReservationRequestDTO {
	@NotBlank
	private String ownerEmail;

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

	public List<FlightReservationDetailsDTO> getFlights() {
		return flights;
	}

	public void setFlights(List<FlightReservationDetailsDTO> flights) {
		this.flights = flights;
	}
}
