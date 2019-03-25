package com.FlightsReservations.domain;

public class Airline {
	private Long id;
	private String name;
	private String location;
	private String promoDescription;
	
	// rest of attributes will be added later
	
	public Airline() {
		super();
	}
	
	public Airline(String name, String location, String promoDescription) {
		super();
		this.name = name;
		this.location = location;
		this.promoDescription = promoDescription;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getPromoDescription() {
		return promoDescription;
	}

	public void setPromoDescription(String promoDescription) {
		this.promoDescription = promoDescription;
	}
}
