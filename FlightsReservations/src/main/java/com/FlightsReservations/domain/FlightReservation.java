package com.FlightsReservations.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue("FR")
public class FlightReservation extends Reservation {

	/*
	 * Be careful with reservation cancel. CascadeType for passengers, owner and
	 * flight is set to ALL. If reservation is deleted after cancel then change
	 * cascade type.
	 */

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Passenger> passengers = new HashSet<>();

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<FlightInvite> invites = new HashSet<>();

	private float discount;

	@ManyToMany
	@JoinTable(name = "reservations_flights", joinColumns = @JoinColumn(name = "reservation_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "flight_id", referencedColumnName = "id"))
	private Set<Flight> flights = new HashSet<>();
	
	//TODO: remove confirmed if unnecessary
	private Boolean confirmed;

	public Boolean getConfirmed() {
		return confirmed;
	}

	public void setConfirmed(Boolean confirmed) {
		this.confirmed = confirmed;
	}

	public FlightReservation() {
		super();
	}

	public FlightReservation(Date dateOfReservation, Float price, User owner, Boolean confirmed) {
		super(dateOfReservation, price, owner);
		this.confirmed = confirmed;
	}

	public Set<Passenger> getPassengers() {
		return passengers;
	}

	public void setPassengers(Set<Passenger> passengers) {
		this.passengers = passengers;
	}

	public Set<Flight> getFlights() {
		return flights;
	}

	public void setFlights(Set<Flight> flights) {
		this.flights = flights;
	}

	public Set<FlightInvite> getInvites() {
		return invites;
	}

	public void setInvites(Set<FlightInvite> invites) {
		this.invites = invites;
	}

	public float getDiscount() {
		return discount;
	}

	public void setDiscount(float discount) {
		this.discount = discount;
	}

}
