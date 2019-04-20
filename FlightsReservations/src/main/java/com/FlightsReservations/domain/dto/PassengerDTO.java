package com.FlightsReservations.domain.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.FlightsReservations.domain.Passenger;

public class PassengerDTO {
	@NotBlank
	private String name;

	@NotBlank
	private String surname;

	@NotBlank
	private String passport;

	@NotNull
	@Positive
	private Integer seatNumber;

	@NotNull
	private boolean owner;

	public PassengerDTO() {
		super();
	}
	
	public PassengerDTO(Passenger p) {
		super();
		name = p.getName();
		surname = p.getSurname();
		passport = p.getPassport();
		seatNumber = p.getSeat().getSeatNumber();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPassport() {
		return passport;
	}

	public void setPassport(String passport) {
		this.passport = passport;
	}

	public Integer getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(Integer seatNumber) {
		this.seatNumber = seatNumber;
	}

	public boolean isOwner() {
		return owner;
	}

	public void setOwner(boolean owner) {
		this.owner = owner;
	}

}
