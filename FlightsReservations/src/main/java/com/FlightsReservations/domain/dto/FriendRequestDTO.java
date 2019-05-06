package com.FlightsReservations.domain.dto;

import java.util.Date;

import com.FlightsReservations.domain.FriendRequest;
import com.fasterxml.jackson.annotation.JsonFormat;

public class FriendRequestDTO {
	private UserDTO sender;
	private UserDTO reciever;

	@JsonFormat(pattern = "dd-MM-yyyy HH:mm", timezone = "Belgrade/Europe")
	private Date dateSent;

	public FriendRequestDTO() {
		super();
	}

	public FriendRequestDTO(UserDTO sender, UserDTO reciever, Date dateSent) {
		super();
		this.sender = sender;
		this.reciever = reciever;
		this.dateSent = dateSent;
	}

	public FriendRequestDTO(FriendRequest fr) {
		this(new UserDTO(fr.getSender()), new UserDTO(fr.getReciever()), fr.getDateCreated());
	}

	public UserDTO getSender() {
		return sender;
	}

	public void setSender(UserDTO sender) {
		this.sender = sender;
	}

	public UserDTO getReciever() {
		return reciever;
	}

	public void setReciever(UserDTO reciever) {
		this.reciever = reciever;
	}

	public Date getDateSent() {
		return dateSent;
	}

	public void setDateSent(Date dateSent) {
		this.dateSent = dateSent;
	}

}
