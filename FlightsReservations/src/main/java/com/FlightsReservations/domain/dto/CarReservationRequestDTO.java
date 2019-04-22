package com.FlightsReservations.domain.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CarReservationRequestDTO {
	@NotBlank
	private String ownerEmail;
	
	@NotNull
	private Long carId;
	
	@NotNull
	private int reservationDurationHours;

	public CarReservationRequestDTO() {
		super();
	}

	public String getOwnerEmail() {
		return ownerEmail;
	}

	public void setOwnerEmail(String ownerEmail) {
		this.ownerEmail = ownerEmail;
	}

	public Long getCarId() {
		return carId;
	}

	public void setCarId(Long carId) {
		this.carId = carId;
	}
	
	public int getReservationDurationHours() {
		return reservationDurationHours;
	}

	public void setReservationDurationHours(int reservationDurationHours) {
		this.reservationDurationHours = reservationDurationHours;
	}
}
