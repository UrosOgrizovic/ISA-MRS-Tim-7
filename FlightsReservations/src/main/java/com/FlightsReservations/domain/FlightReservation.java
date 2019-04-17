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
import javax.persistence.OneToOne;

import com.FlightsReservations.domain.enums.TripType;

@Entity
public class FlightReservation {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private Date dateOfReservation;

	@Column(nullable = false)
	private TripType type;

	@Column(nullable = false)
	private Float discount;

	@Column(nullable = false)
	private String passport;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Airline airline;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Seat seat;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private User owner;
	
	@ManyToMany
    @JoinTable(name = "reservations_flights",
               joinColumns = @JoinColumn(name="reservation_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="flight_id", referencedColumnName="id"))
	private Set<Flight> flights = new HashSet<>();

	public FlightReservation() {
		super();
	}

	public FlightReservation(TripType type, Date date, Float discount, String passport, Airline airline, Seat seat,
			User owner) {
		super();
		this.type = type;
		this.dateOfReservation = date;
		this.discount = discount;
		this.passport = passport;
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

	public String getPassport() {
		return passport;
	}

	public void setPassport(String passport) {
		this.passport = passport;
	}

	public Set<Flight> getFlights() {
		return flights;
	}

	public void setFlights(Set<Flight> flights) {
		this.flights = flights;
	}

	public Date getDateOfReservation() {
		return dateOfReservation;
	}

	public void setDateOfReservation(Date dateOfReservation) {
		this.dateOfReservation = dateOfReservation;
	}

}
