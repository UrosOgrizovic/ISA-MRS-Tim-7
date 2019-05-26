package com.FlightsReservations.domain.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import com.FlightsReservations.domain.AirlinePriceList;

public class PricelistDTO {
	@NotNull
	@PositiveOrZero
	public float first;

	@NotNull
	@PositiveOrZero
	public float business;

	@NotNull
	@PositiveOrZero
	public float economic;

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

	public float getFirst() {
		return first;
	}

	public void setFirst(float first) {
		this.first = first;
	}

	public float getBusiness() {
		return business;
	}

	public void setBusiness(float business) {
		this.business = business;
	}

	public float getEconomic() {
		return economic;
	}

	public void setEconomic(float economic) {
		this.economic = economic;
	}

	public String getAirline() {
		return airline;
	}

	public void setAirline(String airline) {
		this.airline = airline;
	}

}
