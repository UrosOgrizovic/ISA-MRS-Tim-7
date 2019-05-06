package com.FlightsReservations.domain.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.FlightsReservations.domain.Airport;
import com.FlightsReservations.domain.Flight;
import com.FlightsReservations.domain.Seat;
import com.fasterxml.jackson.annotation.JsonFormat;

public class FlightDTO {
	private Long id;

	@JsonFormat(pattern = "dd-MM-yyyy HH:mm", timezone="Europe/Belgrade")
	private Date takeoffTime;

	@JsonFormat(pattern = "dd-MM-yyyy HH:mm", timezone="Europe/Belgrade")
	private Date landingTime;

	private int flightTime;
	private double flightDistance;
	private double price;
	private String airlineName;
	private Set<String> stops;
	private String startAirport;
	private String endAirport;
	private Set<SeatDTO> seats;
	private float averageScore;
	private int numberOfVotes;

	public FlightDTO() {
		super();
	}

	public FlightDTO(Long id, Date takeoffTime, Date landingTime, int flightTime, double flightDistance, double price,
			String airlineName, String startAirport, String endAirport, float averageScore, int numberOfVotes) {
		super();
		this.id = id;
		this.takeoffTime = takeoffTime;
		this.landingTime = landingTime;
		this.flightTime = flightTime;
		this.flightDistance = flightDistance;
		this.price = price;
		this.airlineName = airlineName;
		this.startAirport = startAirport;
		this.endAirport = endAirport;
		this.stops = new HashSet<>();
		this.seats = new HashSet<>();
		this.averageScore = averageScore;
		this.numberOfVotes = numberOfVotes;
	}

	public FlightDTO(Flight f) {
		this(f.getId(), f.getTakeoffTime(), f.getLandingTime(), f.getFlightTime(), f.getFlightDistance(), f.getPrice(),
				f.getAirline().getName(), f.getStart().getName(), f.getEnd().getName(), f.getAverageScore(),
				f.getNumberOfVotes());

		for (Airport a : f.getStops())
			stops.add(a.getName());

		for (Seat s : f.getSeats()) {
			seats.add(new SeatDTO(s));
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getTakeoffTime() {
		return takeoffTime;
	}

	public void setTakeoffTime(Date takeoffTime) {
		this.takeoffTime = takeoffTime;
	}

	public Date getLandingTime() {
		return landingTime;
	}

	public void setLandingTime(Date landingTime) {
		this.landingTime = landingTime;
	}

	public int getFlightTime() {
		return flightTime;
	}

	public void setFlightTime(int flightTime) {
		this.flightTime = flightTime;
	}

	public double getFlightDistance() {
		return flightDistance;
	}

	public void setFlightDistance(double flightDistance) {
		this.flightDistance = flightDistance;
	}

	public String getAirlineName() {
		return airlineName;
	}

	public void setAirlineName(String airlineName) {
		this.airlineName = airlineName;
	}

	public Set<String> getStops() {
		return stops;
	}

	public void setStops(Set<String> stops) {
		this.stops = stops;
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

	public Set<SeatDTO> getSeats() {
		return seats;
	}

	public void setSeats(Set<SeatDTO> seats) {
		this.seats = seats;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

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

}
