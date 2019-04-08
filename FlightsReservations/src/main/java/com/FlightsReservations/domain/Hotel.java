package com.FlightsReservations.domain;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Hotel
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@NotBlank
	private String name;
	@NotBlank
	private String address;
	@NotBlank
	private String promoDescription;
	@NotNull
	private ArrayList<PricelistItem> servicesPriceList;
	@NotNull
	@OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE}, orphanRemoval = true)
	private Set<Room> roomConfiguration;
	@NotNull
	private double overallRating;
	
	
	public Hotel() {
		super();
	}
	
	
	
	

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getPromoDescription()
	{
		return promoDescription;
	}

	public void setPromoDescription(String promoDescription)
	{
		this.promoDescription = promoDescription;
	}


	public Hotel(Long id, @NotBlank String name, @NotBlank String address, @NotBlank String promoDescription,
			@NotNull ArrayList<PricelistItem> servicesPriceList, @NotNull Set<Room> roomConfiguration,
			@NotNull double overallRating) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.promoDescription = promoDescription;
		this.servicesPriceList = servicesPriceList;
		this.roomConfiguration = roomConfiguration;
		this.overallRating = overallRating;
	}





	public ArrayList<PricelistItem> getServicesPriceList() {
		return servicesPriceList;
	}





	public void setServicesPriceList(ArrayList<PricelistItem> servicesPriceList) {
		this.servicesPriceList = servicesPriceList;
	}





	public Set<Room> getRoomConfiguration()
	{
		return roomConfiguration;
	}

	public void setRoomConfiguration(Set<Room> roomConfiguration)
	{
		this.roomConfiguration = roomConfiguration;
	}

	public double getOverallRating()
	{
		return overallRating;
	}

	public void setOverallRating(double overallRating)
	{
		this.overallRating = overallRating;
	}
}
