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

import com.FlightsReservations.domain.enums.SeatType;

@Entity
public class Seat {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private Integer seatNumber;

	@Column(nullable = false)
	private Boolean available;

	@Column(nullable = false)
	private SeatType type;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	private Flight flight;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	private AirReservation reservation;
	
	public Seat() {
		super();
	}
	
	public Seat(Integer seatNumber, Boolean available, SeatType type, Flight flight) {
		super();
		this.seatNumber = seatNumber;
		this.available= available;
		this.type = type;
		this.flight = flight;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(Integer seatNumber) {
		this.seatNumber = seatNumber;
	}

	public Boolean isAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available= available;
	}

	public SeatType getType() {
		return type;
	}

	public void setType(SeatType type) {
		this.type = type;
	}

	public Flight getFlight() {
		return flight;
	}

	public void setFlight(Flight flight) {
		this.flight = flight;
	}

	public AirReservation getReservation() {
		return reservation;
	}

	public void setReservation(AirReservation reservation) {
		this.reservation = reservation;
	}

}
