package com.FlightsReservations.domain.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.FlightsReservations.domain.User;

public class RegistrationUserDTO extends UserDTO {
	@NotBlank
	private String password;
	@NotNull
	private boolean enabled;
	
	public RegistrationUserDTO() {
		super();
	}
	
	public RegistrationUserDTO(String firstName, String lastName, String email, String phone, String address,
			String password, boolean enabled) {
		super(firstName, lastName, email, phone, address, enabled);
		this.password = password;
	}
	
	public RegistrationUserDTO(User user) {
		this(
			user.getFirstName(), 
			user.getLastName(),
			user.getEmail(),
			user.getPhone(),
			user.getAddress(),
			user.getPassword(),
			user.isEnabled()
			);
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
