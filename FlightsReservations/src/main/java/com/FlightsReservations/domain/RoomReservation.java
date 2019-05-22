package com.FlightsReservations.domain;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("RR")
public class RoomReservation extends Reservation {
	private Long roomId;

	private Date startTime;
	
	private Date endTime;
	
	//TODO: remove confirmed if unnecessary
	private Boolean confirmed;

	public Boolean getConfirmed() {
		return confirmed;
	}

	public void setConfirmed(Boolean confirmed) {
		this.confirmed = confirmed;
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
	
	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}

	public RoomReservation() {}

	public RoomReservation(Date dateOfReservation, Float price, Boolean confirmed, User owner,
			Long roomId, Date startTime, Date endTime) {
		super(dateOfReservation, price, owner);
		this.roomId = roomId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.confirmed = confirmed;
	}
}
