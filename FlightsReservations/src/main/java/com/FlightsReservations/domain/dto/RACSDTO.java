package com.FlightsReservations.domain.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.FlightsReservations.domain.BranchOffice;
import com.FlightsReservations.domain.Car;
import com.FlightsReservations.domain.RACS;
import com.FlightsReservations.domain.RACSAdmin;
import com.FlightsReservations.domain.RACSPricelistItem;

public class RACSDTO {
	private Set<RACSPricelistItem> pricelist = new HashSet<>();
	private Set<Car> cars = new HashSet<>();	
	private ArrayList<String> branchOffices;
	@NotNull
	private RACSAdmin admin;
	@NotBlank
	private String name;
	@NotBlank
	private String city;
	@NotBlank
	private String state;
	private Float longitude;
	private Float latitude;
	@NotBlank
	private String promoDescription;
	@NotNull
	private float averageScore;
	@NotNull
	private int numberOfVotes;
	
	
	public Set<RACSPricelistItem> getPricelist() {
		return pricelist;
	}
	public void setPricelist(Set<RACSPricelistItem> pricelist) {
		this.pricelist = pricelist;
	}
	public Set<Car> getCars() {
		return cars;
	}
	public void setCars(Set<Car> cars) {
		this.cars = cars;
	}
	public ArrayList<String> getBranchOffices() {
		return branchOffices;
	}
	public void setBranchOffices(ArrayList<String> branchOffices) {
		this.branchOffices = branchOffices;
	}
	public RACSAdmin getAdmin() {
		return admin;
	}
	public void setAdmin(RACSAdmin admin) {
		this.admin = admin;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public void setAverageScore(float averageScore) {
		this.averageScore = averageScore;
	}
	public int getNumberOfVotes() {
		return numberOfVotes;
	}
	public void setNumberOfVotes(int numberOfVotes) {
		this.numberOfVotes = numberOfVotes;
	}
	public RACSDTO(Set<RACSPricelistItem> pricelist, Set<Car> cars, ArrayList<String> branchOffices, RACSAdmin admin,
			String name, String city, String state, Float longitude, Float latitude, String promoDescription,
			float averageScore, int numberOfVotes) {
		super();
		this.pricelist = pricelist;
		this.cars = cars;
		this.branchOffices = branchOffices;
		this.admin = admin;
		this.name = name;
		this.city = city;
		this.state = state;
		this.longitude = longitude;
		this.latitude = latitude;
		this.promoDescription = promoDescription;
		this.averageScore = averageScore;
		this.numberOfVotes = numberOfVotes;
		this.branchOffices = branchOffices;
		this.pricelist = pricelist;
	}
	public RACSDTO() {
		super();
	}
	
}
