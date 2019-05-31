package com.FlightsReservations.domain.dto;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;

import com.FlightsReservations.domain.Hotel;

public class SearchHotelDTO {
	@NotBlank
	private String name;
	
	private String city;
	
	private String state;

	private String averageScore;

	private Set<Integer> rooms = new HashSet<>();
	private Set<HotelReservationDTO> reservations = new HashSet<>();

	public SearchHotelDTO() {
		super();
	}

	public SearchHotelDTO(String name, String city, String state, String score) {
		super();
		this.name = name;
		this.city = city;
		this.state = state;
		this.averageScore = score;
	}

	public SearchHotelDTO(Hotel h) {
		this(h.getName(), h.getCity(), h.getState(),Float.toString(h.getAverageScore()));
	}

	public String getAverageScore() {
		return averageScore;
	}

	public void setAverageScore(String avarageScore) {
		this.averageScore = avarageScore;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<HotelReservationDTO> getReservations() {
		return reservations;
	}

	public void setReservations(Set<HotelReservationDTO> reservations) {
		this.reservations = reservations;
	}

	public Set<Integer> getRooms() {
		return rooms;
	}

	public void setRooms(Set<Integer> rooms) {
		this.rooms = rooms;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}


	public String getState()
	{
		return state;
	}

	public void setState(String state)
	{
		this.state = state;
	}

}
