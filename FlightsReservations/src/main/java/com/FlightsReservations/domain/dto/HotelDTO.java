package com.FlightsReservations.domain.dto;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import com.FlightsReservations.domain.Hotel;
import com.FlightsReservations.domain.dto.HotelReservationDTO;

public class HotelDTO {
	@NotBlank
	private String name;
	
	@NotNull
	private Float longitude;
	
	@NotNull
	private Float latitude;
	
	@NotBlank
	private String promoDescription;

	@NotNull
	@PositiveOrZero
	private float averageScore;

	@NotNull
	@PositiveOrZero
	private int numberOfVotes;

	private Set<String> rooms = new HashSet<>();
	private Set<HotelReservationDTO> reservations = new HashSet<>();

	public HotelDTO() {
		super();
	}

	public HotelDTO(String name, Float longitude, Float latitude, String promoDescription, float score,
			int numberOfVotes) {
		super();
		this.name = name;
		this.longitude = longitude;
		this.latitude = latitude;
		this.promoDescription = promoDescription;
		this.averageScore = score;
		this.numberOfVotes = numberOfVotes;
	}

	public int getNumberOfVotes() {
		return numberOfVotes;
	}

	public void setNumberOfVotes(int numberOfVotes) {
		this.numberOfVotes = numberOfVotes;
	}

	public HotelDTO(Hotel h) {
		this(h.getName(), h.getLongitude(), h.getLatitude(), h.getPromoDescription(), h.getAverageScore(),
				h.getNumberOfVotes());
	}

	public float getAverageScore() {
		return averageScore;
	}

	public void setAverageScore(float avarageScore) {
		this.averageScore = avarageScore;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float getLongitude() {
		return longitude;
	}

	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}

	public Float getLatitude() {
		return latitude;
	}

	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}

	public String getPromoDescription() {
		return promoDescription;
	}

	public void setPromoDescription(String promoDescription) {
		this.promoDescription = promoDescription;
	}

	public Set<HotelReservationDTO> getReservations() {
		return reservations;
	}

	public void setAirports(Set<HotelReservationDTO> reservations) {
		this.reservations = reservations;
	}

	public Set<String> getRooms() {
		return rooms;
	}

	public void setRooms(Set<String> rooms) {
		this.rooms = rooms;
	}

}
