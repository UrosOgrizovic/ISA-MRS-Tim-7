package com.FlightsReservations.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;



@Entity
@DiscriminatorValue("R")
public class RACS extends Company {
	

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "racs_id")
	private Set<RACSPricelistItem> pricelist = new HashSet<RACSPricelistItem>();
	
	@OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	private RACSAdmin admin;
	

	public RACSAdmin getAdmin() {
		return admin;
	}
	public void setAdmin(RACSAdmin admin) {
		this.admin = admin;
	}
	public Set<RACSPricelistItem> getPricelist() {
		return pricelist;
	}
	public void setPricelist(Set<RACSPricelistItem> pricelist) {
		this.pricelist = pricelist;
		this.pricelist.clear();
		if (pricelist != null) {
			this.pricelist.addAll(pricelist);
		}
	}
	
	public RACS(String name, Float longitude, Float latitude, String city, String state, String promoDescription, float avarageScore,
			int numberOfVotes, Set<RACSPricelistItem> pricelist, RACSAdmin admin) {
		super(name, longitude, latitude, city, state, promoDescription, avarageScore, numberOfVotes);
		this.pricelist = pricelist;
		this.admin = admin;
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
