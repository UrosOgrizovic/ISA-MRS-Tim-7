package com.FlightsReservations.domain.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import com.FlightsReservations.domain.Hotel;
import com.FlightsReservations.domain.HotelPricelistItem;
import com.FlightsReservations.domain.Room;
import com.FlightsReservations.domain.dto.HotelReservationDTO;

public class HotelDTO {
	private Long id;
	@NotBlank
	private String name;
	
	@NotNull
	private Float longitude;
	
	@NotNull
	private Float latitude;
	
	@NotBlank
	private String city;
	
	@NotBlank
	private String state;
	
	@NotBlank
	private String promoDescription;

	@NotNull
	@PositiveOrZero
	private float averageScore;

	@NotNull
	@PositiveOrZero
	private int numberOfVotes;
	
	private String hotelAdminEmail;

	private Set<HotelPricelistItem> pricelist = new HashSet<HotelPricelistItem>();
	private Set<Room> roomConfiguration = new HashSet<Room>();
	private Set<HotelReservationDTO> reservations = new HashSet<>();
	
	

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public void setReservations(Set<HotelReservationDTO> reservations)
	{
		this.reservations = reservations;
	}

	public int getNumberOfVotes() {
		return numberOfVotes;
	}

	public void setNumberOfVotes(int numberOfVotes) {
		this.numberOfVotes = numberOfVotes;
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

	public Set<Room> getRoomConfiguration() {
		return roomConfiguration;
	}

	public void setRoomConfiguration(Set<Room> roomConfiguration) {
		this.roomConfiguration = roomConfiguration;
	}
	
	public Set<HotelReservationDTO> getReservations() {
		return reservations;
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

	public String getHotelAdminEmail()
	{
		return hotelAdminEmail;
	}

	public void setHotelAdminEmail(String hotelAdminEmail)
	{
		this.hotelAdminEmail = hotelAdminEmail;
	}

	public void setPricelist(Set<HotelPricelistItem> pricelist)
	{
		this.pricelist = pricelist;
	}

	public Set<HotelPricelistItem> getPricelist()
	{
		return pricelist;
	}
	
	public HotelDTO(Hotel h) {
		this(h.getId(), h.getName(), h.getLongitude(), h.getLatitude(), h.getCity(), h.getState(), h.getPromoDescription(), h.getAverageScore(),
				h.getNumberOfVotes());
		//this.id = h.getId();
	}
	
	public HotelDTO() {
		super();
	}

	public HotelDTO(Long id, String name, Float longitude, Float latitude, String city, String state, String promoDescription, float score,
			int numberOfVotes) {
		super();
		this.id = id;
		this.name = name;
		this.longitude = longitude;
		this.latitude = latitude;
		this.city = city;
		this.state = state;
		this.promoDescription = promoDescription;
		this.averageScore = score;
		this.numberOfVotes = numberOfVotes;
	}
	
	public HotelDTO(String name, Float longitude, Float latitude, String city, String state, String promoDescription, float score,
			int numberOfVotes) {
		super();
		this.name = name;
		this.longitude = longitude;
		this.latitude = latitude;
		this.city = city;
		this.state = state;
		this.promoDescription = promoDescription;
		this.averageScore = score;
		this.numberOfVotes = numberOfVotes;
	}
	
	public HotelDTO(Long id, String name, Float longitude, Float latitude, String city, String state, String promoDescription, float score,
			int numberOfVotes, String email) {
		super();
		this.id = id;
		this.name = name;
		this.longitude = longitude;
		this.latitude = latitude;
		this.city = city;
		this.state = state;
		this.promoDescription = promoDescription;
		this.averageScore = score;
		this.numberOfVotes = numberOfVotes;
		this.hotelAdminEmail = email;
	}
	
	public HotelDTO(String name, Float longitude, Float latitude, String city, String state, String promoDescription, float score,
			int numberOfVotes, String email) {
		super();
		this.name = name;
		this.longitude = longitude;
		this.latitude = latitude;
		this.city = city;
		this.state = state;
		this.promoDescription = promoDescription;
		this.averageScore = score;
		this.numberOfVotes = numberOfVotes;
		this.hotelAdminEmail = email;
	}

}
