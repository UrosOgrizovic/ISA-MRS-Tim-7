package com.FlightsReservations.domain.dto;

import java.util.Date;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

public class RoomReservationRequestDTO {
	@NotBlank
	private String ownerEmail;
	
	@NotNull
	private Long roomId;
	
	@NotNull
	@Future
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm")
	private Date startTime;
	
	@NotNull
	@Future
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm")
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

	public String getOwnerEmail() {
		return ownerEmail;
	}

	public void setOwnerEmail(String ownerEmail) {
		this.ownerEmail = ownerEmail;
	}

	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}

	public RoomReservationRequestDTO() {
		super();
	}
	
	
}
