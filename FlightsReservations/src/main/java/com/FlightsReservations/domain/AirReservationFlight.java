package com.FlightsReservations.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Entity
public class AirReservationFlight {

	@EmbeddedId
	private AirReservationFlightKey id;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@MapsId("flight_id")
	@JoinColumn(name = "flight_id")
	private Flight flight;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@MapsId("reservation_id")
	@JoinColumn(name = "reservation_id")
	private AirReservation reservation;

	@Column(name = "flight_number")
	private Integer flightNumber;

	public AirReservationFlight() {
		super();
	}

	public AirReservationFlight(Flight flight, AirReservation reservation, Integer flightNumber) {
		super();
		this.flight = flight;
		this.reservation = reservation;
		this.flightNumber = flightNumber;
	}

	public AirReservationFlightKey getId() {
		return id;
	}

	public void setId(AirReservationFlightKey id) {
		this.id = id;
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

	public Integer getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(Integer flightNumber) {
		this.flightNumber = flightNumber;
	}

}
