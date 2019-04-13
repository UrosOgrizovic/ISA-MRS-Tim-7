package com.FlightsReservations.domain.dto;

import java.util.Date;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;

public class FlightSearchQueryDTO {
	@NotBlank
	private String startAirportName;

	@NotBlank
	private String endAirportName;
	
	@Future
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date takeOffTime;
	
	@Future
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date landingTime;
	
	public FlightSearchQueryDTO() {
		super();
	}

	public FlightSearchQueryDTO(String startAirportName, String endAirportName, Date takeOffTime, Date landingTime) {
		super();
		this.startAirportName = startAirportName;
		this.endAirportName = endAirportName;
		this.takeOffTime = takeOffTime;
		this.landingTime = landingTime;
	}

	public String getStartAirportName() {
		return startAirportName;
	}

	public void setStartAirport(String startAirportName) {
		this.startAirportName = startAirportName;
	}

	public String getEndAirportName() {
		return endAirportName;
	}

	public void setEndAirportName(String endAirportName) {
		this.endAirportName = endAirportName;
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

}
