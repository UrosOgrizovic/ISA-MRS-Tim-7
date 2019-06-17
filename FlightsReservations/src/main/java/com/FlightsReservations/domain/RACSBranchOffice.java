package com.FlightsReservations.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;


@Entity
@DiscriminatorValue("RBO")
public class RACSBranchOffice extends BranchOffice {
	
	/* orphanRemoval = true - guarantees that when a car is removed from  
	 * the RACS's set it will also be removed from the database
	 * more: https://stackoverflow.com/a/5642615
	*/
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "racs_branch_office_id")
	private Set<Car> cars = new HashSet<Car>();	

	public Set<Car> getCars() {
		return cars;
	}
	public void setCars(Set<Car> cars) {
		this.cars.clear();
		if (cars != null) {
			this.cars.addAll(cars);
		}
	}
	
	public RACSBranchOffice(String name, Float longitude, Float latitude, Company company, Set<Car> cars) {
		super(name, longitude, latitude, company);
		this.cars = cars;
	}
	public RACSBranchOffice() {
		super();
	}
	@Override
	public String toString() {
		return "RACSBranchOffice [cars=" + cars + ", getId()=" + getId() + ", getName()=" + getName()
				+ ", getLongitude()=" + getLongitude() + ", getLatitude()=" + getLatitude() + ", getCompany()="
				+ getCompany() + "]";
	}
	
	
	
	
}
