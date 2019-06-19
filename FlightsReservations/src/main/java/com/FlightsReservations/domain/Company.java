package com.FlightsReservations.domain;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Version;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="company_type", discriminatorType=DiscriminatorType.STRING)
public class Company {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, nullable = false)
	private String name;

	@Column(unique = false, nullable = false)
	private String city;
	
	@Column(unique = false, nullable = false)
	private String state;
	
	@Column(nullable = false)
	private Float longitude;

	@Column(nullable = false)
	private Float latitude;

	@Column(nullable = false)
	private String promoDescription;

	@Column(nullable = false)
	private float averageScore;

	@Column(nullable = false)
	private int numberOfVotes;

	@Version
	@Column(nullable = false)
	private Long version;
	
	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
	
	//TODO: add admin field
	/*
	@Column(nullable = true)
	private Admin admin;
	*/
	
	public Company() {
		super();
	}

	public Company(String name, Float longitude, Float latitude, String city, String state, String promoDescription, float avarageScore,
			int numberOfVotes) {
		super();
		this.name = name;
		this.longitude = longitude;
		this.latitude = latitude;
		this.city = city;
		this.state = state;
		this.promoDescription = promoDescription;
		this.averageScore = avarageScore;
		this.numberOfVotes = numberOfVotes;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float getLongitude() {
		return longitude;
	}

	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}

	public Float getLatitude() {
		return latitude;
	}

	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}

	public String getPromoDescription() {
		return promoDescription;
	}

	public void setPromoDescription(String promoDescription) {
		this.promoDescription = promoDescription;
	}

	public float getAverageScore() {
		return averageScore;
	}

	public void setAverageScore(float avarageScore) {
		this.averageScore = avarageScore;
	}

	public int getNumberOfVotes() {
		return numberOfVotes;
	}

	public void setNumberOfVotes(int numberOfVotes) {
		this.numberOfVotes = numberOfVotes;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	
}
