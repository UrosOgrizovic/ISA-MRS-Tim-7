package com.FlightsReservations.domain.dto;

import java.util.ArrayList;
import java.util.List;

import com.FlightsReservations.domain.Flight;
import com.FlightsReservations.domain.FlightReservation;
import com.FlightsReservations.domain.Passenger;

public class FlightReservationDTO {
	private Long id;
	private Float totalPrice;
	private String ownerEmail;
	private Boolean confirmed;
	private List<Long> flights;
	private List<PassengerDTO> passengers;
	
	public FlightReservationDTO() {
		super();
	}

	public FlightReservationDTO(Long id, Float totalPrice, String ownerEmail, Boolean confirmed,
			List<Long> flights, List<PassengerDTO> passengers) {
		super();
		this.id = id;
		this.totalPrice = totalPrice;
		this.flights = flights;
		this.passengers = passengers;
		this.ownerEmail = ownerEmail;
		this.confirmed = confirmed;
	}
	
	public FlightReservationDTO(FlightReservation reservation) {
		this(
			reservation.getId(),
			reservation.getPrice(),
			reservation.getOwner().getEmail(),
			reservation.getConfirmed(),
			new ArrayList<>(),
			new ArrayList<>()
			);
		
		for (Flight f : reservation.getFlights())
			flights.add(f.getId());
		
		for (Passenger p : reservation.getPassengers())
			passengers.add(new PassengerDTO(p));
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Float totalPrice) {
		this.totalPrice = totalPrice;
	}

	public List<PassengerDTO> getPassengers() {
		return passengers;
	}

	public void setPassengers(List<PassengerDTO> passengers) {
		this.passengers = passengers;
	}

	public String getOwnerEmail() {
		return ownerEmail;
	}

	public void setOwnerEmail(String ownerEmail) {
		this.ownerEmail = ownerEmail;
	}

	public Boolean getConfirmed() {
		return confirmed;
	}

	public void setConfirmed(Boolean confirmed) {
		this.confirmed = confirmed;
	}

	public List<Long> getFlights() {
		return flights;
	}

	public void setFlights(List<Long> flights) {
		this.flights = flights;
	}
	
}
