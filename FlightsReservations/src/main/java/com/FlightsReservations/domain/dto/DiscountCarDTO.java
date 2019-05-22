package com.FlightsReservations.domain.dto;

import java.util.Date;

import com.FlightsReservations.domain.Car;

public class DiscountCarDTO {
	private Long id;
	

	private String manufacturer;
	private String name;
	private int yearOfManufacture;
	private String color;
	private double totalPrice;
	private Date startTime;
	private Date endTime;
	private double initialPrice;
	private float discountValue;
	
	public double getInitialPrice() {
		return initialPrice;
	}
	public void setInitialPrice(double initialPrice) {
		this.initialPrice = initialPrice;
	}
	public float getDiscountValue() {
		return discountValue;
	}
	public void setDiscountValue(float discountValue) {
		this.discountValue = discountValue;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
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
	
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	public DiscountCarDTO() {
		super();
	}
	public DiscountCarDTO(Long id, String manufacturer, String name, int yearOfManufacture, String color, double totalPrice,
			Date startTime, Date endTime, double initialPrice, float discountValue) {
		super();
		this.id = id;
		this.manufacturer = manufacturer;
		this.name = name;
		this.yearOfManufacture = yearOfManufacture;
		this.color = color;
		this.totalPrice = totalPrice;
		this.startTime = startTime;
		this.endTime = endTime;
		this.initialPrice = initialPrice;
		this.discountValue = discountValue;
	}
	
	public DiscountCarDTO(Car car, double totalPrice, Date startTime, Date endTime, double initialPrice, float discountValue) {
		this.id = car.getId();
		this.manufacturer = car.getManufacturer();
		this.name = car.getName();
		this.yearOfManufacture = car.getYearOfManufacture();
		this.color = car.getColor();
		this.totalPrice = totalPrice;
		this.startTime = startTime;
		this.endTime = endTime;
		this.initialPrice = initialPrice;
		this.discountValue = discountValue;
	}
	
	
}
