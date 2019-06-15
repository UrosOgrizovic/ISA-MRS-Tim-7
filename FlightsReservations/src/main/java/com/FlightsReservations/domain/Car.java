package com.FlightsReservations.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Car {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String manufacturer;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private int yearOfManufacture;
	
	@Column(nullable = false)
	private String color;
	
	@Column(nullable = false)
	private double pricePerHour;
	
	@ElementCollection
	private Set<Discount> discounts;
	
	@Version
	@Column(nullable = false)
	private Long version;
	
	//@JsonIgnore is used so as to avoid infinite recursion
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private RACSBranchOffice racsBranchOffice;

	@Column(nullable = false)
	private float averageScore;

	@Column(nullable = false)
	private int numberOfVotes;
	
	public Long getVersion() {
		return version;
	}
	
	public void setVersion(Long version) {
		this.version = version;
	}
	
	
	public int getNumberOfVotes() {
		return numberOfVotes;
	}

	public void setNumberOfVotes(int numberOfVotes) {
		this.numberOfVotes = numberOfVotes;
	}

	public float getAverageScore() {
		return averageScore;
	}
	
	public Set<Discount> getDiscounts() {
		return discounts;
	}

	public void setDiscounts(Set<Discount> discounts) {
		this.discounts = discounts;
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

	public RACSBranchOffice getRACSBranchOffice() {
		return racsBranchOffice;
	}

	public void setRACSBranchOffice(RACSBranchOffice racsBranchOffice) {
		this.racsBranchOffice = racsBranchOffice;
	}
	
	public double getPricePerHour() {
		return pricePerHour;
	}

	public void setPricePerHour(double pricePerHour) {
		this.pricePerHour = pricePerHour;
	}
	
	public void setAverageScore(float averageScore) {
		this.averageScore = averageScore;
	}

	public Car(String manufacturer,String name, int yearOfManufacture,
			String color, RACSBranchOffice racsBranchOffice, double pricePerHour, float averageScore, int numberOfVotes) {
		super();
		this.manufacturer = manufacturer;
		this.name = name;
		this.yearOfManufacture = yearOfManufacture;
		this.color = color;
		this.racsBranchOffice = racsBranchOffice;
		this.pricePerHour = pricePerHour;
		this.averageScore = averageScore;
		this.numberOfVotes = numberOfVotes;
	}
	
	public Car() {
		super();
	}
	

	@Override
	public String toString() {
		return "Car [id=" + id + ", manufacturer=" + manufacturer + ", name=" + name + ", yearOfManufacture="
				+ yearOfManufacture + ", color=" + color + "]";
	}
	
	public float getDiscountValueForPeriod(Date startTime, Date endTime) {
		if (discounts != null) {
			for (Discount d : discounts) {
				if (d.getStartTime().compareTo(startTime) == 0 && d.getEndTime().compareTo(endTime) == 0) {
					return d.getDiscountValue();
				}
			}
			return 0;
		}
		return 0;
	}
	
	public boolean checkIfAnyDiscountsForPeriod(Date startTime, Date endTime) {
		if (discounts != null) {
			for (Discount d : discounts) {
				if ( (d.getStartTime().compareTo(startTime) == 0 || d.getStartTime().after(startTime)) && ( d.getEndTime().compareTo(endTime) == 0) || d.getEndTime().before(endTime)) {
					return true;
				}
			}
			return false;
		}
		return false;
		
	}

}
	