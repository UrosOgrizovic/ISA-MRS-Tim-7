package com.FlightsReservations.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class RACS extends Company {
	

	private ArrayList<PricelistItem> pricelist;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "racs_id")
	private Set<Car> cars = new HashSet<>();	
	
	private ArrayList<String> branchOffices;
	
	public ArrayList<PricelistItem> getPricelist() {
		return pricelist;
	}
	public void setPricelist(ArrayList<PricelistItem> pricelist) {
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
	public RACS(String name, Float longitude, Float latitude, String promoDescription, float avarageScore,
			int numberOfVotes) {
		super(name, longitude, latitude, promoDescription, avarageScore, numberOfVotes);
	}
	
	public RACS() {
		super();
	}
	
		
	
}
