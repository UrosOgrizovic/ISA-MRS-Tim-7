package com.FlightsReservations.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.FlightsReservations.domain.enums.ReservationType;

@Entity
public class AirReservation {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private ReservationType type;

	@Column(nullable = false)
	private Float discount;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Airline airline;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Seat seat;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Flight firstFlight;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Flight returnFlight;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private User owner;

	public AirReservation() {
		super();
	}

	public AirReservation(ReservationType type, Float discount, Airline airline, Seat seat, Flight firstFlight,
			Flight returnFlight, User owner) {
		super();
		this.type = type;
		this.discount = discount;
		this.airline = airline;
		this.seat = seat;
		this.firstFlight = firstFlight;
		this.returnFlight = returnFlight;
		this.owner = owner;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ReservationType getType() {
		return type;
	}

	public void setType(ReservationType type) {
		this.type = type;
	}

	public Float getDiscount() {
		return discount;
	}

	public void setDiscount(Float discount) {
		this.discount = discount;
	}

	public Airline getAirline() {
		return airline;
	}

	public void setAirline(Airline airline) {
		this.airline = airline;
	}

	public Seat getSeat() {
		return seat;
	}

	public void setSeat(Seat seat) {
		this.seat = seat;
	}

	public Flight getFirstFlight() {
		return firstFlight;
	}

	public void setFirstFlight(Flight firstFlight) {
		this.firstFlight = firstFlight;
	}

	public Flight getReturnFlight() {
		return returnFlight;
	}

	public void setReturnFlight(Flight returnFlight) {
		this.returnFlight = returnFlight;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

}
