package com.FlightsReservations.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("RR")
public class RoomReservation extends Reservation {
	private Long roomId;

	private Date startTime;
	
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
	
	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}

	public RoomReservation() {}

	public RoomReservation(Date dateOfReservation, Float discount, Float price, Boolean confirmed, User owner,
			Long roomId, Date startTime, Date endTime) {
		super(dateOfReservation, discount, price, confirmed, owner);
		this.roomId = roomId;
		this.startTime = startTime;
		this.endTime = endTime;
	}
}
