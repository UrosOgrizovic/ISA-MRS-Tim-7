package com.FlightsReservations.domain;

import java.io.Serializable;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class HotelPricelistItem implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1310671014090079791L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	private String name;
	
	@NotNull
	private double price;
	
	//@JsonIgnore is used so as to avoid infinite recursion
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	private Hotel hotel;
	
	public HotelPricelistItem() 
	{
		super();
	}
	
	public HotelPricelistItem(@NotBlank String name, @NotNull double price, Hotel hotel) 
	{
		super();
		this.name = name;
		this.price = price;
		this.hotel = hotel;
	}
	public Long getId() 
	{
		return id;
	}
	public void setId(Long id) 
	{
		this.id = id;
	}
	
	public Hotel getHotel() 
	{
		return hotel;
	}
	public void setHotel(Hotel hotel) 
	{
		this.hotel = hotel;
	}
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
	public static long getSerialversionuid() 
	{
		return serialVersionUID;
	}
	
	
}