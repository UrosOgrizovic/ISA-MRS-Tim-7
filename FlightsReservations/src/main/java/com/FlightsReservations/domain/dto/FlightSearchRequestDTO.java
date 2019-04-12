package com.FlightsReservations.domain.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import com.FlightsReservations.domain.enums.SeatType;
import com.FlightsReservations.domain.enums.TripType;

public class FlightSearchRequestDTO {
	@NotEmpty
	private List<FlightSearchQueryDTO> queries;

	@NotNull
	private TripType tripType;

	@NotNull
	private SeatType seatType;

	@Positive
	private Integer numOfPassengers;

	@PositiveOrZero
	private Integer startIndex;

	@Positive
	private Integer endIndex;

	public FlightSearchRequestDTO() {
		super();
	}

	public FlightSearchRequestDTO(List<FlightSearchQueryDTO> queries, TripType tripType, SeatType seatType,
			Integer numOfPassengers, Integer startIndex, Integer endIndex) {
		super();
		this.queries = queries;
		this.tripType = tripType;
		this.seatType = seatType;
		this.numOfPassengers = numOfPassengers;
		this.startIndex = startIndex;
		this.endIndex = endIndex;
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

	public Integer getEndIndex() {
		return endIndex;
	}

	public void setEndIndex(Integer endIndex) {
		this.endIndex = endIndex;
	}

}
