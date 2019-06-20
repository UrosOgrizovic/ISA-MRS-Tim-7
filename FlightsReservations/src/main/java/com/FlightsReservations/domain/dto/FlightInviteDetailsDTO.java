package com.FlightsReservations.domain.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.FlightsReservations.domain.FlightInvite;

public class FlightInviteDetailsDTO {
	private String sender;
	private String acceptDeadline;
	private FlightReservationDTO reservation;

	public FlightInviteDetailsDTO() {
		super();
	}

	public FlightInviteDetailsDTO(FlightInvite invite) {
		super();
		this.sender = invite.getReservation().getOwner().getEmail();
		Date d = null;
		if (invite.getFlightStart().before(invite.getExpirationDate()))
			d = invite.getFlightStart();
		else
			d = invite.getExpirationDate();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		this.acceptDeadline = sdf.format(d);
		this.reservation = new FlightReservationDTO(invite.getReservation());
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getAcceptDeadline() {
		return acceptDeadline;
	}

	public void setAcceptDeadline(String acceptDeadline) {
		this.acceptDeadline = acceptDeadline;
	}

	public FlightReservationDTO getReservation() {
		return reservation;
	}

	public void setReservation(FlightReservationDTO reservation) {
		this.reservation = reservation;
	}

}
