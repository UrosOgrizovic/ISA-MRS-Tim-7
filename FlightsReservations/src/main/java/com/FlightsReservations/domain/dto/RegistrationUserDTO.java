package com.FlightsReservations.domain.dto;

import javax.validation.constraints.NotBlank;

public class RegistrationUserDTO extends UserDTO {
	@NotBlank
	private String password;
	
	public RegistrationUserDTO() {
		super();
	}
	
	public RegistrationUserDTO(String firstName, String lastName, String email, String phone, String address,
			String password) {
		super(firstName, lastName, email, phone, address);
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
