package com.FlightsReservations.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Passenger {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String surname;

	@Column(nullable = false, unique = true)
	private String passport;
	
	@OneToOne(mappedBy = "passenger")
	private Seat seat;
	
	public Passenger() {
		super();
	}

	public Passenger(String name, String surname, String passport, Seat seat) {
		super();
		this.name = name;
		this.surname = surname;
		this.passport = passport;
		this.seat = seat;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Seat getSeats() {
		return seat;
	}

	public void setSeats(Seat seat) {
		this.seat = seat;
	}
}
