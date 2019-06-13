package com.FlightsReservations.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;


@Entity
@DiscriminatorValue("R")
public class RACS extends Company {
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "racs_id")
	private Set<RACSPricelistItem> pricelist;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "racs_id")
	private Set<RACSBranchOffice> branchOffices;
	
	public Set<RACSPricelistItem> getPricelist() {
		return pricelist;
	}
	public void setPricelist(Set<RACSPricelistItem> pricelist) {
		this.pricelist = pricelist;
	}
	
	public Set<RACSBranchOffice> getBranchOffices() {
		return branchOffices;
	}
	public void setBranchOffices(Set<RACSBranchOffice> branchOffices) {
		this.branchOffices = branchOffices;
	}
	public RACS(String name, Float longitude, Float latitude, String city, String state, String promoDescription, float avarageScore,
			int numberOfVotes) {
		super(name, longitude, latitude, city, state, promoDescription, avarageScore, numberOfVotes);
	}
	
	public RACS() {
		super();
	}
	@Override
	public String toString() {
		return "RACS [getId()=" + getId() + ", getName()=" + getName() + ", getLongitude()=" + getLongitude()
				+ ", getLatitude()=" + getLatitude() + ", getPromoDescription()=" + getPromoDescription()
				+ ", getAverageScore()=" + getAverageScore() + ", getNumberOfVotes()=" + getNumberOfVotes() + "]";
	}
	
	
	
		
	
}
