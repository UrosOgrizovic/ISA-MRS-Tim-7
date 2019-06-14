package com.FlightsReservations.domain.dto;

import java.util.Date;

import com.FlightsReservations.domain.FriendRequest;
import com.fasterxml.jackson.annotation.JsonFormat;

public class FriendRequestDTO {
	private AbstractUserDTO sender;
	private AbstractUserDTO reciever;

	@JsonFormat(pattern = "dd-MM-yyyy HH:mm", timezone = "Belgrade/Europe")
	private Date dateSent;

	public FriendRequestDTO() {
		super();
	}

	public FriendRequestDTO(AbstractUserDTO sender, AbstractUserDTO reciever, Date dateSent) {
		super();
		this.sender = sender;
		this.reciever = reciever;
		this.dateSent = dateSent;
	}

	public FriendRequestDTO(FriendRequest fr) {
		this(new AbstractUserDTO(fr.getSender()), new AbstractUserDTO(fr.getReciever()), fr.getDateCreated());
	}

	public AbstractUserDTO getSender() {
		return sender;
	}

	public void setSender(AbstractUserDTO sender) {
		this.sender = sender;
	}

	public AbstractUserDTO getReciever() {
		return reciever;
	}

	public void setReciever(AbstractUserDTO reciever) {
		this.reciever = reciever;
	}

	public Date getDateSent() {
		return dateSent;
	}

	public void setDateSent(Date dateSent) {
		this.dateSent = dateSent;
	}

}
