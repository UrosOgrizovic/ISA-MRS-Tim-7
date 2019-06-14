package com.FlightsReservations.domain.dto;

public class CarDTO {
	private String manufacturer;
	private String name;
	private int yearOfManufacture;
	private String color;
	private Long racsBranchOfficeId;
	private int pricePerHour;
	private float averageScore;
	private int numberOfVotes;
	
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
	public int getPricePerHour() {
		return pricePerHour;
	}
	public void setPricePerHour(int pricePerHour) {
		this.pricePerHour = pricePerHour;
	}
	public Long getRACSBranchOfficeId() {
		return racsBranchOfficeId;
	}
	public void setRACSBranchOfficeId(Long racsBranchOfficeId) {
		this.racsBranchOfficeId = racsBranchOfficeId;
	}
	
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
	
}
