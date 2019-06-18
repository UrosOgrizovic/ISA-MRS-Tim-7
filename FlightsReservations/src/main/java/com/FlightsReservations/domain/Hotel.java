package com.FlightsReservations.domain;


import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.FlightsReservations.domain.dto.HotelDTO;

@Entity
@DiscriminatorValue("H")
public class Hotel extends Company
{
	@OneToMany(mappedBy = "hotel")
	private Set<HotelPricelistItem> pricelist = new HashSet<HotelPricelistItem>();
	

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "hotel_id")
	private Set<Room> roomConfiguration = new HashSet<Room>();
	
	//@OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "admin")
	private HotelAdmin admin;
	
	
	public Set<Room> getRoomConfiguration() {
		return roomConfiguration;
	}

	public void setRoomConfiguration(Set<Room> roomConfiguration) {
		this.roomConfiguration = roomConfiguration;
	}
	

	public Set<HotelPricelistItem> getPricelist() {
		return pricelist;
	}

	public void setPricelist(Set<HotelPricelistItem> pricelist) {
		this.pricelist = pricelist;
	}

	public HotelAdmin getAdmin()
	{
		return admin;
	}

	public void setAdmin(HotelAdmin admin)
	{
		this.admin = admin;
	}
	public Hotel() {
		super();
	}

	public Hotel(String name, Float longitude, Float latitude, String city, String state, String promoDescription, float avarageScore,
			int numberOfVotes) {
		super(name, longitude, latitude, city, state, promoDescription, avarageScore, numberOfVotes);
	}
	
	public Hotel(HotelDTO dto)
	{
		this.setAverageScore(dto.getAverageScore() );
		this.setCity(dto.getCity());
		this.setLatitude(dto.getLatitude());
		this.setLongitude(dto.getLongitude());
		this.setName(dto.getName() );
		this.setNumberOfVotes(dto.getNumberOfVotes() );
		this.setPromoDescription(dto.getPromoDescription() );
		this.setState(dto.getState());
		//this.setBranchOffices();
		//this.setPricelist(pricelist);
		//this.setAdmin();
	}
	
	
}