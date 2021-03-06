package com.FlightsReservations.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
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

	@ManyToMany(mappedBy = "reservations", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Airline> airlines = new HashSet<>();

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Passenger> passengers = new HashSet<>();

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<FlightInvite> invites = new HashSet<>();

	private float discount;

	@ManyToMany
	@JoinTable(name = "reservations_flights", joinColumns = @JoinColumn(name = "reservation_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "flight_id", referencedColumnName = "id"))
	private List<Flight> flights = new ArrayList<>();

	public FlightReservation() {
		super();
	}

	public FlightReservation(Date dateOfReservation, Float price, User owner, Boolean confirmed) {
		super(dateOfReservation, price, confirmed, owner);
	}

	public Set<Passenger> getPassengers() {
		return passengers;
	}

	public void setPassengers(Set<Passenger> passengers) {
		this.passengers = passengers;
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

	public Set<Airline> getAirlines() {
		return airlines;
	}

	public void setAirlines(Set<Airline> airlines) {
		this.airlines = airlines;
	}

	public List<Flight> getFlights() {
		return flights;
	}

	public void setFlights(List<Flight> flights) {
		this.flights = flights;
	}

}
