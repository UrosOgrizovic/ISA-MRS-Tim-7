package com.FlightsReservations.domain.dto;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class FlightReservationDetailsDTO {
	@NotNull
	@Positive
	private Long flightId;

	@NotNull
	@Valid
	private List<PassengerDTO> passengers;
	
	@NotNull 
	@Valid
	private List<FlightInviteDTO> invites;

	public FlightReservationDetailsDTO() {
		super();
	}

	public Long getFlightId() {
		return flightId;
	}

	public void setFlightId(Long flightId) {
		this.flightId = flightId;
	}

	public List<PassengerDTO> getPassengers() {
		return passengers;
	}

	public void setPassengers(List<PassengerDTO> passengers) {
		this.passengers = passengers;
	}

	public List<FlightInviteDTO> getInvites() {
		return invites;
	}

	public void setInvites(List<FlightInviteDTO> invites) {
		this.invites = invites;
	}

}