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

	@NotNull
	private TripType tripType;

	private SeatType seatType; // can be null

	@Positive
	private Integer numOfPassengers; // can be null

	@PositiveOrZero
	@NotNull
	private Integer startIndex;

	@Positive
	@NotNull
	private Integer numberOfResults;

	public FlightSearchRequestDTO() {
		super();
	}

	public FlightSearchRequestDTO(List<FlightSearchQueryDTO> queries, TripType tripType, SeatType seatType,
			Integer numOfPassengers, Integer startIndex, Integer numberOfResults) {
		super();
		this.queries = queries;
		this.tripType = tripType;
		this.seatType = seatType;
		this.numOfPassengers = numOfPassengers;
		this.startIndex = startIndex;
		this.numberOfResults = numberOfResults;
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

	public Integer getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(Integer startIndex) {
		this.startIndex = startIndex;
	}

	public Integer getNumberOfResults() {
		return numberOfResults;
	}

	public void setNumberOfResults(Integer numberOfResults) {
		this.numberOfResults = numberOfResults;
	}

}
