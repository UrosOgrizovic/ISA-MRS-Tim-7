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
public class Flight {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Date takeoffTime;

	@Column(nullable = false)
	private Date landingTime;

	@Column(nullable = false)
	private int flightTime;

	@Column(nullable = false)
	private double flightDistance;

	@Column(nullable = false)
	private double price;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Airline airline;

	@OneToMany(mappedBy = "flight", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Seat> seats = new HashSet<>();

	@ManyToMany
	@JoinTable(name = "flight_stops", joinColumns = @JoinColumn(name = "flight_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "airport_id", referencedColumnName = "id"))
	private Set<Airport> stops = new HashSet<>();

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Airport start;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Airport end;

	@ManyToMany(mappedBy = "flights")
	private Set<FlightReservation> reservations = new HashSet<>();

	@Column(nullable = false)
	private float averageScore;

	@Column(nullable = false)
	private int numberOfVotes;
	
	public Flight() {
		super();
	}

	public Flight(Date takeoffTime, Date landingTime, int flightTime, double flightDistance, double price,
			Airline airline, Airport start, Airport end, Set<Airport> stops, float averageScore, int numberOfVotes) {
		super();
		this.takeoffTime = takeoffTime;
		this.landingTime = landingTime;
		this.flightTime = flightTime;
		this.flightDistance = flightDistance;
		this.price = price;
		this.airline = airline;
		this.start = start;
		this.end = end;
		this.stops = stops;
		this.averageScore = averageScore;
		this.numberOfVotes = numberOfVotes;
	}

	public float getAverageScore() {
		return averageScore;
	}

	public void setAverageScore(float averageScore) {
		this.averageScore = averageScore;
	}

	public int getNumberOfVotes() {
		return numberOfVotes;
	}

	public void setNumberOfVotes(int numberOfVotes) {
		this.numberOfVotes = numberOfVotes;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getTakeoffTime() {
		return takeoffTime;
	}

	public void setTakeoffTime(Date takeoffTime) {
		this.takeoffTime = takeoffTime;
	}

	public Date getLandingTime() {
		return landingTime;
	}

	public void setLandingTime(Date landingTime) {
		this.landingTime = landingTime;
	}

	public int getFlightTime() {
		return flightTime;
	}

	public void setFlightTime(int flightTime) {
		this.flightTime = flightTime;
	}

	public double getFlightDistance() {
		return flightDistance;
	}

	public void setFlightDistance(double flightDistance) {
		this.flightDistance = flightDistance;
	}

	public Airline getAirline() {
		return airline;
	}

	public void setAirline(Airline airline) {
		this.airline = airline;
	}

	public Set<Seat> getSeats() {
		return seats;
	}

	public void setSeats(Set<Seat> seats) {
		this.seats = seats;
	}

	public Set<Airport> getStops() {
		return stops;
	}

	public void setStops(Set<Airport> stops) {
		this.stops = stops;
	}

	public Airport getStart() {
		return start;
	}

	public void setStart(Airport start) {
		this.start = start;
	}

	public Airport getEnd() {
		return end;
	}

	public void setEnd(Airport end) {
		this.end = end;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Set<FlightReservation> getReservations() {
		return reservations;
	}

	public void setReservations(Set<FlightReservation> reservations) {
		this.reservations = reservations;
	}

}
