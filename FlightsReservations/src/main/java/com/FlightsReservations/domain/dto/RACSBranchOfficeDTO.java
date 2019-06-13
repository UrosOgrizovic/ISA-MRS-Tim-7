package com.FlightsReservations.domain.dto;

import java.util.HashSet;
import java.util.Set;

import com.FlightsReservations.domain.Car;
import com.FlightsReservations.domain.Company;

public class RACSBranchOfficeDTO {

	private String name;
	private Float longitude;
	private Float latitude;
	private Company company;
	private Set<Car> cars = new HashSet<>();
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Float getLongitude() {
		return longitude;
	}
	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}
	public Float getLatitude() {
		return latitude;
	}
	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public Set<Car> getCars() {
		return cars;
	}
	public void setCars(Set<Car> cars) {
		this.cars = cars;
	}
	public RACSBranchOfficeDTO(String name, Float longitude, Float latitude, Company company, Set<Car> cars) {
		super();
		this.name = name;
		this.longitude = longitude;
		this.latitude = latitude;
		this.company = company;
		this.cars = cars;
	}
	public RACSBranchOfficeDTO() {
		super();
	}
	
}
