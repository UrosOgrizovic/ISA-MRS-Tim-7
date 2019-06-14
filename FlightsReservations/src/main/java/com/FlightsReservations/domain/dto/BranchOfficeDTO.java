package com.FlightsReservations.domain.dto;

public class BranchOfficeDTO {
	private String name;
	private Long longitude;
	private Long latitude;
	private String companyName;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getLongitude() {
		return longitude;
	}
	public void setLongitude(Long longitude) {
		this.longitude = longitude;
	}
	public Long getLatitude() {
		return latitude;
	}
	public void setLatitude(Long latitude) {
		this.latitude = latitude;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public BranchOfficeDTO(String name, Long longitude, Long latitude, String companyName) {
		super();
		this.name = name;
		this.longitude = longitude;
		this.latitude = latitude;
		this.companyName = companyName;
	}
	public BranchOfficeDTO() {
		super();
	}
	
	
	
}
