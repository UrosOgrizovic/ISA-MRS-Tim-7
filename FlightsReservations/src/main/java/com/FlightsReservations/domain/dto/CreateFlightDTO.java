package com.FlightsReservations.domain.dto;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import com.fasterxml.jackson.annotation.JsonFormat;

public class CreateFlightDTO {

	@NotNull
	@Future
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm")
	private Date takeOffTime;

	@NotNull
	@Future
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm")
	private Date landingTime;

	@NotNull
	@Positive
	private int numberOfSeats;
	
	@NotNull
	@PositiveOrZero
	private int firstClassNum;

	@NotNull
	@PositiveOrZero
	private int businessClassNum;
	
	@NotNull
	private Set<String> stopNames;
	
	@NotBlank
	private String startAirportName;
	
	@NotBlank
	private String endAirportName;
	
	@NotBlank
	private String airlineName;
	
	@NotNull
	private float averageScore;

	@NotNull
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
