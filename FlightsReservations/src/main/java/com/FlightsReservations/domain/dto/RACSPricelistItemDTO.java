package com.FlightsReservations.domain.dto;

import com.FlightsReservations.domain.RACSPricelistItem;

public class RACSPricelistItemDTO {
	private String name;
	private double price;
	private String racsName;
	
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
	
	public String getRacsName() {
		return racsName;
	}
	public void setRacsName(String racsName) {
		this.racsName = racsName;
	}
	public RACSPricelistItemDTO() {
		super();
	}
	public RACSPricelistItemDTO(String name, double price, String racsName) {
		super();
		this.name = name;
		this.price = price;
		this.racsName = racsName;
	}
	public RACSPricelistItemDTO(RACSPricelistItem pli) {
		this.name = pli.getName();
		this.price = pli.getPrice();
		this.racsName = pli.getRacs().getName();
	}
	
	
<<<<<<< HEAD
}
=======
}
>>>>>>> master
