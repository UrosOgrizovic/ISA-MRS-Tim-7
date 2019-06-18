package com.FlightsReservations.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
<<<<<<< HEAD
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;


@Entity
@DiscriminatorValue("RBO")
public class RACSBranchOffice extends BranchOffice {
=======
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class RACSBranchOffice {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String name;
	
	@Column(nullable = false)
	private Float longitude;

	@Column(nullable = false)
	private Float latitude;
	
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private RACS racs;

>>>>>>> master
	
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
	
<<<<<<< HEAD
	public RACSBranchOffice(String name, Float longitude, Float latitude, Company company, Set<Car> cars) {
		super(name, longitude, latitude, company);
		this.cars = cars;
=======
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
	public RACS getRacs() {
		return racs;
	}
	public void setRacs(RACS racs) {
		this.racs = racs;
>>>>>>> master
	}
	public RACSBranchOffice() {
		super();
	}
<<<<<<< HEAD
	@Override
	public String toString() {
		return "RACSBranchOffice [cars=" + cars + ", getId()=" + getId() + ", getName()=" + getName()
				+ ", getLongitude()=" + getLongitude() + ", getLatitude()=" + getLatitude() + ", getCompany()="
				+ getCompany() + "]";
=======
	public RACSBranchOffice(Long id, String name, Float longitude, Float latitude, RACS racs, Set<Car> cars) {
		super();
		this.id = id;
		this.name = name;
		this.longitude = longitude;
		this.latitude = latitude;
		this.racs = racs;
		this.cars = cars;
	}
	@Override
	public String toString() {
		return "RACSBranchOffice [id=" + id + ", name=" + name + ", longitude=" + longitude + ", latitude=" + latitude
				+ ", racs=" + racs + ", cars=" + cars + "]";
>>>>>>> master
	}
	
	
	
	
<<<<<<< HEAD
}
=======
}
>>>>>>> master
