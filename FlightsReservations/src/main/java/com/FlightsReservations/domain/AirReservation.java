package com.FlightsReservations.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.FlightsReservations.domain.enums.TripType;

@Entity
public class AirReservation {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private TripType type;

	@Column(nullable = false)
	private Float discount;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Airline airline;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Seat seat;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private User owner;

	@OneToMany(mappedBy = "reservation")
	private Set<AirReservationFlight> flights = new HashSet<>();

	public AirReservation() {
		super();
	}

	public AirReservation(TripType type, Float discount, Airline airline, Seat seat, User owner) {
		super();
		this.type = type;
		this.discount = discount;
		this.airline = airline;
		this.seat = seat;
		this.owner = owner;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TripType getType() {
		return type;
	}

	public void setType(TripType type) {
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

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

}
