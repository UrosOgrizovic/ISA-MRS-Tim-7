package com.FlightsReservations.domain;

import javax.persistence.Embeddable;

@Embeddable
public class RACSPricelistItem {

	private String name;
	private double price;
	
	public RACSPricelistItem() {
		super();
	}
	public RACSPricelistItem(String name, double price) {
		super();
		this.name = name;
		this.price = price;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	
}
