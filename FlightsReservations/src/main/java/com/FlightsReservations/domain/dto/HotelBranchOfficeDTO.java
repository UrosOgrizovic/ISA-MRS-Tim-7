package com.FlightsReservations.domain.dto;

import java.util.HashSet;
import java.util.Set;

import com.FlightsReservations.domain.Company;
import com.FlightsReservations.domain.Room;

public class HotelBranchOfficeDTO {
	private String name;
	private Float longitude;
	private Float latitude;
	private Company company;
	private Set<Room> rooms = new HashSet<Room>();
	
	
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


	public Set<Room> getRooms() {
		return rooms;
	}


	public void setRooms(Set<Room> rooms) {
		this.rooms = rooms;
	}


	public HotelBranchOfficeDTO() {
		super();
	}


	public HotelBranchOfficeDTO(String name, Float longitude, Float latitude, Company company, Set<Room> rooms) {
		super();
		this.name = name;
		this.longitude = longitude;
		this.latitude = latitude;
		this.company = company;
		this.rooms = rooms;
	}
	
	
	
}