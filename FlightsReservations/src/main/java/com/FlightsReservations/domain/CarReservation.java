package com.FlightsReservations.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class CarReservation extends Reservation {
	@Column(nullable = false)
	private Long carId;
	
	@Column(nullable = false)
	private int reservationDurationHours;

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

	public CarReservation(Date dateOfReservation, Float discount, Float price, Boolean confirmed, User owner,
			Long carId, int reservationDurationHours) {
		super(dateOfReservation, discount, price, confirmed, owner);
		this.carId = carId;
		this.reservationDurationHours = reservationDurationHours;
	}
	
}
