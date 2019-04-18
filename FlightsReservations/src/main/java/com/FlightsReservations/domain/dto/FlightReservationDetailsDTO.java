package com.FlightsReservations.domain.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class FlightReservationDetailsDTO {
	@NotNull
	@Positive
	private Integer flightId;

	@NotEmpty
	private List<PassengerDTO> passengers;

	//@NotNull Friends not implemented yet
	private List<FlightInviteDTO> invites;

	public FlightReservationDetailsDTO() {
		super();
	}

	public Integer getFlightId() {
		return flightId;
	}

	public void setFlightId(Integer flightId) {
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
