package com.FlightsReservations.domain;

import java.io.Serializable;

public class PricelistItem implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3948229935567519125L;
	
	private String name;
	private double price;
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
	public PricelistItem(String name, double price) {
		super();
		this.name = name;
		this.price = price;
	}
	public PricelistItem() {
		super();
	}
	
	
}
