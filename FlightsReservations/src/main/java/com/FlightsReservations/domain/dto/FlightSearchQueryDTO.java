package com.FlightsReservations.domain.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

public class FlightSearchQueryDTO {
	@NotBlank
	private String startCity;

	@NotBlank
	private String endCity;

	@NotNull
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date takeoffDate;

	public FlightSearchQueryDTO() {
		super();
	}

	public FlightSearchQueryDTO(String startCity, String endCity, Date takeoffDate) {
		super();
		this.startCity = startCity;
		this.endCity = endCity;
		this.takeoffDate = takeoffDate;
	}

	public String getStartCity() {
		return startCity;
	}

	public void setStartCity(String startCity) {
		this.startCity = startCity;
	}

	public String getEndCity() {
		return endCity;
	}

	public void setEndCity(String endCity) {
		this.endCity = endCity;
	}

	public Date getTakeoffDate() {
		return takeoffDate;
	}

	public void setTakeoffDate(Date takeoffDate) {
		this.takeoffDate = takeoffDate;
	}

}
