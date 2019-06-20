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
	
	private float roomRating;
	
	private Long hotelId;
	
	public Long getHotelId() {
		return hotelId;
	}

	public void setHotelId(Long hotelId) {
		this.hotelId = hotelId;
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
	
	

	public float getRoomRating() {
		return roomRating;
	}

	public void setRoomRating(float roomRating) {
		this.roomRating = roomRating;
	}

	public RoomReservation() {}

	public RoomReservation(Date dateOfReservation, Float price, Boolean confirmed, User owner,
			Long roomId, Date startTime, Date endTime, Long hotelId) {
		super(dateOfReservation, price, confirmed, owner);
		this.roomId = roomId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.roomRating = 0;
		this.hotelId = hotelId;
	}
}