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

import com.FlightsReservations.domain.dto.AirportDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Airport {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true, nullable = false)
	private String name;

	@Column(nullable = false)
	private String city;

	@Column(nullable = false)
	private String state;
	
	@Column(nullable = false)
	private float longitude;
	
	@Column(nullable = false)
	private float latitude;
	
	@JsonBackReference
	@ManyToMany(mappedBy = "airports")
	Set<Airline> airlines = new HashSet<>();
	
	@ManyToMany(mappedBy = "stops")
	@JsonBackReference
	Set<Flight> stops = new HashSet<>();
	
	@OneToMany(mappedBy = "start")
	Set<Flight> starts = new HashSet<>();
	
	@OneToMany(mappedBy = "end")
	Set<Flight> ends = new HashSet<>();

	public Airport() {
		super();
	}

	public Airport(String name, String city, String state, float longitude, float latitude) {
		super();
		this.name = name;
		this.city = city;
		this.state = state;
		this.longitude = longitude;
		this.latitude = latitude;
	}
	
	public Airport(AirportDTO dto) {
		this(dto.getName(), dto.getCity(), dto.getState(), dto.getLongitude(), dto.getLatitude());
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
