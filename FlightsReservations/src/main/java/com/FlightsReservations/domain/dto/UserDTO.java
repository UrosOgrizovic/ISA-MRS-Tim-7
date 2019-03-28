package com.FlightsReservations.domain.dto;

import javax.validation.constraints.NotBlank;

import com.FlightsReservations.domain.User;

public class UserDTO {
	@NotBlank
	private String firstName;
	@NotBlank
	private String lastName;
	@NotBlank
	private String email;
	@NotBlank
	private String phone;
	@NotBlank
	private String address;

	public UserDTO() {
		super();
	}

	public UserDTO(String firstName, String lastName, String email, String phone, String address) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.address = address;
	}
	
	public UserDTO(User user) {
		this(
			user.getFirstName(), 
			user.getLastName(),
			user.getEmail(),
			user.getPhone(),
			user.getAddress()
			);
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
}
