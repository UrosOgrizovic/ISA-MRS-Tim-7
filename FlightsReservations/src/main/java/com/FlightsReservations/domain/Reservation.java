package com.FlightsReservations.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Reservation {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@JsonFormat(pattern = "dd-MM-yyyy HH:mm")
	@Column(nullable = false)
	private Date dateOfReservation;

	@Column(nullable = false)
	private Float discount;

	@Column(nullable = false)
	private Float price;

	@Column(nullable = false)
	private Boolean confirmed;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private User owner;
	
	public Reservation(Date dateOfReservation, Float discount, Float price, Boolean confirmed, User owner) {
		super();
		this.dateOfReservation = dateOfReservation;
		this.discount = discount;
		this.price = price;
		this.confirmed = confirmed;
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

	public Float getDiscount() {
		return discount;
	}

	public void setDiscount(Float discount) {
		this.discount = discount;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Boolean getConfirmed() {
		return confirmed;
	}

	public void setConfirmed(Boolean confirmed) {
		this.confirmed = confirmed;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	@Override
	public String toString() {
		return "Reservation [id=" + id + ", dateOfReservation=" + dateOfReservation + ", discount=" + discount
				+ ", price=" + price + ", confirmed=" + confirmed + ", owner=" + owner + "]";
	}
}
