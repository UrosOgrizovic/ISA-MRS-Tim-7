package com.FlightsReservations.domain.dto;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import com.FlightsReservations.domain.enums.SeatType;
import com.FlightsReservations.domain.enums.TripType;

public class FlightSearchRequestDTO {
	@NotEmpty
	@Valid
	private List<FlightSearchQueryDTO> queries;

	@Positive
	private Integer numOfPassengers;

	@PositiveOrZero
	@NotNull
	private Integer pageNumber;

	@Positive
	@NotNull
	private Integer resultCount;
	
	@NotNull
	private TripType tripType;
	
	private SeatType seatType;

	public FlightSearchRequestDTO() {
		super();
	}

	public FlightSearchRequestDTO(List<FlightSearchQueryDTO> queries, TripType tripType, SeatType seatType,
			Integer numOfPassengers, Integer pageNumber, Integer resultCount) {
		super();
		this.queries = queries;
		this.tripType = tripType;
		this.seatType = seatType;
		this.numOfPassengers = numOfPassengers;
		this.pageNumber = pageNumber;
		this.resultCount = resultCount;
	}

	public List<FlightSearchQueryDTO> getQueries() {
		return queries;
	}

	public void setQueries(List<FlightSearchQueryDTO> queries) {
		this.queries = queries;
	}

	public TripType getTripType() {
		return tripType;
	}

	public void setTripType(TripType tripType) {
		this.tripType = tripType;
	}

	public SeatType getSeatType() {
		return seatType;
	}

	public void setSeatType(SeatType seatType) {
		this.seatType = seatType;
	}

	public Integer getNumOfPassengers() {
		return numOfPassengers;
	}

	public void setNumOfPassengers(Integer numOfPassengers) {
		this.numOfPassengers = numOfPassengers;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Integer getResultCount() {
		return resultCount;
	}

	public void setResultCount(Integer resultCount) {
		this.resultCount = resultCount;
	}
}
