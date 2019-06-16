package com.FlightsReservations.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class AirlinePriceList {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Airline airline;

	@Column(nullable = false)
	private float bussines;

	@Column(nullable = false)
	private float economic;

	@Column(nullable = false)
	private float first;

	public AirlinePriceList() {
		super();
	}

	public AirlinePriceList(Airline airline, float bussines, float economic, float first) {
		super();
		this.airline = airline;
		this.bussines = bussines;
		this.economic = economic;
		this.first = first;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Airline getAirline() {
		return airline;
	}

	public void setAirline(Airline airline) {
		this.airline = airline;
	}

	public float getBussines() {
		return bussines;
	}

	public void setBussines(float bussines) {
		this.bussines = bussines;
	}

	public float getEconomic() {
		return economic;
	}

	public void setEconomic(float economic) {
		this.economic = economic;
	}

	public float getFirst() {
		return first;
	}

	public void setFirst(float first) {
		this.first = first;
	}

}