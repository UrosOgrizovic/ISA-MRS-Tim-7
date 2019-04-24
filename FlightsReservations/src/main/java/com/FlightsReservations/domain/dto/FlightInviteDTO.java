package com.FlightsReservations.domain.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class FlightInviteDTO {
	@NotBlank
	private String friendUsername;
	
	@NotNull
	@Positive
	private Integer seatNumber;

	public FlightInviteDTO() {
		super();
	}

	public String getFriendUsername() {
		return friendUsername;
	}

	public void setFriendUsername(String friendUsername) {
		this.friendUsername = friendUsername;
	}

	public Integer getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(Integer seatNumber) {
		this.seatNumber = seatNumber;
	}

}
