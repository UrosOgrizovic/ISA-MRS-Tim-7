package com.FlightsReservations.domain.dto;

import java.util.Set;

import com.FlightsReservations.domain.Car;
import com.FlightsReservations.domain.Discount;

public class DiscountCarDTO {
	private String manufacturer;
	private String name;
	private int yearOfManufacture;
	private String color;
	private double pricePerHour;
	private Set<Discount> discounts;
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getYearOfManufacture() {
		return yearOfManufacture;
	}
	public void setYearOfManufacture(int yearOfManufacture) {
		this.yearOfManufacture = yearOfManufacture;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public double getPricePerHour() {
		return pricePerHour;
	}
	public void setPricePerHour(double pricePerHour) {
		this.pricePerHour = pricePerHour;
	}
	public Set<Discount> getDiscounts() {
		return discounts;
	}
	public void setDiscounts(Set<Discount> discounts) {
		this.discounts = discounts;
	}
	public DiscountCarDTO(String manufacturer, String name, int yearOfManufacture, String color, double pricePerHour,
			Set<Discount> discounts) {
		super();
		this.manufacturer = manufacturer;
		this.name = name;
		this.yearOfManufacture = yearOfManufacture;
		this.color = color;
		this.pricePerHour = pricePerHour;
		this.discounts = discounts;
	}
	public DiscountCarDTO() {
		super();
	}
	
	public DiscountCarDTO(Car car) {
		this.manufacturer = car.getManufacturer();
		this.name = car.getName();
		this.yearOfManufacture = car.getYearOfManufacture();
		this.color = car.getColor();
		this.pricePerHour = car.getPricePerHour();
		this.discounts = car.getDiscounts();
	}
	
	
}
