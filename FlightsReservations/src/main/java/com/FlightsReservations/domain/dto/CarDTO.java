package com.FlightsReservations.domain.dto;

public class CarDTO {
	private String manufacturer;
	private String name;
	private int yearOfManufacture;
	private String color;
	private Long racsID;
	public CarDTO() {
		super();
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
	public Long getRacsID() {
		return racsID;
	}
	public void setRacsID(Long racsID) {
		this.racsID = racsID;
	}
	
	
}
