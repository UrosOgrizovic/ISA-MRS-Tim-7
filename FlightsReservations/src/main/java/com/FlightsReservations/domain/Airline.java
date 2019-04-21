package com.FlightsReservations.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Airline extends Company {

	@OneToMany(mappedBy = "airline", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Flight> flights = new HashSet<>(); 

	@ManyToMany
    @JoinTable(name = "airline_airports",
               joinColumns = @JoinColumn(name="airline_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="airport_id", referencedColumnName="id"))
	private Set<Airport> airports = new HashSet<>(); 
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private AirlinePriceList pricelist;
	
	public Airline() {
		super();
	}

	public Airline(String name, Float longitude, Float latitude, String promoDescription, float avarageScore,
			int numberOfVotes) {
		super(name, longitude, latitude, promoDescription, avarageScore, numberOfVotes);
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
}
