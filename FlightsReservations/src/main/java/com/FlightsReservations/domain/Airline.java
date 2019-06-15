package com.FlightsReservations.domain;

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
import javax.persistence.OneToOne;

@Entity
@DiscriminatorValue("A")
public class Airline extends Company {

	@OneToMany(mappedBy = "airline", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Flight> flights = new HashSet<>();

	@ManyToMany
	@JoinTable(name = "airline_airports", joinColumns = @JoinColumn(name = "airline_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "airport_id", referencedColumnName = "id"))
	private Set<Airport> airports = new HashSet<>();

	@ManyToMany
	@JoinTable(name = "airline_reservations", joinColumns = @JoinColumn(name = "airline_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "reservation_id", referencedColumnName = "id"))
	private Set<FlightReservation> reservations = new HashSet<>();

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private AirlinePriceList pricelist;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private AirlineAdmin admin;

	public Airline() {
		super();
	}

	public Airline(String name, Float longitude, Float latitude, String city, String state, String promoDescription,
			float avarageScore, int numberOfVotes) {
		super(name, longitude, latitude, city, state, promoDescription, avarageScore, numberOfVotes);
	}

	public Set<Flight> getFlights() {
		return flights;
	}

	public void setFlights(Set<Flight> flights) {
		this.flights = flights;
	}

	public Set<Airport> getAirports() {
		return airports;
	}

	public void setAirports(Set<Airport> airports) {
		this.airports = airports;
	}

	public AirlinePriceList getPricelist() {
		return pricelist;
	}

	public void setPricelist(AirlinePriceList pricelist) {
		this.pricelist = pricelist;
	}

	public AirlineAdmin getAdmin() {
		return admin;
	}

	public void setAdmin(AirlineAdmin admin) {
		this.admin = admin;
	}

	public Set<FlightReservation> getReservations() {
		return reservations;
	}

	public void setReservations(Set<FlightReservation> reservations) {
		this.reservations = reservations;
	}

}
