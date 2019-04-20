package com.FlightsReservations.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class FlightReservation {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private Date dateOfReservation;

	@Column(nullable = false)
	private Float discount;

	@Column(nullable = false)
	private Float price;

	@Column(nullable = false)
	private Boolean confirmed;

	/*
	 * Be careful with reservation cancel. CascadeType for passengers, owner and
	 * flight is set to ALL. If reservation is deleted after cancel then change cascade type.
	 */

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Passenger> passengers = new HashSet<>();

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private User owner;

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
		super();
		this.dateOfReservation = dateOfReservation;
		this.discount = discount;
		this.price = price;
		this.owner = owner;
		this.confirmed = confirmed;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDateOfReservation() {
		return dateOfReservation;
	}

	public void setDateOfReservation(Date dateOfReservation) {
		this.dateOfReservation = dateOfReservation;
	}

	public Float getDiscount() {
		return discount;
	}

	public void setDiscount(Float discount) {
		this.discount = discount;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Set<Passenger> getPassengers() {
		return passengers;
	}

	public void setPassengers(Set<Passenger> passengers) {
		this.passengers = passengers;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public Set<Flight> getFlights() {
		return flights;
	}

	public void setFlights(Set<Flight> flights) {
		this.flights = flights;
	}

	public Boolean getConfirmed() {
		return confirmed;
	}

	public void setConfirmed(Boolean confirmed) {
		this.confirmed = confirmed;
	}

}
