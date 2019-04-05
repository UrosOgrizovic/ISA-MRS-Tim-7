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
	private double bussines;
	
	@Column(nullable = false)
	private double economic;
	
	@Column(nullable = false)
	private double first;
	
	public AirlinePriceList() {
		super();
	}

	public AirlinePriceList(Airline a, double bussines, double economic, double first) {
		super();
		this.airline = a;
		this.bussines = bussines;
		this.economic = economic;
		this.first = first;
	}
	
	public Airline getAirline() {
		return airline;
	}

	public void setAirline(Airline airline) {
		this.airline = airline;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getBussines() {
		return bussines;
	}

	public void setBussines(double bussines) {
		this.bussines = bussines;
	}

	public double getEconomic() {
		return economic;
	}

	public void setEconomic(double economic) {
		this.economic = economic;
	}

	public double getFirst() {
		return first;
	}

	public void setFirst(double first) {
		this.first = first;
	}
}
