package com.FlightsReservations.domain;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Entity
@DiscriminatorValue("CR") 
public class CarReservation extends Reservation {
	private Long carId;
	
	private Date startTime;
	
	private Date endTime;
	
	private Long racsBranchOfficeId;

	public Long getRACSBranchOfficeId() {
		return racsBranchOfficeId;
	}

	public void setRACSBranchOfficeId(Long racsId) {
		this.racsBranchOfficeId = racsId;
	}

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

	public CarReservation(Date dateOfReservation, Float price, Boolean confirmed, AbstractUser owner,
			Long carId, Date startTime, Date endTime, Long racsBranchOfficeId) {
		super(dateOfReservation, price, confirmed, owner);
		this.carId = carId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.racsBranchOfficeId = racsBranchOfficeId;
	}

	@Override
	public String toString() {
		return "CarReservation [carId=" + carId + ", startTime=" + startTime + ", endTime=" + endTime + ", getId()="
				+ getId() + ", getDateOfReservation()=" + getDateOfReservation() + ", getPrice()=" + getPrice()
				+ ", getConfirmed()=" + getConfirmed() + ", getOwner()=" + getOwner() + "]";
	}

	
	
	
}
