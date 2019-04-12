package com.FlightsReservations.domain.dto;

import java.util.Date;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;

public class FlightSearchQueryDTO {
	@NotBlank
	private String startAirport;

	@NotBlank
	private String endAirport;
	
	@Future
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date departureTime;
	
	@Future
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date landingTime;
	
	public FlightSearchQueryDTO() {
		super();
	}

	public FlightSearchQueryDTO(String startAirport, String endAirport, Date departureTime, Date landingTime) {
		super();
		this.startAirport = startAirport;
		this.endAirport = endAirport;
		this.departureTime = departureTime;
		this.landingTime = landingTime;
	}

	public String getStartAirport() {
		return startAirport;
	}

	public void setStartAirport(String startAirport) {
		this.startAirport = startAirport;
	}

	public String getEndAirport() {
		return endAirport;
	}

	public void setEndAirport(String endAirport) {
		this.endAirport = endAirport;
	}

	public Date getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(Date departureTime) {
		this.departureTime = departureTime;
	}

	public Date getLandingTime() {
		return landingTime;
	}

	public void setLandingTime(Date landingTime) {
		this.landingTime = landingTime;
	}

}
