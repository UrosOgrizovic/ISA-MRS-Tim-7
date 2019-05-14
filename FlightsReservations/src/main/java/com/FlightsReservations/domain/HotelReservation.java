package com.FlightsReservations.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
public class HotelReservation 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	@NotNull
	private Room room;
	
	@NotNull
	private Date start;
	
	@NotNull
	private Date end;
	
	@NotNull
	@OneToMany
	private List<User> customers;
	
	@NotNull
	private Float discount;
	
	public HotelReservation() {}

	public HotelReservation(Long id, @NotNull Room room, @NotNull Date start, @NotNull Date end,
			@NotNull List<User> customers, @NotNull Float discount)
	{
		super();
		this.id = id;
		this.room = room;
		this.start = start;
		this.end = end;
		this.customers = customers;
		this.discount = discount;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Room getRoom()
	{
		return room;
	}

	public void setRoom(Room room)
	{
		this.room = room;
	}

	public Date getStart()
	{
		return start;
	}

	public void setStart(Date start)
	{
		this.start = start;
	}

	public Date getEnd()
	{
		return end;
	}

	public void setEnd(Date end)
	{
		this.end = end;
	}

	public List<User> getCustomers()
	{
		return customers;
	}

	public void setCustomers(List<User> customers)
	{
		this.customers = customers;
	}

	public Float getDiscount()
	{
		return discount;
	}

	public void setDiscount(Float discount)
	{
		this.discount = discount;
	}
	
	
	
}
