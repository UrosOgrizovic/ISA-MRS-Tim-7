package com.FlightsReservations.domain.dto;

import javax.validation.constraints.NotBlank;

import com.FlightsReservations.domain.AirlineAdmin;

public class AirlineAdminDTO {
	@NotBlank
	private String firstName;

	@NotBlank
	private String lastName;

	@NotBlank
	private String email; // unique

	// @NotBlank //TODO: make a mandatory filed if necessary
	private String phone;

	// @NotBlank //TODO: make a mandatory filed if necessary
	private String address;

	@NotBlank
	private String password;

	@NotBlank
	private String airlineName;

	public AirlineAdminDTO(AirlineAdmin a) {
		this.firstName = a.getFirstName();
		this.lastName = a.getLastName();
		this.email = a.getEmail();
		this.password = a.getPassword();
		this.phone = a.getPhone();
		this.address = a.getAddress();
		this.airlineName = a.getAirline().getName();
	}

	public AirlineAdminDTO() {
		super();
	}

	public AirlineAdminDTO(String firstName, String lastName, String email, String phone, String address,
			String password) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAirlineName() {
		return airlineName;
	}

	public void setAirlineName(String airlineName) {
		this.airlineName = airlineName;
	}

}