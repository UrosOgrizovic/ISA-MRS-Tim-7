package com.FlightsReservations.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="reservation_type", discriminatorType=DiscriminatorType.STRING)
public class Reservation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonFormat(pattern = "dd-MM-yyyy HH:mm")
	@Column(nullable = false)
	private Date dateOfReservation;

	@Column(nullable = false)
	private Float price;

	/*
	@Column(nullable = false)
	private Boolean confirmed;
	*/
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private User owner;
	
	public Reservation(Date dateOfReservation, Float price, User owner) {
		super();
		this.dateOfReservation = dateOfReservation;
		this.price = price;
		this.owner = owner;
	}

	public Reservation() {
		super();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDateOfReservation() {
		return dateOfReservation;
	}

	public void setDateOfReservation(Date dateOfReservation) {
		this.dateOfReservation = dateOfReservation;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	@Override
	public String toString() {
		return "Reservation [id=" + id + ", dateOfReservation=" + dateOfReservation + ", price=" + price + 
				", owner=" + owner + "]";
	}
}
