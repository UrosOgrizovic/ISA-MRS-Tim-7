package com.FlightsReservations.domain.dto;

import java.util.ArrayList;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.security.core.GrantedAuthority;

import com.FlightsReservations.domain.Authority;
import com.FlightsReservations.domain.User;

public class RegistrationUserDTO extends AbstractUserDTO {
	@NotBlank
	private String password;
	@NotNull
	private boolean enabled;
	@NotNull
	private ArrayList<String> authorities;
	
	public ArrayList<String> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(ArrayList<String> authorities) {
		this.authorities = authorities;
	}

	public RegistrationUserDTO() {
		super();
	}
	
	public RegistrationUserDTO(String firstName, String lastName, String email, String phone, String address,
			String password, boolean enabled, ArrayList<String> authorities) {
		super(firstName, lastName, email, phone, address, enabled);
		this.password = password;
		this.authorities = authorities;
	}
	
	public RegistrationUserDTO(User user) {
		ArrayList<String> authorities = new ArrayList<String>();
		for (GrantedAuthority a : user.getAuthorities()) {
			authorities.add( ((Authority) a).getName());
		}
		this.setFirstName(user.getFirstName());
		this.setLastName(user.getLastName());
		this.setEmail(user.getEmail());
		this.setPhone(user.getPhone());
		this.setAddress(user.getAddress());
		this.setPassword(user.getPassword());
		this.setEnabled(user.isEnabled());
		this.setAuthorities(authorities);
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
