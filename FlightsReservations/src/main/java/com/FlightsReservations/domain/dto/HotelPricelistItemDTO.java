package com.FlightsReservations.domain.dto;

import com.FlightsReservations.domain.HotelPricelistItem;

public class HotelPricelistItemDTO {
	private String name;
	private double price;
	private String hotelName;
	
	public String getName() 
	{
		return name;
	}
	public void setName(String name) 
	{
		this.name = name;
	}
	public double getPrice() 
	{
		return price;
	}
	public void setPrice(double price) 
	{
		this.price = price;
	}
	
	public String getHotelName() 
	{
		return hotelName;
	}
	public void setHotelName(String hotelName) 
	{
		this.hotelName = hotelName;
	}
	public HotelPricelistItemDTO() 
	{
		super();
	}
	public HotelPricelistItemDTO(String name, double price, String hotelName) 
	{
		super();
		this.name = name;
		this.price = price;
		this.hotelName = hotelName;
	}
	public HotelPricelistItemDTO(HotelPricelistItem hpl) {
		this.name = hpl.getName();
		this.price = hpl.getPrice();
		if(hpl.getHotel()!=null)this.hotelName = hpl.getHotel().getName();
		else this.hotelName = "";
	}
	
	
}