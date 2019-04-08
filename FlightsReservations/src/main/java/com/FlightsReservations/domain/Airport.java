package com.FlightsReservations.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Airport {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private String city;

	@Column(nullable = false)
	private String state;
	
	@Column(nullable = false)
	private float longitude;
	
	@Column(nullable = false)
	private float latitude;
	
	@ManyToMany(mappedBy = "airports")
	Set<Airline> airlines = new HashSet<>();
	
	@ManyToMany(mappedBy = "stops")
	Set<Flight> stops = new HashSet<>();
	
	@OneToMany(mappedBy = "start")
	Set<Flight> starts = new HashSet<>();
	
	@OneToMany(mappedBy = "end")
	Set<Flight> ends = new HashSet<>();

	public Airport() {
		super();
	}

	public Airport(Long id, String city, String state, float longitude, float latitude, Set<Airline> airlines,
			Set<Flight> stops, Set<Flight> starts, Set<Flight> ends) {
		super();
		this.id = id;
		this.city = city;
		this.state = state;
		this.longitude = longitude;
		this.latitude = latitude;
		this.airlines = airlines;
		this.stops = stops;
		this.starts = starts;
		this.ends = ends;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public Set<Airline> getAirlines() {
		return airlines;
	}

	public void setAirlines(Set<Airline> airlines) {
		this.airlines = airlines;
	}

	public Set<Flight> getStops() {
		return stops;
	}

	public void setStops(Set<Flight> stops) {
		this.stops = stops;
	}

	public Set<Flight> getStarts() {
		return starts;
	}

	public void setStarts(Set<Flight> starts) {
		this.starts = starts;
	}

	public Set<Flight> getEnds() {
		return ends;
	}

	public void setEnds(Set<Flight> ends) {
		this.ends = ends;
	}
}
