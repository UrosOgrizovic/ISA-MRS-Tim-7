package com.FlightsReservations.domain;

import java.util.Map;
import java.util.Set;

public class Hotel
{
	private Long id;
	private String name;
	private String address;//TODO: add address via map
	private String promoDescription;
	private HotelAdmin hotelAdmin;
	private Map<String, Double> servicesPriceList;
	private Set <Room> roomConfiguration;
	private double overallRating;
	private Set<String> additionalServices;
	private String logoPath;
	//TODO: add the list of reservations?
	
	public Hotel() {}
	
	public Hotel(Long id, String name, String address, String promoDescription, HotelAdmin hotelAdmin, Map<String, Double> servicesPriceList,
			Set<Room> roomConfiguration, double overallRating, Set<String> additionalServices, String logoPath)
	{
		this.id = id;
		this.name = name;
		this.address = address;
		this.promoDescription = promoDescription;
		this.hotelAdmin = hotelAdmin;
		this.servicesPriceList = servicesPriceList;
		this.roomConfiguration = roomConfiguration;
		this.overallRating = overallRating;
		this.additionalServices = additionalServices;
		this.logoPath = logoPath;
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

	public HotelAdmin getHotelAdmin()
	{
		return hotelAdmin;
	}

	public void setHotelAdmin(HotelAdmin hotelAdmin)
	{
		this.hotelAdmin = hotelAdmin;
	}

	public Map<String, Double> getServicesPriceList()
	{
		return servicesPriceList;
	}

	public void setServicesPriceList(Map<String, Double> servicesPriceList)
	{
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

	public Set<String> getAdditionalServices()
	{
		return additionalServices;
	}

	public void setAdditionalServices(Set<String> additionalServices)
	{
		this.additionalServices = additionalServices;
	}

	public String getLogoPath()
	{
		return logoPath;
	}

	public void setLogoPath(String logoPath)
	{
		this.logoPath = logoPath;
	}
	
	
	
}
