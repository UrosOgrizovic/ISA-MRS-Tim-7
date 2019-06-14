package com.FlightsReservations.domain.dto;

import java.util.HashSet;
import java.util.Set;

import com.FlightsReservations.domain.Car;
import com.FlightsReservations.domain.Company;
import com.FlightsReservations.domain.RACSBranchOffice;

public class RACSBranchOfficeDTO {

	private String name;
	private Float longitude;
	private Float latitude;
	private Set<CarDTO> cars = new HashSet<CarDTO>();
	private String RACSCompanyName;
	
	
	public String getRACSCompanyName() {
		return RACSCompanyName;
	}
	public void setRACSCompanyName(String rACSCompanyName) {
		RACSCompanyName = rACSCompanyName;
	}
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
	
	public Set<CarDTO> getCars() {
		return cars;
	}
	public void setCars(Set<CarDTO> cars) {
		this.cars = cars;
	}
	public RACSBranchOfficeDTO(String name, Float longitude, Float latitude, Company company, Set<CarDTO> cars, String RACSCompanyName) {
		super();
		this.name = name;
		this.longitude = longitude;
		this.latitude = latitude;
		this.RACSCompanyName = RACSCompanyName;
		this.cars = cars;
	}
	public RACSBranchOfficeDTO() {
		super();
	}
	public RACSBranchOfficeDTO(RACSBranchOffice rbo) {
		Set<Car> cars = rbo.getCars();
		Set<CarDTO> cardtos = new HashSet<CarDTO>();
		for (Car c : cars) { 
			CarDTO cdto = new CarDTO(c);
			cardtos.add(cdto);
		}
		this.cars = cardtos;
		this.latitude = rbo.getLatitude();
		this.longitude = rbo.getLongitude();
		this.name = rbo.getName();
		this.RACSCompanyName = rbo.getCompany().getName();
	}
	
}
