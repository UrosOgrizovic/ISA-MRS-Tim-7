package com.FlightsReservations.domain.dto;

import java.util.HashSet;
import java.util.Set;

import com.FlightsReservations.domain.Car;
import com.FlightsReservations.domain.Discount;

public class CarDTO {
	private Long id;
	private String manufacturer;
	private String name;
	private int yearOfManufacture;
	private String color;
	private String racsBranchOfficeName;
	private double pricePerHour;
	private float averageScore;
	private int numberOfVotes;
	private Set<Discount> discounts = new HashSet<Discount>();
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public float getAverageScore() {
		return averageScore;
	}
	public void setAverageScore(float averageScore) {
		this.averageScore = averageScore;
	}
	
	public int getNumberOfVotes() {
		return numberOfVotes;
	}
	public void setNumberOfVotes(int numberOfVotes) {
		this.numberOfVotes = numberOfVotes;
	}
	public double getPricePerHour() {
		return pricePerHour;
	}
	public void setPricePerHour(double pricePerHour) {
		this.pricePerHour = pricePerHour;
	}
	public String getRacsBranchOfficeName() {
		return racsBranchOfficeName;
	}
	public void setRacsBranchOfficeName(String racsBranchOfficeName) {
		this.racsBranchOfficeName = racsBranchOfficeName;
	}
	public Set<Discount> getDiscounts() {
		return discounts;
	}
	public void setDiscounts(Set<Discount> discounts) {
		this.discounts = discounts;
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
	public CarDTO(String manufacturer, String name, int yearOfManufacture, String color, String racsBranchOfficeName,
			double pricePerHour, float averageScore, int numberOfVotes, Set<Discount> discounts, Long id) {
		super();
		this.manufacturer = manufacturer;
		this.name = name;
		this.yearOfManufacture = yearOfManufacture;
		this.color = color;
		this.racsBranchOfficeName = racsBranchOfficeName;
		this.pricePerHour = pricePerHour;
		this.averageScore = averageScore;
		this.numberOfVotes = numberOfVotes;
		this.discounts = discounts;
		this.id = id;
	}
	public CarDTO() {
		super();
	}
	public CarDTO(Car c) {
		this.id = c.getId();
		this.averageScore = c.getAverageScore();
		this.color = c.getColor();
		this.discounts = c.getDiscounts();
		this.manufacturer = c.getManufacturer();
		this.name = c.getName();
		this.numberOfVotes = c.getNumberOfVotes();
		this.pricePerHour = c.getPricePerHour();
		this.racsBranchOfficeName = c.getRACSBranchOffice().getName();
		this.yearOfManufacture = c.getYearOfManufacture();
	}
<<<<<<< HEAD
}
=======
}
>>>>>>> master
