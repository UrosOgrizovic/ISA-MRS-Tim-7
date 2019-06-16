package com.FlightsReservations.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.FlightsReservations.domain.dto.HotelBranchOfficeDTO;

@Entity
@DiscriminatorValue("HBO")
public class HotelBranchOffice extends BranchOffice {
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "hotel_branch_office_id")
	private Set<Room> roomConfiguration;
	
	public Set<Room> getRoomConfiguration()
	{
		return roomConfiguration;
	}

	public void setRoomConfiguration(Set<Room> roomConfiguration)
	{
		this.roomConfiguration = roomConfiguration;
	}

	public HotelBranchOffice(String name, Float longitude, Float latitude, Company company, Set<Room> roomConfiguration) {
		super(name, longitude, latitude, company);
		this.roomConfiguration = roomConfiguration;
	}

	public HotelBranchOffice() {
		super();
	}

	public HotelBranchOffice(HotelBranchOfficeDTO dto) {
		this.setCompany(dto.getCompany());
		this.setLatitude(dto.getLatitude());
		this.setLongitude(dto.getLongitude());
		this.setName(dto.getName());
		this.setRoomConfiguration(dto.getRooms());
	}
	
	@Override
	public String toString() {
		return "HotelBranchOffice [roomConfiguration=" + roomConfiguration + ", getId()=" + getId()
				+ ", getName()=" + getName() + ", getLongitude()=" + getLongitude() + ", getLatitude()=" + getLatitude()
				+ ", getCompany()=" + getCompany() + "]";
	}
	
	
}