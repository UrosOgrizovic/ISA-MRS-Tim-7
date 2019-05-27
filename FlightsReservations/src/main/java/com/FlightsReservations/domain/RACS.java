package com.FlightsReservations.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;


@Entity
@DiscriminatorValue("R")
public class RACS extends Company {
	
	@ElementCollection
	private Set<RACSPricelistItem> pricelist;
	
	
	/* orphanRemoval = true - guarantees that when a car is removed from  
	 * the RACS's set it will also be removed from the database
	 * more: https://stackoverflow.com/a/5642615
	*/
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "racs_id")
	private Set<Car> cars = new HashSet<>();	
	
	private ArrayList<String> branchOffices;
	
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
		this.cars.clear();
		if (cars != null) {
			this.cars.addAll(cars);
		}
		
	}
	public ArrayList<String> getBranchOffices() {
		return branchOffices;
	}
	public void setBranchOffices(ArrayList<String> branchOffices) {
		this.branchOffices = branchOffices;
	}
	public RACS(String name, Float longitude, Float latitude, String promoDescription, float averageScore,
			int numberOfVotes) {
		super(name, longitude, latitude, promoDescription, averageScore, numberOfVotes);
	}
	
	public RACS() {
		super();
	}
	
	
	@Override
	public String toString() {
		return "RACS [getId()=" + getId() + ", getName()=" + getName() + ", getLongitude()=" + getLongitude()
				+ ", getLatitude()=" + getLatitude() + ", getPromoDescription()=" + getPromoDescription()
				+ ", getAverageScore()=" + getAverageScore() + ", getNumberOfVotes()=" + getNumberOfVotes() + ", getVersion()=" + getVersion() + "]";
	}
	
	
	
		
	
}
