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
import javax.persistence.OneToOne;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

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

	@Column(nullable = false)
	private Boolean confirmed;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private AbstractUser owner;
	
	// rating that user has given to airline, hotel or RACS
	// this column is nullable because user won't enter a rating when making the reservation, but rather later
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Rating rating;

	@Version
	@Column(nullable = false)
	private Long version;
	
	public Reservation(Date dateOfReservation, Float price, Boolean confirmed, AbstractUser owner) {
		super();
		this.dateOfReservation = dateOfReservation;
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

	public AbstractUser getOwner() {
		return owner;
	}

	public void setOwner(AbstractUser owner) {
		this.owner = owner;
	}
	
	public Rating getRating() {
		return rating;
	}

	public void setRating(Rating rating) {
		this.rating = rating;
	}

	@Override
	public String toString() {
		return "Reservation [id=" + id + ", dateOfReservation=" + dateOfReservation + ", price=" + price
				+ ", confirmed=" + confirmed + ", owner=" + owner + ", rating=" + rating + "]";
	}

}