package com.FlightsReservations.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Car {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank
	private String manufacturer;
	@NotBlank
	private String name;
	@NotNull
	private int yearOfManufacture;
	@NotBlank
	private String color;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private RACS racs;

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

	public RACS getRacs() {
		return racs;
	}

	public void setRacs(RACS racs) {
		this.racs = racs;
	}

	public Car(Long id, @NotBlank String manufacturer, @NotBlank String name, @NotNull int yearOfManufacture,
			@NotBlank String color, RACS racs) {
		super();
		this.id = id;
		this.manufacturer = manufacturer;
		this.name = name;
		this.yearOfManufacture = yearOfManufacture;
		this.color = color;
		this.racs = racs;
	}

	public Car() {
		super();
	}
	
	

}
	