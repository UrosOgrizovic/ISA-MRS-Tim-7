package com.FlightsReservations.domain;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class FlightInvite {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private boolean accepted;

	@Column(nullable = false)
	private Date expirationDate;

	@Column(nullable = false)
	private Date flightStart;

	@ElementCollection
	private Set<Long> seatIds = new HashSet<>();

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	private FlightReservation reservation;

	public FlightInvite() {
		super();
	}

	public FlightInvite(FlightReservation reservation, User user, Date flightStart) {
		super();
		this.reservation = reservation;
		this.user = user;
		this.accepted = false;
		
		this.expirationDate = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(this.expirationDate);
		c.add(Calendar.DATE, 3);
		this.expirationDate = c.getTime();
		
		this.flightStart = flightStart;
		c.setTime(this.flightStart);
		c.add(Calendar.HOUR_OF_DAY, -3);
		this.flightStart = c.getTime();
	}

	public FlightReservation getReservation() {
		return reservation;
	}

	public void setReservation(FlightReservation reservation) {
		this.reservation = reservation;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isAccepted() {
		return accepted;
	}

	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public Date getFlightStart() {
		return flightStart;
	}

	public void setFlightStart(Date flightStart) {
		this.flightStart = flightStart;
	}

	public Set<Long> getSeatIds() {
		return seatIds;
	}

	public void setSeatIds(Set<Long> seatIds) {
		this.seatIds = seatIds;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
