package com.FlightsReservations.domain;


import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
@DiscriminatorValue("H")
public class Hotel extends Company
{
	@OneToMany(mappedBy = "hotel")
	private Set<PricelistItem> pricelist;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "hotel_id")
	private Set<Room> roomConfiguration;
	
	/*
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "admin")
	private HotelAdmin admin;
	*/
	@OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE}, orphanRemoval = true)
	private Set<HotelReservation> reservations;
	
	public Hotel() {
		super();
	}

	public Hotel(String name, Float longitude, Float latitude, String city, String promoDescription, float avarageScore,
			int numberOfVotes) {
		super(name, longitude, latitude, city, promoDescription, avarageScore, numberOfVotes);
	}


	public Set<PricelistItem> getPricelist() {
		return pricelist;
	}

	public void setPricelist(Set<PricelistItem> pricelist) {
		this.pricelist = pricelist;
	}

	public Set<Room> getRoomConfiguration()
	{
		return roomConfiguration;
	}

	public void setRoomConfiguration(Set<Room> roomConfiguration)
	{
		this.roomConfiguration = roomConfiguration;
	}

	public Set<HotelReservation> getReservations() {
		return reservations;
	}

	public void setReservations(Set<HotelReservation> reservations) {
		this.reservations = reservations;
	}

	
}
