package com.FlightsReservations.domain;

import java.util.ArrayList;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity
@DiscriminatorValue("H")
public class Hotel extends Company
{
	@NotNull
	private ArrayList<PricelistItem> pricelist;
	@NotNull
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "hotel_id")
	private Set<Room> roomConfiguration;
		
	public Hotel() {
		super();
	}

	public Hotel(String name, Float longitude, Float latitude, String promoDescription, float avarageScore,
			int numberOfVotes) {
		super(name, longitude, latitude, promoDescription, avarageScore, numberOfVotes);
	}


	public ArrayList<PricelistItem> getPricelist() {
		return pricelist;
	}

	public void setPricelist(ArrayList<PricelistItem> pricelist) {
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

}
