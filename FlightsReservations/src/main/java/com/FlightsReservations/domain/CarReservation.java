package com.FlightsReservations.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class CarReservation extends Reservation {
	@Column(nullable = false)
	private Long carId;
	
	@Column(nullable = false)
	private Date startTime;
	
	@Column(nullable = false)
	private Date endTime;
	
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Long getCarId() {
		return carId;
	}

	public void setCarId(Long carId) {
		this.carId = carId;
	}

	public CarReservation() {}

	public CarReservation(Date dateOfReservation, Float discount, Float price, Boolean confirmed, User owner,
			Long carId, Date startTime, Date endTime) {
		super(dateOfReservation, discount, price, confirmed, owner);
		this.carId = carId;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
}
