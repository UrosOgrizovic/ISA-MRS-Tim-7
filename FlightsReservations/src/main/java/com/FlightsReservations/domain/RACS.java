package com.FlightsReservations.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class RACS extends Company {
	

	private ArrayList<PricelistItem> pricelist;
	
	
	/* orphanRemoval = true - guarantees that when a car is removed from  
	 * the RACS's set it will also be removed from the database
	 * more: https://stackoverflow.com/a/5642615
	*/
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
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
	public RACS(String name, Float longitude, Float latitude, String promoDescription, float avarageScore,
			int numberOfVotes) {
		super(name, longitude, latitude, promoDescription, avarageScore, numberOfVotes);
	}
	
	public RACS() {
		super();
	}
	@Override
	public int hashCode() {
		
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((branchOffices == null) ? 0 : branchOffices.hashCode());
		result = prime * result + ((cars == null) ? 0 : cars.hashCode());
		result = prime * result + ((pricelist == null) ? 0 : pricelist.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		if (! super.equals(obj)) return false;
		else {
			RACS other = (RACS) obj;
			if (branchOffices == null) {
				if (other.branchOffices != null)
					return false;
			} else if (!branchOffices.equals(other.branchOffices))
				return false;
			if (cars == null) {
				if (other.cars != null)
					return false;
			} else if (!cars.equals(other.cars))
				return false;
			if (pricelist == null) {
				if (other.pricelist != null)
					return false;
			} else if (!pricelist.equals(other.pricelist))
				return false;
			return true;
		}
		
	}
	
	
	
		
	
}
