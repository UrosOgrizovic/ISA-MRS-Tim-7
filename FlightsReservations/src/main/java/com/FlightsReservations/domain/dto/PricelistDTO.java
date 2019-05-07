package com.FlightsReservations.domain.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import com.FlightsReservations.domain.AirlinePriceList;

public class PricelistDTO {
	@NotNull
	@PositiveOrZero
	public double first;
	
	@NotNull
	@PositiveOrZero
	public double business;
	
	@NotNull
	@PositiveOrZero
	public double economic;
	
	@NotBlank
	public String airline;

	public PricelistDTO() {
		super();
	}

	public PricelistDTO(AirlinePriceList ap) {
		super();
		first = ap.getFirst();
		business = ap.getBussines();
		economic = ap.getEconomic();
		airline = ap.getAirline().getName();
	}

	public double getFirst() {
		return first;
	}

	public void setFirst(double first) {
		this.first = first;
	}

	public double getBusiness() {
		return business;
	}

	public void setBusiness(double business) {
		this.business = business;
	}

	public double getEconomic() {
		return economic;
	}

	public void setEconomic(double economic) {
		this.economic = economic;
	}

	public String getAirline() {
		return airline;
	}

	public void setAirline(String airline) {
		this.airline = airline;
	}

}
