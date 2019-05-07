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
	 * flight is set to ALL. If reservation is deleted after cancel then change cascade type.
	 */

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Passenger> passengers = new HashSet<>();

	@ManyToMany
	@JoinTable(
			name = "reservations_flights", 
			joinColumns = @JoinColumn(name = "reservation_id", referencedColumnName = "id"), 
			inverseJoinColumns = @JoinColumn(name = "flight_id", referencedColumnName = "id"))
	private Set<Flight> flights = new HashSet<>();

	public FlightReservation() {
		super();
	}

	public FlightReservation(Date dateOfReservation, Float discount, Float price, User owner, Boolean confirmed) {
		super(dateOfReservation, discount, price, confirmed, owner);
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
 

}
