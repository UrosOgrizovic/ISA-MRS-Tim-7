package com.FlightsReservations.domain.dto;

import java.util.Date;
import java.util.Set;

import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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
	private Integer numberOfSeats;
	
	@NotNull
	@PositiveOrZero
	private Integer firstClassNum;

	@NotNull
	@PositiveOrZero
	private Integer businessClassNum;
	
	@NotNull
	@PositiveOrZero
	private Double price;
	
	@NotNull
	private Set<String> stopNames;
	
	@NotBlank
	private String startAirportName;
	
	@NotBlank
	private String endAirportName;
	
	@NotBlank
	private String airlineName;
	
	@NotNull
	@PositiveOrZero
	@Min(value = 1)
	@Max(value = 5)
	private Float averageScore;

	@NotNull
	@PositiveOrZero
	private Integer numberOfVotes;
	
	public CreateFlightDTO() {
		super();
	}
	
	public Float getAverageScore() {
		return averageScore;
	}

	public void setAverageScore(Float averageScore) {
		this.averageScore = averageScore;
	}

	public Integer getNumberOfVotes() {
		return numberOfVotes;
	}

	public void setNumberOfVotes(Integer numberOfVotes) {
		this.numberOfVotes = numberOfVotes;
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

	public Integer getNumberOfSeats() {
		return numberOfSeats;
	}

	public void setNumberOfSeats(Integer numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
	}

	public Integer getFirstClassNum() {
		return firstClassNum;
	}

	public void setFirstClassNum(Integer firstClassNum) {
		this.firstClassNum = firstClassNum;
	}

	public Integer getBusinessClassNum() {
		return businessClassNum;
	}

	public void setBusinessClassNum(Integer businessClassNum) {
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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
}
