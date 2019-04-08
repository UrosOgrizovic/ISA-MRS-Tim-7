package com.FlightsReservations.domain.dto;

import java.util.Date;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CreateFlightDTO {

	@NotNull
	private Date takeOffTime;

	@NotNull
	private Date landingTime;

	@NotNull
	private int numberOfSeats;
	
	@NotNull
	private int firstClassNum;

	@NotNull
	private int businessClassNum;
	
	@NotNull
	private int economicClassNum;
	
	@NotNull
	private Set<String> stopNames;
	
	@NotBlank
	private String startAirportName;
	
	@NotBlank
	private String endAirportName;
	
	@NotBlank
	private String airlineName;
	
	public CreateFlightDTO() {
		super();
	}

	public Date getTakeOffTime() {
		return takeOffTime;
	}

	public void setTakeOffTime(Date takeOffTime) {
		this.takeOffTime = takeOffTime;
	}

	public Date getLandingTime() {
		return landingTime;
	}

	public void setLandingTime(Date landingTime) {
		this.landingTime = landingTime;
	}

	public int getNumberOfSeats() {
		return numberOfSeats;
	}

	public void setNumberOfSeats(int numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
	}

	public int getFirstClassNum() {
		return firstClassNum;
	}

	public void setFirstClassNum(int firstClassNum) {
		this.firstClassNum = firstClassNum;
	}

	public int getBusinessClassNum() {
		return businessClassNum;
	}

	public void setBusinessClassNum(int businessClassNum) {
		this.businessClassNum = businessClassNum;
	}

	public int getEconomicClassNum() {
		return economicClassNum;
	}

	public void setEconomicClassNum(int economicClassNum) {
		this.economicClassNum = economicClassNum;
	}

	public Set<String> getStopNames() {
		return stopNames;
	}

	public void setStopNames(Set<String> stopNames) {
		this.stopNames = stopNames;
	}

	public String getStartAirportName() {
		return startAirportName;
	}

	public void setStartAirportName(String startAirportName) {
		this.startAirportName = startAirportName;
	}

	public String getEndAirportName() {
		return endAirportName;
	}

	public void setEndAirportName(String endAirportName) {
		this.endAirportName = endAirportName;
	}

	public String getAirlineName() {
		return airlineName;
	}

	public void setAirlineName(String airlineName) {
		this.airlineName = airlineName;
	}

}
