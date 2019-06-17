package com.FlightsReservations.domain.dto;

import java.util.HashSet;
import java.util.Set;

import com.FlightsReservations.domain.RACS;
import com.FlightsReservations.domain.RACSAdmin;
import com.FlightsReservations.domain.RACSBranchOffice;
import com.FlightsReservations.domain.RACSPricelistItem;

public class RACSDTO {
	private String name;
	private String city;
	private String state;
	private Float longitude;
	private Float latitude;
	private String promoDescription;
	private float averageScore;
	private int numberOfVotes;
	private Set<RACSBranchOfficeDTO> branchOffices = new HashSet<RACSBranchOfficeDTO>();
	private Set<RACSPricelistItemDTO> pricelist = new HashSet<RACSPricelistItemDTO>(); 
	private RACSAdmin admin;
	
	public Set<RACSPricelistItemDTO> getPricelist() {
		return pricelist;
	}
	public void setPricelist(Set<RACSPricelistItemDTO> pricelist) {
		this.pricelist = pricelist;
	}
	public Set<RACSBranchOfficeDTO> getBranchOffices() {
		return branchOffices;
	}
	public void setBranchOffices(Set<RACSBranchOfficeDTO> branchOffices) {
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
	
	
	public RACSDTO(Set<RACSPricelistItemDTO> pricelist, Set<CarDTO> cars, Set<RACSBranchOfficeDTO> branchOffices, RACSAdmin admin,
			String name, String city, String state, Float longitude, Float latitude, String promoDescription,
			float averageScore, int numberOfVotes) {
		super();
		this.pricelist = pricelist;
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
	public RACSDTO(RACS r) {
		this.averageScore = r.getAverageScore();
		Set<RACSBranchOffice> rbos = r.getBranchOffices();
		Set<RACSBranchOfficeDTO> rbodtos = new HashSet<RACSBranchOfficeDTO>();
		for (RACSBranchOffice rbo : rbos) {
			rbo.setRacs(r);
			RACSBranchOfficeDTO rbodto = new RACSBranchOfficeDTO((RACSBranchOffice) rbo);
			rbodtos.add(rbodto);
		}
		this.branchOffices = rbodtos;
		this.city = r.getCity();
		this.latitude = r.getLatitude();
		this.longitude = r.getLongitude();
		this.name = r.getName();
		this.numberOfVotes = r.getNumberOfVotes();
		Set<RACSPricelistItem> plis = r.getPricelist();
		Set<RACSPricelistItemDTO> rplidtos = new HashSet<RACSPricelistItemDTO>();
		for (RACSPricelistItem pli : plis) {
			RACSPricelistItemDTO rplidto = new RACSPricelistItemDTO(pli);
			rplidtos.add(rplidto);
		}
		this.pricelist = rplidtos;
		this.promoDescription = r.getPromoDescription();
		this.state = r.getState();
	}
	
}
