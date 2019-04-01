package com.FlightsReservations.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Company {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(unique = true, nullable = false)
	private String name;

	@Column(nullable = false)
	private Float longitude;

	@Column(nullable = false)
	private Float latitude;

	@Column(nullable = false)
	private String promoDescription;

	@Column(nullable = false)
	private float avarageScore;

	@Column(nullable = false)
	private int numberOfVotes;

	public Company() {
		super();
	}

	public Company(String name, Float longitude, Float latitude, String promoDescription, float avarageScore,
			int numberOfVotes) {
		super();
		this.name = name;
		this.longitude = longitude;
		this.latitude = latitude;
		this.promoDescription = promoDescription;
		this.avarageScore = avarageScore;
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

	public float getAvarageScore() {
		return avarageScore;
	}

	public void setAvarageScore(float avarageScore) {
		this.avarageScore = avarageScore;
	}

	public int getNumberOfVotes() {
		return numberOfVotes;
	}

	public void setNumberOfVotes(int numberOfVotes) {
		this.numberOfVotes = numberOfVotes;
	}

}
